using ChatsApp.Models;
using ChatsApp.Data;
using Microsoft.EntityFrameworkCore;
using System.Globalization;

namespace ChatsApp.Services
{
    // ContactService class implements the methods that handle the access To the contacts in the DB. 
    public class ContactService : IContactService
    {
        private readonly ChatsAppContext _context;
        private readonly IChatsService chatsService;

        public ContactService(ChatsAppContext context, IChatsService chatsService)
        {
            _context = context;
            this.chatsService = chatsService;
        }

        // The method get the contact with the contactUserName of the connected user.
        public Contact GetContact(string connectedUserName, string contactUserName)
        {
           Chat c = chatsService.GetChat(connectedUserName, contactUserName);
           if (c == null)
           {
               return null;
           }
           return c.Contact;
        }

        // The method return all the contacts of the user with connectedUserName.
        public List<Contact> GetAllContacts(string connectedUserName)
        {
            var chats = from chat in _context.Chat.Include(c => c.Contact)
                        where chat.UserId == connectedUserName
                        select chat;

            List<Contact> contacts = new();

            foreach (var c in chats)
            {
                contacts.Add(c.Contact);
            }

            // Sort the list of the contacts by the last message time stamp.
            contacts.Sort(delegate (Contact x, Contact y)
            {
                if ((x.LastMessageTimeStamp == null) || (x.LastMessageContent == null))
                {
                    return -1;
                }
                if ((y.LastMessageTimeStamp == null) || (y.LastMessageContent == null))
                {
                    return 1;
                }
                try
                {
                    DateTime xTime = DateTime.ParseExact(x.LastMessageTimeStamp, "o", CultureInfo.InvariantCulture);
                    DateTime yTime = DateTime.ParseExact(y.LastMessageTimeStamp, "o", CultureInfo.InvariantCulture);
                    if (xTime < yTime)
                    {
                        return 1;
                    }
                    else
                    {
                        return -1;
                    }
                } catch
                {
                    return -1;
                }
            });

            return contacts;
        }

        // The method add the contact of the connectedUser To the DB.
        public async Task<bool> AddContact(User connectedUser, Contact contact)
        {
            try
            {
                _context.Add(contact);
                Chat chat = new(connectedUser, contact);
                _context.Add(chat);
                await _context.SaveChangesAsync();
                return true;
            } catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return false;
            }
        }
    }
}
