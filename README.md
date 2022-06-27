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
cd backend
docker-compose up --build my-deployment
```

## Usage

1. Investigate a specific character (i.e. Spectrum)

```
GET localhost:8080/api/v1/character/investigate?name=Spectrum
```

2. See the list of characters that were persisted.

```
GET localhost:8080/api/v1/character/persisted
```
