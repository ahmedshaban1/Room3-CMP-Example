package org.room3.exmple

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform