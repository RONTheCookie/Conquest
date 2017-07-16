package net.warvale.conquest.game.teams;

import net.warvale.conquest.utils.misc.NumberUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TeamBalancing {

    public static void balanceTeams(){

        ArrayList<Player> teamBlue = new ArrayList<>();
        ArrayList<Player> teamRed = new ArrayList<>();

        teamBlue.addAll(TeamManager.get().getBlueTeam());
        teamRed.addAll(TeamManager.get().getRedTeam());

        int blueSize = teamBlue.size();
        int redSize = teamRed.size();

        if ((blueSize - 1) > redSize){
            double x = blueSize - redSize;
            if (x%2 == 0){
                x = x/2;
            } else {
                x = (x/2) + 0.5;
            }
            for (double i = x; i > 0; i--){
                int random = NumberUtils.random(blueSize - 1, 0);
                TeamManager.get().getBlueTeam().remove(teamBlue.get(random));
                TeamManager.get().joinRedTeam(teamBlue.get(random));
            }
        } else if ((redSize - 1) > blueSize){
            double x = redSize - blueSize;
            if (x%2 == 0){
                x = x/2;
            } else {
                x = (x/2) + 0.5;
            }
            for (double i = x; i > 0; i--){
                int random = NumberUtils.random(redSize - 1, 0);
                TeamManager.get().getRedTeam().remove(teamRed.get(random));
                TeamManager.get().joinBlueTeam(teamRed.get(random));
            }
        }

    }

    public static void assignTeam(Player player) {
        if (TeamManager.get().getBlueTeam().size() < TeamManager.get().getRedTeam().size()){
            //join blue
            TeamManager.get().joinBlueTeam(player);
        } else if (TeamManager.get().getRedTeam().size() < TeamManager.get().getBlueTeam().size()){
            //join red
            TeamManager.get().joinRedTeam(player);
        } else if (TeamManager.get().getBlueTeam().size() == TeamManager.get().getRedTeam().size()){
            int rteam = NumberUtils.random(2,1);
            if (rteam == 1){
                TeamManager.get().joinRedTeam(player);
            } else {
                TeamManager.get().joinBlueTeam(player);
            }
        } else {
            //join random
            int rteam = NumberUtils.random(2,1);
            if (rteam == 1){
                TeamManager.get().joinRedTeam(player);
            } else {
                TeamManager.get().joinBlueTeam(player);
            }
        }
    }

}
