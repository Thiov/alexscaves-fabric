package com.github.alexmodguy.alexscaves.fabric;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.world.InteractionResult;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public final class ACFabricEventBridge {

    private ACFabricEventBridge() {
    }

    public static void registerCommon() {
        UseEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
            if (level.isClientSide()) {
                return InteractionResult.PASS;
            }
            PlayerInteractEvent.EntityInteract event = new PlayerInteractEvent.EntityInteract(
                player,
                level,
                hand,
                player.getItemInHand(hand),
                entity
            );
            NeoForge.EVENT_BUS.post(event);
            return event.isCanceled() ? event.getCancellationResult() : InteractionResult.PASS;
        });

        UseItemCallback.EVENT.register((player, level, hand) -> {
            // 26.1: UseItemCallback.interact returns InteractionResult (InteractionResultHolder is gone).
            if (level.isClientSide()) {
                return InteractionResult.PASS;
            }
            PlayerInteractEvent.RightClickItem event = new PlayerInteractEvent.RightClickItem(
                player,
                level,
                hand,
                player.getItemInHand(hand)
            );
            NeoForge.EVENT_BUS.post(event);
            if (!event.isCanceled()) {
                return InteractionResult.PASS;
            }
            return event.getCancellationResult();
        });

        AttackEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
            if (level.isClientSide()) {
                return InteractionResult.PASS;
            }
            AttackEntityEvent event = new AttackEntityEvent(player, entity);
            NeoForge.EVENT_BUS.post(event);
            return event.isCanceled() ? InteractionResult.FAIL : InteractionResult.PASS;
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (var player : server.getPlayerList().getPlayers()) {
                NeoForge.EVENT_BUS.post(new PlayerTickEvent.Post(player));
            }
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
            NeoForge.EVENT_BUS.post(new PlayerEvent.PlayerLoggedInEvent(handler.getPlayer()))
        );

        // NOTE: 26.1/Fabric removed TradeOfferHelper; AC's cartographer cabin-map trade and wandering
        // trader offers are no longer injected into vanilla merchants. Deep One trades are unaffected
        // (they are built on AC's own entities, not via these hooks).
    }
}
