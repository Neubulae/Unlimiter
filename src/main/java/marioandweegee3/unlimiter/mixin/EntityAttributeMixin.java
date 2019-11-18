package marioandweegee3.unlimiter.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.attribute.AbstractEntityAttribute;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;

@Mixin(ClampedEntityAttribute.class)
public abstract class EntityAttributeMixin extends AbstractEntityAttribute {
    protected EntityAttributeMixin(EntityAttribute entityAttribute_1, String string_1, double double_1) {
        super(entityAttribute_1, string_1, double_1);
    }

    @Shadow
    private double maxValue;

    @Inject(at = @At("RETURN"), method = "<init>", cancellable = true)
    private void removeLimit(EntityAttribute attribute, String name, double def, double minimum, double maximum, CallbackInfo ci){
        ci.cancel();
        maxValue = Double.MAX_VALUE;
    }
}