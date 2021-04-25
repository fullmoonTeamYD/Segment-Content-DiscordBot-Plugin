package com.dochsoft.fullmoon.nymphwardiscordbot.sendMessage;

import com.dochsoft.fullmoon.nymphwardiscordbot.GetPatcherUrl;
import com.dochsoft.fullmoon.nymphwardiscordbot.GetPlayerUUID;
import com.dochsoft.fullmoon.nymphwardiscordbot.WriteLog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;

import java.io.IOException;
import java.util.UUID;

import static com.dochsoft.fullmoon.nymphwardiscordbot.listener.MessageListener.playerName;

public class PlayerAddContentTester {
    public static String AGREE_EMOJI = "✅";
    public static String DISAGREE_EMOJI = "❎";
    public static String PLZ_AGREE_SEND_MESSAGE = "님, 아래 내용을 읽고 동의해주세요.";

    public static void sendTesterTermsMessage(MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        Message temp = new MessageBuilder()
                .setContent(event.getAuthor().getAsMention() + PLZ_AGREE_SEND_MESSAGE)
                .setEmbed(new EmbedBuilder()
                        .setTitle("세그먼트 테스트 참가")
                        .setDescription("컨텐츠 테스트에 참가하기 위해서는 아래 내용을 동의하셔야 테스트 참가가 가능합니다.\n내용을 읽어보시고 동의하시는 경우에 동의 한다는 반응을 달아주세요.\n" +
                                "\n1. 테스트를 하면서 알게된 컨텐츠의 세부기획 및 스크린샷 등 을 외부에 공유하지 않습니다. " +
                                "\n2. 컨텐츠의 시스템(모드, 설정파일 등)을 뜯어보거나 다른 곳에 사용 및 공유하지 않습니다. " +
                                "\n3. 컨텐츠의 테스트를 진행하면서 발생하는 로그 및 플레이어의 모드 리스트 등을 수집합니다. 수집 목적 및 자세한 정보는 개인정보이용약관을 참고해주세요." +
                                "\n4. 위 점을 지키시지 않을 시 얻는 불이익은 모두 본인에게 있음을 알립니다.")
                        .setColor(0x133FF00F) //rgb 색상
                        .addField("개인정보이용약관", "[바로가기](https://sites.google.com/view/fullmoonteam/%EA%B0%9C%EC%9D%B8%EC%A0%95%EB%B3%B4%EC%9D%B4%EC%9A%A9%EC%95%BD%EA%B4%80)", true)
                        .addField("마인크래프트 닉네임", playerName.get(event.getAuthor().getId()), true)
                        .addField("약관에 동의", "동의하실 경우 " + AGREE_EMOJI + ", 동의하지 않으실 경우 " + DISAGREE_EMOJI + "를 눌러주세요.", false)
                        .setFooter("세그먼트 디스코드 봇 | 도치", "https://fullmoon-ff654.firebaseapp.com/logo.png")
                        .build()).build();

        channel.sendMessage(temp).queue(message -> {
            message.addReaction(AGREE_EMOJI).queue();
            message.addReaction(DISAGREE_EMOJI).queue();
        });
    }

