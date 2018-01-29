#!/bin/sh
set -e

echo Compiling...
javac -cp ../lib/java-json.jar CommandServer.java

echo Running...
java -cp .:../lib/java-json.jar CommandServer


