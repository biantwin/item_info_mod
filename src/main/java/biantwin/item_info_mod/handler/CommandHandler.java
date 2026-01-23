package biantwin.item_info_mod.handler;

import biantwin.item_info_mod.Config;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

/**
 * 命令处理器，负责处理与物品信息相关的命令
 */
public class CommandHandler {
    
    public static int enableItemInfo(CommandContext<CommandSourceStack> ctx) {
        Config.setEnableItemInfo(true);
        ctx.getSource().sendSuccess(() -> Component.literal("§a物品信息功能已启用"), true);
        return 1;
    }
    public static int disableItemInfo(CommandContext<CommandSourceStack> ctx) {
        Config.setEnableItemInfo(false);
        ctx.getSource().sendSuccess(() -> Component.literal("§a物品信息功能已禁用"), true);
        return 1;
    }//物品信息



    public static int enableFoodInfo(CommandContext<CommandSourceStack> ctx) {
        Config.setEnableFoodInfo(true);
        ctx.getSource().sendSuccess(() -> Component.literal("§a食物信息显示已启用"), true);
        return 1;
    }
    public static int disableFoodInfo(CommandContext<CommandSourceStack> ctx) {
        Config.setEnableFoodInfo(false);
        ctx.getSource().sendSuccess(() -> Component.literal("§a食物信息显示已禁用"), true);
        return 1;
    }//食物信息



    public static int enableNBTInfo(CommandContext<CommandSourceStack> ctx) {
        Config.setEnableNBTInfo(true);
        ctx.getSource().sendSuccess(() -> Component.literal("§aNBT信息显示已启用"), true);
        return 1;
    }
    public static int disableNBTInfo(CommandContext<CommandSourceStack> ctx) {
        Config.setEnableNBTInfo(false);
        ctx.getSource().sendSuccess(() -> Component.literal("§aNBT信息显示已禁用"), true);
        return 1;
    }//nbt
    


    public static int enableItemName(CommandContext<CommandSourceStack> ctx) {
        Config.setEnableItemName(true);
        ctx.getSource().sendSuccess(() -> Component.literal("§a物品名称显示已启用"), true);
        return 1;
    }
    public static int disableItemName(CommandContext<CommandSourceStack> ctx) {
        Config.setEnableItemName(false);
        ctx.getSource().sendSuccess(() -> Component.literal("§a物品名称显示已禁用"), true);
        return 1;
    }//物品名称



    public static int showStatus(CommandContext<CommandSourceStack> ctx) {
        String itemState = Config.isEnableItemInfo() ? "启用" : "禁用";
        String foodState = Config.isEnableFoodInfo() ? "启用" : "禁用";
        String nbtState = Config.isEnableNBTInfo() ? "启用" : "禁用";
        String nameState = Config.isEnableItemName() ? "启用" : "禁用";
        ctx.getSource().sendSuccess(
                () -> Component.literal("\n§a物品信息总开关: §b" + itemState + "\n§a食物信息: §b" + foodState + "\n§aNBT信息: §b" + nbtState + "\n§a物品名称: §b" + nameState), true);
        return 1;
    }
}