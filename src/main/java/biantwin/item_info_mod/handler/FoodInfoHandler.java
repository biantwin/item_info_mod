package biantwin.item_info_mod.handler;

import biantwin.item_info_mod.Config;
import biantwin.item_info_mod.util.MessageUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
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
     */
    public static boolean displayFoodInfo(Player player, ItemStack heldItem) {
        if (!Config.isEnableFoodInfo() || !heldItem.isEdible()) {
            return false;
        }
        
        FoodProperties foodProperties = heldItem.getItem().getFoodProperties();
        if (foodProperties != null) {
            int nutrition = foodProperties.getNutrition();
            float saturationModifier = foodProperties.getSaturationModifier();
            float actualSaturation = (float) nutrition * saturationModifier;
            
            String foodName = heldItem.getDisplayName().getString();
            String nutritionText = "§a[食物信息] §f食物的饱食度: §b" + nutrition;
            String saturationText = "§a[食物信息] §f食物的饱和度: §b" + actualSaturation;
            
            // 发送食物名称
            MessageUtils.sendCopyableMessageToPlayer(player, "§a[食物信息] §f你手持的食物是: §b" + foodName, foodName);
            
            // 发送饱食度
            MessageUtils.sendCopyableMessageToPlayer(player, nutritionText, String.valueOf(nutrition));
            
            // 发送饱和度
            MessageUtils.sendCopyableMessageToPlayer(player, saturationText, String.valueOf(actualSaturation));
            
            return true;
        } else {
            return false; // 不显示消息，因为物品不可食用
        }
    }
}