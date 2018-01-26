#!/bin/sh
set -e

javac -cp ../lib/java-json.jar CommandServer.java
echo Compilation Successful
echo Running...
java -cp .:../lib/java-json.jar CommandServer


