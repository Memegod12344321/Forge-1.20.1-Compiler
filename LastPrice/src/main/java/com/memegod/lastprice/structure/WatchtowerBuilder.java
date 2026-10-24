package com.memegod.lastprice.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;

public final class WatchtowerBuilder {
    private WatchtowerBuilder() {}

    public static void build(ServerLevel level, BlockPos origin) {
        for (int y = 0; y < 9; y++) {
            level.setBlock(origin.offset(0, y, 0), Blocks.SPRUCE_LOG.defaultBlockState(), 3);
            level.setBlock(origin.offset(3, y, 0), Blocks.SPRUCE_LOG.defaultBlockState(), 3);
            level.setBlock(origin.offset(0, y, 3), Blocks.SPRUCE_LOG.defaultBlockState(), 3);
            level.setBlock(origin.offset(3, y, 3), Blocks.SPRUCE_LOG.defaultBlockState(), 3);
        }
        for (int x = 0; x <= 3; x++) for (int z = 0; z <= 3; z++)
            level.setBlock(origin.offset(x, 8, z), Blocks.SPRUCE_PLANKS.defaultBlockState(), 3);
        for (int y = 1; y < 8; y++) level.setBlock(origin.offset(1, y, 0), Blocks.LADDER.defaultBlockState(), 3);
        level.setBlock(origin.offset(1, 9, 1), Blocks.SOUL_LANTERN.defaultBlockState(), 3);
    }
}
