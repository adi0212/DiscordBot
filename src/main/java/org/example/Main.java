package org.example;

import at.mukprojects.giphy4j.exception.GiphyException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.apache.commons.logging.Log;
import org.example.giphy.GipyAPI;
import org.example.giphy.discord.DiscordMessagesProcessed;
import org.example.parameters.eTokens;
import org.example.youtubemusic.MySendHandler;

import java.util.logging.Logger;


public class Main extends ListenerAdapter {
    static GipyAPI gipyAPI = new GipyAPI();

    static DiscordMessagesProcessed discordMessagesProcessed = new DiscordMessagesProcessed();

    public static void main(String[] args) {
        //add your token instead of eTokens.tokenDiscord.botToken
        JDA discordBot = JDABuilder.createDefault(eTokens.tokenDiscord.botToken)
                .addEventListeners(new Main())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("We received a massage " +
                event.getAuthor().getName() + " " +
                event.getMessage().getContentDisplay()+ "  chanel " +
                event.getMember().getVoiceState().getChannel().getId());


        String message = event.getMessage().getContentRaw();
        String[] botMessageProcessed = discordMessagesProcessed.messageSplit(message);

        if (message.equals("scope")) {
            event.getChannel().sendMessage("Sa inveti " + event.getMessage().getAuthor().getName()).queue();
        }
        if(botMessageProcessed[0].equals("!pic")) {
            try {
                event.getChannel().sendMessage(gipyAPI.giphy(botMessageProcessed[1])).queue();
            } catch (GiphyException giphyException) {
                System.out.println(giphyException);
            }
        }
        if (!event.isFromGuild()) return;
        if (!event.getMessage().getContentRaw().startsWith("!play")) return;
        if (event.getAuthor().isBot()) return;
        Member member = event.getMember();
        Guild guild = event.getGuild();
        VoiceChannel channel = guild.getVoiceChannelsByName(member.getVoiceState().getChannel().getName(),true).get(0);
        AudioManager manager = guild.getAudioManager();
        manager.setSendingHandler(new MySendHandler());
        manager.openAudioConnection(channel);
    }
}