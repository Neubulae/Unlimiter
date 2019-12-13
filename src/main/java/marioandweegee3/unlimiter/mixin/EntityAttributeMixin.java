package marioandweegee3.unlimiter.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import marioandweegee3.unlimiter.Unlimiter;
import marioandweegee3.unlimiter.util.ConfigClampedAttribute;
import net.minecraft.entity.attribute.AbstractEntityAttribute;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.math.MathHelper;

@Mixin(ClampedEntityAttribute.class)
public class EntityAttributeMixin extends AbstractEntityAttribute implements ConfigClampedAttribute {
    protected EntityAttributeMixin(EntityAttribute entityAttribute_1, String string_1, double double_1) {
        super(entityAttribute_1, string_1, double_1);
    }

    private double max;

    private double min;

    @Shadow
    private String name;

    @Inject(at = @At("RETURN"), method = "<init>")
    private void addToSet(CallbackInfo ci){
        Unlimiter.attributes.add(this);
    }

    @Override
    public void setMax(double max) {
        this.max = max;
    }

    @Override
    public void setMin(double min) {
        this.min = min;
    }

    @Override
    public double clamp(double d) {
        return MathHelper.clamp(d, min, max);
    }
}