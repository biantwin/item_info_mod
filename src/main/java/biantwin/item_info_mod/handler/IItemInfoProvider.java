package biantwin.item_info_mod.handler;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * 物品信息提供者接口，允许不同的信息类型以插件方式添加到物品信息显示中
 */
public interface IItemInfoProvider {
    /**
     * 尝试为指定物品提供信息
     * @param player 玩家
     * @param itemStack 物品栈
     * @return 如果提供了信息则返回true，否则返回false
     */
    boolean provideInfo(Player player, ItemStack itemStack);
    String getName();
    boolean isEnabled();
}