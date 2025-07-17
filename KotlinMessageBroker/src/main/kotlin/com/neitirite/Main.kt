package com.neitirite
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.json.*
import kotlin.time.Duration.Companion.seconds

val port = System.getenv("BROKER_PORT")?.toIntOrNull() ?: 5000
fun main() {
    Topics().createDB()
    embeddedServer(Netty, port, "0.0.0.0") {
        install(WebSockets){
            pingPeriod = 15.seconds
            timeout = 30.seconds
            maxFrameSize = Long.MAX_VALUE
            masking = false
        }
        println("Starting websocket api...")
        routing {
            webSocket ("/"){
                println("New connection: ${this.call.request.origin.remoteHost}")
                try {
                    for (frame in incoming) {
                        when (frame) {
                            is Frame.Text -> {
                                val text = frame.readText()
                                val command = Json.decodeFromString<Serialization.Command>(text)
                                println("Received command: ${command.command}")
                                when (command.command) {
                                    "connectTopic" -> {
                                        val topic = command.properties["name"]?.jsonPrimitive?.content
                                        try {
                                            val response = Topics().connectTopic(topic.toString())
                                            send(response)
                                        } catch (e: Exception) {
                                            println(e.message)
                                            send(e.message ?: "Unknown error")
                                        }
                                    }
                                    "sendMessage" -> {
                                        val topic = command.properties["topic"]?.jsonPrimitive?.content
                                        val response = Topics().sendMessage(topic.toString(),
                                                command.properties["message"] as JsonObject)
                                        send(response)
                                    }
                                    "closeTopic" -> {
                                        val topic = command.properties["name"]?.jsonPrimitive?.content
                                        try {
                                            val response = Topics().closeTopic(topic.toString())
                                            send(response)
                                        } catch (e: Exception) {
                                            println("${e.message}")
                                            send(e.message ?: "Unknown error")
                                        }
                                    }
                                    "receiveMessage" -> {
                                        val topic = command.properties["topic"]?.jsonPrimitive?.content
                                        try {
                                            val response = Topics().receiveMessage(topic.toString())
                                            send(response)
                                        } catch (e: Exception) {
                                            println(e.message)
                                            send(e.message ?: "Unknown error")
                                        }
                                    }
                                }
                            }
                            else -> {
                                continue
                            }
                        }
                    }
                } catch (e: Exception) {
                    println("Error: ${e.message}")
                }
            }

        }
    }.start(wait = true)
}