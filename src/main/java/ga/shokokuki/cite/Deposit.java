package ga.shokokuki.cite;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Deposit implements CommandExecutor {

    private TeamsManager tm;
    private Material currency;

    public Deposit(TeamsManager tm, Material currency) {
        this.tm = tm;
        this.currency = currency;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Team team = this.tm.getTeamByPlayer((Player) sender);

        if (team == null) {
            sender.sendMessage("§cYou are not in any team!");
            return false;
        }

        Inventory inv = ((Player) sender).getInventory();
        int balance = 0;

        for (int i = 0; i < 36; i++) {
            ItemStack item = inv.getItem(i);

            if (item != null) {
                if (item.getType() == currency) {
                    balance += item.getAmount();
                    inv.setItem(i, null);
                }
            }
        }

        sender.sendMessage("You have deposited "+balance+" "+currency.name().toLowerCase()+"(s)!");
        team.addBalance(balance);

        return false;
    }
}
