#!/usr/bin/env bash
export JAVA_PROGRAM_ARGS=`echo "$@"`
mvn exec:java -Dexec.mainClass="client.Client" -Dexec.args="$JAVA_PROGRAM_ARGS"