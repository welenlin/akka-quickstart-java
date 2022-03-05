package com.example;

import akka.actor.typed.ActorSystem;

import java.io.IOException;

public class AkkaQuickstart {
//  public static void main(String[] args) {
//    //#actor-system
//    final ActorSystem<GreeterMain.SayHello> greeterMain = ActorSystem.create(GreeterMain.create(), "helloakka");
//    //#actor-system
//
//    //#main-send-messages
//    greeterMain.tell(new GreeterMain.SayHello("Charles"));
//    //#main-send-messages
//
//    try {
//      System.out.println(">>> Press ENTER to exit <<<");
//      System.in.read();
//    } catch (IOException ignored) {
//    } finally {
//      greeterMain.terminate();
//    }
//  }


    public static void main(String[] args) {
        final ActorSystem<NotificationMain.Message> notificationMain = ActorSystem.create(NotificationMain.create(), "helloakka");

        //#main-send-messages
        notificationMain.tell(new NotificationMain.Message("message", "SMS"));
        //#main-send-messages

        try {
            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();
        } catch (IOException ignored) {
        } finally {
            notificationMain.terminate();
        }

    }
}
