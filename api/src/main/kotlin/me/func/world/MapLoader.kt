package me.func.world

import org.bukkit.World
import ru.cristalix.core.map.BukkitWorldLoader
import ru.cristalix.core.map.LoadedMap
import ru.cristalix.core.map.MapService
import java.util.concurrent.TimeUnit

object MapLoader {
    private val mapService: MapService by lazy { MapService() }

    @JvmOverloads
    fun load(
        type: String,
        name: String,
        timeUnit: TimeUnit = TimeUnit.SECONDS,
        wait: Int = 15,
    ): LoadedMap<World> {
        return try {
            mapService.loadMap(
                mapService.getLatestMapByGameTypeAndMapName(type, name)
                    .orElseThrow { RuntimeException("Map download failure. $type/$name") }
                    .latest,
                BukkitWorldLoader.INSTANCE
            )[wait.toLong(), timeUnit]
        } catch (throwable: Throwable) {
            throw RuntimeException("Map get latest failure. $type/$name", throwable)
        }
    }

    fun loadAll(vararg paths: Pair<String, String>) = paths.map { load(it.first, it.second) }
}
