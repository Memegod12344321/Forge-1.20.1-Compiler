package com.memegod.lastprice.horror;

import com.memegod.lastprice.LastPrice;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.monster.Enderman;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = LastPrice.MOD_ID)
public final class HorrorEvents {
    private static final Map<UUID, Integer> PLAYER_TICKS = new HashMap<>();
    private static final int EVENT_INTERVAL = 20 * 60 * 4;

    private HorrorEvents() {}

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide) return;
        if (!(event.player instanceof ServerPlayer player)) return;
        if (player.isCreative() || player.isSpectator()) return;

        int ticks = PLAYER_TICKS.getOrDefault(player.getUUID(), 0) + 1;
        if (ticks < EVENT_INTERVAL) {
            PLAYER_TICKS.put(player.getUUID(), ticks);
            return;
        }
        PLAYER_TICKS.put(player.getUUID(), 0);

        ServerLevel level = player.serverLevel();
        if (level.getDayTime() % 24000 < 13000 && level.getDayTime() % 24000 > 1000) {
            if (level.random.nextFloat() < 0.65f) return;
        }

        switch (level.random.nextInt(5)) {
            case 0 -> distantWhisper(player);
            case 1 -> lightsOutMoment(player);
            case 2 -> falseFootsteps(player);
            case 3 -> markTheRitual(player);
            default -> watcher(player);
        }
    }

    private static void distantWhisper(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        player.displayClientMessage(Component.literal("§8Something whispers your name from far away."), true);
        level.playSound(null, player.blockPosition(), SoundEvents.AMBIENT_CAVE, SoundSource.AMBIENT, 1.8f, 0.55f);
        player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 50, 0, false, false, true));
    }

    private static void lightsOutMoment(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        player.displayClientMessage(Component.literal("§4THE LIGHTS WENT OUT."), true);
        level.playSound(null, player.blockPosition(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.HOSTILE, 1.1f, 0.7f);
        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 35, 0, false, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 80, 0, false, false, false));
    }

    private static void falseFootsteps(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        BlockPos behind = player.blockPosition().relative(player.getDirection().getOpposite(), 4);
        level.playSound(null, behind, SoundEvents.WOODEN_STEP, SoundSource.HOSTILE, 1.4f, 0.65f);
        level.playSound(null, behind, SoundEvents.GRAVEL_STEP, SoundSource.HOSTILE, 0.9f, 0.8f);
        player.displayClientMessage(Component.literal("§7Footsteps stop when you stop."), true);
    }

    private static void markTheRitual(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        BlockPos center = player.blockPosition().offset(0, -1, 0);
        for (int i = 0; i < 8; i++) {
            double angle = Math.PI * 2 * i / 8.0;
            BlockPos p = center.offset((int)Math.round(Math.cos(angle) * 3), 0,
                    (int)Math.round(Math.sin(angle) * 3));
            if (level.getBlockState(p).isAir() || level.getBlockState(p).canBeReplaced())
                level.setBlock(p, Blocks.CRYING_OBSIDIAN.defaultBlockState(), 3);
        }
        level.setBlock(center, Blocks.BLACKSTONE.defaultBlockState(), 3);
        level.playSound(null, center, SoundEvents.RESPAWN_ANCHOR_DEPLETE, SoundSource.HOSTILE, 1.0f, 0.6f);
        player.displayClientMessage(Component.literal("§4A symbol has appeared beneath you."), true);
    }

    private static void watcher(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        BlockPos spawn = player.blockPosition().relative(player.getDirection().getOpposite(), 12);
        if (!level.isEmptyBlock(spawn) || !level.isEmptyBlock(spawn.above())) {
            player.displayClientMessage(Component.literal("§8You feel watched."), true);
            return;
        }
        Enderman watcher = EntityType.ENDERMAN.create(level);
        if (watcher == null) return;
        watcher.moveTo(spawn.getX() + 0.5, spawn.getY(), spawn.getZ() + 0.5, player.getYRot() + 180, 0);
        watcher.setTarget(null);
        watcher.setSilent(true);
        watcher.setPersistenceRequired();
        level.addFreshEntity(watcher);
        player.displayClientMessage(Component.literal("§8Don't look behind you."), true);
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        PLAYER_TICKS.remove(event.getEntity().getUUID());
    }
}
