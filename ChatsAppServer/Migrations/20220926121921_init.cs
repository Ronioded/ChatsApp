using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace ChatsApp.Migrations
{
    public partial class init : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Chat_Contact_ContactId",
                table: "Chat");

            migrationBuilder.AlterColumn<int>(
                name: "Id",
                table: "Contact",
                type: "INTEGER",
                nullable: false,
                oldClrType: typeof(string),
                oldType: "TEXT")
                .Annotation("Sqlite:Autoincrement", true);

            migrationBuilder.AlterColumn<int>(
                name: "ContactId",
                table: "Chat",
                type: "INTEGER",
                nullable: false,
                defaultValue: 0,
                oldClrType: typeof(string),
                oldType: "TEXT",
                oldNullable: true);

            migrationBuilder.AddForeignKey(
                name: "FK_Chat_Contact_ContactId",
                table: "Chat",
                column: "ContactId",
                principalTable: "Contact",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Chat_Contact_ContactId",
                table: "Chat");

            migrationBuilder.AlterColumn<string>(
                name: "Id",
                table: "Contact",
                type: "TEXT",
                nullable: false,
                oldClrType: typeof(int),
                oldType: "INTEGER")
                .OldAnnotation("Sqlite:Autoincrement", true);

            migrationBuilder.AlterColumn<string>(
                name: "ContactId",
                table: "Chat",
                type: "TEXT",
                nullable: true,
                oldClrType: typeof(int),
                oldType: "INTEGER");

            migrationBuilder.AddForeignKey(
                name: "FK_Chat_Contact_ContactId",
                table: "Chat",
                column: "ContactId",
                principalTable: "Contact",
                principalColumn: "Id");
        }
    }
}
