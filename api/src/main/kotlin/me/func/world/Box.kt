package me.func.world

import net.minecraft.server.v1_12_R1.BlockPosition
import net.minecraft.server.v1_12_R1.IBlockData
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld
import org.bukkit.entity.Entity
import ru.cristalix.core.math.V3
import java.util.function.BiConsumer
import java.util.function.Consumer

class Box(
    val container: Area,
    val min: Location,
    val max: Location,
    override val name: String,
    override val tag: String
) : Area {
    val dimensions: V3 = V3(
        max.x - min.x,
        max.y - min.y,
        max.z - min.z
    )

    override val labels: MutableList<Label>
    val meta: Map<String, Any>? = null

    fun expandVert(): Box {
        min.setY(0.0)
        max.setY(255.0)
        updateLabelsCache()
        return this
    }

    fun updateLabelsCache() {
        labels.clear()
        for (label in container.labels) {
            if (this.contains(label)) labels.add(label)
        }
    }

    override val world: CraftWorld
        get() = container.world

    override fun contains(location: Location?) =
        min.x <= location!!.x && max.x >= location.x &&
                min.y <= location.y && max.y >= location.y &&
                min.z <= location.z && max.z >= location.z

    val center: Location
        get() = Location(
            container.world,
            min.x + (max.x - min.x) / 2,
            min.y + (max.y - min.y) / 2,
            min.z + (max.z - min.z) / 2
        )

    fun toRelativeVector(location: Location): V3 {
        return V3(
            location.getX() - min.x,
            location.getY() - min.y,
            location.getZ() - min.z
        )
    }

    fun forEachNMS(action: BiConsumer<BlockPosition?, IBlockData?>) {
        var x = min.getX().toInt()
        while (x <= max.getX()) {
            var y = min.getY().toInt()
            while (y <= max.getY()) {
                var z = min.getZ().toInt()
                while (z <= max.getZ()) {
                    val t = BlockPosition(x, y, z)
                    action.accept(t, world.handle.getType(t))
                    z++
                }
                y++
            }
            x++
        }
    }

    fun forEachBukkit(action: Consumer<Location>) {
        var x = min.getX().toInt()
        while (x <= max.getX()) {
            var y = min.getY().toInt()
            while (y <= max.getY()) {
                var z = min.getZ().toInt()
                while (z <= max.getZ()) {
                    action.accept(Location(world, x.toDouble(), y.toDouble(), z.toDouble()))
                    z++
                }
                y++
            }
            x++
        }
    }

    fun forEach(action: Consumer<V3>) {
        var x = min.getX()
        while (x <= max.getX()) {
            var y = min.getY()
            while (y <= max.getY()) {
                var z = min.getZ()
                while (z <= max.getZ()) {
                    action.accept(V3(x, y, z))
                    z++
                }
                y++
            }
            x++
        }
    }

    fun transpose(
        absoluteOrigin: V3,
        orientation: Orientation,
        relativeOrigin: V3,
        x: Int, y: Int, z: Int
    ): Location {
        val newX = x - min.getX() - relativeOrigin.x
        val newY = y - min.getY() - relativeOrigin.y
        val newZ = z - min.getZ() - relativeOrigin.z
        var tx: Double = (if (orientation.swap) newZ else newX) * orientation.factor
        var ty = newY
        var tz: Double = (if (orientation.swap) -newX else newZ) * orientation.factor
        tx += absoluteOrigin.x
        ty += absoluteOrigin.y
        tz += absoluteOrigin.z
        return Location(world, tx, ty, tz)
    }

    fun <T : Entity> getEntities(type: Class<T>) =
        world.getEntitiesByClass(type).filter { entity -> contains(entity.location) }

    fun outset(amount: Int) {
        min.subtract(amount.toDouble(), amount.toDouble(), amount.toDouble())
        max.add(amount.toDouble(), amount.toDouble(), amount.toDouble())
        updateLabelsCache()
    }

    fun loadChunks() {
        for (x in min.chunk.x..max.chunk.x) {
            for (z in min.chunk.z..max.chunk.z) {
                world.loadChunk(x, z)
            }
        }
    }

    init {
        labels = arrayListOf()
        updateLabelsCache()
    }
}
