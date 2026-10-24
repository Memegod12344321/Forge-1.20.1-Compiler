package com.memegod.lastprice.client;

import com.memegod.lastprice.LastPrice;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LastPrice.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class TitleMenuText {
    private TitleMenuText() {}

    @SubscribeEvent
    public static void onTitleScreenInit(ScreenEvent.Init.Post event) {
        if (!(event.getScreen() instanceof TitleScreen)) return;
        for (var child : event.getListenersList()) {
            if (!(child instanceof AbstractButton button)) continue;
            String label = button.getMessage().getString();
            if (label.equalsIgnoreCase("Singleplayer")) {
                button.setMessage(Component.literal("Singleplayer — There is still hope."));
                button.setWidth(Math.min(260, button.getWidth() + 40));
            } else if (label.equalsIgnoreCase("Multiplayer")) {
                button.setMessage(Component.literal("Multiplayer — Friends can't help you."));
                button.setWidth(Math.min(260, button.getWidth() + 40));
            }
        }
    }
}
