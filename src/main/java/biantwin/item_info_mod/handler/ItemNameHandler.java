package biantwin.item_info_mod.handler;

import biantwin.item_info_mod.Config;
import biantwin.item_info_mod.util.MessageUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * 物品名称处理器，负责处理和显示物品名称信息
 */
public class ItemNameHandler {
    
    /**
     * 显示物品名称
     *
     * @param player 玩家对象
     * @param heldItem 当前手持的物品
     * @return 是否成功显示了信息
     */
    public static boolean displayItemName(Player player, ItemStack heldItem) {
        if (!Config.isEnableItemName()) {
            return false;
        }
        
        String itemName = heldItem.hasCustomHoverName() ? 
            heldItem.getDisplayName().getString() : heldItem.getItem().getDescriptionId();
        MessageUtils.sendCopyableMessageToPlayer(player, "§a[物品信息] §f你手持的物品: §b" + itemName, itemName);
        
        return true;
    }
}