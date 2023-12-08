runFDTests : FrontendDeveloperTests.class
	java -jar ../junit5.jar -cp . -c FrontendDeveloperTests
FrontendDeveloperTests.class: FrontendDeveloperTests.java Frontend.java BackendPlaceholder.java
	javac Frontend.java
	javac BackendPlaceholder.java
	javac -cp .:../junit5.jar FrontendDeveloperTests.java
runBDTests: compileBDTests
	java -jar ../junit5.jar -cp . -c BackendDeveloperTests
compileBDTests: BackendDeveloperTests.java
	javac -cp .:../junit5.jar BackendDeveloperTests.java
clean:
	rm *.class
