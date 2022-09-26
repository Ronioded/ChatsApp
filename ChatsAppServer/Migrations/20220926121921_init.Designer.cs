﻿// <auto-generated />
using ChatsApp.Data;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

#nullable disable

namespace ChatsApp.Migrations
{
    [DbContext(typeof(ChatsAppContext))]
    [Migration("20220926121921_init")]
    partial class init
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder.HasAnnotation("ProductVersion", "6.0.8");

            modelBuilder.Entity("ChatsApp.Models.Chat", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("INTEGER");

                    b.Property<int>("ContactId")
                        .HasColumnType("INTEGER");

                    b.Property<string>("UserId")
                        .HasColumnType("TEXT");

                    b.HasKey("Id");

                    b.HasIndex("ContactId");

                    b.HasIndex("UserId");

                    b.ToTable("Chat");
                });

            modelBuilder.Entity("ChatsApp.Models.Contact", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("INTEGER");

                    b.Property<string>("LastMessageContent")
                        .HasColumnType("TEXT");

                    b.Property<string>("LastMessageTimeStamp")
                        .HasColumnType("TEXT");

                    b.Property<string>("NickName")
                        .HasColumnType("TEXT");

                    b.Property<string>("UserName")
                        .HasColumnType("TEXT");

                    b.HasKey("Id");

                    b.ToTable("Contact");
                });

            modelBuilder.Entity("ChatsApp.Models.Message", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("INTEGER");

                    b.Property<int>("ChatID")
                        .HasColumnType("INTEGER");

                    b.Property<string>("Content")
                        .IsRequired()
                        .HasColumnType("TEXT");

                    b.Property<bool>("Sent")
                        .HasColumnType("INTEGER");

                    b.Property<string>("TimeStamp")
                        .HasColumnType("TEXT");

                    b.HasKey("Id");

                    b.HasIndex("ChatID");

                    b.ToTable("Message");
                });

            modelBuilder.Entity("ChatsApp.Models.User", b =>
                {
                    b.Property<string>("UserName")
                        .HasColumnType("TEXT");

                    b.Property<string>("AndroidToken")
                        .HasColumnType("TEXT");

                    b.Property<string>("NickName")
                        .HasColumnType("TEXT");

                    b.Property<string>("Password")
                        .HasColumnType("TEXT");

                    b.HasKey("UserName");

                    b.ToTable("User");
                });

            modelBuilder.Entity("ChatsApp.Models.Chat", b =>
                {
                    b.HasOne("ChatsApp.Models.Contact", "Contact")
                        .WithMany()
                        .HasForeignKey("ContactId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("ChatsApp.Models.User", "User")
                        .WithMany("Chats")
                        .HasForeignKey("UserId");

                    b.Navigation("Contact");

                    b.Navigation("User");
                });

            modelBuilder.Entity("ChatsApp.Models.Message", b =>
                {
                    b.HasOne("ChatsApp.Models.Chat", "Chat")
                        .WithMany("Messages")
                        .HasForeignKey("ChatID")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Chat");
                });

            modelBuilder.Entity("ChatsApp.Models.Chat", b =>
                {
                    b.Navigation("Messages");
                });

            modelBuilder.Entity("ChatsApp.Models.User", b =>
                {
                    b.Navigation("Chats");
                });
#pragma warning restore 612, 618
        }
    }
}