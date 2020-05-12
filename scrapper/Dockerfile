FROM gradle:4.7.0-jdk8-alpine

USER root

COPY . /home/gradle/scrapper
WORKDIR /home/gradle/scrapper

RUN date

CMD ["gradle", "run"]