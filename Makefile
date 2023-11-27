runBDTests: compileBDTests
	java -jar ../junit5.jar -cp . -c BackendDeveloperTests
compileBDTests: BackendDeveloperTests.java DijkstraGraph.java BaseGraph.java
	javac -cp .:../junit5.jar BaseGraph.java
	javac -cp .:../junit5.jar DijkstraGraph.java
	javac -cp .:../junit5.jar BackendDeveloperTests.java
clean:
	rm *.class
