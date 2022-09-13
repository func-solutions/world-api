package me.func.world

import org.bukkit.Location
import org.bukkit.World

class Label(
    val name: String,
    val tag: String = "",
    world: World,
    x: Double,
    y: Double,
    z: Double
) : Location(world, x, y, z) {
    val tagInt: Int
        get() {
            validateTag()
            return tag.toInt()
        }

    val tagDouble: Double
        get() {
            validateTag()
            return tag.toDouble()
        }
    val tagFloat: Float
        get() {
            validateTag()
            return tag.toFloat()
        }

    private fun validateTag() {
        if (tag.isEmpty()) throw RuntimeException("Null tag")
    }

    val coords: String
        get() = "$blockX, $blockY, $blockZ"

    override fun toString() = "Label{name=$name,tag=$tag,x=$x,y=$y,z=$z}"
}
