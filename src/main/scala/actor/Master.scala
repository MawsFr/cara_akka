package actor

import actor.actions.{ CountWordsInLine, WordCount, Process, End }
import util.Constants
import akka.actor.{ Actor, Props, ActorLogging, Terminated, ActorSystem }
import akka.routing.{ ActorRefRoutee, RoundRobinRoutingLogic, Router }

import scala.io.Source

/**
 * This actor creates Counters that will count the number of words of a file.
 * It processes a file like that :
 * - Read the entire file
 * - For each line :
 *     - Route a line to a Counter
 *     - Wait for the number of words
 *     - Add the number of words to the total
 * - Show the total number of words 
 */
class Master extends Actor with ActorLogging {
	var nbWords : Int = 0
	var nbLines : Int = 0
	var i : Int = 0
	
	/**
	 * The router
	 */
	var router : Router = {
    val routees = Vector.fill(Constants.COUNTER_NUMBER) {
      val r = context.actorOf(Props[Counter])
      context watch r
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }
	
	override def preStart = {
		log.info("Master started");
	}

	override def postStop = {
		log.info("Master stopped");
	}

	override def receive = {
	  // Starts the file processing
  	case process: Process => {
  	  log.info(s"Processing file : ${process.filename}")
  	  var lines = Source.fromFile(process.filename).getLines
  	  nbLines = Source.fromFile(process.filename).getLines.size;
  		Source.fromFile(process.filename).getLines.foreach(line => {
  			router.route(CountWordsInLine(line), self)
  		})
  	}
  	
  	// End the file processing
		case process: End => {
		  log.info(s"Nb total words : ${this.nbWords}")
		}

		// Count the number of word of a line
  	case wordCount: WordCount => {
  		nbWords += wordCount.result
//  		log.info(s"${wordCount.result} words(s) counted(s)")
  		i += 1
			if (i == nbLines) {
			  self ! End()
			}
  	}
  	
  	case Terminated(a) ⇒
      router = router.removeRoutee(a)
      val r = context.actorOf(Props[Counter])
      context watch r
      router = router.addRoutee(r)
	}
}