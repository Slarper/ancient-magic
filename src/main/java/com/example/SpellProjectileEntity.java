package com.example;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static com.example.ExampleMod.SPELL;

public class SpellProjectileEntity extends ThrownItemEntity {

    public int age2 = 3*20;

    public static final double SPEED = 2d;

    public SpellProjectileEntity(World world, LivingEntity owner, ItemStack stack) {
        super(EntityType.ENDER_PEARL, owner, world, stack);
        Vec3d speed = owner.getRotationVector().multiply(SPEED);
        this.setVelocity(speed);
        // Set arrow properties for invisibility and no damage
        this.setNoGravity(true);  // Optional: Make arrow behave like it has no gravity

        this.setSilent(true);


    }
    public SpellProjectileEntity(EntityType<? extends ThrownItemEntity> type, double x, double y, double z, World world, ItemStack stack) {
        super(type, x, y, z, world, stack);

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
        this.age2--;
        if (this.age2 <= 0){
            this.discard();
        }
    }


}
