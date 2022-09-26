using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ChatsApp.Models
{
    public class Chat
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }
        public string UserId { get; set; }
        public User User { get; set; }
        public int ContactId { get; set; }
        public Contact Contact { get; set; }
        public List<Message> Messages { get; set; }
        public Chat(User user, Contact contact)
        {
            this.User = user;
            this.Contact = contact;
            this.UserId = user.UserName;
            this.ContactId = contact.Id;
            this.Messages = new List<Message>();
        }
        public Chat()
        {
            this.User = null;
            this.Contact = null;
            this.UserId = "";
            this.ContactId = 0;
            this.Messages = new List<Message>();
        }
    }
}
