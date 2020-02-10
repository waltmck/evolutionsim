simulation: 
	javac -cp src -d build src/Simulation.java

test: 
	javac -cp src -d build src/Test.java
	cd build && java Test