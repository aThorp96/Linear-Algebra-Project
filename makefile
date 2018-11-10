JAVAC = javac
JAVA = java
FLAGS = -g
LIBS = -cp .:Jama\*:

MAIN = test
sources = Plotter.java $(MAIN).java

default:
	$(JAVAC) $(LIBS) $(sources)

run:
	$(JAVA) $(LIBS) $(MAIN)

clean:
	$(RM) *.class
