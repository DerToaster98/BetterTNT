package de.dertoaster.bettertnt.config;

import de.dertoaster.bettertnt.config.feature.ContactExplosivesFeature;
import de.dertoaster.bettertnt.config.feature.DurabilityOverrideFeature;
import net.minecraftforge.common.ForgeConfigSpec;

public class BetterTNTConfig {
	
	public DurabilityOverrideFeature durabilityOverride;
	public ContactExplosivesFeature contactExplosives;
	
	public BetterTNTConfig(ForgeConfigSpec.Builder builder) {
		builder.push("bettertntmod");
		this.durabilityOverride = new DurabilityOverrideFeature(builder);
		this.contactExplosives = new ContactExplosivesFeature(builder);
		builder.pop();
	}
	
}
