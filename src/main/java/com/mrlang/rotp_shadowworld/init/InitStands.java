package com.mrlang.rotp_shadowworld.init;


import com.github.standobyte.jojo.action.non_stand.NonStandAction;
import com.mrlang.rotp_shadowworld.RotpSHADOWWORLDAddon;
import com.mrlang.rotp_shadowworld.action.stand.*;
import com.github.standobyte.jojo.action.stand.TimeResume;
import com.github.standobyte.jojo.action.stand.TimeStop;
import com.mrlang.rotp_shadowworld.entity.stand.stands.SHADOWWORLDEntity;
import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.stand.*;
import com.github.standobyte.jojo.action.stand.StandEntityAction.Phase;
import com.github.standobyte.jojo.action.stand.TimeStopInstant;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.init.power.stand.ModStandsInit;
import com.github.standobyte.jojo.power.impl.stand.StandInstance.StandPart;
import com.github.standobyte.jojo.power.impl.stand.stats.TimeStopperStandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;


import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class InitStands {
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<Action<?>> ACTIONS = DeferredRegister.create(
            (Class<Action<?>>) ((Class<?>) Action.class), RotpSHADOWWORLDAddon.MOD_ID);
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<StandType<?>> STANDS = DeferredRegister.create(
            (Class<StandType<?>>) ((Class<?>) StandType.class), RotpSHADOWWORLDAddon.MOD_ID);




    public static final RegistryObject<StandEntityAction> SHADOW_WORLD_CIRCLE_KNIVES = ACTIONS.register(
            "shadow_world_circle_knives",
            () -> new CircleKnivesBarrier(new StandEntityAction.Builder()
                    .standPose(SingleKnifeThrow.KNIFE_THROW)
                    .standOffsetFront()
                    .resolveLevelToUnlock(4)
                    .autoSummonStand()
                    .holdType()
                    .staminaCost(600)
                    .cooldown(800)
                    .partsRequired(StandPart.MAIN_BODY, StandPart.ARMS)
                    .standSound(Phase.WINDUP, InitSounds.SDDIO_KNIVES_THROW_VOICE)
                    .shout(InitSounds.SHADOW_WORLD_KNIVES_THROW)
            )
    );

    public static final RegistryObject<StandEntityHeavyAttack> SHADOW_WORLD_STUN_PUNCH = ACTIONS.register("shadow_world_stun_punch",
            () -> new SHADOWWORLDStunPunch(new StandEntityHeavyAttack.Builder()
                    .standOffsetFromUser(0.75, 0.75, 0.2)
                    .punchSound(InitSounds.SHADOW_WORLD_PUNCH_HEAVY) 
                    .standPose(StandPose.HEAVY_ATTACK)
                    .resolveLevelToUnlock(4)
                    .shout(InitSounds.SDDIO_DIE)
                    .partsRequired(StandPart.ARMS)
                    .cooldown(240)
                    .staminaCost(200) 
            )
    );

    public static final RegistryObject<StandEntityAction> SHADOW_WORLD_EVOLUTION = ACTIONS.register(
            "shadow_world_evolution",
            () -> new SHADOWWORLDEvolution(new StandEntityAction.Builder()
                    .resolveLevelToUnlock(4)
                    .partsRequired(StandPart.MAIN_BODY)
             
            )
    );

    public static final RegistryObject<StandEntityHeavyAttack> SUPER_HEAVY_PUNCH = ACTIONS.register(
            "super_heavy_punch",
            () -> new SuperHeavyPunch(
                    new StandEntityHeavyAttack.Builder() 
                            .staminaCost(150)
                            .resolveLevelToUnlock(3)
                            .standWindupDuration(10)
                            .cooldown(60)
                            .shout(InitSounds.SDDIO_SUPERPUNCH)
                            .punchSound(InitSounds.SHADOW_WORLD_SUPER_PUNCH)
                            .partsRequired(StandPart.MAIN_BODY, StandPart.ARMS) 
            )
    );

    public static final RegistryObject<StandEntityAction> SHADOW_WORLD_SINGLE_KNIFE = ACTIONS.register(
            "shadow_world_single_knife",
            () -> new SingleKnifeThrow(new StandEntityAction.Builder()
                    .standWindupDuration(5)       
                    .staminaCost(90)        
                    .cooldown(25)           
                    .standPose(SingleKnifeThrow.KNIFE_THROW)
                    .standSound(Phase.WINDUP, InitSounds.SDDIO_KNIFE_THROW_VOICE)
                    .shout(InitSounds.SHADOW_WORLD_KNIFE_THROW)
                    .partsRequired(StandPart.ARMS) 
                    .standOffsetFront()      
            )
    );

    public static final RegistryObject<StandEntityAction> SHADOW_WORLD_MULTIPLE_KNIVES = ACTIONS.register(
            "shadow_world_multiple_knives",
            () -> new MultipleKnivesThrow(new StandEntityAction.Builder()
                    .standWindupDuration(5)
                    .staminaCost(180)
                    .cooldown(180)
                    .resolveLevelToUnlock(2)
                    .shiftVariationOf(SHADOW_WORLD_SINGLE_KNIFE)
                    .standSound(Phase.WINDUP, InitSounds.SDDIO_KNIVES_THROW_VOICE)
                    .shout(InitSounds.SHADOW_WORLD_KNIVES_THROW)
                    .partsRequired(StandPart.ARMS)

            )
    );

    public static final RegistryObject<StandEntityAction> SHADOW_WORLD_PUNCH = ACTIONS.register("shadow_world_punch",
            () -> new StandEntityLightAttack(new StandEntityLightAttack.Builder()
                    .standOffsetFromUser(0.75, 0.75, 0.2)
                    .punchSound(InitSounds.SHADOW_WORLD_PUNCH_LIGHT)
                    .standSound(Phase.WINDUP, InitSounds.SDDIO_MUDA)));

    public static final RegistryObject<StandEntityAction> SHADOW_WORLD_BARRAGE = ACTIONS.register(
            "shadow_world_barrage",
            () -> new TheWorldBarrage(new StandEntityMeleeBarrage.Builder()
                    .barrageHitSound(InitSounds.SHADOW_WORLD_BARRAGE)
                    .standSound(InitSounds.SHADOW_WORLD_MUDA_MUDA_MUDA)
                    .shout(InitSounds.SDDIO_MUDA_MUDA)
                    .cooldown(30) 
                    , InitSounds.SDDIO_WRY)
    );

    public static final RegistryObject<StandEntityHeavyAttack> SHADOW_WORLD_KNOCKBACK_PUNCH = ACTIONS.register("shadow_world_knockback_punch",
            () -> new SHADOWWORLDKnockbackPunch(new StandEntityHeavyAttack.Builder()
                    .standOffsetFromUser(0.75, 0.85, 0.2)
                    .resolveLevelToUnlock(1).isTrained()
                    .punchSound(InitSounds.SHADOW_WORLD_PUNCH_HEAVY)
                    .standPose(StandPose.HEAVY_ATTACK_FINISHER)
                    .standSound(Phase.WINDUP, InitSounds.SHADOW_WORLD_MUDA_LONG)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityHeavyAttack> SHADOW_WORLD_HEAVY_PUNCH = ACTIONS.register("shadow_world_heavy_punch",
            () -> new SHADOWWORLDHeavyPunch(new StandEntityHeavyAttack.Builder()
                    .standOffsetFromUser(0.75, 0.75, 0.2)
                    .punchSound(InitSounds.SHADOW_WORLD_PUNCH_HEAVY)
                    .standPose(StandPose.HEAVY_ATTACK)
                    .shout(InitSounds.SDDIO_DIE)
                    .partsRequired(StandPart.ARMS)
                    .setFinisherVariation(SHADOW_WORLD_KNOCKBACK_PUNCH)
                    .shiftVariationOf(SHADOW_WORLD_PUNCH).shiftVariationOf(SHADOW_WORLD_BARRAGE)));

    public static final RegistryObject<StandEntityAction> SHADOW_WORLD_BLOCK = ACTIONS.register("shadow_world_block",
            () -> new StandEntityBlock());

    public static final RegistryObject<TimeStop> SHADOW_WORLD_TIME_STOP = ACTIONS.register("shadow_world_time_stop",
            () -> new SHADOWWORLDTimeStop(new TimeStop.Builder().holdToFire(40, false).staminaCost(80).staminaCostTick(3.5F).heldWalkSpeed(0.7f)
                    .resolveLevelToUnlock(2).isTrained()
                    .ignoresPerformerStun()
                    .shout(InitSounds.SDDIO_SHADOW_WORLD)
                    .partsRequired(StandPart.MAIN_BODY)
                    .voiceLineWithStandSummoned(InitSounds.SDDIO_TIME_STOP).timeStopSound(InitSounds.SHADOW_WORLD_TIME_STOP)
                    .addTimeResumeVoiceLine(InitSounds.SDDIO_TIME_RESUMES, true).addTimeResumeVoiceLine(InitSounds.SDDIO_TIMES_UP, false)
                    .timeResumeSound(InitSounds.SHADOW_WORLD_TIME_RESUME)
                    .shaderEffect(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "shaders/post/time_stop_tw.json"), true)
                    .shaderEffect(new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "shaders/post/time_stop_tw_old.json"), false)));

    public static final RegistryObject<TimeResume> SHADOW_WORLD_TIME_RESUME = ACTIONS.register("shadow_world_time_resume",
            () -> new TimeResume(new StandAction.Builder().shiftVariationOf(SHADOW_WORLD_TIME_STOP)));
    public static final RegistryObject<TimeStopInstant> SHADOW_WORLD_TIME_STOP_BLINK = ACTIONS.register("shadow_world_ts_blink",
            () -> new SHADOWWORLDTimeStopInstant(new StandAction.Builder()
                    .resolveLevelToUnlock(2).isTrained()
                    .ignoresPerformerStun()
                    .partsRequired(StandPart.MAIN_BODY),
                    SHADOW_WORLD_TIME_STOP, InitSounds.SHADOW_WORLD_TIME_STOP_BLINK));


    public static final EntityStandRegistryObject<EntityStandType<TimeStopperStandStats>, StandEntityType<SHADOWWORLDEntity>> STAND_SHADOW_WORLD =
            new EntityStandRegistryObject<>("shadow_world",
                    STANDS,
                    () -> new EntityStandType.Builder<TimeStopperStandStats>()
                            .color(0xF28AFF)
                            .storyPartName(ModStandsInit.PART_3_NAME)
              
                            .leftClickHotbar(
                                    SHADOW_WORLD_PUNCH.get(),
                                    SHADOW_WORLD_BARRAGE.get(),
                                    SHADOW_WORLD_SINGLE_KNIFE.get(),
                                    SHADOW_WORLD_CIRCLE_KNIVES.get()
                            )
     
                            .rightClickHotbar(
                                    SHADOW_WORLD_BLOCK.get(),
                                    SHADOW_WORLD_TIME_STOP.get(),
                                    SUPER_HEAVY_PUNCH.get(),
                                    SHADOW_WORLD_STUN_PUNCH.get(),
                                    SHADOW_WORLD_EVOLUTION.get()
                            )

                            .defaultStats(TimeStopperStandStats.class,  new TimeStopperStandStats.Builder()
                                    .tier(6)
                                    .power(13.5)
                                    .speed(15.0)
                                    .range(10.0, 10.0)
                                    .durability(5.0)
                                    .precision(10)
                                    .build("SHADOW WORLD"))
                            .addSummonShout(InitSounds.SDDIO_SHADOW_WORLD)
                            .addOst(InitSounds.SHADOW_WORLD_OST)
                            .build(),
                    InitEntities.ENTITIES,
                    () -> new StandEntityType<SHADOWWORLDEntity>(SHADOWWORLDEntity::new, 0.7F, 2.15F)
                            .summonSound(InitSounds.SHADOW_WORLD_SUMMON)
                            .unsummonSound(InitSounds.SHADOW_WORLD_UNSUMMON)
            )
                    .withDefaultStandAttributes();
}
