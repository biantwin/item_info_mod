package biantwin.item_info_mod.handler;

import biantwin.item_info_mod.Config;
import biantwin.item_info_mod.util.MessageUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * NBT信息处理器，负责处理和显示物品的NBT数据
 */
public class NBTInfoHandler {
    
    /**
     * 显示物品的NBT信息
     *
     * @param player 玩家对象
     * @param heldItem 当前手持的物品
     * @return 是否成功显示了信息
     */
    public static boolean displayNBTInfo(Player player, ItemStack heldItem) {
        if (!Config.isEnableNBTInfo()) {
            return false;
        }
        
        String nbtData = (heldItem.getTag() != null) ? heldItem.getTag().toString() : "无NBT数据";
        String nbtText = "§a[物品NBT] §f当前手持物品的NBT数据:§b " + nbtData;
        MessageUtils.sendCopyableMessageToPlayer(player, nbtText, nbtData);
        
        return true;
    }
}