package me.oijka.testing_tree_decay;

import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class Testing_Tree_Decay extends JavaPlugin implements Listener {

   @Override
   public void onEnable() {
      System.out.println("Tree Decay Plugin Loaded Successfully");
      getServer().getPluginManager().registerEvents(this, this);
   }

   @Override
   public void onDisable() {
   }

   @EventHandler
   public void BlockBroken(BlockBreakEvent event) {
      if (event.getBlock().getType() == Material.OAK_LOG) {
         Location CurrentBlock = event.getBlock().getLocation();
         Location BlockToStart = event.getBlock().getLocation();
         int treeHeight = 0;
         while (CurrentBlock.getBlock().getType() == Material.OAK_LOG) {
            CurrentBlock.add(0, 1, 0);
            treeHeight++;
         }
         if (CurrentBlock.getBlock().getType() == Material.OAK_LEAVES) {
            int delay = 1;
            for (int i=0; i<(treeHeight-1); i++){
               delay++;
               new BukkitRunnable(){
                  public void run(){
                     BlockToStart.add(0, 1, 0);
                     ItemStack itemToDrop = new ItemStack(BlockToStart.getBlock().getType());
                     BlockToStart.getWorld().dropItemNaturally(BlockToStart, itemToDrop);
                     BlockToStart.getBlock().setType(Material.AIR);
                     BlockToStart.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, BlockToStart, 2);
                     event.getPlayer().playSound(BlockToStart, Sound.ENTITY_ARMOR_STAND_BREAK, 1F, 1F);
                  }
               }.runTaskLater(this, delay*4L);
            }
         }
      }
   }
}
