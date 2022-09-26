using ChatsApp.Models;
using ChatsApp.Data;
using Microsoft.EntityFrameworkCore;

namespace ChatsApp.Services
{
    // UsersService class implements the methods that handle the access To the users in the DB. 
    public class UsersService : IUsersService
    {
        // Context in order To get access To the DB.
        private readonly ChatsAppContext _context;

        public UsersService(ChatsAppContext context)
        {
            _context = context;
        }

        // The method return the user with the id given.
        public async Task<User> GetByUserName(string connectedUserName)
        {
            if ((connectedUserName == null) || (_context.User == null))
            {
                return null;
            }
            var user = await _context.User.FirstOrDefaultAsync(u => u.UserName == connectedUserName);
            return user;
        }

        // The method add new User to the DB.
        public async Task<bool> CreateUser(User user)
        {
            try
            {
                User u = await GetByUserName(user.UserName);
                if (u != null)
                {
                    return false;
                }
                _context.Add(user);
                await _context.SaveChangesAsync();
                return true;
            } catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return false;
            }
        }

        // The method validate the user details on login.
        public async Task<User> LoginValidation(string UserName, string Password)
        {
            if (_context.IsEmpty())
            {
                return null;
            }

            User user = await GetByUserName(UserName);
            if ((user != null) && (user.Password == Password))
            {
                return user;
            }
            return null;
        }

        public async Task<bool> AndroidTokenUpdate(AndroidToken Token)
        {
            try
            {
                User u = await GetByUserName(Token.UserName);
                if (u == null)
                {
                    return false;
                }
                u.AndroidToken = Token.Token;
                _context.Update(u);
                await _context.SaveChangesAsync();
                return true;
            } catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
                return false;
            }
        }
    }
}