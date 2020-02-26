simulation: 
	javac -cp src -d build src/Simulation.java

test: 
	javac -cp src -d build src/Test.java
	cd build && java Test

analysis: 
	javac -cp src -d build src/Analysis.java
	cd build && java Analysis

graphics:
	javac -cp src -d build src/Graphics.java
	cd build && java Graphics