package com.github.alexmodguy.alexscaves.fabric;

import com.github.alexthe666.citadel.ServerProxy;
import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

/**
 * Minimal Citadel client proxy for the Fabric port.
 *
 * Astryxion's standalone Citadel (Fabric) never instantiates or sets its own {@code ClientProxy}
 * ({@code Citadel.setClientProxy} has no callers in the jar — unlike the NeoForge build, which wires it
 * via {@code unsafeRunForDist}). So on Fabric {@code Citadel.PROXY} stays the base {@link ServerProxy},
 * whose {@code handleAnimationPacket} is a no-op ({@code return;}). The upshot: server→client
 * {@code AnimationMessage}s (citadel:animation) are decoded but do nothing, so non-walk animations
 * (attack / roar / bite) never play client-side. Walk cycles are procedural and unaffected.
 *
 * AC installs this proxy (only when no real ClientProxy is already present) so that
 * {@code AnimationMessage.handleClient} → {@code Citadel.PROXY.handleAnimationPacket} actually applies the
 * animation. We extend {@link ServerProxy} (NOT Citadel's ClientProxy) and override ONLY
 * {@code handleAnimationPacket}: Citadel never casts {@code PROXY} to {@code ClientProxy}, so this changes
 * no other Citadel client behaviour. Mirrors what AC's bundled AnimationMessage handler did directly.
 */
public class ACCitadelClientProxy extends ServerProxy {

    @Override
    public void handleAnimationPacket(int entityID, int index) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) {
            return;
        }
        Entity entity = mc.level.getEntity(entityID);
        if (entity instanceof IAnimatedEntity animated) {
            if (index == -1) {
                animated.setAnimation(IAnimatedEntity.NO_ANIMATION);
            } else {
                Animation[] animations = animated.getAnimations();
                if (index >= 0 && index < animations.length) {
                    animated.setAnimation(animations[index]);
                }
            }
            animated.setAnimationTick(0);
        }
    }
}
