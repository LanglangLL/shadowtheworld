package com.mrlang.rotp_shadowworld.action.stand;

import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.punch.StandEntityPunch;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;
import com.mrlang.rotp_shadowworld.client.render.entity.model.stand.SHADOWWORLDModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber
public class SHADOWWORLDStunPunch extends StandEntityHeavyAttack {

    private static final Map<UUID, Integer> knockedDownPlayers = new HashMap<>();
    private static final Map<UUID, Boolean> wasSwimming = new HashMap<>();
    private static final Map<UUID, Integer> bleedingMobs = new HashMap<>();

    public SHADOWWORLDStunPunch(Builder builder) {
        super(builder.standPose(SHADOWWORLDModel.STUN_PUNCH));
    }

    @Override
    public int getStandWindupTicks(IStandPower power, StandEntity stand) {
        return 7;
    }

    @Override
    public int getStandRecoveryTicks(IStandPower power, StandEntity stand) {
        return 8;
    }

    @Override
    public StandEntityPunch punchEntity(StandEntity stand, Entity target, StandEntityDamageSource dmgSource) {
        return new SHADOWWORLDStunPunchInstance(stand, target, dmgSource)
                .copyProperties(super.punchEntity(stand,  target, dmgSource))
                .addKnockback(0.5F)
                .impactSound(ModSounds.THE_WORLD_PUNCH_HEAVY_ENTITY);
    }

    @Override
    public void standPerform(World world, StandEntity stand, IStandPower power, StandEntityTask task) {
        super.standPerform(world,  stand, power, task);
        if (!world.isClientSide  && stand.isAlive())  {
             {
                power.setStandManifestation(null);
                if (!stand.removed)  {
                    stand.remove();
                }
                LivingEntity user = stand.getUser();
                if (user != null) {
                    user.stopUsingItem();
                }
            };
        }
    }

    public static class SHADOWWORLDStunPunchInstance extends StandEntityPunch {
        public SHADOWWORLDStunPunchInstance(StandEntity stand, Entity target, StandEntityDamageSource dmgSource) {
            super(stand, target, dmgSource);
        }

        @Override
        protected void afterAttack(StandEntity stand, Entity target, StandEntityDamageSource dmgSource,
                                   StandEntityTask task, boolean hurt, boolean killed) {
            if (!stand.level.isClientSide()  && hurt) {
                if (target instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) target;
                    wasSwimming.put(player.getUUID(),  player.isSwimming());

                    player.addEffect(new  EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 10, false, false, true));
                    player.addEffect(new  EffectInstance(Effects.DIG_SLOWDOWN, 100, 10, false, false, true));
                    player.addEffect(new  EffectInstance(Effects.BLINDNESS, 100, 0, false, false, true));
                    player.addEffect(new  EffectInstance(Effects.CONFUSION, 100, 0, false, false, true));
                    player.addEffect(new  EffectInstance(Effects.DAMAGE_RESISTANCE, 100, 10, false, false, false));

                    player.setPose(Pose.SWIMMING);
                    player.setSwimming(true);
                    player.setDeltaMovement(0,  0, 0);

                    knockedDownPlayers.put(player.getUUID(),  100);

                    if (player.isPassenger())  {
                        player.stopRiding();
                    }
                } else if (target instanceof LivingEntity) {
                    LivingEntity mob = (LivingEntity) target;
                    bleedingMobs.put(mob.getUUID(),  5);
                    mob.addEffect(new  EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 9, false, false, true));
                }
            }
            super.afterAttack(stand,  target, dmgSource, task, hurt, killed);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase  == TickEvent.Phase.END && !event.player.level.isClientSide())  {
            PlayerEntity player = event.player;
            UUID uuid = player.getUUID();

            if (knockedDownPlayers.containsKey(uuid))  {
                int remainingTicks = knockedDownPlayers.get(uuid);

                if (remainingTicks > 0) {
                    if (player.getPose()  != Pose.SWIMMING) {
                        player.setPose(Pose.SWIMMING);
                    }
                    player.setSwimming(true);
                    player.setDeltaMovement(0,  0, 0);
                    player.abilities.flying  = false;
                    knockedDownPlayers.put(uuid,  remainingTicks - 1);
                } else {
                    player.setPose(Pose.STANDING);
                    player.setSwimming(wasSwimming.getOrDefault(uuid,  false));
                    player.removeEffect(Effects.MOVEMENT_SLOWDOWN);
                    player.removeEffect(Effects.DIG_SLOWDOWN);
                    player.removeEffect(Effects.BLINDNESS);
                    player.removeEffect(Effects.CONFUSION);

                    knockedDownPlayers.remove(uuid);
                    wasSwimming.remove(uuid);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (!entity.level.isClientSide())  {
            UUID uuid = entity.getUUID();

            if (entity instanceof PlayerEntity && knockedDownPlayers.containsKey(uuid))  {
                PlayerEntity player = (PlayerEntity) entity;
                player.xxa  = 0;
                player.yya  = 0;
                player.zza  = 0;
            }

            if (bleedingMobs.containsKey(uuid))  {
                int remainingSeconds = bleedingMobs.get(uuid);
                if (remainingSeconds > 0) {
                    if (entity.tickCount  % 20 == 0) {
                        float healthPercent = entity.getHealth()  / entity.getMaxHealth();
                        float damagePercent = getDamagePercentByHealth(healthPercent);
                        float damage = entity.getMaxHealth()  * damagePercent;
                        entity.hurt(new  StandEntityDamageSource("stand", entity, null), damage);
                        bleedingMobs.put(uuid,  remainingSeconds - 1);
                    }
                } else {
                    bleedingMobs.remove(uuid);
                    entity.removeEffect(Effects.MOVEMENT_SLOWDOWN);
                }
            }
        }
    }

    private static float getDamagePercentByHealth(float healthPercent) {
        if (healthPercent >= 1.0f) {
            return 0.05f;
        } else if (healthPercent >= 0.85f) {
            return 0.03f;
        } else if (healthPercent >= 0.75f) {
            return 0.01f;
        } else if (healthPercent >= 0.5f) {
            return 0.005f;
        } else {
            return 0.003f;
        }
    }
}