#!/usr/bin/env bash
export JAVA_PROGRAM_ARGS=`echo "$@"`
mvn exec:java -Dexec.mainClass="broker.Broker" -Dexec.args="$JAVA_PROGRAM_ARGS"