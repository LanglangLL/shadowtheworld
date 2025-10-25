package com.mrlang.rotp_shadowworld;

import com.mrlang.rotp_shadowworld.init.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RotpSHADOWWORLDAddon.MOD_ID)
public class RotpSHADOWWORLDAddon {

    public static final String MOD_ID = "rotp_shadowworld";
    private static final Logger LOGGER = LogManager.getLogger();

    public RotpSHADOWWORLDAddon() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(modEventBus);

        InitEntities.ENTITIES.register(modEventBus);
        InitSounds.SOUNDS.register(modEventBus);
        InitStands.ACTIONS.register(modEventBus);
        InitStands.STANDS.register(modEventBus);
    }

    public static Logger getLogger() {
        return LOGGER;
    }

}
