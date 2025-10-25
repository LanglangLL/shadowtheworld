package com.mrlang.rotp_shadowworld.init;

import java.util.function.Supplier;

import com.mrlang.rotp_shadowworld.RotpSHADOWWORLDAddon;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.util.mc.MultiSoundEvent;
import com.github.standobyte.jojo.util.mc.OstSoundList;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, RotpSHADOWWORLDAddon.MOD_ID);

    public static final RegistryObject<SoundEvent> SHADOW_WORLD_PUNCH_LIGHT = SOUNDS.register("shadow_world_punch_light",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "shadow_world_punch_light")));

    public static final RegistryObject<SoundEvent> SHADOW_WORLD_UNSUMMON = SOUNDS.register("shadow_world_unsummon",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "shadow_world_unsummon")));

    public static final Supplier<SoundEvent> SHADOW_WORLD_PUNCH_HEAVY = ModSounds.STAND_PUNCH_HEAVY;

    public static final RegistryObject<SoundEvent> SHADOW_WORLD_BARRAGE = SOUNDS.register("shadow_world_barrage",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "shadow_world_barrage")));

    public static final RegistryObject<SoundEvent> SDDIO_EVOLUTION = SOUNDS.register("sddio_evolution",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "sddio_evolution")));

    public static final RegistryObject<SoundEvent> SDDIO_SUPERPUNCH = SOUNDS.register("sddio_superpunch",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "sddio_superpunch")));

    public static final RegistryObject<SoundEvent> SHADOW_WORLD_SUPER_PUNCH = SOUNDS.register("shadow_world_superpunch",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "shadow_world_superpunch")));

    public static final RegistryObject<SoundEvent> SHADOW_WORLD_SUMMON = SOUNDS.register("shadow_world_summon",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "shadow_world_summon")));

    public static final RegistryObject<SoundEvent> SHADOW_WORLD_MUDA = SOUNDS.register("shadow_world_muda",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "shadow_world_muda")));

    public static final RegistryObject<SoundEvent> SHADOW_WORLD_MUDA_LONG = SOUNDS.register("shadow_world_muda_long",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "shadow_world_muda_long")));

    public static final RegistryObject<SoundEvent> SHADOW_WORLD_MUDA_MUDA_MUDA = SOUNDS.register("shadow_world_muda_muda_muda",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "shadow_world_muda_muda_muda")));

    public static final RegistryObject<SoundEvent> SHADOW_WORLD_TIME_STOP = SOUNDS.register("shadow_world_time_stop",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "shadow_world_time_stop")));

    public static final RegistryObject<SoundEvent> SHADOW_WORLD_TIME_RESUME = SOUNDS.register("shadow_world_time_resume",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "shadow_world_time_resume")));

    public static final RegistryObject<SoundEvent> SHADOW_WORLD_TIME_STOP_BLINK = SOUNDS.register("shadow_world_time_stop_blink",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "shadow_world_time_stop_blink")));

    public static final RegistryObject<SoundEvent> SDDIO_SHADOW_WORLD = SOUNDS.register("sddio_shadow_world",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "sddio_shadow_world")));

    public static final RegistryObject<SoundEvent> SDDIO_MUDA = SOUNDS.register("sddio_muda",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "sddio_muda")));

    public static final RegistryObject<SoundEvent> SDDIO_MUDA_MUDA = SOUNDS.register("sddio_muda_muda",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "sddio_muda_muda")));

    public static final RegistryObject<SoundEvent> SDDIO_WRY = SOUNDS.register("sddio_wry",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "sddio_wry")));

    public static final RegistryObject<SoundEvent> SDDIO_DIE = SOUNDS.register("sddio_die",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "sddio_die")));

    public static final RegistryObject<SoundEvent> SDDIO_THIS_IS_SHADOW_WORLD = SOUNDS.register("sddio_this_is_shadow_world",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "sddio_this_is_shadow_world")));

    public static final RegistryObject<SoundEvent> SDDIO_TIME_STOP = SOUNDS.register("sddio_time_stop",
            () -> new MultiSoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "sddio_toki_yo_tomare"), new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "sddio_tomare_toki_yo")));

    public static final RegistryObject<SoundEvent> SDDIO_TIME_RESUMES = SOUNDS.register("sddio_time_resumes",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "sddio_time_resumes")));

    public static final RegistryObject<SoundEvent> SDDIO_TIMES_UP = SOUNDS.register("sddio_times_up",
            () -> new MultiSoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "sddio_time_resumes"),
                    new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "sddio_times_up"), new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "sddio_zero")));

    public static final RegistryObject<SoundEvent> SDDIO_5_SECONDS = SOUNDS.register("sddio_5_seconds",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "sddio_5_seconds")));

    public static final RegistryObject<SoundEvent> SDDIO_ONE_MORE = SOUNDS.register("sddio_one_more",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "sddio_one_more")));

    public static final RegistryObject<SoundEvent> SDDIO_CANT_MOVE = SOUNDS.register("sddio_cant_move",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "sddio_cant_move")));

    public static final RegistryObject<SoundEvent> REVOLVER_SHOT = SOUNDS.register("revolver_shot",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "revolver_shot")));

    public static final RegistryObject<SoundEvent> REVOLVER_NO_AMMO = SOUNDS.register("revolver_no_ammo",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "revolver_no_ammo")));

    public static final RegistryObject<SoundEvent> SHADOW_WORLD_KNIFE_THROW = SOUNDS.register("shadow_world_knife_throw",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "shadow_world_knife_throw")));

    public static final RegistryObject<SoundEvent> SHADOW_WORLD_KNIVES_THROW = SOUNDS.register("shadow_world_knives_throw",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "shadow_world_knives_throw")));

    public static final RegistryObject<SoundEvent> SDDIO_KNIVES_THROW_VOICE = SOUNDS.register("sddio_knives_throw_voice",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "sddio_knives_throw_voice")));

    public static final RegistryObject<SoundEvent> SDDIO_KNIFE_THROW_VOICE = SOUNDS.register("sddio_knife_throw_voice",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "sddio_knife_throw_voice")));

    public static final RegistryObject<SoundEvent> EVOLUTIONBGM = SOUNDS.register("shadow_world_ost",
            () -> new SoundEvent(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "shadow_world_ost")));

    static final OstSoundList SHADOW_WORLD_OST = new OstSoundList(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "shadow_world_ost"), SOUNDS);

}
