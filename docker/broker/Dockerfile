FROM openjdk:8-alpine

MAINTAINER zuqin.zheng

RUN apk add --no-cache bash

COPY bin /usr/src/qmq/bin
COPY conf /usr/src/qmq/conf
COPY lib /usr/src/qmq/lib
COPY scripts /usr/src/qmq/scripts

WORKDIR /usr/src/qmq

RUN ["chmod", "+x", "/usr/src/qmq/scripts/start-broker.sh"]

CMD ["./scripts/start-broker.sh"]