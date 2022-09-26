using ChatsApp.Models;

namespace ChatsApp.Services
{
    // IChatsService interface define the methods that handle the access To the chats in the DB. 
    public interface IChatsService
    {
        public Chat GetChat(string connectedUserName, string contactUserName);
    }
}
