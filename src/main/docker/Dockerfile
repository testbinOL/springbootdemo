FROM openjdk:8-jdk-alpine
MAINTAINER xubin <958339079@qq.com>

RUN mkdir /apps

ADD target/demo-0.0.1-SNAPSHOT.jar /apps/demo.jar

EXPOSE 8081

CMD ["sh","-c","java -jar /apps/demo.jar"]