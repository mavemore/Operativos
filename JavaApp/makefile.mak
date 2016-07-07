JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
        KeyValueMap.java \
        Error.java \
        KeyValueStore.java \
        TCPServer.java \
        TCPClient.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
        $(RM) *.class