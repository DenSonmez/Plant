# Eksamen 3. semester efterår 2023 - Deniz Sønmez

# App

-----

## Opgave 1

### 1.5

```json
[
  1
] GET http://localhost:7070/api/plants

- {
"plantId": 1,
"plantType": "Rose",
"plantName": "Albertine",
"maxHeight": 400.0,
"price": 199.5
},
- {
"plantId": 2,
"plantType": "Rose",
"plantName": "Gallicanae",
"maxHeight": 350.0,
"price": 299.0
},

[2] GET http: //localhost:7070/api/plants/1
- {
"plantId": 1,
"plantType": "Rose",
"plantName": "Albertine",
"maxHeight": 400.0,
"price": 199.5
}

[3] POST http: //localhost:7070/api/plants

- {
"plantId": 6,
"plantType": "Tulip",
"plantName": "Red Tulip",
"maxHeight": 20.0,
"price": 5.99
}

[4] GET http: //localhost:7070/api/plants/1

- {
"plantId": 1,
"plantType": "Rose",
"plantName": "Albertine",
"maxHeight": 400.0,
"price": 199.5
},
- {
"plantId": 2,
"plantType": "Rose",
"plantName": "Gallicanae",
"maxHeight": 350.0,
"price": 299.0
}
```

-----

## Opgave 2

### 2.1

- Hvis der opstår en generel fejl ved hentning af planter, id eller type, returnerer vi HTTP 500 indikerer normalt en
  serverfejl.
- Hvis der ikke er nogen planter tilgængelige, returnerer vi HTTP 204 No Content (tomt svar).
- Hvis planten med det angivne ID ikke findes, returnerer vi HTTP 404 Not Found.
- Hvis anmodningen om at tilføje en ny plante indeholder ugyldige data, returnerer vi HTTP 400 Bad Request med en
  passende fejlmeddelelse.

-----

- | HTTP method | REST Ressource            | Exceptions and status(es)  |
  | HTTP method | REST Ressource                   | Exceptions and status(es)  |
  |-------------|----------------------------------|:--------------------------:|
  | GET         | `/api/plants`                    | 200 or 500                 |
  | GET         | `/api/plants/{id}`               | 200, 404 or 500            |
  | GET         | `/api/plants/type/{type}`        | 200, 404 or 500            |
  | POST        | `/api/plants`                    | 201 or 500                 |



------

### 3.4

# Streams

- Stream API er inspireret af principperne inden for funktionel programmering,
- såsom map, filter, og reduce. Dette tillader mere deklarativ og funktionel kodning i Java.
- Det gør kodning mere letlæselig og hjælper med at reducere kompleksiteten i iteration og manipulation af lister,
- arrays og andre datastrukturer.

-----

## Task 4.7

# Unit test and integration test

- Unit test er en test, der tester en enkelt enhed af kode. En enhed kan være en metode eller en klasse, for eksempel.
  fordelen ved unit test er, at de kan afsløre fejl, der opstår, når en enhed af kode ændres.
- Integrationstest er en test, der tester flere enheder af kode som en gruppe. fordelen ved integrationstest er, at de
  kan afsløre fejl, der opstår, når to eller flere enheder interagerer med hinanden.

## Task 4.8

# Rest Assured test

- REST endpoints testning er en vigtig del af enhver API-teststrategi.
- Rest Assured er et Java-bibliotek, der giver en domænespecifik sprog (DSL) til at skrive kraftfulde, vedligeholdelige
  test til RESTful API'er.
- Rest Assured giver en række metoder til at validere RESTful endpoints, herunder GET, POST, PUT, DELETE og PATCH.

## Task 5

## Task 6