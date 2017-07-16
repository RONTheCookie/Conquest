package net.warvale.conquest.tasks;

import de.robingrether.idisguise.disguise.PlayerDisguise;
import net.warvale.conquest.ConquestCore;
import net.warvale.conquest.game.Game;
import net.warvale.conquest.game.State;
import net.warvale.conquest.game.teams.TeamBalancing;
import net.warvale.conquest.game.teams.TeamManager;
import net.warvale.conquest.hooks.DisguiseHook;
import net.warvale.conquest.maps.GameMap;
import net.warvale.conquest.maps.VoteMenu;
import net.warvale.conquest.message.MessageManager;
import net.warvale.conquest.message.PrefixType;
import net.warvale.conquest.utils.chat.ChatUtils;
import net.warvale.conquest.utils.dates.DateUtils;
import net.warvale.staffcore.bossbar.BarManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Random;


public class BossbarCountdownTask extends BukkitRunnable {

    private static int countdown = 1 * 60 + 1;
    public static GameMap map;

    public BossbarCountdownTask() {
        countdown = 1 * 60 + 1;
    }

    @Override
    public void run(){
        countdown = countdown - 1;
        Game.get().setState(State.COUNTDOWN);
        BarManager.getAnnounceBar().setVisible(true);
        if (countdown == 15){
            TeamBalancing.balanceTeams();
            map = VoteMenu.calculateMap();
            Game.get().setChosenmap(map);
            MessageManager.broadcast(PrefixType.MAIN, ChatColor.RED + (map != null ? map.getName() : "ERROR GETTING MAP NAME") + ChatColor.GRAY + " has been chosen as the map you will be playing on!");
        }
        if (countdown == 10){

            for (Player player : TeamManager.get().getPlayers()){
                player.addPotionEffects(
                        Arrays.asList(
                                new PotionEffect(PotionEffectType.SLOW, 100000, 128, false, false),
                                new PotionEffect(PotionEffectType.JUMP, 100000, 250, false, false)));
                player.sendMessage((String[]) Arrays.asList("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n",
                        ChatUtils.divider,
                        "\n",
                        ChatUtils.gray + "Your goal is to destroy the other teams " + ChatUtils.yellow + "core" + ChatUtils.gray + " 20 times!",
                        ChatUtils.gray + "The game is divided into " + ChatUtils.red + "5" + ChatUtils.gray + " stages!",
                        ChatUtils.yellow + "1." + ChatUtils.gray + " During the first " + ChatUtils.red + "20" + ChatUtils.gray + " minutes of the game, your core is invulnerable!",
                        ChatUtils.yellow + "2." + ChatUtils.gray + " At the end of the first stage, a boss spawns in the middle of the map!",
                        ChatUtils.yellow + "3." + ChatUtils.gray + " After that, diamonds will start to appear at middle. Diamonds can be used to craft the strongest armor in the game!",
                        ChatUtils.yellow + "4." + ChatUtils.yellow + " Siege mode!" + ChatUtils.gray + " During this stage of the game, core breaking is 2x as powerful!",
                        ChatUtils.yellow + "5." + ChatUtils.gray + " During the last stage of the game, cores will be broken instantly!",
                        "\n",
                        ChatUtils.divider).toArray());
            }

            //disguise before teleporting
            if (DisguiseHook.getInstance().isEnabled()) {

                Random r = new Random();

                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                    PlayerDisguise disguise = new PlayerDisguise(
                            DisguiseHook.getInstance().getRandomPlayers()
                                    .get(r.nextInt(DisguiseHook.getInstance().getRandomPlayers().size())),
                            false);

                    DisguiseHook.getInstance().getAPI().disguise(online, disguise);

                    online.sendMessage(MessageManager.getPrefix(PrefixType.MAIN) + "You are now disguised");
                }
            }
        }
        if (countdown <= 10 && countdown >= 1){
            MessageManager.broadcast(PrefixType.MAIN, ChatColor.DARK_RED + "Conquest " + ChatColor.GRAY + "starts in " + ChatColor.RED + countdown + ChatColor.GRAY + (countdown == 1 ? " second." : " seconds."));
            BarManager.broadcastSound(Sound.BLOCK_NOTE_PLING);
        }
        if (countdown <= 0){

            new StartGameTask().runTaskTimer(ConquestCore.get(), 20, 20);

            this.cancel();
            return;
        }
        BarManager.getAnnounceBar().setTitle(ChatColor.DARK_RED + "Conquest " + ChatColor.GRAY + "starts in " + DateUtils.secondsToString(countdown));
        BarManager.getAnnounceBar().setProgress((float)countdown/(float)(60 * 5));
        BarManager.getAnnounceBar().setColor(BarColor.BLUE);
    }

    public static int getCountdown() {
        return countdown;
    }
}
