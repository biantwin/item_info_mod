package biantwin.item_info_mod.handlers;

import biantwin.item_info_mod.Config;
import biantwin.item_info_mod.info_provider.FoodInfoProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * 食物信息处理器，负责处理和显示食物相关的属性信息
 */
public class FoodInfoHandler {
    
    /**
     * 显示食物相关信息
     *
     * @param player 玩家对象
     * @param heldItem 当前手持的物品
     * @return 是否成功显示了信息
     * @deprecated 推荐使用 ItemInfoManager 来处理所有信息类型
     */
    @Deprecated
    public static boolean displayFoodInfo(Player player, ItemStack heldItem) {
        FoodInfoProvider provider = new FoodInfoProvider();
        return provider.provideInfo(player, heldItem);
    }
}