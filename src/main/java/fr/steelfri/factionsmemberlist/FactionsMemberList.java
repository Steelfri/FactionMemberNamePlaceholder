package fr.steelfri.factionsmemberlist;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.stream.Collectors;

public final class FactionsMemberList extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(
                "§9" + getDescription().getName() + " enable!\n" +
                        "§9Version : " + getDescription().getVersion() + "\n" +
                        "§9Steelfri"
        );
        Bukkit.getPluginManager().registerEvents(this, this);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PlaceholderAPI.registerExpansion(new PlaceholderExpansion() {
                @Override
                public boolean persist() {
                    return true;
                }

                @Override
                public boolean canRegister() {
                    return true;
                }

                @Override
                public String getAuthor() {
                    return "Steelfri";
                }

                @Override
                public String getIdentifier() {
                    return "factionsmember";
                }

                @Override
                public String getVersion() {
                    return "1.0.0";
                }

                @Override
                public String onPlaceholderRequest(Player player, String params) {
                    FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
                    if (fPlayer == null) {
                        return "";
                    }

                    try {
                        int numMembers = Integer.parseInt(params);
                        List<String> memberList = fPlayer.getFaction().getFPlayers().stream().map(FPlayer::getName).limit(numMembers).collect(Collectors.toList());
                        return String.join(",\n", memberList);
                    } catch (NumberFormatException e) {
                        return "";
                    }
                }
            });
        }
    }
}