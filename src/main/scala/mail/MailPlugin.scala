package mail

import play.api._
import org.codemonkey.simplejavamail.{TransportStrategy, Mailer}

/** Play plugin implementation for mailer
  *
  * Put '`400:mail.MailPlugin`' into `conf/play.plugins` to make it work.
  *
  * Recognized configuration properties (to be set in `application.conf`):
  *  - '''mail.mock''':     creates mocked mailer actor on startup and omits any other settings if set to `true`
  *  - '''smtp.host''':     hostname of smtp server to be used, defaults to `localhost`
  *  - '''smtp.port''':     port of smtp server to be used, defaults to `25`
  *  - '''smtp.username''': username to be used when accessing smtp server, defaults to empty string
  *  - '''smtp.password''': password to be used when accessing smtp server, defaults to empty string
  *  - '''smtp.ssl''': ssl
  *  - '''smtp.tls''': tls
  */
class MailPlugin(app:Application) extends Plugin {
  override def onStart() {
    MailPlugin.app = app
    Logger.debug("Mail plugin starting... ")
    if (MailPlugin.mock) {
      Logger.info("Mail plugin successfully started using mocked mailer")
    }
    else {
      Logger.info("Mail plugin successfully started with smtp server on %s:%s".format(MailPlugin.host, MailPlugin.port))
    }
  }
}

object MailPlugin {
  var app: Application = null
  private val DEFAULT_HOST = "localhost"
  private val DEFAULT_PORT = 25

  private lazy val mock = app.configuration.getBoolean("mail.mock") getOrElse false
  private lazy val host = app.configuration.getString("smtp.host") getOrElse DEFAULT_HOST
  private lazy val port = app.configuration.getInt("smtp.port") getOrElse DEFAULT_PORT
  private lazy val username = app.configuration.getString("smtp.username") getOrElse ""
  private lazy val password = app.configuration.getString("smtp.password") getOrElse ""
  private lazy val ssl = app.configuration.getBoolean("smtp.ssl") getOrElse false
  private lazy val tls = app.configuration.getBoolean("smtp.tls") getOrElse false

  lazy val mailer = {
    if (ssl) {
      Logger.info("Using ssl strategy")
      new Mailer(host, port, username, password, TransportStrategy.SMTP_SSL)
    } else if (tls) {
      Logger.info("Using tls strategy")
      new Mailer(host, port, username, password, TransportStrategy.SMTP_TLS)
    } else {
      new Mailer(host, port, username, password)
    }

  }
}
