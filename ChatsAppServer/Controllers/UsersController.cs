using ChatsApp.Services;
using ChatsApp.Models;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;

namespace ChatsApp.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class UsersController : Controller
    {
        private readonly IUsersService UsersService;

        public UsersController(IUsersService UsersService)
        {
            this.UsersService = UsersService;
        }

        // POST: api/Users/login - the method handle the login validation of the user.
        [HttpPost("login")]
        public async Task<IActionResult> Login([Bind("UserName,Password")] User user)
        {
            User u = await UsersService.LoginValidation(user.UserName, user.Password);
            if (u != null)
            {
                return Ok(JsonConvert.SerializeObject(new { NickName = u.NickName }));
            }
            return BadRequest();
        }

        // POST:  api/Users/register - the method handle the registration of the user.
        [HttpPost("register")]
        public async Task<IActionResult> Register([Bind("Username,Nickname,Password")] User user)
        {
            if (await UsersService.CreateUser(user))
            {
                return Ok();
            }
            return BadRequest();
        }

        // POST: api/Users/androidToken
        [HttpPost("androidToken")]
        public async Task<IActionResult> AndroidTokenUpdate([Bind("Token,UserName")] AndroidToken Token)
        {
            if (await UsersService.AndroidTokenUpdate(Token))
            {
                return Ok();
            }
            return BadRequest();
        }
    }
}
