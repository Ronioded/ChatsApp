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
    public class ContactsController : Controller
    {
        private readonly IUsersService UsersService;
        private readonly IContactService ContactService;
        private readonly IHubContext<MessagesHub> hubContext;
        private readonly IFireBaseService fireBaseService;

        public ContactsController(IUsersService UsersService, IContactService ContactService, IHubContext<MessagesHub> context, IFireBaseService fireBaseService)
        {
            this.UsersService = UsersService;
            this.ContactService = ContactService;
            this.hubContext = context;
            this.fireBaseService = fireBaseService;
        }

        [HttpGet]
        // GET: api/Contacts - the request get all the contacts of the user with username.
        public async Task<IActionResult> Index(string username)
        {
            User user = await UsersService.GetByUserName(username);
            if (user == null)
            {
                return BadRequest();
            }
            return Ok(JsonConvert.SerializeObject(ContactService.GetAllContacts(username)));
        }

        // POST: api/Contacts - the request create new contact.
        [HttpPost]
        public async Task<IActionResult> AddContact([Bind("UserName,NickName")] Contact contact, string username)
        {
            User connectedUser = await UsersService.GetByUserName(username);
            User contactUser = await UsersService.GetByUserName(contact.UserName);
            if ((connectedUser == null) || (contactUser == null))
            {
                return NotFound();
            }
            
            if (!(await ContactService.AddContact(connectedUser, contact)))
            {
                return NotFound();
            }
            Contact connectedUserContact = new(username, connectedUser.NickName);
            if (!(await ContactService.AddContact(contactUser, connectedUserContact)))
            {
                return NotFound();
            }
            if (connectedUser.AndroidToken == "")
            {
                await hubContext.Clients.Group(contact.UserName).SendAsync("AddContact", connectedUserContact);
            }
            else
            {
                fireBaseService.SendEventToAddContactByToken(connectedUser.AndroidToken, username, contact.UserName);
            }
            return StatusCode(201);
        }

        // GET: api/Contacts/:contactUserName - the request return the details about the contact with contactUserName of the user with username.
        [HttpGet("{contactUserName}")]
        public IActionResult GetContact(string contactUserName, string username)
        {
            Contact contact = ContactService.GetContact(username, contactUserName);
            if (contact == null)
            {
                return NotFound();
            }
            return Ok(JsonConvert.SerializeObject(contact));
        }
    }
}
