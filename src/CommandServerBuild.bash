#!/bin/sh

javac -cp ../lib/java-json.jar CommandServer.java
java -cp .:../lib/java-json.jar CommandServer

