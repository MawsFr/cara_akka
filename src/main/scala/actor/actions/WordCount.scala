package actor.actions

import scala.beans.{ BeanProperty, BooleanBeanProperty }

/**
 * Stores the number of words in a line
 * 
 * This class is an akka message sent by the Counter to the Master (after the Master sent a CountWordsInLine message) storing the number of words of a line.
 */
case class WordCount(result: Int) {

}
