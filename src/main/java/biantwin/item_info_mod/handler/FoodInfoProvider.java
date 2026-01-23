package biantwin.item_info_mod.handler;

import biantwin.item_info_mod.Config;
import biantwin.item_info_mod.util.MessageUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;

/**
 * 食物信息提供者
 */
public class FoodInfoProvider implements IItemInfoProvider {
    
    @Override
    public boolean provideInfo(Player player, ItemStack itemStack) {
        if (!isEnabled() || !itemStack.isEdible()) {
            return false;
        }
        
        FoodProperties foodProperties = itemStack.getItem().getFoodProperties();
        if (foodProperties != null) {
            int nutrition = foodProperties.getNutrition();
            float saturationModifier = foodProperties.getSaturationModifier();
            float actualSaturation = (float) nutrition * saturationModifier;
            
            String foodName = itemStack.getDisplayName().getString();
            String nutritionText = "§a[食物信息] §f食物的饱食度: §b" + nutrition;
            String saturationText = "§a[食物信息] §f食物的饱和度: §b" + actualSaturation;
            
            // 发送食物名称
            MessageUtils.sendCopyableMessageToPlayer(player, "§a[食物信息] §f你手持的食物是: §b" + foodName, foodName);
            
            // 发送饱食度
            MessageUtils.sendCopyableMessageToPlayer(player, nutritionText, String.valueOf(nutrition));
            
            // 发送饱和度
            MessageUtils.sendCopyableMessageToPlayer(player, saturationText, String.valueOf(actualSaturation));
            
            return true;
        }
        
        return false;
    }
    
    @Override
    public String getName() {
        return "foodInfo";
    }
    
    @Override
    public boolean isEnabled() {
        return Config.isEnableFoodInfo();
    }
}