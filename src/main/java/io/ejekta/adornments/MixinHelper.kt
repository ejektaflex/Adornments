package io.ejekta.adornments

import io.ejekta.kambrik.Kambrik
import io.ejekta.kambrikx.ext.SpecialRecipes
import net.minecraft.item.ItemStack

object MixinHelper {
    fun smithingCanTake(stackA: ItemStack, stackB: ItemStack): ItemStack? {
        for (recipe in Kambrik.SpecialRecipes.AnvilRecipes.values) {
            val result = recipe(stackA, stackB)
            if (result != null) {
                return result
            }
        }
        return null
    }
}