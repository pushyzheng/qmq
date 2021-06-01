## MetaServer

构建本地镜像：

```shell
$ docker image build -t qunar/qmq-metaserver:0.0.1 .
```

通过 docker-compose 运行:

```shell
$ docker-compose up

# 后台运行
$ docker-compose up -d
```

## Server

构建本地镜像：

```shell
$ docker image build -t qunar/qmq-broker:0.0.1 .
```

通过 docker-compose 运行:

```shell
$ docker-compose up
```