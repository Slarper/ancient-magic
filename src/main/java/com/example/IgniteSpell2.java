package com.example;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

import java.util.ArrayList;

public class IgniteSpell2 extends EmptySpell{
    @Override
    public String getName() {
        return "ignite";
    }

    @Override
    public double getManaCost() {
        return 10;
    }

    @Override
    public ActionResult interact(NbtCompound nbt, PlayerEntity caster, ArrayList<HitResult> hitResults, World world) {


        return super.interact(nbt, caster, hitResults, world);
    }
}
