JAVAC = javac

FLAGS = -classpath

LIBS=./Jama-1.0.3.jar 

.SUFFIXES: .java .class

.java.class:
	$(JAVAC) $(FLAGS) $(LIBS) $*.java

CLASSES = test.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
