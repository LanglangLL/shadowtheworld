package com.mrlang.rotp_shadowworld.action.stand;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.itemprojectile.KnifeEntity;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class MultipleKnivesThrow extends StandEntityAction {
    public MultipleKnivesThrow(Builder builder) {
        super(builder
                .standPose(SingleKnifeThrow.KNIFE_THROW)
                .standOffsetFront()
                .autoSummonStand()
        );
    }

    @Override
    public void standPerform(World world, StandEntity stand, IStandPower power, StandEntityTask task) {
        if (!world.isClientSide())  {
            LivingEntity user = power.getUser();
            if (user != null && stand != null) {
                Vector3d lookVec = user.getLookAngle();
                stand.setPos(
                        user.getX()  + lookVec.x * 1.5,
                        user.getY()  + user.getEyeHeight()  - 0.5,
                        user.getZ()  + lookVec.z * 1.5
                );
                stand.setYBodyRot(user.yRot);
                stand.setYHeadRot(user.yRot);

                Vector3d shootDirection = user.getLookAngle();
                for (int i = 0; i < 9; i++) {
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
                    knife.setTimeStopFlightTicks(4);

                    float yawOffset = (i % 3 - 1) * 2f;
                    float pitchOffset = (i / 3 - 1) * 3f;

                    Vector3d dir = shootDirection
                            .yRot((float)Math.toRadians(yawOffset))
                            .xRot((float)Math.toRadians(pitchOffset));

                    knife.shoot(
                            dir.x, dir.y, dir.z,
                            3.0F,
                            0.6F + i % 3 * 0.7F 
                    );
                    world.addFreshEntity(knife);
                }
            }
        }
    }
}