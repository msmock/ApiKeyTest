# Overview
Test project for api key authorization in spring boot with spring security

# Test

```curl --location --request GET 'http://localhost:8080/home' --header 'X-API-KEY: ${API_KEY}'```

The ```${API_KEY}``` must be listed in the application properties file.