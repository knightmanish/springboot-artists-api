# Artists register service

- API version: 1.0.0-SNAPSHOT
- A simple Artists register service


## Requirements

Building the API client library requires:
1. Java 1.8
2. Maven
3. Spring Boot 2.7.6
4. springdoc-openapi-ui 2.6.4


## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn clean install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
java -jar artists-api/target/artists-api-1.0.0-SNAPSHOT.jar
```

## Getting Started


## Documentation for API Endpoints

Swagger-UI documentation can be accessed on /swagger-ui/index.html.
For localhost, it could be, assuming the tomcat runs on port 8080
http://localhost:8080/swagger-ui/index.html

Swagger-UI usage requires ApiKeyAuth authentication
{x-api-key: key}

All URIs are relative to *https://company.io*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*DefaultApi* | [**artistsPost**](docs/DefaultApi.md#artistsPost) | **POST** /artists | 
*DefaultApi* | [**artistsUsernameGet**](docs/DefaultApi.md#artistsUsernameGet) | **GET** /artists/{username} | 
*DefaultApi* | [**getAllArtists**](docs/DefaultApi.md#getAllArtists) | **GET** /artists | 

## Documentation for Models
The lib was generated using [swagger-codegen](https://github.com/swagger-api/swagger-codegen)

 - [Artist](docs/Artist.md)
 - [Error](docs/Error.md)
 - [RestError](docs/RestError.md)

## Documentation for Authorization

Authentication schemes defined for the API:
### ApiKeyAuth

- **Type**: API key
- **API key parameter name**: x-api-key
- **API key value**: key
- **Location**: HTTP header

Api key can be changed in artist-api/src/main/resources/application.properties

###Known issues
- Some exceptions require further handing
    - HTTP 503
    - HTTP 429
- Code coverage for Rest Controller and various other Handlers.

## Author
Manish Arya



