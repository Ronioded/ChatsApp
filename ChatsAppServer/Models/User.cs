using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace ChatsApp.Models
{
    public class User
    {
        [Key]
        public string UserName { get; set; }
        public string NickName { get; set; }
        public string Password { get; set; }
        public List<Chat> Chats { get; set; }
        public string AndroidToken { get; set; }

        [JsonConstructor]
        public User(string UserName, string NickName, string Password)
        {
            this.UserName = UserName;
            this.NickName = NickName;
            this.Password = Password;
            this.Chats = new List<Chat>();
            this.AndroidToken = "";
        }

        public User(string UserName, string NickName, string Password, List<Chat> list1)
        {
            this.UserName = UserName;
            this.NickName = NickName;
            this.Password = Password;
            this.Chats = list1;
            this.AndroidToken = "";
        }
    }
}