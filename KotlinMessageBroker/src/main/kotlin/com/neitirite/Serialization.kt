package com.neitirite
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

class Serialization {
    @Serializable
    data class Command(val command: String, val properties: Map<String, JsonElement>)
    @Serializable
    data class ResultMessage(val UUID: String, val message: JsonElement)
}