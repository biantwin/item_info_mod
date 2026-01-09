package biantwin.item_info_mod;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.server.command.CommandHelper;

public class item_info {
    public item_info(){
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ClientCommandHandler());
    }
    public void onLeftClick(PlayerInteractEvent.LeftClickBlock event){
        if(!Config.enableItemSaturation)
            return;
        Player player = event.getEntity();
    }
}
