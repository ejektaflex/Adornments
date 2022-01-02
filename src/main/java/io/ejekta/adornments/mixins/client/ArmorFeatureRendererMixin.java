package io.ejekta.adornments.mixins.client;

import io.ejekta.adornments.MixinHelperClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M>
{

    private boolean useArmor(A armorModel, EquipmentSlot slot) {
        this.getContextModel().setAttributes(armorModel);
        this.setVisible(armorModel, slot);
        return this.usesSecondLayer(slot);
    }

    @Inject(at = @At("RETURN"), method = "renderArmor(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;ILnet/minecraft/client/render/entity/model/BipedEntityModel;)V")
    private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot slot, int light, A armorModel, CallbackInfo cbi)
    {
        MixinHelperClient.INSTANCE.renderArmor(matrices, vertexConsumers, entity, slot, light, armorModel, this::useArmor);
    }

    public ArmorFeatureRendererMixin(FeatureRendererContext<T, M> context)
    {
        super(context);
    }

    @Shadow
    protected void setVisible(BipedEntityModel bipedModel, EquipmentSlot slot)
    {
    }

    @Shadow
    private boolean usesSecondLayer(EquipmentSlot slot)
    {
        return false;
    }
}
