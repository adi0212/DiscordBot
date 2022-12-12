package org.example.youtubemusic;

import net.dv8tion.jda.api.audio.AudioSendHandler;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;

public class MySendHandler implements AudioSendHandler {
    @Override
    public boolean canProvide() {
        return false;
    }

    @Nullable
    @Override
    public ByteBuffer provide20MsAudio() {
        return null;
    }
}
