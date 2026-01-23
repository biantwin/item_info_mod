package biantwin.item_info_mod.handler;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import java.util.ArrayList;
import java.util.List;

/**
 * 物品信息管理器，负责管理所有信息提供者并按顺序调用它们
 */
public class ItemInfoManager {
    private static final List<IItemInfoProvider> providers = new ArrayList<>();
    
    static {
        // 注册所有信息提供者
        // 注意：我们按照优先级顺序添加提供者，更具体的信息类型优先
        providers.add(new NBTInfoProvider());          // NBT信息
        providers.add(new DurabilityInfoProvider());   // 耐久信息
        providers.add(new FoodInfoProvider());         // 食物信息
        providers.add(new ItemNameInfoProvider());     // 物品名称
    }
    
    /**
     * 为指定物品显示信息
     * @param player 玩家
     * @param itemStack 物品栈
     * @return 如果至少有一个提供者成功提供信息则返回true
     */
    public static boolean displayItemInfo(Player player, ItemStack itemStack) {
        boolean displayedAnyInfo = false;
        for (IItemInfoProvider provider : providers) {
            if (provider.provideInfo(player, itemStack)) {
                displayedAnyInfo = true;
            }
        }
        return displayedAnyInfo;
    }
    public static void addProvider(IItemInfoProvider provider) {
        providers.add(provider);
    }
    public static void removeProvider(IItemInfoProvider provider) {
        providers.remove(provider);
    }
    public static List<IItemInfoProvider> getProviders() {
        return new ArrayList<>(providers);
    }
}