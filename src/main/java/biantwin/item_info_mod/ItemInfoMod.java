package biantwin.item_info_mod;

import biantwin.item_info_mod.handler.CommandHandler;
import biantwin.item_info_mod.handler.InputHandler;
import net.minecraft.client.KeyMapping;
import net.minecraft.commands.Commands;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.lwjgl.glfw.GLFW;


@Mod(ItemInfoMod.MOD_ID)
public class ItemInfoMod {

    public static final String MOD_ID = "item_info_mod";

    // 定义按键映射
    public static KeyMapping itemInfoKey;

    public ItemInfoMod() {
        MinecraftForge.EVENT_BUS.register(this);

        // 初始化按键映射
        itemInfoKey = new KeyMapping(
                "key.iteminfo.showinfo", // 按键名称
                GLFW.GLFW_KEY_B,         // 默认按键 B
                "key.categories.misc"    // 按键分类
        );

        // 注册配置
        Config.register();
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.Key event) {
        // 检查是否按下了B键
        if (itemInfoKey.consumeClick()) {
            InputHandler.handleItemInfoRequest();
        }
    }

    // 监听配置变化事件
    @SubscribeEvent
    public void onConfigChanged(final ModConfigEvent event) {
        Config.sync();
    }

    // 注册命令
    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        var dispatcher = event.getDispatcher();
        dispatcher.register(
                Commands.literal("iteminfo")
                        .then(Commands.literal("item")
                                .then(Commands.literal("on")
                                        .executes(CommandHandler::enableItemInfo))
                                .then(Commands.literal("off")
                                        .executes(CommandHandler::disableItemInfo)))
                        .then(Commands.literal("food")
                                .then(Commands.literal("on")
                                        .executes(CommandHandler::enableFoodInfo))
                                .then(Commands.literal("off")
                                        .executes(CommandHandler::disableFoodInfo)))
                        .then(Commands.literal("nbt")
                                .then(Commands.literal("on")
                                        .executes(CommandHandler::enableNBTInfo))
                                .then(Commands.literal("off")
                                        .executes(CommandHandler::disableNBTInfo)))
                        .then(Commands.literal("name")
                                .then(Commands.literal("on")
                                        .executes(CommandHandler::enableItemName))
                                .then(Commands.literal("off")
                                        .executes(CommandHandler::disableItemName)))
                        .executes(CommandHandler::showStatus)
        );
    }

    // 注册按键映射 - 这个方法会在MOD总线上调用
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
            event.register(itemInfoKey);
        }
    }

}