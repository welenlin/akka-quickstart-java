package com.example;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class ApplicationActor extends AbstractBehavior<ApplicationActor.Sender> {


    private ApplicationActor(ActorContext<Sender> context) {
        super(context);
    }

    public static final class Sender {
        public final String whom;
        public final ActorRef<Receiver> replyTo;

        public Sender(String whom, ActorRef<ApplicationActor.Receiver> replyTo) {
            this.whom = whom;
            this.replyTo = replyTo;
        }
    }

    public final class Receiver {
        public final String whom;
        public final ActorRef<ApplicationActor.Sender> from;

        public Receiver(String whom, ActorRef<ApplicationActor.Sender> from) {
            this.whom = whom;
            this.from = from;
        }
    }

    public static Behavior<Sender> create() {
        return Behaviors.setup(ApplicationActor::new);
    }

    @Override
    public Receive<Sender> createReceive() {
        return newReceiveBuilder().onMessage(Sender.class, this::onSend).build();
    }

    private Behavior<Sender> onSend(Sender sender) {
        getContext().getLog().info("Hello {}!", sender.whom);
        sender.replyTo.tell(new Receiver(sender.whom, getContext().getSelf()));
        getContext().getLog().info("Ack {}!", sender.whom);
        return this;
    }
}