package de.dertoaster.bettertnt.config;

import com.mojang.serialization.Codec;
import commoble.databuddy.config.ConfigHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

public class BetterTNTConfig {

	public ForgeConfigSpec.BooleanValue contactExplosivesEnabled;
	public ForgeConfigSpec.DoubleValue minImpulse;
	public ForgeConfigSpec.DoubleValue maxImpulse;

	public ForgeConfigSpec.BooleanValue durabilityOverridesEnabled;
	public ConfigHelper.ConfigObject<Map<ResourceLocation, Integer>> blockDurabilities;
	public ConfigHelper.ConfigObject<Map<ResourceLocation, Integer>> blockTagDurabilities;

	public BetterTNTConfig(ForgeConfigSpec.Builder builder) {
		builder.push("contact-explosives");
		contactExplosivesEnabled = builder
				.comment("Enable or disable contact explosives")
				.define("enabled", true);
		minImpulse = builder
				.comment("min impulse to indicate wether TNT is moving fast enough")
				.defineInRange("minImpulse", 0.35D, 0.0D, Double.MAX_VALUE);
		maxImpulse = builder
				.comment("If the current velocity is smaller than the velocity of the previous tick divided by this value, the TNT will explode")
				.defineInRange("maxImpulse", 10.0, 1.0D, Double.MAX_VALUE);
		builder.pop();

		builder.push("durability overrides");
		durabilityOverridesEnabled = builder
				.comment("Enable or disable durability overrides")
				.define("enabled", true);
		builder.comment("Overrides per block");
		blockDurabilities = ConfigHelper.defineObject(builder, "blockDurabilities", Codec.unboundedMap(ResourceLocation.CODEC, Codec.INT), Map.of(
				ForgeRegistries.BLOCKS.getKey(Blocks.END_STONE), 80,
				ForgeRegistries.BLOCKS.getKey(Blocks.END_STONE_BRICKS), 80,
				ForgeRegistries.BLOCKS.getKey(Blocks.END_STONE_BRICK_SLAB), 80,
				ForgeRegistries.BLOCKS.getKey(Blocks.END_STONE_BRICK_STAIRS), 80,
				ForgeRegistries.BLOCKS.getKey(Blocks.END_STONE_BRICK_WALL), 80
		));

		builder.comment("Override per block-tag (fallbacks)");
		builder.comment("If multiple tags are set for one block, it will use the highest value in those tags");
		blockTagDurabilities = ConfigHelper.defineObject(builder, "blockTagDurabilities", Codec.unboundedMap(ResourceLocation.CODEC, Codec.INT), Map.of(
				new ResourceLocation("forge", "end_stones"), 80
		));

		builder.pop();
	}
	
}
