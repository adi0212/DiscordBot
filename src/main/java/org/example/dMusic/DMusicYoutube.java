package org.example.dMusic;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import discord4j.voice.AudioProvider;
import org.example.giphy.GipyAPI;
import org.example.parameters.eTokens;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class DMusicYoutube {
    static GipyAPI gipyAPI = new GipyAPI();


    private static final Map<String, Command> commands = new HashMap<>();


    public static void main(String[] args) {
        final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        final AudioPlayer player = playerManager.createPlayer();
        final TrackScheduler scheduler = new TrackScheduler(player);
        AudioProvider provider = new LavaPlayerAudioProvider(player);

        final GatewayDiscordClient client = DiscordClientBuilder.create(eTokens.tokenDiscord.botToken).build()
                .login()
                .block();

        playerManager.getConfiguration()
                .setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);
        AudioSourceManagers.registerRemoteSources(playerManager);

        client.getEventDispatcher().on(MessageCreateEvent.class)
                // 3.1 Message.getContent() is a String
                .flatMap(event -> Mono.just(event.getMessage().getContent())
                        .flatMap(content -> Flux.fromIterable(commands.entrySet())
                                // We will be using ! as our "prefix" to any command in the system.
                                .filter(entry -> content.startsWith("!" + entry.getKey()))
                                .flatMap(entry -> entry.getValue().execute(event))
                                .next()))
                .subscribe();

        commands.put("join", event -> Mono.justOrEmpty(event.getMember())
                .flatMap(Member::getVoiceState)
                .flatMap(VoiceState::getChannel)
                // join returns a VoiceConnection which would be required if we were
                // adding disconnection features, but for now we are just ignoring it.
                .flatMap(channel -> channel.join(spec -> spec.setProvider(provider)))
                .then());

        commands.put("play", event -> Mono.justOrEmpty(event.getMessage().getContent())
                .map(content -> Arrays.asList(content.split(" ")))
                .doOnNext(command -> playerManager.loadItem(command.get(1), scheduler))
                .then());


//        String[] botMessageProcessed = discordMessagesProcessed.messageSplit(message)
//        commands.put("pic", event -> event.getMessage().getChannel()
//                .filter(messageChannel -> messageChannel.createMessage(gipyAPI.giphy(botMessageProcessed[1])))
//                .then());
//
//
        client.onDisconnect().block();


    }


}
