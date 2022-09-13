package me.func.world

import org.bukkit.Location
import org.bukkit.World
import ru.cristalix.core.math.V3

fun V3.toLocation(world: World) = Location(world, x, y, z)
