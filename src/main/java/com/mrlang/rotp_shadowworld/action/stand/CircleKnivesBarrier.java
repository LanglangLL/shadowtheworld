package com.mrlang.rotp_shadowworld.action.stand;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.itemprojectile.KnifeEntity;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import com.github.standobyte.jojo.entity.stand.StandPose;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import java.util.Comparator;
import java.util.Random;

public class CircleKnivesBarrier extends StandEntityAction {

    private final Random random = new Random();

    public static final StandPose KNIFE_THHH = new StandPose("knifethhh");

    public CircleKnivesBarrier(Builder builder) {
        super(builder
                .standPose(KNIFE_THHH)
                .standOffsetFront()
                .autoSummonStand()
                .holdType()
        );
    }

    @Override
    public void standPerform(World world, StandEntity stand, IStandPower power, StandEntityTask task) {
        if (!world.isClientSide())  {
            LivingEntity user = power.getUser();
            if (user == null) {
                return;
            }

            LivingEntity target = findTargetInViewCone(user, 15.0f, 25.0f);

            if (target == null) {
                return;
            }

            if (target != null) {
                int knifeCount = 60;
                float radius = 6.0f;
                Vector3d center = target.position().add(0,  target.getBbHeight()/2,  0);

                for (int i = 0; i < knifeCount; i++) {
                    Vector3d spherePos = generateSpherePoint(radius);
                    Vector3d knifePos = center.add(spherePos);

                    KnifeEntity knife = new KnifeEntity(world, user) {
                        private int delayTicks = 5 + random.nextInt(5);
                        private boolean hasShot = false;
                        private final Vector3d target = center;
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

                            if (!this.hasShot)  {
                                if (this.delayTicks--  <= 0) {
                                    Vector3d shootDir = this.target.subtract(this.position()).normalize();
                                    this.setNoGravity(false);
                                    this.shoot(
                                            shootDir.x,
                                            shootDir.y,
                                            shootDir.z,
                                            1.5f,
                                            0.5f
                                    );
                                    this.hasShot  = true;
                                }
                            }
                        }
                    };

                    knife.pickup  = AbstractArrowEntity.PickupStatus.DISALLOWED;
                    knife.setPos(knifePos.x,  knifePos.y, knifePos.z);
                    knife.setNoGravity(true);
                    world.addFreshEntity(knife);
                }
            }
        }
    }

    private Vector3d generateSpherePoint(float radius) {
        double theta = 2 * Math.PI * random.nextDouble();
        double phi = Math.acos(2  * random.nextDouble()  - 1);

        double x = radius * Math.sin(phi)  * Math.cos(theta);
        double y = radius * Math.sin(phi)  * Math.sin(theta);
        double z = radius * Math.cos(phi);

        return new Vector3d(x, y, z);
    }

    private LivingEntity findTargetInViewCone(LivingEntity user, float range, float viewAngle) {
        Vector3d lookVec = user.getLookAngle();

        return user.level.getEntitiesOfClass(LivingEntity.class,
                        user.getBoundingBox().inflate(range),
                        e -> isValidTarget(user, e) && isInViewCone(user.position(),  lookVec, e.position(),  viewAngle))
                .stream()
                .min(Comparator.comparingDouble(e  -> e.distanceToSqr(user)))
                .orElse(null);
    }

    private boolean isInViewCone(Vector3d playerPos, Vector3d lookVec, Vector3d targetPos, float maxAngle) {
        Vector3d toTarget = targetPos.subtract(playerPos).normalize();
        double angle = Math.toDegrees(Math.acos(lookVec.dot(toTarget)));
        return angle <= maxAngle / 2;
    }

    private boolean isValidTarget(LivingEntity user, LivingEntity target) {
        return target != user && !target.isAlliedTo(user);
    }
}