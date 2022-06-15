package fr.sayoden.fuzeweapon.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

import java.util.Random;

public class FuzeWeaponConstants {

    public static final int FIRING_RATE = 20;

    public static final int RELOAD_TIME = 45;

    public static final Random RANDOM = new Random();

    public static final World MAIN_WORLD = Bukkit.getWorld("world");
}
