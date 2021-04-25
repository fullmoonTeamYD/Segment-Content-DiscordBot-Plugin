package com.dochsoft.fullmoon.nymphwardiscordbot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BukkitEvent implements Listener {

    @EventHandler
    public static void playerJoinEvent (PlayerJoinEvent event) {
        MessageChannel channel = NymphWarDiscordBot.api.getTextChannelById("752685656165056552"); //테스트 디코서버 채널 아이디
        Message temp = new MessageBuilder()
                //.setContent(FormatUtil.filter(manager.getBot().getConfig().getSuccess()+" **Now Playing...**"))
                .setEmbed(new EmbedBuilder()
                        .setTitle("플레이어 접속 이벤트")
                        .setDescription(event.getPlayer().getName() + "님이 서버에 접속하였습니다.")
                        .setColor(0x133FF00F) //rgb 색상
                        .setFooter("세그먼트 디스코드 봇 | 도치", "https://fullmoon-ff654.firebaseapp.com/logo.png")
                        .build()).build();

        channel.sendMessage(temp).queue();
    }

    @EventHandler
    public static void playerQuitEvent (PlayerQuitEvent event) {
        MessageChannel channel = NymphWarDiscordBot.api.getTextChannelById("752685656165056552"); //테스트 디코서버 채널 아이디
        Message temp = new MessageBuilder()
                //.setContent(FormatUtil.filter(manager.getBot().getConfig().getSuccess()+" **Now Playing...**"))
                .setEmbed(new EmbedBuilder()
                        .setTitle("플레이어 접속 종료 이벤트")
                        .setDescription(event.getPlayer().getName() + "님이 서버에서 나가셨습니다.")
                        .setColor(0x1FF0000F) //rgb 색상
                        .setFooter("세그먼트 디스코드 봇 | 도치", "https://fullmoon-ff654.firebaseapp.com/logo.png")
                        .build()).build();

        channel.sendMessage(temp).queue();
    }
}
