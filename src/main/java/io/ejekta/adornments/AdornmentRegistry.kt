package io.ejekta.adornments

import com.mojang.serialization.Lifecycle
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.util.registry.SimpleRegistry

class AdornmentRegistry : SimpleRegistry<Adornment>(KEY, Lifecycle.stable()) {
    fun add(arg: RegistryKey<Adornment>, entry: Adornment): Adornment {
        ADORNMENTS[entry.recipeItem] = arg.value
        return super.add(arg, entry, lifecycle)
    }

    fun getForMaterial(material: Item): Adornment? {
        return get(ADORNMENTS[material])
    }

    companion object {
        private val ADORNMENTS: MutableMap<Item, Identifier> = mutableMapOf()
        val KEY: RegistryKey<Registry<Adornment>> = RegistryKey.ofRegistry<Adornment>(Identifier(AdornmentMod.ID, "adornments"))
    }
}