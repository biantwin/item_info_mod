package biantwin.item_info_mod.handler;

import biantwin.item_info_mod.Config;
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
        
        boolean displayedAnyInfo = false;
        
        if (NBTInfoHandler.displayNBTInfo(player, heldItem)) {
            displayedAnyInfo = true;
        } else if (FoodInfoHandler.displayFoodInfo(player, heldItem)) {
            displayedAnyInfo = true;
        } else if (ItemNameHandler.displayItemName(player, heldItem)) {
            displayedAnyInfo = true;
        }
        
        if (!displayedAnyInfo) {
            player.displayClientMessage(net.minecraft.network.chat.Component.literal("§c[物品信息] §f没有可用的信息要显示"), false);
        }
    }
}