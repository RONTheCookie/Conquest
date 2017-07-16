package net.warvale.conquest.game;

import net.warvale.conquest.game.stages.Bosses;
import net.warvale.staffcore.bossbar.BarManager;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.scheduler.BukkitRunnable;

public class GameRunnable extends BukkitRunnable {

    private static int seconds = 0;

    private static Stage currentStage;

    public GameRunnable(BukkitRunnable startTask) {
        startTask.cancel();

        currentStage = Stage.INVINCIBLE_CORE;
    }

    @Override
    public void run() {
        ++seconds;

        // game_start
        if (seconds == 1) {
            //CoreBlock.setCoreState(CoreState.UNBREAKABLE);
            setCurrentStage(Stage.CORE_BREAK);
        }

        if (seconds == 600) {
            //CoreBlock.setCoreState(CoreState.BREAKABLE);
            BarManager.broadcast(BarColor.PURPLE, "The cores are now breakable!");
            BarManager.broadcastSound(Sound.BLOCK_NOTE_BASS);
            setCurrentStage(Stage.CORE_BREAK);
        }

        if (seconds == 1200) {
            new Bosses().initBoss();
            BarManager.broadcast(BarColor.PURPLE, "Bosses will now begin to spawn!");
            BarManager.broadcastSound(Sound.BLOCK_NOTE_BASS);
            setCurrentStage(Stage.BOSS);
        }

        if (seconds == 1800) {
            //CoreBlock.setCoreState(CoreState.SPEED_BREAK);
            BarManager.broadcast(BarColor.PURPLE, "Siege Mode Activated! Cores break two times quicker!");
            BarManager.broadcastSound(Sound.BLOCK_NOTE_BASS);
            setCurrentStage(Stage.CORE_BREAK_FAST);
        }

        if (seconds == 2400) {
            //CoreBlock.setCoreState(CoreState.INSTANT_BREAK);
            BarManager.broadcast(BarColor.PURPLE, "Deathmatch has begun! You will not respawn if you die!");
            BarManager.broadcastSound(Sound.BLOCK_NOTE_BASS);
            setCurrentStage(Stage.DEATHMATCH);
        }

        if (seconds == 3600) {
            BarManager.broadcast(BarColor.RED, "Conquest has ended!");
            BarManager.broadcastSound(Sound.BLOCK_NOTE_BASS);
            this.cancel();
        }

    }

    public static int getSeconds() {
        return seconds;
    }

    public static Stage getCurrentStage() { return currentStage; }

    public void setCurrentStage(Stage stage) {
        currentStage = stage;
    }

}
