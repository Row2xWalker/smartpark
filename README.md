# Smart Park
This is a Spring Boot application designed to manage parking lots, vehicles, and parking spots. It allows users to register parking lots, vehicles, check-in and check-out vehicles, and view current occupancy and availability.

## Table of Contents
1. [Requirements](#requirements)
2. [Project Setup](#project-setup)
3. [Build Instructions](#build-instructions)
4. [Run Instructions](#run-instructions)
5. [API Endpoints](#api-endpoints)

## Requirements
- Java 17 (or later)
- Maven 3.8.x (or later)
- Spring Boot 3.x

## Project Setup
1. Clone this repository to your local machine:

```bash
git clone https://github.com/Row2xWalker/smartpark.git
```
2. Navigate to the project directory:

```bash
cd smartpark
```
## Build Instructions
To build the application, use Maven. Run the following command in the project root directory:

```bash
mvn clean install
```
This will download dependencies, compile the project, run tests, and package the application into a runnable JAR file.

## Building the Application:
```bash
mvn clean package
```
This will produce a target/parking-lot-management-<version>.jar file that can be run using Java.

## Run Instructions
Once the application is built, you can run it using the following command:

```bash
java -jar target/smartpark-0.0.1-SNAPSHOT.jar
```
By default, the application will start on port 8080. You can customize the port in the application.properties or application.yml file:

```properties
server.port=8080
```

### Access the API
Once the application is running, you can interact with the API using any HTTP client (Postman, cURL, etc.). The API endpoints are documented below.

API Endpoints
1. Register a Parking Lot
   - URL: `/api/parking-lot`
   - Method: `POST`
   - Body:
    ```json
    {
        "lotId": "LOT-001",
        "location": "Downtown",
        "capacity": 50,
        "occupiedSpaces": 0
    }
    ```
2. Register a Vehicle
   - URL: `/api/vehicle`
   - Method: `POST`
   - Body:
    ```json
    {
        "licensePlate": "ABC123",
        "type": "Car",
        "ownerName": "John Doe"
    }
    ```
3. Check-in a Vehicle to a Parking Lot
   - URL: `/api/vehicle/check-in`
   - Method: `POST`
   - Body:
    ```json
    {
    "licensePlate": "ABC123",
    "parkingLotId": "LOT-001"
    }
    ```
4. Check-out a Vehicle from a Parking Lot
   - URL: `/api/vehicle/check-out`
   - Method: `POST`
   - Body:
    ```json
    {
      "licensePlate": "ABC123"
    }
    ```
5. Get Parking Lot Occupancy
   - URL: `/api/parking-lot/{parkingLotId}/occupancy`
   - Method: `GET`

6. Get All Parked Vehicles in a Parking Lot
   - `URL: /api/parking-lot/{parkingLotId}/parked-vehicles` 
   - Method: `GET`

## Additional Features
- **H2 Database:** The application uses an H2 in-memory database for development and testing purposes.
- **Validation:** The application uses JSR-303/JSR-380 annotations for input validation (e.g., @NotNull, @Pattern).
- **Error Handling:** Custom error responses are returned with appropriate HTTP status codes.

## Troubleshooting
- Database Configuration: If you're using an external database (like MySQL or PostgreSQL), make sure to update the application.properties with your database credentials.
- Dependencies: If you encounter issues with missing dependencies, ensure your Maven repositories are correctly configured and try running mvn clean install again.