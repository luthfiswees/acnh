FROM gradle:4.7.0-jdk8-alpine

USER root

COPY . /home/gradle/scheduler
WORKDIR /home/gradle/scheduler

RUN date

CMD ["gradle", "run"]