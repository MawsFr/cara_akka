package actor;

import actor.actions.{ CountWordsInLine, WordCount }
import akka.actor.{ Actor, Props, ActorLogging }

/**
 * This actor counts the number of words of a line and send this number to the Master
 */
class Counter extends Actor with ActorLogging {

	override def preStart = {
		log.info("Counter started");
	}

	override def postStop = {
		log.info("Counter stopped");
	}
	
	override def receive = {
	  // Send the number of words to the Master
  	case line: CountWordsInLine => {
  		sender ! WordCount(line.countWords())
  	}
	
	}
}
