package com.mrlang.rotp_shadowworld.action.stand;

import com.github.standobyte.jojo.action.stand.TimeStop;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import com.github.standobyte.jojo.JojoMod;

public class SHADOWWORLDTimeStop extends TimeStop {

    private static final int HUMAN_DURATION = 100;     
    private static final int VAMPIRE_DURATION = 140;  
    private static final int PILLARMAN_DURATION = 150; 
    private static final int ZOMBIE_DURATION = 110;    

    public static final StandPose TIME_STOP = new StandPose("shadow_world_time_stop");
    public static final StandPose KNIFE_THROW = new StandPose("shadow_world_knife_throw");

    public SHADOWWORLDTimeStop(Builder builder) {
        super(builder
                .timeStopMaxTicks(HUMAN_DURATION, VAMPIRE_DURATION, PILLARMAN_DURATION, ZOMBIE_DURATION)
                .standPose(TIME_STOP)
                .timeStopLearningPerTick(0.2F)
                .timeStopDecayPerDay(0.1F)
        );
    }

    @Override
    public int getMaxTimeStopTicks(IStandPower standPower) {
        LivingEntity livingEntity = standPower.getUser();
        if (livingEntity == null) return HUMAN_DURATION;

        if (isVampire(livingEntity)) {
            return VAMPIRE_DURATION;
        } else if (isPillarman(livingEntity)) {
            return PILLARMAN_DURATION;
        } else if (isZombie(livingEntity)) {
            return ZOMBIE_DURATION;
        }
        return HUMAN_DURATION;
    }

    private boolean isVampire(LivingEntity entity) {
        return ModPowers.VAMPIRISM.get().isHighOnBlood(entity);
    }

    private boolean isPillarman(LivingEntity entity) {
        return ModPowers.PILLAR_MAN.get().isHighLifeForce(entity);
    }

    private boolean isZombie(LivingEntity entity) {
        return ModPowers.ZOMBIE.get().isHighSaturation(entity);
    }

    @Override
    protected boolean autoSummonStand(IStandPower power) {
        return super.autoSummonStand(power)  || power.getResolveLevel()  < 2;
    }

    @Override
    public int getHoldDurationToFire(IStandPower power) {
        int baseDuration = super.getHoldDurationToFire(power);
        if (!power.isUserCreative()  && power.getUser()  != null) {
            LivingEntity user = power.getUser();
            float healthRatio = Math.max(0.3F,  user.getHealth()  / user.getMaxHealth());
            baseDuration = MathHelper.ceil(baseDuration  * healthRatio);
        }
        return shortedHoldDuration(power, baseDuration);
    }

    private int shortedHoldDuration(IStandPower power, int ticks) {
        return power.getResolveLevel()  >= 4 ? Math.max(ticks  / 2, 10) : ticks;
    }

    @Override
    public boolean cancelHeldOnGettingAttacked(IStandPower power, DamageSource dmgSource, float dmgAmount) {
        return dmgAmount > 5.0F;
    }
}