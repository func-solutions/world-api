package me.func.world

import org.bukkit.Location
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld
import java.util.stream.Collectors


interface Area {
    val labels: Collection<Label>
    val world: CraftWorld
    val name: String
    val tag: String

    operator fun contains(location: Location?): Boolean

    fun getLabels(name: String) = labels.filter { it.name == name }

    fun getLabels(name: String, tag: String) = getLabels(name).filter { it.tag == tag }

    fun getLabel(name: String) = labels.firstOrNull { it.name == name }

    fun getLabel(name: String, tag: String) = getLabels(name).firstOrNull { it.tag == tag }

    fun requireLabel(name: String): Label? {
        var result: Label? = null
        for (label in labels) {
            if (label.name != name) continue
            if (result != null) throw RuntimeException(
                "Duplicate label " + name + " inside box " + this.name + "/" + tag +
                        " on " + label.coords + " and " + result.coords
            )
            result = label
        }
        if (result == null) throw RuntimeException("No label '" + name + "' was found inside box '" + this.name + "/" + tag + "'")
        return result
    }

    fun getBoxes(name: String) = getLabels(name).stream()
            .collect(Collectors.groupingBy(Label::tag))
            .entries.stream()
            .map { e -> findBoxSmart(e.value, name, e.key) }
            .collect(Collectors.toMap({ box -> box?.tag }) { box: Box? -> box!! })

    fun getBox(name: String, tag: String) = findBoxSmart(getLabels(name, tag), name, tag)

    fun getBox(name: String) = getBox(name, "")

    fun findBoxSmart(labels: List<Label>, name: String, tag: String): Box {
        if (labels.isEmpty()) throw RuntimeException("Box " + name + "/" + tag + " wasn't found on " + this.name)
        val iterator: Iterator<Label> = labels.iterator()
        val min: Location = iterator.next().clone()
        val max = min.clone()
        while (iterator.hasNext()) {
            val loc: Location = iterator.next()
            if (loc.blockX > max.blockX) max.setX(loc.blockX.toDouble())
            if (loc.blockY > max.blockY) max.setY(loc.blockY.toDouble())
            if (loc.blockZ > max.blockZ) max.setZ(loc.blockZ.toDouble())
            if (loc.blockX < min.blockX) min.setX(loc.blockX.toDouble())
            if (loc.blockY < min.blockY) min.setY(loc.blockY.toDouble())
            if (loc.blockZ < min.blockZ) min.setZ(loc.blockZ.toDouble())
        }
        return Box(this, min, max, name, tag)
    }

    fun sendMessage(message: String) {
        world.players.filter { contains(it.location) }.forEach { it.sendMessage(message) }
    }
}
