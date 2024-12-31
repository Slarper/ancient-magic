package com.example;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static com.example.ExampleMod.SPELL;

public class SpellProjectileEntity extends ThrownItemEntity {

    public int age = 3*20;

    public static final double SPEED = 2d;

    public SpellProjectileEntity(World world, LivingEntity owner, ItemStack stack) {
        super(EntityType.ENDER_PEARL, owner, world, stack);
        Vec3d speed = owner.getRotationVector().multiply(SPEED);
        this.setVelocity(speed);
        // Set arrow properties for invisibility and no damage
        this.setNoGravity(true);  // Optional: Make arrow behave like it has no gravity
        this.setInvisible(true);  // Make the arrow invisible
        this.setSilent(true);

    }

    @Override
    protected Item getDefaultItem() {
        return SPELL;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);

//        despawn it
        this.discard();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        this.discard();
    }

    @Override
    public void tick() {
        super.tick();
        this.age--;
        if (this.age <= 0){
            this.discard();
        }
    }
}
