package com.dochsoft.fullmoon.nymphwardiscordbot.listener;

import com.dochsoft.fullmoon.nymphwardiscordbot.GetPlayerUUID;
import com.dochsoft.fullmoon.nymphwardiscordbot.sendMessage.PlayerAddContentTester;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class MessageListener extends ListenerAdapter {
    public static HashMap<String, String> playerName = new HashMap();

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();

        if (content.contains("!테스터신청")) {
            if (content.length() < 8) {
                event.getChannel().sendMessage("!테스터신청 명령어 뒤에 마인크래프트 닉네임을 적어주세요.").queue();
            } else {
                String[] messageList = message.getContentRaw().split(" ");
                playerName.put(message.getAuthor().getId(), messageList[1]);
                PlayerAddContentTester.sendTesterTermsMessage(event);
            }
        } else if (content.equals("!도움말")) {
            MessageChannel channel = event.getChannel();
            Message temp = new MessageBuilder()
                    .setContent(event.getAuthor().getAsMention())
                    .setEmbed(new EmbedBuilder()
                            .setTitle("세그먼트 디스코드 봇 도움말")
                            .addField(" - !테스터신청", "컨텐츠 테스트에 참가할 수 있어요. ", false)
                            .setColor(0x1FF9900F) //rgb 색상
                            .setFooter("세그먼트 디스코드 봇 | 도치", "https://fullmoon-ff654.firebaseapp.com/logo.png")
                            .build()).build();

            channel.sendMessage(temp).queue();
        }
    }
}