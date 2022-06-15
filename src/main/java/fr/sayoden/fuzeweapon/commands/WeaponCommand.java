package fr.sayoden.fuzeweapon.commands;

import fr.sayoden.fuzeweapon.FuzeWeapon;
import fr.sayoden.fuzeweapon.weapon.WeaponService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WeaponCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player) {
            if (player.isOp()) {
                switch (args.length) {
                    case 1 -> {
                        FuzeWeapon plugin = FuzeWeapon.getPlugin();
                        WeaponService weaponService = plugin.getWeaponService();
                        if (args[0].equalsIgnoreCase("all")) {
                            WeaponService.getWeapons().forEach(weapon -> weaponService.giveWeaponToPlayer(player, weapon.getId()));
                            player.sendMessage("§aVous venez de récupérer toutes les armes");
                        } else {
                            weaponService.giveWeaponToPlayer(player, args[0]);
                            player.sendMessage("§aVous venez de récupérer l'arme " + args[0]);
                        }
                    }
                    default -> player.sendMessage("§cVeuillez utiliser la command: §7/weapon nom");
                }
            }
        } else {
            sender.sendMessage("§cVous n'avez pas la permission!");
        }
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> completer = new ArrayList<>();
        WeaponService.getWeapons().forEach(aWeapon -> completer.add(aWeapon.getId()));
        return completer;
    }
}
