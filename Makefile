runFDTests : FrontendDeveloperTests.java

	javac -cp .:../junit5.jar FrontendDeveloperTests.java
	java -jar ../junit5.jar -cp . -c FrontendDeveloperTests

clean:
	rm -f *.class
