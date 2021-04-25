package com.dochsoft.fullmoon.nymphwardiscordbot.listener.thread;

import com.dochsoft.fullmoon.nymphwardiscordbot.listener.ReactionListener;
import com.dochsoft.fullmoon.nymphwardiscordbot.sendMessage.PlayerAddContentTester;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import org.bukkit.entity.Player;

import java.io.IOException;

public class MessageReactionThread extends Thread {
    private GuildMessageReactionAddEvent event;

    public MessageReactionThread(GuildMessageReactionAddEvent event) {
        this.event = event;
    }

    public void run() {
        try {
            Thread.sleep(500); //1000 = 1초
            Boolean checkMessage = !event.getMember().getUser().equals(event.getJDA().getSelfUser()) && ReactionListener.messageContent.equals("@" + event.getUser().getName() + PlayerAddContentTester.PLZ_AGREE_SEND_MESSAGE);
            if (event.getReactionEmote().getName().equals(PlayerAddContentTester.AGREE_EMOJI) && checkMessage) {
                PlayerAddContentTester.sendPlayerAgreeMessage(event);
            } else if (event.getReactionEmote().getName().equals(PlayerAddContentTester.DISAGREE_EMOJI) && checkMessage) {
                PlayerAddContentTester.sendPlayerDisagreeMessage(event);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
