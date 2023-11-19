package de.dertoaster.bettertnt.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.dertoaster.bettertnt.BetterTNTMod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@Mixin(PrimedTnt.class)
public abstract class PrimedTNTMixin extends Entity {
	
	@Unique
	protected boolean isFlying = false;
	@Unique
	protected double vLastTick = 0D;
	
	public PrimedTNTMixin(EntityType<?> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}
	
	private void handleContactExplosive() {
		if (!BetterTNTMod.CONFIG.contactExplosivesEnabled.get()) {
			return;
		}
		
		if(((PrimedTnt)((Object)this)).getFuse() <= 0 || !this.isAlive() || this.isRemoved()) {
			return;
		}
		
		final double v = this.getDeltaMovement().lengthSqr();
		final double vOld = this.vLastTick;
		this.isFlying = this.isFlying || (v > BetterTNTMod.CONFIG.minImpulse.get());
		if(this.isFlying) {
			if(v < (vOld / BetterTNTMod.CONFIG.maxImpulse.get())) {
				this.setNoGravity(true);
				this.setDeltaMovement(Vec3.ZERO);
				((PrimedTnt)((Object)this)).setFuse(0);
			}
		}
		this.vLastTick = v;
	}

	@Inject(at = @At("HEAD"), method = "tick()V", cancellable = true)
	private void mxinTick(CallbackInfo ci) {
		this.handleContactExplosive();
	}

}
