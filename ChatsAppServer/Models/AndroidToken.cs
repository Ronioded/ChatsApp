namespace ChatsApp.Models
{
    public class AndroidToken
    {
        public String Token { get; set; }
        public String UserName { get; set; }

        public AndroidToken(String Token, String UserName)
        {
            this.Token = Token;
            this.UserName = UserName;
        }
    }
}
