package ga.shokokuki.cite;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import sun.security.krb5.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Team {

    private Main main;
    private Configuration config;
    private String path;
    private String name;
    private int balance;
    private List<UUID> members = new ArrayList<>();

    public Team(Main main, String path, String name) {

        this.balance = main.getConfig().getInt(path+".balance", 0);
        this.name = name;
        this.path = path;
        this.config = main.getConfig();
        this.main = main;

        List<String> members = (List<String>) this.config.getList(path+".members");

        for (String member : members)
            this.members.add(UUID.fromString(member));
    }

    public boolean isMember(Player player) {
        return this.members.contains(player.getUniqueId());
    }

    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
        config.set(path+".balance", this.balance);
        main.saveConfig();
    }

    public void addBalance(int to_add) {
        this.balance += to_add;
        config.set(path+".balance", this.balance);
        main.saveConfig();
    }

    public String getName() {
        return name;
    }
}
