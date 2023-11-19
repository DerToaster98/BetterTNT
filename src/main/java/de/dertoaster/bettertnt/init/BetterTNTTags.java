package de.dertoaster.bettertnt.init;

import de.dertoaster.bettertnt.BetterTNTMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

public class BetterTNTTags {

	public static class Blocks {
		public static final TagKey<Block> AIR = BlockTags.create(BetterTNTMod.prefix("air"));
	}

	public static class EntityTypes {
		public static final TagKey<EntityType<?>> EXPLOSIVES = create(BetterTNTMod.prefix("explosives"));

		public static TagKey<EntityType<?>> create(ResourceLocation rs) {
			return TagKey.create(Registries.ENTITY_TYPE, rs);
		}
	}

}
