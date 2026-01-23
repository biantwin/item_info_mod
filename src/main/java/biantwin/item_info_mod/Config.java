package biantwin.item_info_mod;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    private static ForgeConfigSpec.BooleanValue enableItemInfoSpec;
    private static ForgeConfigSpec.BooleanValue enableFoodInfoSpec;
    private static ForgeConfigSpec.BooleanValue enableNBTInfoSpec;
    private static ForgeConfigSpec.BooleanValue enableItemNameSpec;
    private static ForgeConfigSpec.BooleanValue enableDurabilityInfoSpec; // 新增耐久信息配置
    
    public static boolean enableItemInfo = true;
    public static boolean enableFoodInfo = true;
    public static boolean enableNBTInfo = false;
    public static boolean enableItemName = true;
    public static boolean enableDurabilityInfo = true; // 新增耐久信息配置

    public static final ForgeConfigSpec SPEC;
    private static final Config CONFIG;

    static {
        final Pair<Config, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(Config::new);
        CONFIG = pair.getLeft();
        SPEC = pair.getRight();
    }

    public Config(ForgeConfigSpec.Builder builder) {
        enableItemInfoSpec = builder
                .comment("是否启用物品信息功能（总开关）.")
                .translation("item_info_mod.config.enableItemInfo")
                .define("enableItemInfo", true);
        
        enableFoodInfoSpec = builder
                .comment("Whether to show food information (saturation, nutrition, etc.) when holding food items.")
                .translation("item_info_mod.config.enableFoodInfo")
                .define("enableFoodInfo", true);
        
        enableNBTInfoSpec = builder
                .comment("Whether to show NBT data of items.")
                .translation("item_info_mod.config.enableNBTInfo")
                .define("enableNBTInfo", false);
        
        enableItemNameSpec = builder
                .comment("Whether to show item name for non-food items.")
                .translation("item_info_mod.config.enableItemName")
                .define("enableItemName", true);
        
        // 新增耐久信息配置
        enableDurabilityInfoSpec = builder
                .comment("Whether to show item durability information when holding damageable items.")
                .translation("item_info_mod.config.enableDurabilityInfo")
                .define("enableDurabilityInfo", true);
    }

    public static void register() {
        // 配置将在ItemInfoMod构造函数中通过FMLJavaModLoadingContext注册
        // 此方法现在为空，因为我们直接访问SPEC字段
    }

    public static boolean isEnableItemInfo() {
        try {
            return enableItemInfoSpec != null ? enableItemInfoSpec.get() : enableItemInfo;
        } catch (Exception e) {
            // 如果配置未初始化，返回静态默认值
            return enableItemInfo;
        }
    }
    
    public static boolean isEnableFoodInfo() {
        try {
            return enableFoodInfoSpec != null ? enableFoodInfoSpec.get() : enableFoodInfo;
        } catch (Exception e) {
            // 如果配置未初始化，返回静态默认值
            return enableFoodInfo;
        }
    }
    
    public static boolean isEnableNBTInfo() {
        try {
            return enableNBTInfoSpec != null ? enableNBTInfoSpec.get() : enableNBTInfo;
        } catch (Exception e) {
            // 如果配置未初始化，返回静态默认值
            return enableNBTInfo;
        }
    }
    
    public static boolean isEnableItemName() {
        try {
            return enableItemNameSpec != null ? enableItemNameSpec.get() : enableItemName;
        } catch (Exception e) {
            // 如果配置未初始化，返回静态默认值
            return enableItemName;
        }
    }
    
    // 新增耐久信息相关方法
    public static boolean isEnableDurabilityInfo() {
        try {
            return enableDurabilityInfoSpec != null ? enableDurabilityInfoSpec.get() : enableDurabilityInfo;
        } catch (Exception e) {
            // 如果配置未初始化，返回静态默认值
            return enableDurabilityInfo;
        }
    }
    
    public static void setEnableDurabilityInfo(boolean value) {
        enableDurabilityInfo = value;
    }
    
    public static void sync() {
        enableItemInfo = enableItemInfoSpec.get();
        enableFoodInfo = enableFoodInfoSpec.get();
        enableNBTInfo = enableNBTInfoSpec.get();
        enableItemName = enableItemNameSpec.get();
        enableDurabilityInfo = enableDurabilityInfoSpec.get(); // 添加同步
    }
    
    public static void setEnableItemInfo(boolean value) {
        enableItemInfo = value;
    }
    
    public static void setEnableFoodInfo(boolean value) {
        enableFoodInfo = value;
    }
    
    public static void setEnableNBTInfo(boolean value) {
        enableNBTInfo = value;
    }
    
    public static void setEnableItemName(boolean value) {
        enableItemName = value;
    }
}