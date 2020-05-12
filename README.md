# ACNH
Animal Crossing Telegram Bot Project

## Summary 

This repository contains 3 separate projects. All built using Java with Gradle support
- **bot** contains main bot project
- **scrapper** contains project that scraps data from Animal Crossing wikia to the DB
- **scheduler** contains project that notify reminder to users that subscribe to the bot

## Architecture Diagram

![architecture diagram](/home/luthfiswees/Documents/acnh/acnh-diagram.png)



## Installation

###  Manual Installation

This projects need all of this to properly run:

- **Gradle** (I'm using version `4.4.1` )
- **MySQL** (I'm using version `5.7`)
- **Redis** (I'm using version `4`) 
- **Selenium Hub** (with Chrome node)
- **Telegram Bot** (you need the token)

You should adjust those needs according to your development environment.

First, clone the repo and go to the directory

```
git clone https://github.com/luthfiswees/acnh.git
cd acnh
```

And then, copy each `env.sample` to `.env` files in each of the project.

```
cp bot/env.sample bot/.env
cp scheduler/env.sample scheduler/.env
cp scrapper/env.sample scrapper/.env
```

Please adjust each `.env` file to your development environment.

Next, just simply run `gradle run`on each of the projects to make it work.

#### Bot

```
cd bot
gradle run
```

#### Scheduler

```
cd scheduler
gradle run
```

#### Scrapper

```
cd scrapper
gradle run
```

### Using Docker Compose

You can also use `docker-compose.yml` provided in the project.

First, copy the `env.sample` file to `.env` file.

```
cd acnh
cp env.sample .env
```

 Then, change value of `ACNH_TELEGRAM_TOKEN` to your own telegram bot token.

```
# This is the .env file
# Adjust "blablablabla" to your own telegram bot token
ACNH_TELEGRAM_TOKEN=blablablabla
```

To finish it up. Run the compose

```
docker-compose up
```

