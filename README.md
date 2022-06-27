# BlueVoyant-Marvel-Impossible-Travel-Challenge

A small application that talks to the Marvel Comics API and exfiltrates data.

## Requirements

1. Java 17+
2. Docker
3. Marvel API key (sign up for free here: https://developer.marvel.com/account)

## Instructions

1. Add your Marvel API public and private keys to `backend/src/main/resources/application.yaml`

```yaml
marvel.api.public.key: your_public_key
marvel.api.private.key: your_private_key
```

Alternatively you can also create `backend/src/main/resources/application-default.yaml` and add your keys there,
to avoid seeing your keys in the Git diff (also less chance of accidentally committing them!).

2. Build the JARs

```bash
./mvnw clean package
```

3. Build the Docker containers and run

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

2. Optional endpoint: manually clear database of any persisted characters.

```
DELETE localhost:8080/api/v1/character/persisted
```
