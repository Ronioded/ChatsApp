using ChatsApp.Models;

namespace ChatsApp.Services
{
    // IMessagesService interface define the methods that handle the access To the messages in the DB. 
    public interface IMessagesService
    {
        public Task<List<Message>> GetMessagesOfChat(int chatId);
        public Task<List<Message>> GetAllMessagesWithUser(string connectedUserName, string contactUserName);
        public Task<Message> AddMessage(string connectedUserName, string contactUserName, string content, bool sent);
    }
}
