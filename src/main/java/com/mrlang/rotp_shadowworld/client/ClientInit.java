package com.mrlang.rotp_shadowworld.client;

import com.mrlang.rotp_shadowworld.RotpSHADOWWORLDAddon;
import com.mrlang.rotp_shadowworld.client.render.entity.renderer.stand.SHADOWWORLDRenderer;
import com.mrlang.rotp_shadowworld.init.AddonStands;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = RotpSHADOWWORLDAddon.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {
    
    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(AddonStands.SHADOW_WORLD.getEntityType(), SHADOWWORLDRenderer::new);
    }
}
