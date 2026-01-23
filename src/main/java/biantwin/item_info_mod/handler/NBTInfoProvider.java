package biantwin.item_info_mod.handler;

import biantwin.item_info_mod.Config;
import biantwin.item_info_mod.util.MessageUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * NBT信息提供者
 */
public class NBTInfoProvider implements IItemInfoProvider {
    
    @Override
    public boolean provideInfo(Player player, ItemStack itemStack) {
        if (!isEnabled()) {
            return false;
        }
        
        String nbtData = (itemStack.getTag() != null) ? itemStack.getTag().toString() : "无NBT数据";
        String nbtText = "§a[物品NBT] §f当前手持物品的NBT数据:§b " + nbtData;
        MessageUtils.sendCopyableMessageToPlayer(player, nbtText, nbtData);
        
        return true;
    }
    
    @Override
    public String getName() {
        return "nbtInfo";
    }
    
    @Override
    public boolean isEnabled() {
        return Config.isEnableNBTInfo();
    }
}