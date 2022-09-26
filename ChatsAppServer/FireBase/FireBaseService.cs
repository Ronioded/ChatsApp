using FirebaseAdmin;
using FirebaseAdmin.Messaging;
using Google.Apis.Auth.OAuth2;

namespace ChatsApp.FireBase
{
    public class FireBaseService : IFireBaseService
    {
        public FireBaseService()
        {
            if (FirebaseApp.DefaultInstance == null)
            {
                FirebaseApp.Create(new AppOptions()
                {
                    Credential = GoogleCredential.FromFile("./FireBase/privateKey.json")
                });
            }
        }
        public void SendMessageToClientByToken(string Token, string FromUserName, string ToUserName, string Content, int MessageId)
        {
            var message = new Message()
            {
                Data = new Dictionary<string, string>()
                {
                    { "connectedUserName", ToUserName },
                    { "contactUserName", FromUserName },
                    { "addContact", "false" },
                    { "messageId", MessageId.ToString() }

                },
                Token = Token,
                Notification = new Notification()
                {
                    Title = "New Message From " + FromUserName,
                    Body = Content
                }
            };
            FirebaseMessaging.DefaultInstance.SendAsync(message);
        }

        public void SendEventToAddContactByToken(string Token, string FromUserName, string ToUserName)
        {
            var message = new Message()
            {
                Data = new Dictionary<string, string>()
                {
                    { "connectedUserName", ToUserName },
                    { "contactUserName", FromUserName },
                    { "addContact", "true" }
                },
                Token = Token,
                Notification = new Notification()
                {
                    Title = "",
                    Body = ""
                }
            };
            FirebaseMessaging.DefaultInstance.SendAsync(message);
        }
    }
}
