package com.github.alexmodguy.alexscaves.mixin;

import com.github.alexmodguy.alexscaves.server.entity.util.MobTargetAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// Exposes Mob's goal/target selectors through the MobTargetAccessor duck interface. StunnedEffect casts a
// stunned Mob to MobTargetAccessor to lock its MOVE/JUMP/LOOK goal flags; without this the cast threw a
// ClassCastException every time a mob was stunned (Primitive Club hit, Tremorzilla/Tremorsaurus AoE, etc.),
// crashing the server tick. The mixin adds the interface + method bodies to every Mob instance.
@Mixin(Mob.class)
public abstract class MobMixin implements MobTargetAccessor {

    @Shadow @Final protected GoalSelector goalSelector;
    @Shadow @Final protected GoalSelector targetSelector;

    @Override
    public GoalSelector ac_getGoalSelector() {
        return this.goalSelector;
    }

    @Override
    public GoalSelector ac_getTargetSelector() {
        return this.targetSelector;
    }
}
