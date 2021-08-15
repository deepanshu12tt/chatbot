package com.example.chatbot.utils

import com.example.chatbot.utils.constants.OPEN_GOOGLE
import com.example.chatbot.utils.constants.OPEN_SEARCH
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

object Botresponse {
    fun basicresponse(_message: String):String
    {
        val random=(0..2).random()
        val message =_message.toLowerCase()
        return when
        {
    //Hello
            message.contains("hello")->
            {
               when (random)
               {
                   0->"Hello there!"
                   1->"sup"
                   2->"Buongiorno!"
                   else ->"error"
               }
            }
            //flipped coin
            message.contains("flip")&& message.contains("coin")->
            {
                var r=(0..1).random()
                val result=if(r==0)"heads" else "tails"
                "I flipped a coin and it landes as $result"
            }
            //How are you?
            message.contains("how are you") -> {
                when (random) {
                    0 -> "I'm doing fine, thanks!"
                    1 -> "I'm hungry..."
                    2 -> "Pretty good! How about you?"
                    else -> "error"
                }
            }
            //solve maths
            message.contains("solve")->
            {
                val equation:String?=message.substringAfter("solve")
                return try {
                    val answer=SolveMath.solveMath(equation ?:"0")
                    answer.toString()

                }catch (e:Exception)
                {
                    "sorry,i can't find that!"
                }
            }
            message.contains("open") && message.contains("google")->
            {
                OPEN_GOOGLE
            }
            message.contains("search")->
            {
                OPEN_SEARCH
            }

           //get current time
            message.contains("time") && message.contains("?")-> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date = sdf.format(Date(timeStamp.time))

                date.toString()
            }

            else ->
            {
                when (random)
                {
                    0->"I don't understand!"
                    1->"Idk"
                    2->"Try asking me somethinh different!"
                    else ->"error"
                }


            }

        }
    }
}