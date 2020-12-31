package ga.shokokuki.cite;

import kr.entree.spigradle.annotations.PluginMain;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;

@PluginMain
public class Main extends JavaPlugin implements Listener {

    private TeamsManager tm;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        Material currency = Material.getMaterial(getConfig().getString("currency"));
        this.tm = new TeamsManager(this);

        getCommand("deposit").setExecutor(new Deposit(tm, currency));
        getCommand("baltop").setExecutor(new Baltop(tm, getConfig()));

        getServer().getPluginManager().registerEvents(this, this);

        (new Updater(this, this.tm)).runTaskTimer(this, 0L, 20L*10);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if(((new Date()).getTime()/1000) > getConfig().getLong("end")) { // if the cité des cookies ended
            e.getPlayer().kickPlayer("\n"+(new Baltop(tm, getConfig())).getBaltopMSG()); // kick with baltop
        }
    }
}
