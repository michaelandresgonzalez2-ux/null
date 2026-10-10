package com.nullclient.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Inject(
            method = "shouldDrawSide",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void nullclient$shouldDrawSide(
            BlockState state,
            BlockView world,
            BlockPos pos,
            Direction direction,
            BlockPos adjPos,
            CallbackInfoReturnable<Boolean> cir
    ) {
        Block current = state.getBlock();
        if (isOre(current)) {
            cir.setReturnValue(true);
            return;
        }

        Block adjacent = world.getBlockState(adjPos).getBlock();
        if (isOre(adjacent)) {
            cir.setReturnValue(true);
        }
    }

    private static boolean isOre(Block block) {
        return block == Blocks.DIAMOND_ORE
                || block == Blocks.DEEPSLATE_DIAMOND_ORE
                || block == Blocks.GOLD_ORE
                || block == Blocks.DEEPSLATE_GOLD_ORE
                || block == Blocks.IRON_ORE
                || block == Blocks.DEEPSLATE_IRON_ORE
                || block == Blocks.ANCIENT_DEBRIS
                || block == Blocks.NETHER_GOLD_ORE;
    }
}
