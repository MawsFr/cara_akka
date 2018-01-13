package actor.actions

import scala.beans.{ BeanProperty, BooleanBeanProperty }

/**
 * Stores the line and can return the number of words of it
 * 
 * This class is an akka message sent by the Master to the Counter when it reads a single line
 */
case class CountWordsInLine(line: String) {
  /**
   * Returns the number of words of the stored line
   */
  def countWords(): Int =
    if ((line == null || line.isEmpty)) 0 else line.split("\\s").length

}
