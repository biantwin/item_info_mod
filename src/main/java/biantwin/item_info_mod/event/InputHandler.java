package biantwin.item_info_mod.event;

import biantwin.item_info_mod.Config;
import biantwin.item_info_mod.ItemInfoManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class InputHandler {
    
    /**
     * 处理物品信息显示请求
     */
    public static void handleItemInfoRequest() {
        if (!Config.isEnableItemInfo()) {
            return;
        }
        
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        
        ItemStack heldItem = player.getMainHandItem();
        if (heldItem.isEmpty()) {
            return;
        }
        
        boolean displayedAnyInfo = ItemInfoManager.displayItemInfo(player, heldItem);
        
        if (!displayedAnyInfo) {
            player.displayClientMessage(net.minecraft.network.chat.Component.literal("§c[物品信息] §f没有可用的信息要显示"), false);
        }
    }
}