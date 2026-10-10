package com.nullclient.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

@Mixin(Block.class)
public class BlockMixin {

    /**
     * Intercepts Block.shouldDrawSide to force rendering of ore faces through stone walls.
     * This enables X-Ray vision for valuable ores (Diamond, Gold, Iron, Debris).
     */
    @Inject(
        method = "shouldDrawSide",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void injectShouldDrawSide(BlockState state, BlockView world, BlockPos pos, 
                                             Direction direction, BlockPos adjPos, 
                                             CallbackInfoReturnable<Boolean> cir) {
        Block currentBlock = state.getBlock();
        
        // Check if current block is a valuable ore
        if (isValueableOre(currentBlock)) {
            // Force render all sides
            cir.setReturnValue(true);
            return;
        }

        // Check if adjacent block is a valuable ore
        try {
            BlockState adjState = world.getBlockState(adjPos);
            Block adjBlock = adjState.getBlock();
            
            if (isValueableOre(adjBlock)) {
                // Allow rendering ore through this block
                cir.setReturnValue(true);
            }
        } catch (Exception e) {
            // Fail silently if world boundary is reached
        }
    }

    /**
     * Checks if a block is a valuable ore that should be rendered through stone.
     */
    private static boolean isValueableOre(Block block) {
        return block == Blocks.DIAMOND_ORE ||
               block == Blocks.DEEPSLATE_DIAMOND_ORE ||
               block == Blocks.GOLD_ORE ||
               block == Blocks.DEEPSLATE_GOLD_ORE ||
               block == Blocks.IRON_ORE ||
               block == Blocks.DEEPSLATE_IRON_ORE ||
               block == Blocks.ANCIENT_DEBRIS ||
               block == Blocks.COPPER_ORE ||
               block == Blocks.DEEPSLATE_COPPER_ORE ||
               block == Blocks.EMERALD_ORE ||
               block == Blocks.DEEPSLATE_EMERALD_ORE ||
               block == Blocks.LAPIS_ORE ||
               block == Blocks.DEEPSLATE_LAPIS_ORE;
    }
}
