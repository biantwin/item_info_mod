package biantwin.item_info_mod.handler;

import biantwin.item_info_mod.Config;
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
     * @deprecated 推荐使用 ItemInfoManager 来处理所有信息类型
     */
    @Deprecated
    public static boolean displayItemName(Player player, ItemStack heldItem) {
        ItemNameInfoProvider provider = new ItemNameInfoProvider();
        return provider.provideInfo(player, heldItem);
    }
}