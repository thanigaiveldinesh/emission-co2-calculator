<div align="center">
<h1 align="center">:boom: Emission Calculator CO2 :boom:</h1>
</div>

This Java application calculates the amount of CO2 equivalent produced when traveling between two cities using a specified transportation method.

## Table of Contents
- [Requirements](#requirements)
- [Installation and Build the Project](#installation-and-build-the-project)
- [Set Up Environment Variable](#set-up-environment-variable)
- [Usage](#usage)
- [Sample Emission CO2 Data](#sample-emission-co2-data)
- [API Token Configuration](#api-token-configuration)
- [Testing](#testing)
- [Sample Output Accpetance](#sample-output-accpetance)
- [License](#license)

## Requirements

- Java 17
- Maven

## Installation and Build the Project

1. **Clone the Repository**:

   git clone https://github.com/thanigaiveldinesh/emission-co2-calculator.git
   
   cd EmissionCalculatorCO2
   
2. **Build the project**:
   
    mvn clean package

   
## Set Up Environment Variable: 

You need to create an environment variable for the OpenRouteService API token.

1. **On Windows**:
setx ORS_TOKEN "<your_api_token_here>"
2. **On macOS/Linux**:
export ORS_TOKEN="<your_api_token_here>"

## Usage

You can run the application using the following command:


java -cp EmissionCalculatorCO2-1.0-SNAPSHOT.jar com.emissioncalculatorco2.EmissionCalculatorCO2 --start <City1> --end <City2> --transportation-method=<method>

**Example Commands:**


1.java -cp target/EmissionCalculatorCO2-1.0-SNAPSHOT.jar com.emissioncalculatorco2.EmissionCalculatorCO2 --start Hamburg --end Berlin --transportation-method=diesel-car-medium

2.java -cp target/EmissionCalculatorCO2-1.0-SNAPSHOT.jar com.emissioncalculatorco2.EmissionCalculatorCO2 --start "Los Angeles" --end "New York" --transportation-method=diesel-car-medium


## Sample Emission CO2 Data


| Vehicle Type        | Diesel Car | Petrol Car | Plugin Hybrid Car | Electric Car |
|---------------------|------------|------------|-------------------|--------------|
| Small               | 142g      | 153g      | 73g               | 50g          |
| Medium              | 171g      | 192g      | 110g              | 58g          |
| Large               | 209g      | 282g      | 126g              | 73g          |
| Bus (default)      | 27g       | -          | -                 | -            |
| Train (default)    | 6g        | -          | -                 | -            |


Source: https://www.gov.uk/government/publications/greenhouse-gas-reporting-conversion-factors-2019



## API Token Configuration

To use the OpenRouteService API, you need to sign up for an account and obtain an API token. Set this token as an environment variable called ORS_TOKEN.

**Important API Endpoints**:

1. **Get Coordinates**:
Endpoint: [https://openrouteservice.org/geocode/search](https://openrouteservice.org/dev/#/api-docs/geocode/search/get)

            Parameters: api_key and text (the city name).

3. **Get Distance**:
Endpoint: [https://openrouteservice.org/v2/matrix/driving-car](https://openrouteservice.org/dev/#/api-docs/v2/matrix/%7Bprofile%7D/post)

            Body: Provide locations (list of coordinates) and metrics=["distance"].
			
			

## Testing

To run the unit tests, use:

**mvn test**

The application includes unit tests that verify the functionality of the CO2 calculation logic.


## Sample Output Accpetance

Accepts two city names: **--start and --end**

Accepts a transportation method: **--transportation-method**

Outputs CO2-equivalent emission in kilograms, e.g.:


**Your trip caused 49.2kg of CO2-equivalent.**

Named parameters can be in any order, with space or equal sign separators.

Includes unit tests and handles errors/edge cases gracefully.

Build and test works on Windows, Linux, and macOS.

Reads the API token securely from the environment variable ORS_TOKEN.


## License

This project is licensed under the MIT License.
