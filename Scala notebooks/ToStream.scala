import scala.collection.JavaConverters._
import com.microsoft.azure.eventhubs._
import java.util.concurrent._
import scala.collection.immutable._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

// enter event hub authentication
val namespaceName = ""
val eventHubName = ""
val sasKeyName = ""
val sasKey = ""
val connStr = new ConnectionStringBuilder()
            .setNamespaceName(namespaceName)
            .setEventHubName(eventHubName)
            .setSasKeyName(sasKeyName)
            .setSasKey(sasKey)

val pool = Executors.newScheduledThreadPool(1)
val eventHubClient = EventHubClient.create(connStr.toString(), pool)

def sleep(time: Long): Unit = Thread.sleep(time)

def sendEvent(message: String, delay: Long) = {
  sleep(delay)
  val messageData = EventData.create(message.getBytes("UTF-8"))
  eventHubClient.get().send(messageData)
  System.out.println("Sent event: " + message + "\n")
}

// Add your own values to the list
val testSource = List("Azure is the greatest!", "Azure isn't working :(", "Azure is okay.")


val dataSource = "twitter"

if (dataSource == "twitter") {

  import twitter4j._
  import twitter4j.TwitterFactory
  import twitter4j.Twitter
  import twitter4j.conf.ConfigurationBuilder

  // Twitter configuration!
  // Replace values below with you

  val twitterConsumerKey = ""
  val twitterConsumerSecret = ""
  val twitterOauthAccessToken = ""
  val twitterOauthTokenSecret = ""

  val cb = new ConfigurationBuilder()
    cb.setDebugEnabled(true)
    .setOAuthConsumerKey(twitterConsumerKey)
    .setOAuthConsumerSecret(twitterConsumerSecret)
    .setOAuthAccessToken(twitterOauthAccessToken)
    .setOAuthAccessTokenSecret(twitterOauthTokenSecret)

  val twitterFactory = new TwitterFactory(cb.build())
  val twitter = twitterFactory.getInstance()

  // Getting tweets with keyword 
  val query = new Query(" # ")
  query.setCount(100)
  query.lang("en")
  var finished = false
  while (!finished) {
    val result = twitter.search(query)
    val statuses = result.getTweets()
    var lowestStatusId = Long.MaxValue
    for (status <- statuses.asScala) {
      if(!status.isRetweet()){
        sendEvent(status.getText()+"@ll@"+status.getGeoLocation(), 5000)
      }
      lowestStatusId = Math.min(status.getId(), lowestStatusId)
    }
    query.setMaxId(lowestStatusId - 1)
  }

} else {
  System.out.println("Unsupported Data Source. Set 'dataSource' to \"twitter\" or \"test\"")
}

// Closing connection to the Event Hub
eventHubClient.get().close()