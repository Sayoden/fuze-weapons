package fr.sayoden.fuzeweapon;

import fr.sayoden.fuzeweapon.commands.WeaponCommand;
import fr.sayoden.fuzeweapon.weapon.listeners.WeaponListener;
import fr.sayoden.fuzeweapon.weapon.WeaponService;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class FuzeWeapon extends JavaPlugin {

    @Getter
    private static FuzeWeapon plugin;

    @Getter
    private WeaponService weaponService;

    @Override
    public void onEnable() {
        super.onEnable();
        plugin = this;

        this.weaponService = new WeaponService();

        registerListeners();

        WeaponCommand weaponCommand = new WeaponCommand();
        this.getCommand("weapon").setExecutor(weaponCommand);
        this.getCommand("weapon").setTabCompleter(weaponCommand);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new WeaponListener(), this);
    }

}
