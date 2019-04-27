#!/usr/bin/env bash
# The first parameter is the name of the module that need to be compiled and the second one
# is the artifact version
# Example: sh scripts/generate-aar.sh base 1.0

mkdir -p libs/com/mytvseries/$1/$2

./gradlew $1:aR

mv $1/build/outputs/aar/$1-release.aar libs/com/mytvseries/$1/$2/$1-$2.aar
