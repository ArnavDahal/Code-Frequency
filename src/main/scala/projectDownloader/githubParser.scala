package projectDownloader

import spray.json._

import scala.util.matching.Regex
import scalaj.http._


// This class parses payload requests from the Github API and returns a list of Git URIs
class githubParser {

  // Project size in Kilobytes
  val sizeMax = 10000
  val sizeMin = 3000

  // Github client ID and Secret
  val githubSecret = "?client_id=c69d7273c76ecf086428&client_secret=9a2463270a5dca5e092efae035797202622c9564"
  val githubReqURL = "https://api.github.com/search/repositories?q=size%3A" + sizeMin + ".." + sizeMax + "+language:java&sort=stars"

  def getGitURIs(): Array[String] = {

    // Grabs the JSON payload from GitHub
    val githubPayloadHTTP: HttpRequest = Http(githubReqURL + githubReqURL)
    val githubPayloadString: String = githubPayloadHTTP.asString.body

    // Parses it into a JSON file
    val githubPayloadJSON: JsValue = githubPayloadString.parseJson
    val githubPayload: String = githubPayloadJSON.prettyPrint

    // Finds alls the git urls to download
    val gitRegex = new Regex("(\\\"url\\\"\\: \\\"https:\\/\\/api.github.com/repos/).*(\",)")

    // Creates array of git URIs and returns them
    val gitURIs = (gitRegex findAllIn githubPayload).toArray

    gitURIs
  }
}
