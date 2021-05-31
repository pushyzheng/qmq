FROM openjdk:8-alpine

MAINTAINER zuqin.zheng

RUN apk add --no-cache bash

COPY bin /usr/src/qmq/bin
COPY conf /usr/src/qmq/conf
COPY lib /usr/src/qmq/lib

WORKDIR /usr/src/qmq

CMD ./bin/metaserver.sh start-foreground