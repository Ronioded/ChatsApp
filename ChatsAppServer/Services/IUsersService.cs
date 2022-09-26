using ChatsApp.Models;

namespace ChatsApp.Services
{
    // IUsersService interface define the methods that handle the access To the users in the DB. 
    public interface IUsersService
    {
        public Task<User> GetByUserName(string connectedUserName);
        public Task<bool> CreateUser(User user);        
        public Task<User> LoginValidation(string UserName, string Password);
        public Task<bool> AndroidTokenUpdate(AndroidToken Token);
    }
}
