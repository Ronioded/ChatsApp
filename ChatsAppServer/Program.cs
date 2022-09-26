using Microsoft.EntityFrameworkCore;
using ChatsApp.Data;
using ChatsApp.Services;
using ChatsApp.Hubs;
using ChatsApp.FireBase;
using Microsoft.AspNetCore.Authentication.Cookies;
using System.Text.Json.Serialization;

var builder = WebApplication.CreateBuilder(args);

// Add services To the container.
builder.Services.AddDbContext<ChatsAppContext>(options =>
   options.UseSqlite("Filename=ChatsAppDB.db"));
builder.Services.AddControllersWithViews();
builder.Services.AddDistributedMemoryCache();
builder.Services.AddAntiforgery();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
builder.Services.AddControllers().AddJsonOptions(x =>
   x.JsonSerializerOptions.ReferenceHandler = ReferenceHandler.Preserve);
builder.Services.AddScoped<IUsersService, UsersService>();
builder.Services.AddScoped<IMessagesService, MessagesService>();
builder.Services.AddScoped<IContactService, ContactService>();
builder.Services.AddScoped<IChatsService, ChatsService>();
builder.Services.AddScoped<IFireBaseService, FireBaseService>();
builder.Services.AddScoped<MessagesHub>();
builder.Services.AddSignalR();
builder.Services.AddSession(options =>
{
    options.Cookie.IsEssential = true;
    options.Cookie.HttpOnly = false;
    options.Cookie.SameSite = SameSiteMode.None;
    options.IdleTimeout = TimeSpan.FromMinutes(30);
});
builder.Services.AddCors(options =>
{
    // options.AddDefaultPolicy(policy => policy.AllowAnyOrigin());

    options.AddPolicy("cors_policy",
    builder =>
    {
        builder.WithOrigins("http://localhost:3002").AllowAnyMethod().AllowAnyHeader().AllowCredentials();
        builder.WithOrigins("http://localhost:3000").AllowAnyMethod().AllowAnyHeader().AllowCredentials();
    });

    options.AddPolicy("ClientPermission", policy =>
    {
        policy.AllowAnyHeader()
            .AllowAnyMethod()
            .WithOrigins("http://localhost:3000")
            .WithOrigins("http://localhost:3001")
            .AllowCredentials();
    });
});
builder.Services.AddAuthentication(options =>
{
    options.DefaultScheme = CookieAuthenticationDefaults.AuthenticationScheme;
}).AddCookie(options =>
{
    options.LoginPath = "/api/Users/login/";
});
var app = builder.Build();

// Configure the HTTP request pipeline.
app.UseSwagger();
if (app.Environment.IsDevelopment())
{
    app.UseSwaggerUI();
}
if (!app.Environment.IsDevelopment())
{
    app.UseSwaggerUI(options =>
    {
        options.SwaggerEndpoint("/swagger/v1/swagger.json", "v1");
        options.RoutePrefix = string.Empty;
    });
}
app.UseCors("Allow All");
app.UseCors("ClientPermission");
app.UseCookiePolicy(
         new CookiePolicyOptions
         {
             Secure = CookieSecurePolicy.Always
         });
app.UseRouting();
app.UseCors("cors_policy");
app.UseSession();
app.UseHttpsRedirection();
app.UseAuthentication();
app.UseAuthorization();
app.MapControllers();
app.UseEndpoints(endpoints =>
{
    endpoints.MapControllers();
    endpoints.MapHub<MessagesHub>("/hub");
});
app.Run();