package ga.shokokuki.cite;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TeamsManager {

    private List<Team> teams = new ArrayList<>();

    public TeamsManager(Main main) {

        Set<String> teams = main.getConfig().getConfigurationSection("teams").getKeys(false);

        for (String team : teams)
            this.teams.add(new Team(main, "teams."+team, team));
    }

    public Team getTeamByName(String name) {
        for (Team team : this.teams)
            if (team.getName().equalsIgnoreCase(name))
                return team;

        return null;
    }

    public Team getTeamByPlayer(Player player) {
        for (Team team : this.teams)
            if (team.isMember(player))
                return team;

        return null;
    }

    public List<Team> getTeams() {
        return teams;
    }
}
