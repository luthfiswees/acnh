FROM gradle:4.7.0-jdk8-alpine

USER root

COPY . /home/gradle/bot
WORKDIR /home/gradle/bot

RUN date

CMD ["gradle", "run"]