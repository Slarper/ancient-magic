package com.example;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class IgniteSpell {
    public static void register() {
        SpellCallback.EVENT.register(((nbt, caster, targets, blocks) -> {
            NbtElement n1 = nbt.get("Ignite");
            if (n1 != null) {
                caster.sendMessage(Text.of("You ignite!"), false);

            }

            for (var target : targets) {
                if (target != null) {
                    if (target instanceof LivingEntity) {
                        if (!target.isInLava() && !target.isOnFire()) {
                            caster.sendMessage(Text.of("You ignite an Entity!"), false);
                            target.setOnFireFor(10);
                        }
                    }

                }
            }

            World world = caster.getWorld();


            for (var block : blocks) {
                if (block != null) {
                    BlockState blockState = world.getBlockState(block);
                    if (!CampfireBlock.canBeLit(blockState) && !CandleBlock.canBeLit(blockState) && !CandleCakeBlock.canBeLit(blockState)) {
                        BlockPos blockPos2 = block.offset(Direction.UP);
                        if (AbstractFireBlock.canPlaceAt(world, blockPos2, Direction.UP)) {
                            world.playSound(caster, blockPos2, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
                            BlockState blockState2 = AbstractFireBlock.getState(world, blockPos2);
                            world.setBlockState(blockPos2, blockState2, Block.NOTIFY_ALL_AND_REDRAW);
                            world.emitGameEvent(caster, GameEvent.BLOCK_PLACE, block);

                        }
                    }else {
                        world.playSound(caster, block, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
                        world.setBlockState(block, blockState.with(Properties.LIT, Boolean.valueOf(true)), Block.NOTIFY_ALL_AND_REDRAW);
                        world.emitGameEvent(caster, GameEvent.BLOCK_CHANGE, block);
                    }
                }
            }



            return ActionResult.PASS;
        }));
    }
}
