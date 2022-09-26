using ChatsApp.Models;
using ChatsApp.Data;
using Microsoft.EntityFrameworkCore;

namespace ChatsApp.Services
{
    // MessagesService class implements the methods that handle the access To the messages in the DB. 
    public class MessagesService : IMessagesService
    {
        // Context in order To get access To the DB.
        private readonly ChatsAppContext _context;
        private readonly IChatsService chatsService;

        public MessagesService(ChatsAppContext context, IChatsService chatsService)
        {
            _context = context;
            this.chatsService = chatsService;
        }

        // The method return all the messages in the chat with the chatId.
        public async Task<List<Message>> GetMessagesOfChat(int chatId)
        {
            return await _context.Message.Where(m => m.ChatID == chatId).ToListAsync();
        }

        // The method return all the messages in the chat of the connected user and the contact.
        public async Task<List<Message>> GetAllMessagesWithUser(string connectedUserName, string contactUserName)
        {
            if ((connectedUserName == null) || (contactUserName == null))
            {
                return null;
            }

            Chat c = chatsService.GetChat(connectedUserName, contactUserName);
            if (c == null)
            {
                return null;
            }
            List<Message> chatMessages = await GetMessagesOfChat(c.Id);
            foreach (var message in chatMessages)
            {
                message.Chat = null;
                message.ChatID = 0;
            }
            return chatMessages;
        }

        // The method add a message to the DB.
        public async Task<Message> AddMessage(string connectedUserName, string contactUserName, string content, bool sent)
        {
            try
            {
                Chat chat = chatsService.GetChat(connectedUserName, contactUserName);
                if (chat == null)
                {
                    return null;
                }
                var time = DateTime.Now.ToString("o");
                Message message = new(time, content, chat, sent);
                _context.Add(message);
                chat.Contact.LastMessageTimeStamp = time;
                chat.Contact.LastMessageContent = content;
                _context.Update(chat.Contact);
                await _context.SaveChangesAsync();
                return message;
            } catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
                return null;
            }
        }
    }
}