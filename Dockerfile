FROM maven:3-jdk-8

RUN mkdir /app

WORKDIR /app

ADD src pom.xml /app/

RUN mvn clean package && \
    ls -hals /app/target

