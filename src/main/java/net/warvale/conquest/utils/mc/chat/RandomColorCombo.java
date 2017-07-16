package net.warvale.conquest.utils.mc.chat;

import net.warvale.conquest.ConquestCore;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class RandomColorCombo {
    private List<List<ChatColor>> colors = new ArrayList<>();

    public RandomColorCombo(int amt) {
        for (int i = 0; i < amt + 1; i++) {
            colors.add(generateNewChatColors());
        }
    }

    public String getRandomColorCombo() {
        Collections.shuffle(colors);
        String s = "";
        for (List<ChatColor> chatColorList : colors) {
            s += fromList(chatColorList).toString();
        }
        return s;
    }

    private ChatColor fromList(List<ChatColor> chatColorList) {
        if (chatColorList.isEmpty()) {
            chatColorList.addAll(Arrays.asList(ChatColor.values()));
        }
        return chatColorList.remove(ConquestCore.getRandom().nextInt(chatColorList.size()));
    }

    private List<ChatColor> generateNewChatColors() {
        List<ChatColor> colors = new ArrayList<>();
        colors.addAll(Arrays.asList(ChatColor.values()));
        return colors;
    }
}
