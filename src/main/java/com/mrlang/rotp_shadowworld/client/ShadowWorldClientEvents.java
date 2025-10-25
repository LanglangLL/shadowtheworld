package com.mrlang.rotp_shadowworld.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "rotp_shadowworld", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ShadowWorldClientEvents {

    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Post event) {

    }
}