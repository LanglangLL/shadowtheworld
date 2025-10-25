package com.mrlang.rotp_shadowworld.client.render.entity.model.stand;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.client.render.entity.model.stand.HumanoidStandModel;
import com.github.standobyte.jojo.client.render.entity.pose.IModelPose;
import com.github.standobyte.jojo.client.render.entity.pose.ModelPose;
import com.github.standobyte.jojo.client.render.entity.pose.RotationAngle;
import com.github.standobyte.jojo.client.render.entity.pose.anim.PosedActionAnimation;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.mrlang.rotp_shadowworld.entity.stand.stands.SHADOWWORLDEntity;

import net.minecraft.client.renderer.model.ModelRenderer;

public class SHADOWWORLDModel extends HumanoidStandModel<SHADOWWORLDEntity> {
	public static final StandPose STUN_PUNCH = new StandPose("stun_punch");
	public static final StandPose KNIF_THHH = new StandPose("knifethhh");
	public static final StandPose HEAVY_ATTACK_FINISHER = new StandPose("heavy_attack_finisher");

	public SHADOWWORLDModel() {
		super();
	}

	@Override // TODO summon poses
    protected RotationAngle[][] initSummonPoseRotations() {
        return new RotationAngle[][] {
            new RotationAngle[] {
                    
            },
            new RotationAngle[] {
                    
            }
		};
    }
    
    @Override
    protected void initActionPoses() { // TODO pickaxe throwing anim
        actionAnim.put(StandPose.RANGED_ATTACK, new PosedActionAnimation.Builder<SHADOWWORLDEntity>()
                .addPose(StandEntityAction.Phase.BUTTON_HOLD, new ModelPose<>(new RotationAngle[] {
                        new RotationAngle(body, 0.0F, -0.48F, 0.0F),
                        new RotationAngle(leftArm, 0.0F, 0.0F, -0.7854F),
                        new RotationAngle(leftForeArm, 0.0F, 0.0F, 0.6109F),
                        new RotationAngle(rightArm, -1.0908F, 0.0F, 1.5708F), 
                        new RotationAngle(rightForeArm, 0.0F, 0.0F, 0.0F)
                }))
                .build(idlePose));
        
        super.initActionPoses();
    }


    
    

    @Override // TODO idle pose
    protected ModelPose<SHADOWWORLDEntity> initIdlePose() {
        return super.initIdlePose();
    }

    @Override
    protected IModelPose<SHADOWWORLDEntity> initIdlePose2Loop() {
        return super.initIdlePose2Loop();
    }
}