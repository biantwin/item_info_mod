package biantwin.item_info_mod.info_provider;

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
            
            String nutritionText = "§a[饱食度] §f食物的饱食度: §b" + nutrition;
            String saturationText = "§a[饱和度] §f食物的饱和度: §b" + actualSaturation;
            
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