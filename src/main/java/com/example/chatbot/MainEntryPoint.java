package com.example.chatbot;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;

public class MainEntryPoint {

    public static void main(String[] args) {

        System.out.println("================================================================================");
        System.out.println("================================================================================");
        System.out.println("------------------------    CHATBOT APPLICATION  --------------------------------");
        System.out.println("------------------------   AKKA ACTOR MODEL    ----------------------------------");

        ActorRef<ChatBotController.Command> chatBotActorSystem =  ActorSystem.create(ChatBotController.create(), "ChatBotSystem");
        chatBotActorSystem.tell(new ChatBotController.StartCommand());


    }
}
