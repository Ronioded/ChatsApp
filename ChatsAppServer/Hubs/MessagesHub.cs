using Microsoft.AspNetCore.SignalR;

namespace ChatsApp.Hubs
{
    // Handle the signal R service in the server.
    public class MessagesHub : Hub
    {
        // The method handle the event of the registration To the HUB. The user register To the a group with the name username.
        public async Task RegisterToHub(string username)
        {
            await Groups.AddToGroupAsync(Context.ConnectionId, username);
        }
    }
}