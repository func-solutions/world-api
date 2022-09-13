package me.func.world

import net.minecraft.server.v1_12_R1.BlockPosition
import net.minecraft.server.v1_12_R1.Blocks
import net.minecraft.server.v1_12_R1.IBlockData
import org.bukkit.Location
import org.bukkit.World
import ru.cristalix.core.formatting.Color
import ru.cristalix.core.math.V3
import kotlin.math.floor

fun V3.toLocation(world: World) = Location(world, x, y, z)

fun Location.orientation(): Orientation {
    if (yaw < 0) yaw += 360f
    if (yaw >= -45 && yaw <= 45) return Orientation.PX
    if (yaw in 45.0..135.0) return Orientation.PY
    return if (yaw in 135.0..225.0) Orientation.MX else Orientation.MY
}

fun Location.position() = BlockPosition(x, y, z)

fun Location.center() = Location(world, floor(x) + 0.5, floor(y), floor(z) + 0.5, yaw, pitch)

fun Location.cubeCenter() = Location(world, floor(x) + 0.5, floor(y) + 0.5, floor(z) + 0.5, yaw, pitch)

fun applyColor(data: IBlockData, color: Color?): IBlockData {
    val block = data.block
    if (color == null)
        return data
    // concrete и concrete_powder меняют цвет
    return if (block === Blocks.dR || block === Blocks.dS || block === Blocks.STAINED_GLASS) block.fromLegacyData(color.woolData) else data
}