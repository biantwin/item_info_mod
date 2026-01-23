package biantwin.item_info_mod.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

/**
 * 消息工具类，用于统一处理消息创建和发送逻辑
 */
public class MessageUtils {
    
    /**
     * 创建可点击复制的消息组件
     *
     * @param text 要显示的文本
     * @param copyText 要复制到剪贴板的文本
     * @return 构建好的消息组件
     */
    public static MutableComponent createCopyableMessage(String text, String copyText) {
        return Component.literal(text)
                .withStyle(ChatFormatting.GREEN)
                .withStyle(style -> style
                        .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, copyText))
                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                Component.literal("点击复制到剪贴板")))
                );
    }
    
    /**
     * 发送消息给玩家
     *
     * @param player 玩家对象
     * @param message 要发送的消息
     */
    public static void sendMessageToPlayer(Player player, String message) {
        player.sendSystemMessage(Component.literal(message));
    }

    /**
     * 发送可点击复制的消息给玩家
     *
     * @param player 玩家对象
     * @param text 要显示的文本
     * @param copyText 要复制到剪贴板的文本
     */
    public static void sendCopyableMessageToPlayer(Player player, String text, String copyText) {
        MutableComponent component = createCopyableMessage(text, copyText);
        player.sendSystemMessage(component);
    }
}