using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace ChatsApp.Models
{
    public class Message
    {
        [Key]
        public int Id { get; set; }
        public string TimeStamp { get; set; }
        [Required]
        public string Content { get; set; }
        public bool Sent { get; set; }
        public int ChatID { get; set; } 
        public Chat Chat { get; set; }

        [JsonConstructor]
        public Message(String content)
        {
            this.TimeStamp = "";
            this.Content = content;
            this.Sent = true;
            this.Chat = null;
        }

        public Message(string TimeStamp, string Content, Chat Chat, bool Sent)
        {
            this.TimeStamp = TimeStamp;
            this.Content = Content;
            this.Sent = true;
            this.Sent = Sent;
            this.ChatID = Chat.Id;
            this.Chat = Chat;
        }
    } 
}
