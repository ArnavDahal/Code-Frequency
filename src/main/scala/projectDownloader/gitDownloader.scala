package projectDownloader

import java.io.File

import akka.actor.{ActorSystem, Props}
import org.eclipse.jgit.api.Git
import projectAnalyzer._

// This class will download github projects using a list of Git URIs
class gitDownloader(gitURIs: Array[String], downloads: Int, dirString: String) {

  val system = ActorSystem("MappingSystem")
  val AP = system.actorOf(Props[analyzeProjects], name = "MSystem")

  // Counter for amount of projects
  var counter = 0

  def download(): Unit = {

    for (myString <- gitURIs; if counter < downloads) {

      // Converts strings into proper URIs
      val strippedURI = strStrip(myString)

      val gitURI = strToGit(strippedURI)

      val mutated = strToMut(strippedURI)

      println("\t Downloading: " + strippedURI)

      // Downloads the projects
      downloadProjects(gitURI, mutated)
        //  Call parser with mutated
      counter += 1
    }

  }

  // Strips the string into a base name of the project
  def strStrip(strIn: String): String = {
    strIn.drop(37).dropRight(2)

  }

  // Converts the base name into a git URI
  def strToGit(strIn: String): String = {
    val gitStartUrl = "git://github.com/"
    val gitEndUrl = ".git"
    gitStartUrl + strIn + gitEndUrl
  }


  // Changes the base name by replacing /'s with +'s
  def strToMut(strIn: String): String = {
    strIn.replace('/', '+')

  }


  // Downloads the project using JGIT
  def downloadProjects(strIn: String, mutated: String) {


    val dir = new File(dirString + "\\" + mutated)

    // Uses JGit API to download GitHub repo
    var G = Git.cloneRepository()
      .setURI(strIn)
      .setDirectory(dir)
      .call();
    AP ! addProject(mutated)
  }

  // Maps the projects and stopped actor system
  def mapProjects(): Unit =
  {
    AP ! mapAll()
    AP ! stopSys()
  }
}
