Information:
	My program will analyze the frequency of method declarations and calls in the most popular Java projects on github that are within 3 MB and 30 MB.
	The program is my backend, and to view the aggregated and filtered data, the end user has to use a CSV file viewer. (Excel, Google Sheets, etc)
	
Pipeline Flow:
	1. Github API parsed 
	2. Actor for mapping/reducing set up
	3. A project is downloaded
	4. The project is sent to the actor to map and reduce
		* 3 and 4 happen concurrently
	Repeat steps 3 and 4 until all are finished downloading and mapping
	5. Mapping is Sorted and written to a CSV file called Frequency.csv
	6. Actor system is stopped
	
	My pipeline has branches going off of it for the mapping and reducing.
	
How to run:
	Go to the top menu and go to Run > Run...
			Select downloadProjects
	
Limitations:
	The Map and Reduce functions are created by the programmer (me).
	I was initially trying to implement Apache Spark, but had issues with the file system and trying to get it to work properly on my Windows enviroment. In the end I decided to scrap it and implement my own map and reduce methods. On very large data sets, the program will become slower as I believe the runtime complexity may be O(n^2) from my function that combines and reduces Mappings.
			
Tests:
	To run the tests:
	Go to the top menu and go to Run > Run...
			Select sTest
