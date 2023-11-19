package de.dertoaster.bettertnt.config.feature;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public abstract class AbstractFeatureConfig {
	
	public ConfigValue<Boolean> enabled;
	
	protected abstract void defineValues(ForgeConfigSpec.Builder builder);
	
	public AbstractFeatureConfig(ForgeConfigSpec.Builder builder, String name) {
		builder.comment("feature: " + name);
		builder.push(name);
		
		enabled = builder.define("enabled", true);
		this.defineValues(builder);
		
		builder.pop();
		
	}

}
