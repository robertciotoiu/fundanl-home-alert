# Funda.nl new home notifier

This application sends notifications when new apartments are available on funda.nl for a given search.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine.

### Prerequisites

You will need to have the following software installed on your machine:
- Java 21
- [Chromedriver](https://chromedriver.chromium.org/downloads)

### Running the application

1. Clone the repository

    ```shell
    git clone https://github.com/robertciotoiu/fundanl-home-alert.git
    ```
2. Create a `application.properties` file in `src/main/resources` and add the following properties:
    ```properties
   notification.url=https://www.funda.nl/zoeken/huur?selected_area=%5B%22nijmegen,5km%22%5D&price=%22-1500%22&object_type=%5B%22house%22,%22apartment%22%5D&sort=%22date_down%22&floor_area=%2250-%22
   file.path=/full/path/to/file.txt
   scheduler.fixed-delay=2
   
   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.username=dummy@gmail.com
   spring.mail.password=dummy
   spring.mail.properties.mail.smtp.auth=true
   spring.mail.properties.mail.smtp.starttls.enable=true
   
   mail.to=dummy@gmail.com
    ```
3. Go to funda.nl and search for the apartments you are interested in. Apply the filters you want and copy the URL.
Paste it in the `application.properties` file as the value for the `notification.url` property.
4. Configure spring mail properties in `application.properties` to send emails from your email account. You can find more details [here](https://www.baeldung.com/spring-email).
5. Configure mail.to property in `application.properties` to the email address you want to send notifications to.
6. Configure scheduler.fixed-delay property in `application.properties` to the interval you want to check for new apartments. I recommend at least 1 minute to not overload funda.nl servers.
7. Configure file.path property in `application.properties` if you want to store the new apartments in a file. This could be useful if you want to do analysis on the housing market.
8. Run the application

```shell 
mvn clean install
mvn spring-boot:run
```

## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Selenium](https://www.selenium.dev/) - Browser automation framework

## Authors

* **Robert Ciotoiu** - *Initial work* - [robertciotoiu](www.github.com/robertciotoiu)

## License

This project is licensed under the Apache 2.0 License - see the [LICENSE.md](LICENSE.md) file for details