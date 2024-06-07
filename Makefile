run: WebServer.java
	javac -cp .:../json.jar WebServer.java
	sudo java -cp .:../json.jar  WebServer
	


tests: BackendTests.java
	javac -cp .:../junit5.jar BackendTests.java
	java ../junit5.jar -cp . -c BackendTests

clean:
	rm *.class
