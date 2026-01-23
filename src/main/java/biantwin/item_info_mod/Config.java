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
    
    public static boolean enableItemInfo = true;
    public static boolean enableFoodInfo = true;
    public static boolean enableNBTInfo = false;
    public static boolean enableItemName = true;

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
    }

    public static void register() {
        // 配置将在ItemInfoMod构造函数中通过FMLJavaModLoadingContext注册
        // 此方法现在为空，因为我们直接访问SPEC字段
    }

    public static boolean isEnableItemInfo() {
        return enableItemInfoSpec.get();
    }
    
    public static boolean isEnableFoodInfo() {
        return enableFoodInfoSpec.get();
    }
    
    public static boolean isEnableNBTInfo() {
        return enableNBTInfoSpec.get();
    }
    
    public static boolean isEnableItemName() {
        return enableItemNameSpec.get();
    }
    
    public static void sync() {
        enableItemInfo = enableItemInfoSpec.get();
        enableFoodInfo = enableFoodInfoSpec.get();
        enableNBTInfo = enableNBTInfoSpec.get();
        enableItemName = enableItemNameSpec.get();
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