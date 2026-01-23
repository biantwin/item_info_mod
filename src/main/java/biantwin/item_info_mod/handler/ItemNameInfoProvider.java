package biantwin.item_info_mod.handler;

import biantwin.item_info_mod.Config;
import biantwin.item_info_mod.util.MessageUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * 物品名称信息提供者
 */
public class ItemNameInfoProvider implements IItemInfoProvider {
    
    @Override
    public boolean provideInfo(Player player, ItemStack itemStack) {
        if (!isEnabled()) {
            return false;
        }
        
        String itemName = itemStack.hasCustomHoverName() ? 
            itemStack.getDisplayName().getString() : itemStack.getItem().getDescriptionId();
        MessageUtils.sendCopyableMessageToPlayer(player, "§a[物品信息] §f你手持的物品: §b" + itemName, itemName);
        
        return true;
    }
    
    @Override
    public String getName() {
        return "itemName";
    }
    
    @Override
    public boolean isEnabled() {
        return Config.isEnableItemName();
    }
}