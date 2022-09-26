using Microsoft.AspNetCore.Mvc;
using ChatsApp.Models;
using ChatsApp.Services;
using ChatsApp.Hubs;
using ChatsApp.FireBase;
using Microsoft.AspNetCore.SignalR;
using Newtonsoft.Json;

namespace ChatsApp.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class MessagesController : Controller
    {
        private readonly IUsersService UsersService;
        private readonly IMessagesService MessagesService;
        private readonly IHubContext<MessagesHub> context;
        private readonly IFireBaseService fireBaseService;

        public MessagesController(IUsersService UsersService, IMessagesService MessagesService, IHubContext<MessagesHub> context, IFireBaseService fireBaseService)
        {
            this.UsersService = UsersService;
            this.MessagesService = MessagesService;
            this.context = context;
            this.fireBaseService = fireBaseService;
        }

        // GET:api/Messages/:contactUserName - the request return all the messages of the user with username and the contact with contactUserName.
        [HttpGet("{contactUserName}")]
        public async Task<IActionResult> Index(string contactUserName, string username)
        {
            var messages = await MessagesService.GetAllMessagesWithUser(username, contactUserName);
            if (messages == null)
            {
                return NotFound();
            }
            return Ok(JsonConvert.SerializeObject(messages));
        }

        // POST: api/Messages/:contactUserName  - the request add a message to the chat of the user with username and the contact with contactUserName.
        [HttpPost("{contactUserName}")]
        public async Task<IActionResult> AddMessage(string contactUserName, [Bind("Content")] Message message, string username)
        {
            User connectedUser = await UsersService.GetByUserName(username);
            User contact = await UsersService.GetByUserName(contactUserName);
            if ((connectedUser == null) || (contact == null))
            {
                return NotFound();
            }
            Message m1 = await MessagesService.AddMessage(username, contactUserName, message.Content, true);
            if (m1 == null)
            {
                return NotFound();
            }
            Message m2 = await MessagesService.AddMessage(contactUserName, username, message.Content, false);
            if (m2 == null)
            {
                return NotFound();
            }
            if (contact.AndroidToken == "")
            {   
                await context.Clients.Group(contactUserName).SendAsync("ReceiveMessage", username, m2);
            }
            else
            {
                fireBaseService.SendMessageToClientByToken(contact.AndroidToken, username, contactUserName, message.Content, m2.Id);
            }
            return StatusCode(201);
        }
    }
}

