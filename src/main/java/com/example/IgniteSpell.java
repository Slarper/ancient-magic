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
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class IgniteSpell {
    public static void register() {
        SpellCallback.EVENT.register(((nbt, caster, hitResults) -> {
            NbtElement n1 = nbt.get("Ignite");
            if (n1 == null) {
                return ActionResult.PASS;
            }

            World world = caster.getWorld();

            for (var hitResult : hitResults){
                if (hitResult instanceof EntityHitResult
                ){
                    var entity = ((EntityHitResult)hitResult).getEntity();
                    if (
                            entity instanceof LivingEntity livingEntity
                    ) {
                        if (
                                !livingEntity.isInLava()
                                && !livingEntity.isOnFire()
                        ) {
                            livingEntity.setOnFireFor(10);
                        }
                    }
                } else if (hitResult instanceof BlockHitResult blockHitResult) {
                    var blockPos = blockHitResult.getBlockPos();
                    var direction = blockHitResult.getSide();

                    if (blockPos != null) {
                        BlockState blockState = world.getBlockState(blockPos);
                        if (!CampfireBlock.canBeLit(blockState) && !CandleBlock.canBeLit(blockState) && !CandleCakeBlock.canBeLit(blockState)) {
                            BlockPos blockPos2 = blockPos.offset(blockHitResult.getSide());
                            if (AbstractFireBlock.canPlaceAt(world, blockPos2, blockHitResult.getSide())) {
                                world.playSound(caster, blockPos2, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
                                BlockState blockState2 = AbstractFireBlock.getState(world, blockPos2);
                                world.setBlockState(blockPos2, blockState2, Block.NOTIFY_ALL_AND_REDRAW);
                                world.emitGameEvent(caster, GameEvent.BLOCK_PLACE, blockPos);

                            }
                        }else {
                            world.playSound(caster, blockPos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
                            world.setBlockState(blockPos, blockState.with(Properties.LIT, Boolean.TRUE), Block.NOTIFY_ALL_AND_REDRAW);
                            world.emitGameEvent(caster, GameEvent.BLOCK_CHANGE, blockPos);
                        }
                    }
                }
            }





            return ActionResult.PASS;
        }));
    }
}
