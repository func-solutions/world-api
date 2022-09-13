package me.func.world

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld
import ru.cristalix.core.map.LoadedMap

class WorldMeta(private val cristalixMap: LoadedMap<World>) : Area {
    override val labels: MutableList<Label> = ArrayList()
    override val world: CraftWorld
        get() = cristalixMap.world as CraftWorld
    override val name: String
        get() = cristalixMap.buildWorldState.name
    override val tag: String
        get() = ""

    override fun contains(location: Location?) = location?.world == world

    @JvmOverloads
    fun label(key: String, offsetX: Double = 0.0, offsetY: Double = 0.0, offsetZ: Double = 0.0) = getLabel(key)?.apply {
        x += offsetX
        y += offsetY
        z += offsetZ
    }

    @JvmOverloads
    fun labels(key: String, offsetX: Double = 0.0, offsetY: Double = 0.0, offsetZ: Double = 0.0) =
        getLabels(key).onEach {
            it.x += offsetX
            it.y += offsetY
            it.z += offsetZ
        }

    @JvmOverloads
    fun labels(key: String, tag: String, offsetX: Double = 0.0, offsetY: Double = 0.0, offsetZ: Double = 0.0) =
        getLabels(key, tag).onEach {
            it.x += offsetX
            it.y += offsetY
            it.z += offsetZ
        }

    fun box(key: String, tag: String) = getBox(key, tag)

    fun boxs(key: String) = getBoxes(key)

    init {
        cristalixMap.buildWorldState.points.forEach { (key, value) ->
            for (point in value) {
                val v3 = point.v3
                labels.add(Label(key, point.tag, world, v3.x, v3.y, v3.z))
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WorldMeta

        if (cristalixMap != other.cristalixMap) return false
        if (labels != other.labels) return false
        if (world != other.world) return false
        if (name != other.name) return false
        if (tag != other.tag) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cristalixMap.hashCode()
        result = 31 * result + labels.hashCode()
        result = 31 * result + world.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + tag.hashCode()
        return result
    }

    override fun toString(): String {
        return "WorldMeta(cristalixMap=$cristalixMap, labels=$labels, world=$world, name='$name', tag='$tag')"
    }
}
