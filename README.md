# UI & API Test Project

## Overview
This project combines both REST API and user interface (UI) test automation frameworks to validate the functionalities of the PetStore API and a sample web application. The goal is to ensure robust test coverage using popular tools and frameworks for both API and UI testing.

### API Testing (PetStore API)
The API test automation framework tests the PetStore API using **RestAssured**. It covers CRUD operations for the `pet` endpoints with various positive and negative scenarios to ensure the robustness of the API.

### UI Testing (Sample Application)
The UI testing framework automates the testing of the user interface of a sample web application. It uses **Selenium** for browser interactions and **TestNG** for test management.

## Project Structure
```
ui-api-test-project/
|
├── api-tests/
│   └── src/
│       └── test/
│           ├── java/
│           │     ├── pages/
│           │     │   ├── Data.java
│           │     │   └── Schema.java
│           │     ├── tests
│           │     │   └── PetApiTest.java
│           │     └── pom.xml
│           └── resources/
│               └── pet-schema.json
│
├── ui-tests/
│   └── src/
│       └── test/
│           └── java/
│               ├── pages/
│               │   ├── PetData.java
│               │   ├── ApplicationPage.java
│               │   ├── CareerPage.java
│               │   ├── FindJob.java
│               │   └── HomePage.java
│               ├── utils/
│               │   │    ├── RestAssuredConfig.java
│               │   │    ├── Config.java
│               │   │    ├── DriverManager.java
│               │   │    └── SeleniumHelper.java
│               │   ├── uiTests/
│               │   │     ├── CareerPageTest.java
│               │   │     ├── FindJobTest.java
│               │   │     └── InsiderHomePageTest.java
│               ├── resources/
│               │    └── config.properties           
│               └── pom.xml
├── pom.xml
└── README.md
```

### Description of Key Files
- **PetData.java**: Contains helper methods to generate dynamic test data such as pet details (e.g., category, tags, name).
- **RestAssuredConfig.java**: Configures RestAssured, including the base URI and request/response logging.
- **PetStoreApiTest.java**: Contains JUnit test cases for testing CRUD operations on the PetStore API.
- **ApplicationPage.java, CareerPage.java, FindJob.java, HomePage.java**: Page Object Model (POM) classes representing different pages of the sample application.
- **DriverManager.java**: Manages WebDriver configurations.
- **SeleniumHelper.java**: Provides common utilities for UI interactions in Selenium.
- **pet-schema.json**: Defines the JSON schema for validating the pet object in API responses.

## Test Scenarios

### API Test Scenarios

#### CREATE (POST)
1. **Positive Scenario**: Create a pet with valid data.
2. **Negative Scenario**: Create a pet with an invalid data type for `id`.
3. **Negative Scenario**: Create a pet with missing required fields.
4. **Negative Scenario**: Create a pet with an empty `name` field.
5. **Negative Scenario**: Create a pet with a negative `id`.
6. **Negative Scenario**: Create a pet with special characters in `name`.

#### READ (GET)
7. **Positive Scenario**: Get a pet by valid `id`.
8. **Negative Scenario**: Get a pet with a non-existent `id`.
9. **Negative Scenario**: Get a pet with an invalid `id` format.

#### UPDATE (PUT)
10. **Positive Scenario**: Update a pet's status and other details.
11. **Negative Scenario**: Update a pet with an invalid `id`.
12. **Negative Scenario**: Update a pet with missing required fields.
14. **Positive Scenario**: Update only the status of an existing pet.

#### DELETE (DELETE)
14. **Positive Scenario**: Delete a pet by valid `id`.
15. **Negative Scenario**: Delete a pet with a non-existent `id`.
16. **Negative Scenario**: Delete a pet that was already deleted.

### UI Test Scenarios
1. **Career Page Test**: Verify career listings and filters.
2. **Find Job Test**: Verify job search functionality.
3. **Home Page Test**: Validate homepage elements and navigation.

## Tools & Technologies
- **Java 11**
- **Maven**: Dependency management.
- **JUnit 5**: Testing framework for API tests.
- **TestNG**: Testing framework for UI tests.
- **RestAssured**: REST API testing library.
- **Selenium WebDriver**: Automates browser actions for UI testing.
- **WebDriver Manager**: Automatically handles browser driver configurations.
- **Log4j**: Provides detailed logs for tracking test executions.

## Setup Instructions
1. Clone the repository:
   ```
   git clone <repository_url>
   ```
2. Navigate to the project directory:
   ```
   cd ui-api-test-project
   ```
3. Install dependencies using Maven:
   ```
   mvn clean install
   ```
4. Configure properties (for UI tests):
   Edit the `src/resources/config.properties` file to set up browser type and other configurations:
   ```properties
   browser=chrome
   ```
5. Run the test cases:
   ```sh
   mvn test
   ```

## Running Tests

### Running UI Tests
To run UI tests with Chrome:
```sh
mvn clean test -pl ui-test-project -Dbrowser=chrome
```
To run UI tests with Firefox:
```sh
mvn clean test -pl ui-test-project -Dbrowser=firefox
```

### Running API Tests
To execute all API test cases, use the following command:
```sh
mvn clean test -pl api-test-project
```
The test results will be printed to the console, and detailed logs of requests and responses will be available.

## Load Test Results
The following are the results from load testing the search functionality of the n11.com website:

### Test1: Search with 'Laptop'
- **Test Parameters**:
  - Users: 1
  - Request Type: GET `/arama?q=laptop`
  - Test Duration: 1 cycle
  - Target URL: `https://www.n11.com`
- **View Result Tree**:
  - Load Time: 605 ms
  - Connect Time: 120 ms
  - Latency: 191 ms
  - Response Code: 200 (OK)
- **Summary Result**:
  - Samples: 1
  - Average: 605 ms
  - Throughput: 1.7/sec

### Test2: Empty Search
- **Test Parameters**:
  - Users: 1
  - Request Type: GET `/arama?q=`
  - Test Duration: 1 cycle
  - Target URL: `https://www.n11.com`
- **View Result Tree**:
  - Load Time: 135 ms
  - Connect Time: 0 ms
  - Latency: 52 ms
  - Response Code: 200 (OK)
- **Summary Result**:
  - Samples: 2
  - Average: 447 ms
  - Throughput: 8.1/hour

### Test3: Search with 'nöcbhll'
- **Test Parameters**:
  - Users: 1
  - Request Type: GET `/arama?q=nöcbhll`
  - Test Duration: 1 cycle
  - Target URL: `https://www.n11.com`
- **View Result Tree**:
  - Load Time: 1012 ms
  - Connect Time: 143 ms
  - Latency: 947 ms
  - Response Code: 200 (OK)
- **Summary Result**:
  - Samples: 1
  - Average: 1012 ms
  - Throughput: 59.3/min

### Test4: Search with Special Characters '!@#$'
- **Test Parameters**:
  - Users: 1
  - Request Type: GET `/arama?q=!@#$`
  - Test Duration: 1 cycle
  - Target URL: `https://www.n11.com`
- **View Result Tree**:
  - Load Time: 1217 ms
  - Connect Time: 96 ms
  - Latency: 1178 ms
  - Response Code: 200 (OK)
- **Summary Result**:
  - Samples: 1
  - Average: 1217 ms
  - Throughput: 49.3/min

- [View the load test results in the PDF file](load-test/n11-load-test-results.pdf)
- [Jmeter load test file](load-test/n11Load.jmx)


## Contribution
Feel free to contribute to this project by opening issues, creating pull requests, or suggesting improvements.

## License
This project is open-source and available under the MIT License.

