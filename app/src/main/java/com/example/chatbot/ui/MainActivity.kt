package com.example.chatbot.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.R
import com.example.chatbot.data.message
import com.example.chatbot.utils.Botresponse
import com.example.chatbot.utils.constants.OPEN_GOOGLE
import com.example.chatbot.utils.constants.OPEN_SEARCH
import com.example.chatbot.utils.constants.RECIEVE_ID
import com.example.chatbot.utils.constants.SEND_ID
import com.example.chatbot.utils.time.timestamp
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.sql.Time
import java.sql.Timestamp

class MainActivity : AppCompatActivity() {


    //You can ignore this messageList if you're coming from the tutorial,
    // it was used only for my personal debugging
    var messagesList = mutableListOf<message>()

    private lateinit var adapter: messagingAdapter
    private val botList = listOf("Peter", "Francesca", "Luigi", "Igor")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView()

        clickEvents()

        val random = (0..3).random()
        customBotMessage("Hello! Today you're speaking with ${botList[random]}, how may I help?")
    }

    private fun clickEvents() {

        //Send a message
        btn_send.setOnClickListener {
            sendMessage()
        }

        //Scroll back to correct position when user clicks on text view
        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(100)

                withContext(Dispatchers.Main) {
                    rv_messages.scrollToPosition(adapter.itemCount - 1)

                }
            }
        }
    }

    private fun recyclerView() {
        adapter = messagingAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)

    }

    override fun onStart() {
        super.onStart()
        //In case there are messages, scroll to bottom when re-opening app
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun sendMessage() {
        val message = et_message.text.toString()
        val timeStamp:String = Timestamp(System.currentTimeMillis()).toString()

        if (message.isNotEmpty()) {
            //Adds it to our local list
            messagesList.add(message(message, SEND_ID, timeStamp))
            et_message.setText("")

            adapter.insertmessage(message(message, SEND_ID, timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount - 1)

            botResponse(message)
        }
    }

    private fun botResponse(message: String) {
        val timeStamp: String = Timestamp(System.currentTimeMillis()).toString()

        GlobalScope.launch {
            //Fake response delay
            delay(1000)

            withContext(Dispatchers.Main) {
                //Gets the response
                val response = Botresponse.basicresponse(message)

                //Adds it to our local list
                messagesList.add(message(response, RECIEVE_ID, timeStamp))

                //Inserts our message into the adapter
                adapter.insertmessage(message(response, RECIEVE_ID, timeStamp))

                //Scrolls us to the position of the latest message
                rv_messages.scrollToPosition(adapter.itemCount - 1)

                //Starts Google
                when (response) {
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }

                }
            }
        }
    }

    private fun customBotMessage(message: String) {

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp:String = Timestamp(System.currentTimeMillis()).toString()
                //val timestamp= Time.timestamp()
                messagesList.add(message(message, RECIEVE_ID, timeStamp))
                adapter.insertmessage(message(message, RECIEVE_ID, timeStamp))

                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
}