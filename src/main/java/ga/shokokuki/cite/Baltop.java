package ga.shokokuki.cite;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Baltop implements CommandExecutor {

    private TeamsManager tm;
    private Configuration config;

    public Baltop(TeamsManager tm, Configuration config) {
        this.tm = tm;
        this.config = config;
    }

    public String getBaltopMSG() {
        String returner = "";

        List<Team> teams = tm.getTeams();
        Collections.sort(teams, (team, t1) -> (t1.getBalance() - team.getBalance()));

        for (Team team : teams)
            returner += team.getName()+": "+team.getBalance()+"\n";

        return returner;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(this.getBaltopMSG());
        return false;
    }
}
