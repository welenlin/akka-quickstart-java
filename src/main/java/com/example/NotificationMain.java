package com.example;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class NotificationMain extends AbstractBehavior<NotificationMain.Message> {

    public static Behavior<Message> create() {
        return Behaviors.setup(NotificationMain::new);
    }
    private ActorRef<ApplicationActor.Sender> applicationActor;

    public static class Message {
        private String message;
        private String type;

        Message(String message, String type) {
            this.message = message;
            this.type = type;
        }

    }

    private NotificationMain(ActorContext context) {
        super(context);
        applicationActor = getContext().spawn(ApplicationActor.create(), "applicationActor");
    }

    @Override
    public Receive<Message> createReceive() {
        return newReceiveBuilder().onMessage(Message.class, this::onMessage).build();
    }

    private Behavior<Message> onMessage(Message message) {
        ActorRef<ApplicationActor.Receiver> replyTo =
                getContext().spawn(NotificationActor.create(), "onMessageName");
        applicationActor.tell(new ApplicationActor.Sender("Sender", replyTo));
        applicationActor.tell(new ApplicationActor.Sender("Sender2", replyTo));
        applicationActor.tell(new ApplicationActor.Sender("Sender3", replyTo));
        applicationActor.tell(new ApplicationActor.Sender("Sender4", replyTo));
        applicationActor.tell(new ApplicationActor.Sender("Sender5", replyTo));
        applicationActor.tell(new ApplicationActor.Sender("Sender6", replyTo));

        return this;
    }
}