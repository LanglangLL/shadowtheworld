package com.mrlang.rotp_shadowworld.action.stand;

import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import com.mrlang.rotp_shadowworld.init.InitSounds;

import java.util.List;

public class SuperHeavyPunch extends StandEntityHeavyAttack {
    private LivingEntity lockedTarget = null;
    private boolean hasCollided = false;
    private static final double PLAYER_SAFE_DISTANCE = 5.0;

    public SuperHeavyPunch(Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(World world, StandEntity stand, IStandPower power, StandEntityTask task) {
        super.standPerform(world,  stand, power, task);

        if (!world.isClientSide())  {
            if (!hasCollided) {
                checkAndAttackCollisions(world, stand, power);
            }
            spawnAttackEffects(world, stand);

            if (stand.isAlive())  {
                power.setStandManifestation(null);
                stand.remove();
            }
        }
    }

    @Override
    public void standTickWindup(World world, StandEntity stand, IStandPower power, StandEntityTask task) {
        if (task.getTick()  == 0) {
            lockedTarget = findTargetInFront(stand, power.getUser());
            hasCollided = false;

            LivingEntity user = power.getUser();
            if (user != null && stand.distanceTo(user)  <= PLAYER_SAFE_DISTANCE) {
                stand.setNoPhysics(true);
                stand.noPhysics  = true;
            } else {
                stand.setNoPhysics(false);
                stand.noPhysics  = false;
            }
        }

        if (!world.isClientSide()  || stand.isManuallyControlled())  {
            if (!hasCollided) {
                moveStandTowardsTarget(stand);
                checkAndAttackCollisions(world, stand, power);
            }
        }

        super.standTickWindup(world,  stand, power, task);
    }

    private LivingEntity findTargetInFront(StandEntity stand, LivingEntity user) {
        Vector3d lookVec = stand.getLookAngle();
        Vector3d standPos = stand.position();
        double range = stand.getMaxRange();

        final double COS_30 = Math.cos(Math.toRadians(30));

        List<LivingEntity> targets = stand.level.getEntitiesOfClass(
                LivingEntity.class,
                stand.getBoundingBox().inflate(range),
                e -> e != stand && e != user && e.isAlive()  &&
                        e.distanceTo(user)  > PLAYER_SAFE_DISTANCE
        );

        return targets.stream()
                .filter(e -> {
                    Vector3d toTarget = e.position().subtract(standPos).normalize();
                    return lookVec.dot(toTarget)  > COS_30;
                })
                .min((e1, e2) -> Float.compare(
                        e1.distanceTo(stand),
                        e2.distanceTo(stand)))
                .orElse(null);
    }

    private void moveStandTowardsTarget(StandEntity stand) {
        Vector3d moveVec = (lockedTarget != null)
                ? lockedTarget.position().subtract(stand.position()).normalize()
                : stand.getLookAngle();

        stand.setDeltaMovement(moveVec.scale(7));
    }

    private void checkAndAttackCollisions(World world, StandEntity stand, IStandPower power) {
        LivingEntity user = power.getUser();
        if (user == null || stand.distanceTo(user)  <= PLAYER_SAFE_DISTANCE) {
            return;
        }

        List<Entity> entities = world.getEntities(
                stand,
                stand.getBoundingBox().expandTowards(stand.getDeltaMovement()).inflate(0.5),
                e -> e != user && e.isAlive()  && e.distanceTo(user)  > PLAYER_SAFE_DISTANCE
        );

        boolean hitSomething = false;

        for (Entity entity : entities) {
            if (entity instanceof LivingEntity) {
                attackLivingEntity(stand, (LivingEntity)entity, power);
                hitSomething = true;
            }
        }

        for (int i = 0; i < 3; i++) {
            BlockPos checkPos = new BlockPos(
                    stand.position().add(stand.getLookAngle().scale(i  * 0.5)));

            if (!world.isEmptyBlock(checkPos)  &&
                    checkPos.distSqr(user.blockPosition())  > PLAYER_SAFE_DISTANCE * PLAYER_SAFE_DISTANCE) {
                attackBlock(stand, checkPos, world);
                hitSomething = true;
                break;
            }
        }

        if (hitSomething) {
            hasCollided = true;
            stand.setDeltaMovement(Vector3d.ZERO);
        }
    }

    private void attackLivingEntity(StandEntity stand, LivingEntity target, IStandPower power) {
        StandEntityDamageSource damageSource = new StandEntityDamageSource("stand_heavy_punch", stand, power);

        float damage = (float) stand.getAttackDamage()  * 1.5F;
        target.hurt(damageSource,  damage);

        target.addEffect(new  EffectInstance(Effects.CONFUSION, 100, 1));
        target.addEffect(new  EffectInstance(Effects.MOVEMENT_SLOWDOWN, 120, 2));
        target.addEffect(new  EffectInstance(Effects.BLINDNESS, 60, 0));

        Vector3d knockback = stand.getLookAngle().scale(0.8);
        target.push(knockback.x,  knockback.y + 0.3, knockback.z);

        target.playSound(ModSounds.THE_WORLD_PUNCH_HEAVY_TS_IMPACT.get(),
                1.5F, 0.7F + stand.getRandom().nextFloat()  * 0.6F);
    }

    private void attackBlock(StandEntity stand, BlockPos pos, World world) {
        BlockState state = world.getBlockState(pos);
        Direction face = Direction.getNearest(
                stand.getLookAngle().x,
                stand.getLookAngle().y,
                stand.getLookAngle().z);

        if (state.getDestroySpeed(world,  pos) < 60.0F) {
            world.destroyBlock(pos,  true);
        }
    }

    private void spawnAttackEffects(World world, StandEntity stand) {
        if (world.isClientSide())  {
            Vector3d pos = stand.position()
                    .add(stand.getLookAngle().scale(0.7))
                    .add(0, stand.getBbHeight()  * 0.7, 0);

            for (int i = 0; i < 5; i++) {
                world.addParticle(ParticleTypes.EXPLOSION,
                        pos.x, pos.y, pos.z,
                        (stand.getRandom().nextFloat()  - 0.5f) * 0.5f,
                        (stand.getRandom().nextFloat()  - 0.5f) * 0.5f,
                        (stand.getRandom().nextFloat()  - 0.5f) * 0.5f);
            }
        }

        stand.playSound(InitSounds.SHADOW_WORLD_TIME_STOP_BLINK.get(),  1.0F, 0.9F);
    }


    protected void onTaskStopped(World world, StandEntity stand, IStandPower power, StandEntityTask task) {
        stand.setNoPhysics(false);
        stand.noPhysics  = false;
        stand.setDeltaMovement(Vector3d.ZERO);

        if (!world.isClientSide()  && !stand.isAlive())  {
            power.setStandManifestation(null);
        }
    }

    @Override
    public int getStandWindupTicks(IStandPower power, StandEntity stand) {
        return 8;
    }

    @Override
    public int getStandRecoveryTicks(IStandPower power, StandEntity stand) {
        return 6;
    }

    @Override
    public boolean noAdheringToUserOffset(IStandPower standPower, StandEntity standEntity) {
        return true;
    }
}