#!/usr/bin/env bash

TOKEN=`cat ./conf/valid-api-tokens.properties`

echo "META_SERVER_ADDRESS = $META_SERVER_ADDRESS, BROKEN_GROUP_NAME = $BROKEN_GROUP_NAME, TOKEN = $TOKEN"

# Register cluster node in meta server
./bin/tools.sh AddBroker \
  --metaserver=$META_SERVER_ADDRESS \
  --token=$TOKEN \
  --brokerGroup=$BROKEN_GROUP_NAME \
  --role=0 \
  --hostname=`hostname` \
  --ip=qmq-broker \
  --servePort=20881 \
  --syncPort=20882


# Start broker service
./bin/broker.sh start-foreground