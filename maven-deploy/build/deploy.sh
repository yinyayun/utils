#!/bin/sh

#check deploy version
echo "updating..."
if [ "$1" == "" ];
then
    echo "you must give a version to deploy.to deploy respository you should give a version not inlude SNAPSHOT,but to snapshot respository you must give a version include -SNAPSHOT.deploy exit"
    exit
fi
mvn -f build.xml clean compile  -Pserver -Dversion=$1 -DinstanceHome=./
echo "ok!"