# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.4/maven-plugin/reference/html/#build-image)
* [Liquibase Migration](https://docs.spring.io/spring-boot/docs/2.6.4/reference/htmlsingle/#howto-execute-liquibase-database-migrations-on-startup)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.6.4/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.6.4/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.6.4/reference/htmlsingle/#using-boot-devtools)

### Guides

The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

### Application description
This application intended for task of converting web url to deeplink and vice versa.
In the app embedded validation of link before start processing it, so you can not care about link right :)
The valid links example is :
* web url - https://www.trendyol.com/casio/saat-p-1925865?boutiqueId=439892&merchantId=105064
* deeplink - ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064

### Run application

You can up the application in a few ways:

1.
* ./mvnw clean install -DskipTests
* docker compose up --build
  

Will be run <b>mysql</b> DB and <b>App</b> containers. 


2.
* ./mvnw clean install -DskipTests
* docker-compose start db
* ./mvnw spring-boot:run after

In both cases the app will start under localhost:8080
Note: make sure that you have <b>JAVA_HOME</b> in environment variables.

### Usage
#### Endpoints:
To convert a WebURL to Deep Link, you need to send a POST request to the following URL - http://localhost:8080/api/linkConvert/webToDeeplink.

To convert a Deep Link to Web URL, you need to send a POST request to the following URL - http://localhost:8080/api/linkConvert/deeplinkToWeb.

##Example:
###To convert a deep link to web:

    curl 'http://localhost:8080/api/linkConvert/deeplinkToWeb' \
    --request POST \
    --header "Content-Type: application/json" \
    --data '{ "link": "ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064" }'

###To convert a web url to deep link:

    curl --location --request POST 'http://localhost:8080/api/linkConvert/webToDeeplink' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "link": "https://www.trendyol.com/casio/saat-p-1925865?boutiqueId=439892&merchantId=1050644"
    }'
