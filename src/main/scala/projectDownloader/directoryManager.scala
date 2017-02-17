package projectDownloader

import java.io.File

import org.apache.commons.io.FileUtils


// This class deletes and creates new directories
class   directoryManager(dirString:String) {


  // Create directory file
  val dir = new File(dirString)

  // Deletes old directory
  def deleteDirectory(): Unit ={
    if (dir.exists())
    FileUtils.deleteDirectory(new File(dirString))
  }

  // Makes a new directory
  def createDirectory(): Unit =
  {
    if (!dir.exists())
      dir.mkdir()
  }
}