    public static void sendPlayerAgreeMessage(GuildMessageReactionAddEvent event) throws IOException { //약관 동의 후 서버 화이트리스트 추가
        MessageChannel channel = event.getChannel();
        String playerUUID = GetPlayerUUID.getPlayerUUID(playerName.get(event.getUserId()));

        event.getChannel().retrieveMessageById(event.getMessageId()).queue((message) -> {
            message.editMessage(new MessageBuilder()
                    .setEmbed(new EmbedBuilder()
                            .setTitle("처리 중...")
                            .setDescription("잠시만 기다려주세요.")
                            .setColor(0x133FF00F) //rgb 색상
                            .setFooter("세그먼트 디스코드 봇 | 도치", "https://fullmoon-ff654.firebaseapp.com/logo.png")
                            .build()).build()).queue();


            if (playerUUID.equalsIgnoreCase("Not Found")) {
                Message sendMessage = new MessageBuilder()
                        .setEmbed(new EmbedBuilder()
                                .setTitle("테스터 참가 오류")
                                .setDescription("플레이어의 닉네임이 존재하지 않습니다. \n제대로 입력하였는지 다시 한 번 확인해주세요.")
                                .setColor(0x1FF0000F) //rgb 색상
                                .setFooter("세그먼트 디스코드 봇 | 도치", "https://fullmoon-ff654.firebaseapp.com/logo.png")
                                .build()).build();
                channel.sendMessage(sendMessage).queue();
            } else {
                String plainUUID = null;
                try {
                    plainUUID = GetPlayerUUID.getPlayerUUID(playerName.get(event.getUserId()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String realUUID = String.format("%s-%s-%s-%s-%s", plainUUID.substring(0, 8), plainUUID.substring(8, 12), plainUUID.substring(12, 16), plainUUID.substring(16, 20), plainUUID.substring(20));
                OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(realUUID));

                if (player.isWhitelisted() || player.isOp()) {
                    Message temp = new MessageBuilder()
                            .setEmbed(new EmbedBuilder()
                                    .setTitle("테스터 참가 오류")
                                    .setDescription("이미 테스트에 참가하셨습니다. 추가적인 등록이 필요하지 않습니다. \n현재 서버 접속이 가능합니다.")
                                    .setColor(0x1FF0000F) //rgb 색상
                                    .setFooter("세그먼트 디스코드 봇 | 도치", "https://fullmoon-ff654.firebaseapp.com/logo.png")
                                    .build()).build();
                    channel.sendMessage(temp).queue();
                } else {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist add " + playerName.get(event.getUserId()));
                    Message temp = new MessageBuilder()
                            .setEmbed(new EmbedBuilder()
                                    .setTitle("테스터 참가 성공")
                                    .setDescription("컨텐츠 테스트에 참가하였습니다. \n이제 서버 접속이 가능합니다.\n컨텐츠 패치기는 개인 DM으로 보내드립니다.")
                                    .addField("마인크래프트 닉네임", playerName.get(event.getUserId()), true)
                                    .setThumbnail("https://minotar.net/helm/" + playerName.get(event.getUserId()) + "/64")
                                    .setColor(0x133FF00F) //rgb 색상
                                    .setFooter("세그먼트 디스코드 봇 | 도치", "https://fullmoon-ff654.firebaseapp.com/logo.png")
                                    .build()).build();
                    channel.sendMessage(temp).queue();

                    String patcherUrl = null;
                    String patcherDate = null;
                    try {
                        patcherUrl = GetPatcherUrl.getPatcherUrl();
                        patcherDate = GetPatcherUrl.getPatchDate();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Message temp2 = new MessageBuilder()
                            .setEmbed(new EmbedBuilder()
                                    .setTitle(patcherDate + "세그먼트 컨텐츠 패치기")
                                    .setDescription("컨텐츠 패치기 링크를 보내드립니다. \n다운 후 설치하시면 서버 접속이 가능합니다. \n **[주의]폰트를 먼저 설치해주시고 컨텐츠 패치기를 실행해주세요.**")
                                    .addField("폰트 다운로드 링크", "[바로가기](https://www.seoul.go.kr/upload/seoul/font/WIN_seoul_font.zip)", true)
                                    .addField("패치기 다운로드 링크", "[바로가기](" + patcherUrl + ")", true)
                                    .setColor(0x133FF00F) //rgb 색상
                                    .setFooter("세그먼트 디스코드 봇 | 도치", "https://fullmoon-ff654.firebaseapp.com/logo.png")
                                    .build()).build();
                    event.getUser().openPrivateChannel().flatMap(privateChannel -> privateChannel.sendMessage(temp2)).queue();
                    WriteLog.writeLog(playerName.get(event.getUserId()), "TesterApply", "Player accept the Terster terms");
                }
            }
            message.delete().queue();
        });
    }

    public static void sendPlayerDisagreeMessage(GuildMessageReactionAddEvent event) {
        event.getChannel().retrieveMessageById(event.getMessageId()).queue((message) -> {
            TextChannel textChannel = message.getTextChannel();
            Message temp = new MessageBuilder()
                    .setEmbed(new EmbedBuilder()
                            .setTitle("테스터 참가 비동의")
                            .setDescription("약관에 비동의를 선택하셨습니다. 약관에 비동의 하실 경우 테스트에 참가하실 수 없습니다.")
                            .setColor(0x1FF0000F) //rgb 색상
                            .setFooter("세그먼트 디스코드 봇 | 도치", "https://fullmoon-ff654.firebaseapp.com/logo.png")
                            .build()).build();
            message.delete().queue();
            textChannel.sendMessage(temp).queue();
        });

    }


}
