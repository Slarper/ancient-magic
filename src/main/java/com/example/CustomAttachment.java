package com.example;


import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Identifier;

public class CustomAttachment {

    public static final Identifier MANA_ATTACHMENT_ID = Identifier.of(ExampleMod.MOD_ID, "mana");
//    MAX_MANA
    public static final Identifier MAX_MANA_ATTACHMENT_ID = Identifier.of(ExampleMod.MOD_ID,"max_mana");

    // mana_level
    public static final Identifier MANA_LEVEL_ATTACHMENT_ID = Identifier.of(ExampleMod.MOD_ID,"mana_level");

    // Define the attachment type for mana.
    public static final AttachmentType<Double> MANA_ATTACHMENT_TYPE = AttachmentRegistry.createPersistent(MANA_ATTACHMENT_ID, Codec.DOUBLE);

    // Define the attachment type for max_mana.
    public static final AttachmentType<Double> MAX_MANA_ATTACHMENT_TYPE = AttachmentRegistry.createPersistent(MAX_MANA_ATTACHMENT_ID, Codec.DOUBLE);

    // Define the attachment type for mana_level.
    public static final AttachmentType<Integer> MANA_LEVEL_ATTACHMENT_TYPE = AttachmentRegistry.createPersistent(MANA_LEVEL_ATTACHMENT_ID, Codec.INT);
    public static void register() {

    }
}
