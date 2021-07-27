package com.dochsoft.fullmoon.nymphwardiscordbot;

import com.dochsoft.fullmoon.nymphwardiscordbot.listener.MessageListener;
import com.dochsoft.fullmoon.nymphwardiscordbot.listener.ReactionListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public final class NymphWarDiscordBot extends JavaPlugin {
    private static String botToken = null;
    public static JDA api;
    BukkitEvent event = new BukkitEvent();

    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            api = JDABuilder.createDefault(botToken).setActivity(Activity.of(Activity.ActivityType.DEFAULT, "명령어를 보고싶다면 !도움말")).addEventListeners(new MessageListener()).addEventListeners(new ReactionListener()).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        getServer().getPluginManager().registerEvents(this.event, this);
        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + Reference.ENABLE_MESSAGE);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + Reference.DISABLE_MESSAGE);
    }
}
