package com.dochsoft.fullmoon.nymphwardiscordbot.listener;

import com.dochsoft.fullmoon.nymphwardiscordbot.listener.thread.MessageReactionThread;
import com.dochsoft.fullmoon.nymphwardiscordbot.sendMessage.PlayerAddContentTester;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReactionListener extends ListenerAdapter {
    public static String messageContent;

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        event.getChannel().retrieveMessageById(event.getMessageId()).queue((message) -> {
            messageContent = message.getContentDisplay();
        });
        Runnable runnable = new MessageReactionThread(event);
        new Thread(runnable).start();

    }



}