# variable for java compiler
JC = javac
J = java

# damage control
.SUFFIXES: .java .class

# target for creating .class from .java in format:
#	.original_extention.target_extention:
#		rule
.java.class:
	$(JC) $*.java

# macro for each java source file
CLASSES = \
	Board.java \
	BackgroundIntelligence.java \
	ConnectFour.java \
	main.java

# default target definition
default: classes

classes: $(CLASSES:.java=.class)

go:
	$(J) main

clean:
	$(RM) *.class
