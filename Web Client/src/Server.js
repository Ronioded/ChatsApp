const serverAPIPort = "5119";
const Url = "http://localhost:" + serverAPIPort + "/api";
const Server = "localhost:" + serverAPIPort;

const fetchAllContacts = async (connectedUser, setContacts, setConnectedUser) => {
  const res = await fetch(`${Url}/Contacts/?username=${connectedUser.UserName}`, { credentials: 'include' });
  const contacts = await res.json();
  setContacts(contacts);
  setConnectedUser(connectedUser);
}

const fetchMessagesWithContact = async (contactUsername, connectedUsername) => {
  const res = await fetch(`${Url}/Messages/${contactUsername}/?username=${connectedUsername}`, { credentials: 'include' });
  if (res.status != 200) {
    return null;
  }
  return await res.json();
};

export default { Url, Server, fetchAllContacts, fetchMessagesWithContact }