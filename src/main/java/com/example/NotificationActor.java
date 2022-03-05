package com.example;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class NotificationActor extends AbstractBehavior<ApplicationActor.Receiver> {

    private int totalSend;

    private NotificationActor(ActorContext<ApplicationActor.Receiver> context) {
        super(context);
    }

    public static Behavior<ApplicationActor.Receiver> create() {
        return Behaviors.setup(context -> new NotificationActor(context));
    }

    @Override
    public Receive<ApplicationActor.Receiver> createReceive() {
        return newReceiveBuilder().onMessage(ApplicationActor.Receiver.class, this::onReceived).build();
    }

    private Behavior<ApplicationActor.Receiver> onReceived(ApplicationActor.Receiver receiver) {
        totalSend++;
        getContext().getLog().info("Receiver {} and total message send would be {}", receiver.whom, totalSend);
        return this;
    }
}
