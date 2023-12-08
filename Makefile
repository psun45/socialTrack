runApp: compileApp
	java Backend
compileApp: ShortestPath.java Backend.java Frontend.java
	javac -cp .:../junit5.jar ShortestPath.java
	javac -cp .:../junit5.jar Backend.java
	javac -cp .:../junit5.jar Frontend.java
	javac -cp .:../junit5.jar DijkstraGraph.java
runBDTests: compileBDTests
	java -jar ../junit5.jar -cp . -c BackendDeveloperTests
compileBDTests: BackendDeveloperTests.java
	javac -cp .:../junit5.jar BackendDeveloperTests.java
clean:
	rm *.class
