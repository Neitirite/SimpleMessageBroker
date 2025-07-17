package com.neitirite
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement
import java.lang.System
import java.io.File
import androidx.sqlite.*
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.util.UUID
import java.time.Instant

val topicDatabase = File(System.getenv("TOPICS_DB").toString())
val databaseConnection = BundledSQLiteDriver().open(topicDatabase.toString())

class Topics {
    fun createDB (){
        if (!topicDatabase.exists()){
            topicDatabase.createNewFile()
        }
    }
    fun connectTopic(topicName: String): String {
            databaseConnection.execSQL("CREATE TABLE IF NOT EXISTS $topicName (MESSAGE_UUID TEXT PRIMARY KEY, MESSAGE TEXT, TIMESTAMP TEXT)")
            return "200"
    }

    fun closeTopic(topicName: String): String{
            databaseConnection.execSQL("DROP TABLE $topicName")
            return "200"
    }

    fun sendMessage(topicName: String, messageJson: JsonObject): String {
        val timestamp = Instant.now().toString()
        val messageUUID = UUID.randomUUID().toString()
        val message = Json.encodeToString(messageJson)
        databaseConnection.execSQL("INSERT INTO $topicName (MESSAGE_UUID, MESSAGE, TIMESTAMP) VALUES ('$messageUUID', '$message', '$timestamp')")
        return "200"
    }

    fun receiveMessage(topicName: String): String {
        val message = databaseConnection.prepare("SELECT MESSAGE FROM $topicName LIMIT 1").use { element ->
            if (element.step()) {
                element.getText(0)
            } else {
                null
            }
        }
        val messageUUID = databaseConnection.prepare("SELECT MESSAGE_UUID FROM $topicName LIMIT 1").use { element ->
            if (element.step()) {
                element.getText(0)
            } else {
                null
            }
        }
        if (messageUUID == null){
            return "No messages found"
        }
        val result = Serialization.ResultMessage(messageUUID, Json.encodeToJsonElement(message))
        databaseConnection.execSQL("DELETE FROM $topicName WHERE MESSAGE_UUID = (SELECT '${messageUUID}' FROM $topicName LIMIT 1)")
        return Json.encodeToString(result)
    }

}