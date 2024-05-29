tests: BackendTests.java
	javac -cp .:../junit5.jar BackendTests.java
	java -jar ../junit5.jar -cp . -c BackendTests

clean:
	rm *.class
