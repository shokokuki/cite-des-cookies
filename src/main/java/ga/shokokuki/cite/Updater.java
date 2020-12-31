package ga.shokokuki.cite;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;

public class Updater extends BukkitRunnable {

    private ProtocolManager protocolManager;
    private TeamsManager tm;
    private Configuration config;

    public Updater(Main main, TeamsManager tm) {
        this.tm = tm;
        this.config = main.getConfig();
        this.protocolManager = ProtocolLibrary.getProtocolManager();
    }

    private String formatRemainingTime() {
        String returner = "";
        long remaining = config.getLong("end")-((new Date()).getTime()/1000);
        long original = remaining;

        if (remaining/2592000 >= 1) {
            returner += (remaining / 2592000) + "M";
            remaining %= 2592000;
        }

        if (remaining/86400 >= 1) {
            if (remaining != original)
                returner += " ";
            returner += (remaining / 86400) + "d";
            remaining %= 86400;
        }

        if (remaining/3600 >= 1) {
            if (remaining != original)
                returner += " ";
            returner += (remaining / 3600) + "h";
            remaining %= 3600;
        }

        if (remaining/60 >= 1) {
            if (remaining != original)
                returner += " ";
            returner += (remaining / 60) + "m";
            remaining %= 60;
        }

        if (remaining >= 1 && returner.length() == 0) {
            returner += remaining + "s";
        }

        return returner;
    }

    public void setHeader(Player p) {
        PacketContainer pc = this.protocolManager.createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
        pc.getChatComponents()
                .write(0, WrappedChatComponent.fromText(""))
                .write(1, WrappedChatComponent.fromText(this.formatRemainingTime()));
        try
        {
            this.protocolManager.sendServerPacket(p, pc);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {

        if(((new Date()).getTime()/1000) > config.getLong("end")) { // if the cité des cookies ended
            for (Player player : Bukkit.getOnlinePlayers())
                player.kickPlayer("\n"+(new Baltop(tm, config)).getBaltopMSG()); // kick with baltop
            cancel();
        }

        for (Player player : Bukkit.getOnlinePlayers())
            this.setHeader(player);
    }
}
