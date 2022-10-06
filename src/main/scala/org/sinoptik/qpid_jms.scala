package org.sinoptik

import javax.jms.Session
import org.apache.log4j.Logger
import org.apache.qpid.jms.JmsConnectionFactory

object qpid_jms {

  def main(args: Array[String]): Unit = {

    val logger = Logger.getRootLogger
    val factory = new JmsConnectionFactory()

    factory.setRemoteURI("amqp://localhost:5672?protocols=AMQP") // amqps://address:443?transport.trustAll=true
    factory.setUsername("admin")
    factory.setPassword("admin")
    factory.setConnectTimeout(300000)

    val connection = factory.createQueueConnection()

    val session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE)

    val queue = session.createQueue("temperature")

    val sender = session.createProducer(queue)

    connection.start()

    //val msg = session.createTextMessage("HelloWorld")
    val message = session.createBytesMessage()

    message.writeBytes("HelloWorld".getBytes())
    message.setStringProperty("schema", "amqp")

    try {
      sender.send(message)
    }
    catch {
      case e: Exception => logger.error(e.getMessage)
    }
    finally {
      sender.close()
      session.close()
      connection.close()
    }

  }

}
