package com.mrlang.rotp_shadowworld.action.stand;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.itemprojectile.KnifeEntity;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class SingleKnifeThrow extends StandEntityAction {

    public static final StandPose KNIFE_THROW = new StandPose("knife_throw");

    public SingleKnifeThrow(Builder builder) {
        super(builder
                .standPose(KNIFE_THROW)
                .standOffsetFront()
        );
    }

    @Override
    public void standPerform(World world, StandEntity stand, IStandPower power, StandEntityTask task) {
        if (!world.isClientSide())  {
            LivingEntity user = power.getUser();
            if (user != null) {
                Vector3d lookDirection = user.getLookAngle();

                for (int i = 0; i < 3; i++) {
                    KnifeEntity knife = new KnifeEntity(world, user) {
                        private int lifeTime = 0;
                        private final int maxLifeTime = 200;

                        @Override
                        public void tick() {
                            super.tick();
                            lifeTime++;

                            if (lifeTime >= maxLifeTime) {
                                this.remove();
                                return;
                            }
                        }
                    };

                    knife.pickup  = AbstractArrowEntity.PickupStatus.DISALLOWED;
                    knife.setTimeStopFlightTicks(3);

                    float yawOffset = (i - 1) * 1.5f;
                    Vector3d shootDirection = lookDirection.yRot((float)Math.toRadians(yawOffset));

                    knife.shoot(
                            shootDirection.x,
                            shootDirection.y,
                            shootDirection.z,
                            5.0F,
                            0.2F
                    );
                    world.addFreshEntity(knife);
                }
            }
        }
    }
}