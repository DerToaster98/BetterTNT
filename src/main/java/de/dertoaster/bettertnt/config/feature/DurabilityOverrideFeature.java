package de.dertoaster.bettertnt.config.feature;

import java.util.HashMap;
import java.util.Map;

import com.mojang.serialization.Codec;

import commoble.databuddy.config.ConfigHelper;
import commoble.databuddy.config.ConfigHelper.ConfigObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class DurabilityOverrideFeature extends AbstractFeatureConfig {

	public DurabilityOverrideFeature(Builder builder) {
		super(builder, "Durability overrides");
	}
	
	public ConfigObject<Map<ResourceLocation, Integer>> blockDurabilities;
	public ConfigObject<Map<ResourceLocation, Integer>> blockTagDurabilities;

	@Override
	protected void defineValues(Builder builder) {
		builder.comment("Overrides per block");
		blockDurabilities = ConfigHelper.defineObject(builder, "blockDurabilities", Codec.unboundedMap(ResourceLocation.CODEC, Codec.INT), new HashMap<>());
		
		builder.comment("Override per block-tag (fallbacks)");
		builder.comment("If multiple tags are set for one block, it will use the highest value in those tags");
		blockTagDurabilities = ConfigHelper.defineObject(builder, "blockTagDurabilities", Codec.unboundedMap(ResourceLocation.CODEC, Codec.INT), new HashMap<>());
	}

}
