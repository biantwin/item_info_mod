package biantwin.item_info_mod.handlers;

import biantwin.item_info_mod.Config;
import biantwin.item_info_mod.info_provider.NBTInfoProvider;
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
     * @deprecated 推荐使用 ItemInfoManager 来处理所有信息类型
     */
    @Deprecated
    public static boolean displayNBTInfo(Player player, ItemStack heldItem) {
        NBTInfoProvider provider = new NBTInfoProvider();
        return provider.provideInfo(player, heldItem);
    }
}