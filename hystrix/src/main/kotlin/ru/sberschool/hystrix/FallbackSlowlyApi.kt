package ru.sberschool.hystrix

class FallbackSlowlyApi : SlowlyApi {
    override fun getSomething() = Pokemon("poke")
}


