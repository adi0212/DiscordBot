package org.example.youtubemusic;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;

public class MySendHandler implements AudioSendHandler {
//    private final AudioPlayer audioPlayer;
    private AudioFrame lastFrame;

//    public MySendHandler(AudioPlayer audioPlayer) {
//        this.audioPlayer = audioPlayer;
//    }

    @Override
    public boolean canProvide() {
//        lastFrame = audioPlayer.provide();
        return lastFrame != null;
    }

    @Override
    public ByteBuffer provide20MsAudio() {
        return ByteBuffer.wrap(lastFrame.getData());
    }

    @Override
    public boolean isOpus() {
        return true;
    }
}
