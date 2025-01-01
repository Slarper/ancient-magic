package com.example;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public interface SpellCallback {
    Event<SpellCallback> EVENT = EventFactory.createArrayBacked(SpellCallback.class,
            (listeners) -> (nbt, caster, targets, blocks) -> {
                for (SpellCallback listener : listeners) {
                    ActionResult result = listener.interact(nbt, caster, targets, blocks);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult interact(NbtCompound nbt, PlayerEntity caster, ArrayList<Entity> targets, ArrayList<BlockPos> blocks);
}
