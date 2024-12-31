package com.example;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import static com.example.ExampleMod.MOD_ID;

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
}
