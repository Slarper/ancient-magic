package com.example;

import net.minecraft.component.type.NbtComponent;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class IgniteSpell {
    public static void register() {
        SpellCallback.EVENT.register(((nbt, caster, targets, blocks) -> {
            NbtElement n1 = nbt.get("Ignite");
            if (n1 != null) {
                caster.sendMessage(Text.of("You ignite!"), false);
            }

            return ActionResult.PASS;
        }));
    }
}
