package com.example;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

import java.util.ArrayList;

public class EmptySpell implements SpellCallback{

    public  String getName(){
        return "empty_spell";
    }

    public double getManaCost() {
        return 0;
    }

    @Override
    public ActionResult interact(NbtCompound nbt, PlayerEntity caster, ArrayList<HitResult> hitResults, World world) {
        if (!nbt.contains(this.getName())){
            return ActionResult.PASS;
        }


        var mana = caster.getAttachedOrSet(CustomAttachment.MANA_ATTACHMENT_TYPE, 0.0d);

        caster.sendMessage(Text.of("Current Mana :" + mana), false);


        return ActionResult.PASS;
    }

    public static void register() {
        SpellCallback.EVENT.register(
                new EmptySpell()
        );
    }


}
