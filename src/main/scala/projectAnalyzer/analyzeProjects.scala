package projectAnalyzer

import java.io.{File, FileOutputStream, PrintWriter}

import akka.actor.Actor

case class finished()
case class addProject(projDir:String)
case class mapAll()
case class stopSys()

class analyzeProjects extends Actor {


  // Location of the directory to download projects to
  var dirString = "githubDownloads\\"

  val mapBase = new File("MapGen")

  // Starts the base map variable
  var listOfDecs = map(mapBase)

  // Counter for amount of files
  var counter = 0
  // Total number of Declarations
  var totalDecs: Double = 0

  // Reciever
  def receive = {
    case addProject(projDir:String) =>{ startMapping(dirString,projDir)}
    case mapAll() => {endMapping}
    case stopSys() => {
      println("System shutting down")
      context.system.terminate()
    }
    case _ => println("Unexpected")
  }

def startMapping(dirString:String, projDir:String): Unit = {

  // Create a file manager
  val FM = new fileManager(dirString+projDir)
  val listOfFiles = FM.getListOfFiles()

  println("\t \t Maping and Reducing " + listOfFiles.length + " files in " + projDir)

  // iterates through the files and maps the Declarations
  for (f <- listOfFiles) {
    var temp = map(f) // Temp mapping
    temp = temp.filterKeys(_ matches "([a-zA-z0-9.]*(\\(.*\\)))")

    listOfDecs = reduce(listOfDecs, temp) // Reduces the mapping
    counter += 1
  }

}


  def endMapping: Unit = {

    // Counts the total number of Declarations
    for (decs <- listOfDecs)
      totalDecs = totalDecs + decs._2

    println("Sorting Mapping")
    val sortedListOfDecs = (listOfDecs.toList sortBy {
      _._2
    }).reverse

    // Writes to a new CSV file
    val write = new PrintWriter(new FileOutputStream(new File("Frequency.csv")))

    write.write("Declaration,Occurrences,Frequency\n")

    try {
      for (decs <- sortedListOfDecs)
        write.write(decs._1 + "," + decs._2 + "," + (decs._2 / totalDecs) + "\n")
    }
    finally write.close()
    println("Finished Mapping")

  }

  // Mapping function will check the amount of Declarations for a file
  def map(file: File): Map[String, Int] =
  scala.io.Source.fromFile(file.getCanonicalPath)
    .getLines
    .flatMap(_.split("[^a-zA-Z\\.0-9_();\"]"))
    .foldLeft(Map.empty[String, Int]) {
      (count, word) => count + (word -> (count.getOrElse(word, 0) + 1))
    }

  // It will reduce two maps into one
  def reduce(first: Map[String, Int], second: Map[String, Int]): Map[String, Int] =
  (first.keySet ++ second.keySet).map(i => (i, first.getOrElse(i, 0) + second.getOrElse(i, 0))).toMap

}
