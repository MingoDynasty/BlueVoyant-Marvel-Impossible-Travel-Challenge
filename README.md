# BlueVoyant-Marvel-Impossible-Travel-Challenge

A small application that talks to the Marvel Comics API and exfiltrates data.

## Requirements

1. Java 17+
2. Docker

## Instructions

1. Build the JARs

```bash
./mvnw package
```

2. Build the Docker containers and run

```bash
docker-compose up --build backend
```

## Usage

1. Get Spectrum

```
GET localhost:8080/api/v1/characters?name=Spectrum
```
