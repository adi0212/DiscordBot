package org.example;

import at.mukprojects.giphy4j.exception.GiphyException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.example.giphy.GipyAPI;
import org.example.parameters.eTokens;

import javax.security.auth.login.LoginException;


public class Main extends ListenerAdapter {
    GipyAPI gipyAPI = new GipyAPI();
    public static void main(String[] args) throws LoginException {
        JDA discordBot = JDABuilder.createDefault(eTokens.tokenDiscord.botToken)
                .addEventListeners(new Main())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();

        try{
        GipyAPI gipyAPI = new GipyAPI();
        gipyAPI.giphy();

        }catch (GiphyException giphyException){

        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("We received a massage " +
                event.getAuthor().getName() + " " +
                event.getMessage().getContentDisplay());
        if (!event.getMessage().getAuthor().getId().equals("1025093707059445844")
                && event.getMessage().getContentRaw().equals("bender")) {
            event.getChannel().sendMessage("I will make my own canal ").queue();
        }

        if (event.getMessage().getContentRaw().equals("scope")) {
            event.getChannel().sendMessage("Sa inveti " + event.getMessage().getAuthor().getName()).queue();
        }
        if (event.getMessage().getContentRaw().equals("pisici")) {
            try {
                event.getChannel().sendMessage(gipyAPI.giphy()).queue();
            }catch (GiphyException giphyException){
                System.out.println(giphyException);
            }
        }
    }
}