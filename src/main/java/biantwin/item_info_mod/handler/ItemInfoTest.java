package biantwin.item_info_mod.handler;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

/**
 * 测试类，用于验证重构后的架构是否正常工作
 */
public class ItemInfoTest {
    
    public static void testArchitecture() {
        System.out.println("=== 物品信息模块架构测试 ===");
        
        System.out.println("注册的信息提供者:");
        for (IItemInfoProvider provider : ItemInfoManager.getProviders()) {
            System.out.println("- " + provider.getName() + " (enabled: " + provider.isEnabled() + ")");
        }
        
        System.out.println("架构测试完成！");
    }
}