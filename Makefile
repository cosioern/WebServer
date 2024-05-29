tests: #*.java
	javac -cp .:../junit5.jar #.java
	java -jar ../junit5.jar -cp . -c #.class

clean:
	rm *.class
