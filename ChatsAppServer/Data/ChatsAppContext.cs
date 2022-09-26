using Microsoft.EntityFrameworkCore;
using ChatsApp.Models;

namespace ChatsApp.Data
{
    // via this context there is an access the DB .
    public class ChatsAppContext : DbContext
    {
        public ChatsAppContext (DbContextOptions<ChatsAppContext> options) : base(options)
        {
            Database.EnsureCreated();
        }

        public bool IsEmpty()
        {
            return !User.Any();
        }

        public DbSet<User> User { get; set; }
        public DbSet<Chat> Chat { get; set; }
        public DbSet<Contact> Contact { get; set; }
        public DbSet<Message> Message { get; set; }
    }
}
