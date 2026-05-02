# Smart Campus Sensor & Room Management API

## Overview
A RESTful API built using JAX-RS (Jersey) for managing Rooms and Sensors 
in a Smart Campus environment. The API supports full CRUD operations, 
sensor readings, filtering, error handling, and request/response logging.

## Technology Stack
- Java 11+
- JAX-RS (Jersey 3.1.3)
- Apache Tomcat 10
- Maven

## How to Build and Run

### Prerequisites
- JDK 11 or higher
- Apache Tomcat 10
- Maven
- NetBeans IDE

### Steps
1. Clone the repository:
   git clone https://github.com/MayumiHewawasam/smart-campus-api.git
2. Open the project in NetBeans
3. Clean and Build the project
4. Right click project and Run
5. Access the API at: http://localhost:8080/smart-campus-api/api/v1

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/v1 | Discovery endpoint |
| GET | /api/v1/rooms | Get all rooms |
| POST | /api/v1/rooms | Create a room |
| GET | /api/v1/rooms/{id} | Get a specific room |
| DELETE | /api/v1/rooms/{id} | Delete a room |
| GET | /api/v1/sensors | Get all sensors |
| GET | /api/v1/sensors?type=CO2 | Filter sensors by type |
| POST | /api/v1/sensors | Create a sensor |
| GET | /api/v1/sensors/{id}/readings | Get sensor readings |
| POST | /api/v1/sensors/{id}/readings | Add a sensor reading |

## Sample curl Commands

### 1. Discovery Endpoint
curl -X GET http://localhost:8080/smart-campus-api/api/v1

### 2. Get All Rooms
curl -X GET http://localhost:8080/smart-campus-api/api/v1/rooms

### 3. Create a Room
curl -X POST http://localhost:8080/smart-campus-api/api/v1/rooms -H "Content-Type: application/json" -d "{\"id\":\"HALL-01\",\"name\":\"Main Hall\",\"capacity\":100}"

### 4. Get All Sensors
curl -X GET http://localhost:8080/smart-campus-api/api/v1/sensors

### 5. Add a Sensor Reading
curl -X POST http://localhost:8080/smart-campus-api/api/v1/sensors/TEMP-001/readings -H "Content-Type: application/json" -d "{\"value\":25.5}"

## Questions and Answers

### Part 1 - Q1: JAX-RS Resource Class Lifecycle
JAX-RS resource classes are request-scoped by default, meaning a new instance 
is created for every HTTP request. In-memory data must be stored in static fields 
(like static HashMaps in DataStore.java) to persist across requests. Without 
static fields, data would be lost after each request.

### Part 1 - Q2: HATEOAS
HATEOAS (Hypermedia as the Engine of Application State) means the API response 
includes links to related resources. This benefits client developers because they 
do not need to hardcode URLs. The API itself guides navigation, making it 
self-discoverable and reducing coupling between client and server.

### Part 2 - Q1: IDs vs Full Objects
Returning full room objects increases network bandwidth but reduces client-side 
processing. Returning only IDs reduces bandwidth but requires clients to make 
additional requests. For small datasets, returning full objects is preferred.

### Part 2 - Q2: DELETE Idempotency
Yes, DELETE is idempotent. The first DELETE removes the room and returns 200. 
Subsequent DELETE requests return 404. The server state remains the same after 
multiple calls - the room is gone.

### Part 3 - Q1: @Consumes Annotation
If a client sends data in text/plain or application/xml format instead of 
application/json, JAX-RS returns HTTP 415 Unsupported Media Type. The server 
rejects the request before it reaches the resource method.

### Part 3 - Q2: @QueryParam vs Path Parameter
Query parameters (?type=CO2) are better for filtering because they are optional 
and keep the resource URL clean. Path parameters (/sensors/type/CO2) imply a 
hierarchical resource structure which is semantically incorrect for filtering.

### Part 4 - Q1: Sub-Resource Locator Pattern
Sub-resource locators delegate request handling to separate classes, improving 
code organization. Each class has a single responsibility, making large APIs 
easier to manage compared to one massive controller class.

### Part 5 - Q1: HTTP 422 vs 404
HTTP 422 is more semantically accurate because the request was syntactically 
valid JSON but contained an invalid reference (non-existent roomId). HTTP 404 
implies the endpoint itself was not found, which is misleading.

### Part 5 - Q2: Stack Trace Security Risk
Exposing stack traces reveals internal class names, package structure, library 
versions, and code logic. Attackers can use this to identify vulnerabilities 
and exploit known library weaknesses.

### Part 5 - Q3: JAX-RS Filters vs Manual Logging
Filters implement cross-cutting concerns in one place, following the DRY 
principle. Manual logging in every method is error-prone, inconsistent, 
and harder to maintain.
