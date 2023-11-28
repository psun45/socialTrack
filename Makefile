runBDTests: compileBDTests
	java -jar ../junit5.jar -cp . -c BackendDeveloperTests
compileBDTests: BackendDeveloperTests.java
	javac -cp .:../junit5.jar BackendDeveloperTests.java
clean:
	rm *.class
