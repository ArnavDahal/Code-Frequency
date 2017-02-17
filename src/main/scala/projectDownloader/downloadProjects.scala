package projectDownloader

object downloadProjects extends App {

  // Location of the directory to download projects to
  val dirString = "githubDownloads"

  // Number of Projects to download
  val numberOfProjects = 30

  // Deletes any old directory and creates a new one
  println("Generating directories")
  val DM = new directoryManager(dirString)
  DM.deleteDirectory()
  DM.createDirectory()

  println("Parsing Github API")
  // Parses Github's API and returns an array of Git Download URIs
  val GP = new githubParser
  val gitURIs: Array[String] = GP.getGitURIs()

  println("Downloading projects")
  // Git downloader will download a # of projects to a directory
  val GD = new gitDownloader(gitURIs, numberOfProjects, dirString)
  GD.download() // Downloads
  GD.mapProjects() // Maps and reduces

}