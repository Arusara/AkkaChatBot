package com.example.chatbot;


import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.io.Serializable;

public class ChatBotController extends AbstractBehavior<ChatBotController.Command> {


    public static interface Command extends Serializable {

    }

    public static class StartCommand implements Command {

    }

    public static class RequestCommand implements Command {

        private String request;


        public String getRequest() {
            return request;
        }

        RequestCommand(String request) {
            this.request = request;
        }
    }

    private ChatBotController(ActorContext context) {
        super(context);
    }

    private ActorRef<ChatBotUser.Command> user;


    public static Behavior<Command> create() {
        return Behaviors.setup(ChatBotController::new);
    }

    @Override
    public Receive createReceive() {

        return newReceiveBuilder()
                .onMessage(StartCommand.class, startCommand-> {
                    System.out.println("ads");
                    user = getContext().spawn(ChatBotUser.create(), "chatbot");
                    user.tell(new ChatBotUser.InputCommand("Welcome to chat bot by sara\n how can i help you?", getContext().getSelf()));
                    return this;
                })
                .onMessage(RequestCommand.class, requestCommand -> {

                    String message = "Please close the chat";

                    if ("akka".equals(requestCommand.getRequest())) {
                        message = "Please learn AKKA concurrency model";
                    } else if ("docker".equals(requestCommand.getRequest())) {
                        message = "Please learn Docker for CD";
                    } else if ("java".equalsIgnoreCase(requestCommand.getRequest())) {
                        message = "Please learn java";
                    }
                    user.tell(new ChatBotUser.ChatBotUserCommand(message, getContext().getSelf()));
                    // we can implement our model here to reply to user.
                    return this;
                })
                .build();

    }
}
