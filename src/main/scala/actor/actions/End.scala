package actor.actions

import scala.beans.{ BeanProperty, BooleanBeanProperty }

/**
 * This message is sent by a Counter to the Master when it has counted the last words of the last lines.
 */
case class End() {
  
}