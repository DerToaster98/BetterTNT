package de.dertoaster.bettertnt;
import java.util.Optional;
import java.util.stream.Collectors;

import de.dertoaster.bettertnt.init.BetterTNTTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public class DurabilityOverrideUtil {
	
	public static boolean isNextToAir(BlockPos pos, Level level) {
		for (Direction direction : Direction.values()) {
			if (checkPosForAirLikeBlock(pos.relative(direction), level)) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean checkPosForAirLikeBlock(BlockPos pos, Level level) {
		if (level.getBlockState(pos.above()).isAir()) {
			return true;
		}
		return level.getBlockState(pos).is(BetterTNTTags.Blocks.AIR);
	}
	
	private static RandomSource RANDOM = RandomSource.create();
	
	public static boolean rollDurabilityOverride(BlockState state, BlockPos pos) {
		Optional<Integer> durability = getDurabilityFor(state);
		if (durability.isEmpty()) {
			return false;
		}
		
		long seed = (pos.getX() * pos.getY() * pos.getZ()) + (System.currentTimeMillis() >> 12);
		RANDOM.setSeed(seed);
		
		return RANDOM.nextInt(100) > Math.abs(durability.get());
	}
	
	private static Optional<Integer> getDurabilityFor(BlockState state) {
		if (BetterTNTMod.CONFIG.blockDurabilities.get().isEmpty() && BetterTNTMod.CONFIG.blockTagDurabilities.get().isEmpty()) {
			return Optional.empty();
		}
		Block block = state.getBlock();
		ResourceLocation rs = ForgeRegistries.BLOCKS.getKey(block);
		
		Integer value = null;
		value = BetterTNTMod.CONFIG.blockDurabilities.get().getOrDefault(rs, null);
		if (value == null && !BetterTNTMod.CONFIG.blockTagDurabilities.get().isEmpty()) {
			// If multiple are set, use the highest
			for (TagKey<? extends Block> tag : state.getTags().collect(Collectors.toList())) {
				Integer valueTmp = BetterTNTMod.CONFIG.blockTagDurabilities.get().getOrDefault(tag.location(), null);
				if (value == null) {
					value = valueTmp;
					continue;
				}
				if (valueTmp != null && value != null && valueTmp > value) {
					value = valueTmp;
				}
			}
		}
		
		return Optional.ofNullable(value);
	}

}
