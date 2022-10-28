package me.func.world

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld
import ru.cristalix.core.map.LoadedMap

data class WorldMeta(
    override val world: CraftWorld,
    override val name: String,
    val points: List<Label> = arrayListOf()
) : Area {

    constructor(cristalixMap: LoadedMap<World>) :
            this(
                cristalixMap.world as CraftWorld,
                cristalixMap.buildWorldState.name,
                cristalixMap.buildWorldState.points.map {(key, value) ->
                    val temp = arrayListOf<Label>()

                    for (point in value) {
                        val v3 = point.v3
                        temp.add(Label(key, point.tag, cristalixMap.world as CraftWorld, v3.x, v3.y, v3.z))
                    }

                    temp
                }.flatten()
            )

    override val labels: MutableList<Label> = points.toMutableList()
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

}
