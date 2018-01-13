package main

import actor.actions.{ Process }
import actor.Master
import akka.actor.{ ActorRef, ActorSystem, Props }

/**
 * This class is the main class
 */
object Main extends App {

    val system: ActorSystem = ActorSystem("counter-system")
    val master: ActorRef = system.actorOf(Props[Master], "master")
    
//    master ! Process("file.txt")
    master ! Process("file2.txt")
    
    Thread.sleep(10000) // To let the programm process the file
    
    system.terminate()
    
    
    
}
