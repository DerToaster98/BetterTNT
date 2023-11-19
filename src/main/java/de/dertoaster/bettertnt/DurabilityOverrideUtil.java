package de.dertoaster.bettertnt;
import de.dertoaster.bettertnt.init.BetterTNTTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

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
		long seed = (pos.getX() * pos.getY() * pos.getZ()) + (System.currentTimeMillis() >> 12);
		RANDOM.setSeed(seed);
		
	}

}
