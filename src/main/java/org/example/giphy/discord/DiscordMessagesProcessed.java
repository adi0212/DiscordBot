package org.example.giphy.discord;

public class DiscordMessagesProcessed {

    public String[] messageSplit (String discordMessage){
        String[] message = new String[2];
        String[] processedMessage = discordMessage.split(" ");
        message[0] = processedMessage [0];
        for (int i = 1; i <= processedMessage.length-1; i++){
            message[1] = message[1] + " " + processedMessage[i];
        }
        return message;
    }
}
