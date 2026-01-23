package biantwin.item_info_mod.info_provider;

import biantwin.item_info_mod.Config;
import biantwin.item_info_mod.util.MessageUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * 物品耐久信息提供者
 */
public class DurabilityInfoProvider implements IItemInfoProvider {
    
    @Override
    public boolean provideInfo(Player player, ItemStack itemStack) {
        if (!isEnabled() || !itemStack.isDamageableItem()) {
            return false;
        }
        
        int maxDurability = itemStack.getMaxDamage();
        int currentDurability = maxDurability - itemStack.getDamageValue();
        int durabilityPercent = (int) (((double) currentDurability / maxDurability) * 100);
        
        String durabilityText = "§a[耐久] §f物品耐久: §b" + currentDurability + "§f/§b" + maxDurability + " §7(" + durabilityPercent + "%§7)";
        MessageUtils.sendCopyableMessageToPlayer(player, durabilityText, currentDurability + "/" + maxDurability);
        
        return true;
    }
    
    @Override
    public String getName() {
        return "durabilityInfo";
    }
    
    @Override
    public boolean isEnabled() {
        return Config.isEnableDurabilityInfo();
    }
}