package com.memegod.lastprice.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;

/** Small procedural ritual-site builder used by the horror event system. */
public final class RitualSiteBuilder {
    private RitualSiteBuilder() {}

    public static void build(ServerLevel level, BlockPos center) {
        for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
                if (x * x + z * z <= 25 && (Math.abs(x) == 5 || Math.abs(z) == 5)) {
                    level.setBlock(center.offset(x, 0, z), Blocks.POLISHED_BLACKSTONE_BRICKS.defaultBlockState(), 3);
                }
            }
        }
        level.setBlock(center, Blocks.CRYING_OBSIDIAN.defaultBlockState(), 3);
        for (int i = 0; i < 4; i++) {
            int x = (i % 2 == 0 ? -3 : 3);
            int z = (i < 2 ? -3 : 3);
            level.setBlock(center.offset(x, 1, z), Blocks.SOUL_LANTERN.defaultBlockState(), 3);
            level.setBlock(center.offset(x, 0, z), Blocks.CRYING_OBSIDIAN.defaultBlockState(), 3);
        }
    }
}
