using ChatsApp.Models;
using ChatsApp.Data;
using Microsoft.EntityFrameworkCore;

namespace ChatsApp.Services
{
    // ChatsService class implements the methods that handle the access To the chats in the DB. 
    public class ChatsService : IChatsService
    {
        private readonly ChatsAppContext _context;

        public ChatsService(ChatsAppContext context)
        {
            _context = context;
        }

        // The method return the chat of username with contact.
        public Chat GetChat(string connectedUserName, string contactUserName)
        {
            var query = from chat in _context.Chat.Include(c => c.Contact).Include(c => c.User).Include(c => c.Messages)
                        where chat.UserId == connectedUserName && chat.Contact.UserName == contactUserName
                        select chat;
            Chat c = null;
            foreach (var result in query)
            {
                c = result;
            }
            return c;
        }
    }
}
