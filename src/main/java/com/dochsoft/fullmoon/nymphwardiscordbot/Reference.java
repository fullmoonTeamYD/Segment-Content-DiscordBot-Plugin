package com.dochsoft.fullmoon.nymphwardiscordbot;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Vector;

public class Reference {
    public static final String prefix_normal = "§6§l[세그먼트-디스코드봇] §r";
    public static final String prefix_opMessage = "§b[세그먼트-OP메세지] §r";
    public static final String prefix_error = "§c§l[세그먼트-디스코드봇] §7";
    public static String ENABLE_MESSAGE =  "플러그인이 §a활성화§r되었습니다. §r| 도치(doch1)";
    public static String DISABLE_MESSAGE = "플러그인이 §c비활성화§r되었습니다. §r| 도치(doch1)";

    public static final String ConfigFolder = "plugins/Segment/";

    public static void sendOpMessage(String sendMessage) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.isOp()) {
                onlinePlayer.sendMessage(Reference.prefix_opMessage + sendMessage);
            }
        }
    }
}
