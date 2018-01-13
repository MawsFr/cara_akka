package actor.actions

import scala.beans.{ BeanProperty, BooleanBeanProperty }

/**
 * This message stores the file we want to count the number of lines
 */
case class Process(filename: String) {
  
}