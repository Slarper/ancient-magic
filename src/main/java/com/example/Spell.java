package com.example;

import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.ArrayList;

import static com.example.ExampleMod.MOD_ID;
import static com.example.ExampleMod.SPELL;
import static net.minecraft.item.EnderPearlItem.POWER;

public class Spell extends Item {
    public Spell(Settings settings) {
        super(settings);
    }

    public static Item register() {
        String name = "spell";
        // Create the identifier for the item.
        Identifier id = Identifier.of(MOD_ID, name);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
        Item.Settings settings = new Item.Settings()
                // If your item is based on a block
//                .useBlockPrefixedTranslationKey()
                .registryKey(key);
        Item item = new Spell(settings);

        // Register the item.

        // Return the registered item!
        return Registry.register(Registries.ITEM, key, item);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {


        if (!world.isClient) {

            SpellProjectileEntity arrow = new SpellProjectileEntity(world, user, new ItemStack(SPELL)) {
                @Override
                protected void onBlockHit(BlockHitResult blockHitResult) {
                    user.sendMessage(Text.of("You hit a block!"), false);

                    ArrayList<HitResult> hitResults = new ArrayList<>();
                    hitResults.add(blockHitResult);

//                    BlockPos blockPos = blockHitResult.getBlockPos();
//                    ArrayList<BlockPos> blockPosArrayList = new ArrayList<>();
//                    blockPosArrayList.add(blockPos);
//                    ArrayList<Entity> entities = new ArrayList<>();

                    ItemStack itemStack = user.getStackInHand(hand);
                    NbtComponent nbtComponent = itemStack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
                    NbtCompound nbt = nbtComponent.copyNbt();

                    SpellCallback.EVENT.invoker().interact(
                            nbt,
                            user,
                            hitResults,
                            world);

                    super.onBlockHit(blockHitResult);
                }

                @Override
                protected void onEntityHit(EntityHitResult entityHitResult) {
                    user.sendMessage(Text.of("You hit an Entity!"), false);


                    ArrayList<HitResult> hitResults = new ArrayList<>();
                    hitResults.add(entityHitResult);

                    ItemStack itemStack = user.getStackInHand(hand);
                    NbtComponent nbtComponent = itemStack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
                    NbtCompound nbt = nbtComponent.copyNbt();

                    SpellCallback.EVENT.invoker().interact(
                            nbt,
                            user,
                            hitResults,
                            world);


                    super.onEntityHit(entityHitResult);
                }


            };

            arrow.setVelocity(user.getRotationVector().multiply(2.0d));


            // Spawn the arrow in the world
            world.spawnEntity(arrow);


        }

        return super.use(world, user, hand);
    }
}
