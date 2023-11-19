package de.dertoaster.bettertnt.config.feature;

import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class ContactExplosivesFeature extends AbstractFeatureConfig {
	
	public ContactExplosivesFeature(Builder builder) {
		super(builder, "Contact explosives");
		// TODO Auto-generated constructor stub
	}
	
	public ConfigValue<Double> minImpulse;
	public ConfigValue<Double> maxImpulse;
	
	@Override
	protected void defineValues(Builder builder) {
		builder.comment("min impulse to indicate wether TNT is moving fast enough");
		minImpulse = builder.defineInRange("minImpulse", 0.35D, 0.0D, Double.MAX_VALUE);
		
		builder.comment("If the current velocity is smaller than the velocity of the previous tick divided by this value, the TNT will explode");
		maxImpulse = builder.defineInRange("maxImpulse", 10.0, 1.0D, Double.MAX_VALUE);
	}

}
