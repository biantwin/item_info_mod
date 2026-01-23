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

    public static KeyMapping itemInfoKey;

    public ItemInfoMod() {
        MinecraftForge.EVENT_BUS.register(this);

        itemInfoKey = new KeyMapping(
                "key.iteminfo.showinfo", // 按键名称
                GLFW.GLFW_KEY_B,         // 默认按键 B
                "key.categories.misc"    // 按键分类
        );

        Config.register();
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.Key event) {
        if (itemInfoKey.consumeClick()) {
            InputHandler.handleItemInfoRequest();
        }
    }

    @SubscribeEvent
    public void onConfigChanged(final ModConfigEvent event) {
        Config.sync();
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        var dispatcher = event.getDispatcher();
        dispatcher.register(
                Commands.literal("iteminfo")
                        .then(Commands.literal("item")
                                .then(Commands.literal("true")
                                        .executes(CommandHandler::enableItemInfo))
                                .then(Commands.literal("false")
                                        .executes(CommandHandler::disableItemInfo)))
                        .then(Commands.literal("food")
                                .then(Commands.literal("true")
                                        .executes(CommandHandler::enableFoodInfo))
                                .then(Commands.literal("false")
                                        .executes(CommandHandler::disableFoodInfo)))
                        .then(Commands.literal("nbt")
                                .then(Commands.literal("true")
                                        .executes(CommandHandler::enableNBTInfo))
                                .then(Commands.literal("false")
                                        .executes(CommandHandler::disableNBTInfo)))
                        .then(Commands.literal("durability")  // 新增耐久命令
                                .then(Commands.literal("true")
                                        .executes(CommandHandler::enableDurabilityInfo))
                                .then(Commands.literal("false")
                                        .executes(CommandHandler::disableDurabilityInfo)))
                        .then(Commands.literal("name")
                                .then(Commands.literal("true")
                                        .executes(CommandHandler::enableItemName))
                                .then(Commands.literal("false")
                                        .executes(CommandHandler::disableItemName)))
                        .executes(CommandHandler::showStatus)
        );
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
            event.register(itemInfoKey);
        }
    }

}