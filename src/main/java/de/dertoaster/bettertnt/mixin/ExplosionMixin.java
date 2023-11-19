package de.dertoaster.bettertnt.mixin;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.dertoaster.bettertnt.BetterTNTMod;
import de.dertoaster.bettertnt.DurabilityOverrideUtil;
import de.dertoaster.bettertnt.init.BetterTNTTags;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.Level;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {

	
	@Shadow
	private ObjectArrayList<BlockPos> toBlow;
	@Shadow
	private Level level;
	@Shadow
	@Nullable
	private Entity source;
	@Shadow
	private Explosion.BlockInteraction blockInteraction;
	
	@Inject(
			method = "finalizeExplosion(Z)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/Util;shuffle(Lit/unimi/dsi/fastutil/objects/ObjectArrayList;Lnet/minecraft/util/RandomSource;)V",
					shift = Shift.BEFORE
			), 
			cancellable = false
	)
	private void mixinFinalizeExplosion(boolean pSpawnParticles, CallbackInfo ci) {
		if (!BetterTNTMod.CONFIG.durabilityOverride.enabled.get()) {
			return;
		}
		
		if (this.blockInteraction == BlockInteraction.KEEP) {
			return;
		}
		// Only if this is tnt
		if (this.source != null && !this.source.getType().is(BetterTNTTags.EntityTypes.EXPLOSIVES)) {
			return;
		}
		// Then we want to remove some blocks from the explosion
		this.toBlow.removeIf(pos -> {
			if (DurabilityOverrideUtil.isNextToAir(pos, this.level)) {
				return true;
			}
			
			return false;
		});
	}
	
}
