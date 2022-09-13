package me.func.world

enum class Orientation(
    val swap: Boolean = false,
    val factor: Int = 0
) {
    PX(false, 1),
    MX(true, 1),
    PY(false, -1),
    MY(true, -1);
}
