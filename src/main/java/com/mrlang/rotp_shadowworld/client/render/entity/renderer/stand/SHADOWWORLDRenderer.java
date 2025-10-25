package com.mrlang.rotp_shadowworld.client.render.entity.renderer.stand;

import com.mrlang.rotp_shadowworld.RotpSHADOWWORLDAddon;
import com.mrlang.rotp_shadowworld.client.render.entity.model.stand.SHADOWWORLDModel;
import com.mrlang.rotp_shadowworld.entity.stand.stands.SHADOWWORLDEntity;
import com.github.standobyte.jojo.client.render.entity.model.stand.StandModelRegistry;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.math.vector.Vector3f;
import java.util.Random;

public class SHADOWWORLDRenderer extends StandEntityRenderer<SHADOWWORLDEntity, SHADOWWORLDModel> {

    private static final Random RANDOM = new Random();
    private static final ResourceLocation MODEL_LOCATION = new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "shadow_world");
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(RotpSHADOWWORLDAddon.MOD_ID, "textures/entity/stand/shadow_world.png");

    public SHADOWWORLDRenderer(EntityRendererManager renderManager) {
        super(renderManager,
                StandModelRegistry.registerModel(MODEL_LOCATION,  SHADOWWORLDModel::new),
                TEXTURE_LOCATION,
                0);
    }

    @Override
    public void render(SHADOWWORLDEntity entity, float entityYaw, float partialTicks,
                       MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        if (entity == null) return;

        float time = (entity.tickCount  + partialTicks) * 0.25f;
        float intensity = 0.15f;

        matrixStack.pushPose();
        try {
            // 保持原有的扭曲特效逻辑
            RANDOM.setSeed(entity.getId());

            float diagonalAngle1 = 30f + RANDOM.nextFloat()  * 30f;
            float diagonalAngle2 = 15f + RANDOM.nextFloat()  * 45f;

            matrixStack.mulPose(Vector3f.YP.rotationDegrees(diagonalAngle1));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(RANDOM.nextFloat()  * 10f - 5f));
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(diagonalAngle2));
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(RANDOM.nextFloat()  * 8f - 4f));

            float xScale = 1 + (float)Math.sin(time  * 0.5 + RANDOM.nextFloat())  * intensity * 1.8f;
            float yScale = 1 + (float)Math.cos(time  * 0.2 + RANDOM.nextFloat())  * intensity * 0.7f;
            float zScale = 1 + (float)Math.sin(time  * 0.3 + RANDOM.nextFloat())  * intensity * 1.2f;
            matrixStack.scale(xScale,  yScale, zScale);

            matrixStack.mulPose(Vector3f.XP.rotationDegrees(-diagonalAngle2));
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(-diagonalAngle1));

            float headRotX = RANDOM.nextFloat()  * 5f - 2.5f;
            float headRotY = RANDOM.nextFloat()  * 7f - 3.5f;
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(headRotX));
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(headRotY));

            super.render(entity,  entityYaw, partialTicks, matrixStack, buffer, packedLight);
        } finally {
            matrixStack.popPose();
        }
    }
}