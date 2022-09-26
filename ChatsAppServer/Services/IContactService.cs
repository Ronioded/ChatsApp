using ChatsApp.Models;

namespace ChatsApp.Services
{
    // IContactService interface define the methods that handle the access To the contacts in the DB. 
    public interface IContactService
    {
        public Contact GetContact(string connectedUserName, string contactUserName);
        public List<Contact> GetAllContacts(string connectedUserName);
        public Task<bool> AddContact(User connectedUser, Contact contact);
    }
}
