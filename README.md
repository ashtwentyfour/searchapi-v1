# Simple Search API

## Build & Package

- Build

```
mvn clean package -e
```

- Build image:

```
mvn spring-boot:build-image -Dspring-boot.build-image.imageName=searchapi:latest
```

## Sample Requests

```
curl 'http://localhost:8080/search/query/techproducts?field=full_text&start=0&stop=10&tag=id&order=asc&value=George*&facetField=id&facetQuery=J*%20AND%20N*
```

```
curl 'http://localhost:8080/search/query/tech_products?field=name&start=0&stop=10&tag=name&order=asc&value=b*&facetField=id&facetQuery=J*%20AND%20N*'
```
