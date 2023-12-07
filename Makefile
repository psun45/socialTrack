runFDTests : FrontendDeveloperTests.class
	java -jar ../junit5.jar -cp . -c FrontendDeveloperTests
FrontendDeveloperTests.class: FrontendDeveloperTests.java Frontend.java BackendPlaceholder.java
	javac Frontend.java
	javac BackendPlaceholder.java
	javac -cp .:../junit5.jar FrontendDeveloperTests.java
clean:
	rm -f *.class
