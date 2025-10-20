#!/usr/bin/env sh
# Minimal mvnw wrapper script placeholder. To generate a full wrapper, run: mvn -N io.takari:maven:wrapper
MAVEN_HOME="${MAVEN_HOME:-}"
if [ -x "${MAVEN_HOME}/bin/mvn" ]; then
  exec "${MAVEN_HOME}/bin/mvn" "$@"
fi
exec mvn "$@"
