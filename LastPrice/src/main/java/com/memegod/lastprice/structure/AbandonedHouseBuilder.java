package com.memegod.lastprice.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;

/** Procedural abandoned-house set piece; called only by explicit gameplay events. */
public final class AbandonedHouseBuilder {
    private AbandonedHouseBuilder() {}

    public static void build(ServerLevel level, BlockPos origin) {
        int width = 9, depth = 7, height = 5;
        for (int x = 0; x < width; x++) for (int z = 0; z < depth; z++) {
            for (int y = 0; y < height; y++) {
                BlockPos p = origin.offset(x, y, z);
                boolean wall = x == 0 || z == 0 || x == width - 1 || z == depth - 1;
                if (y == 0) level.setBlock(p, Blocks.DARK_OAK_PLANKS.defaultBlockState(), 3);
                else if (y == height - 1) level.setBlock(p, Blocks.DARK_OAK_SLAB.defaultBlockState(), 3);
                else if (wall && !(x == width / 2 && z == 0 && y < 3)) {
                    level.setBlock(p, (y == 2 && (x == 0 || x == width - 1))
                            ? Blocks.COBWEB.defaultBlockState() : Blocks.DARK_OAK_PLANKS.defaultBlockState(), 3);
                } else if (level.getBlockState(p).canBeReplaced()) {
                    level.setBlock(p, Blocks.AIR.defaultBlockState(), 3);
                }
            }
        }
        level.setBlock(origin.offset(width / 2, 1, 0), Blocks.AIR.defaultBlockState(), 3);
        level.setBlock(origin.offset(width / 2, 2, 0), Blocks.AIR.defaultBlockState(), 3);
        level.setBlock(origin.offset(2, 1, 2), Blocks.COBWEB.defaultBlockState(), 3);
        level.setBlock(origin.offset(6, 1, 4), Blocks.SOUL_LANTERN.defaultBlockState(), 3);
        level.setBlock(origin.offset(4, 1, 3), Blocks.CRYING_OBSIDIAN.defaultBlockState(), 3);
    }
}
