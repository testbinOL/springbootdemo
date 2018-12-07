FROM openjdk:8
MAINTAINER xubin <xubin@xingshulin.com>

COPY  ./demo-0.0.1-SNAPSHOT.jar /apps/
CMD ["java","-jar","/apps/demo-0.0.1-SNAPSHOT.jar"]