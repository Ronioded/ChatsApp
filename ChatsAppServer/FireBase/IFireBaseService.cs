namespace ChatsApp.FireBase
{
    public interface IFireBaseService
    {
        public void SendMessageToClientByToken(string Token, string FromUserName, string ToUserName, string Content, int ChatId);
        public void SendEventToAddContactByToken(string Token, string FromUserName, string ToUserName);
    }
}
