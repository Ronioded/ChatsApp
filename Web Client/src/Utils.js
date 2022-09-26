let lastContact = '';
let thisContact = '';

// Use dayjs in order to handle date and time representation.
const dayjs = require('dayjs')
const timeFormat = 'HH:mm'
const dateFormat = 'DD.MM.YY';

// The function return the current date.
function getDate() {
    return dayjs().format(); 
}

// The function decides what time we will display of the last message in the chatsList.
function timeDisplay(time) {
    if ((time == "") || (time == null)) {
        return "";
    }
    let currentTime = dayjs();
    let messageTimeStamp = dayjs(time);

    // If the last message was sent yesterday, display yesterday.
    if (messageTimeStamp.add(1, 'day').isSame(currentTime, 'day')) {
        return "yesterday";
    }

    // If the last message is not in this year, month, day - show the date.
    if (messageTimeStamp.isBefore(currentTime, 'year') || messageTimeStamp.isBefore(currentTime, 'month') || messageTimeStamp.isBefore(currentTime, 'day')) {
        return messageTimeStamp.format(dateFormat);
    }

    // If the last message is not in this hour - show the hour.
    if (messageTimeStamp.isBefore(currentTime, 'hour')) {
        return messageTimeStamp.format(timeFormat);
    }

    for (let i = 0; i < 10; i++) {
        // If the last message was sent i min ago, show i min ago.
        if (messageTimeStamp.add(i, 'minute').isSame(currentTime, 'minute')) {
            return i + " min ago";
        }
    }

    // Else, show the hour.
    return dayjs(messageTimeStamp).format(timeFormat);
}

function checkValidPassword(password) {
    var hasNumbers = false;
    var hasLetters = false;

    for (var i = 0; i < password.length; i++) {
        if (Number.isInteger(parseInt(password.charAt(i)))) {
            hasNumbers = true;
        }
        if (/^[a-zA-Z]+$/.test(password.charAt(i))) {
            hasLetters = true;
        }
    }
    return (hasLetters && hasNumbers);
}

const displayLastMessage = (content) => {
    if (content == null){
        return "";
    }
    if (content.length > 30){
        return content.slice(0,30) + "..."
    }
    return content;
}

export default {dayjs, timeFormat, lastContact, thisContact, checkValidPassword, timeDisplay, getDate, displayLastMessage};