package net.warvale.conquest.tasks;

import net.warvale.conquest.game.teams.TeamManager;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyTask extends BukkitRunnable {

    private static LobbyTask instance;

    public static LobbyTask getInstance() {
        if (instance == null) {
            instance = new LobbyTask();
        }
        return instance;
    }

    @Override
    public void run() {

        if (TeamManager.get().getRedTeam().size() >= 1 && TeamManager.get().getBlueTeam().size() >= 1) {
            //start the countdown
//            new BossbarCountdownTask().runTaskTimer(StaffCore.get(), 20, 20);
        }

    }

}
