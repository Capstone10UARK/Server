#!/bin/sh

javac -cp ../lib/java-json.jar TestCommandServer.java
java -cp .:../lib/java-json.jar TestCommandServer

