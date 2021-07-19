package com.example.chatbot;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.io.Serializable;
import java.util.Scanner;

public class ChatBotUser extends AbstractBehavior<ChatBotUser.Command> {

    public interface Command extends Serializable {

    }

    public static class ChatBotUserCommand implements Command {
        private String message;

        public String getMessage() {
            return message;
        }

        ChatBotUserCommand(String message, ActorRef<ChatBotController.Command> actor) {
            this.message = message;
            this.chatBotController = actor;
        }

        public ActorRef<ChatBotController.Command> getChatBotController() {
            return chatBotController;
        }

        private ActorRef<ChatBotController.Command> chatBotController;
    }

    public static class InputCommand implements Command {
        private String message;

        public String getMessage() {
            return message;
        }

        InputCommand(String message, ActorRef<ChatBotController.Command> actor) {
            this.message = message;
            this.chatBotController = actor;
        }

        public ActorRef<ChatBotController.Command> getChatBotController() {
            return chatBotController;
        }

        private ActorRef<ChatBotController.Command> chatBotController;
    }


    private ChatBotUser(ActorContext context) {
        super(context);
    }

    public static Behavior<Command> create() {
        return Behaviors.setup(ChatBotUser::new);
    }

    @Override
    public Receive createReceive() {
        return newReceiveBuilder()
                .onMessage(ChatBotUserCommand.class, userCommand -> {
                    System.out.println(" The Response from chatbot is: "+userCommand.getMessage());
                    return this;
                })

                .onMessage(InputCommand.class, input ->{
                    System.out.println(" The Request from chatbot is: "+input.getMessage());
                    System.out.println("Please provide valid inputs\n");
                    Scanner scanner = new Scanner(System.in);
                    String str = scanner.nextLine();
                    input.getChatBotController().tell(new ChatBotController.RequestCommand(str));

                    return this;
                })
                .build();
    }
}
