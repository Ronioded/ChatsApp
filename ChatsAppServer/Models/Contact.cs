using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ChatsApp.Models
{
    public class Contact
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }
        public string UserName { get; set; }
        public string NickName { get; set; }
        public string LastMessageContent { get; set; }
        public string LastMessageTimeStamp { get; set; }


        public Contact(string UserName, string NickName)
        {
            this.UserName = UserName;
            this.NickName = NickName;
            this.LastMessageContent = null;
            this.LastMessageTimeStamp = null;
        }
    }
}
