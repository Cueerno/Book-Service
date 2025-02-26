# Project Launch Instructions

### Step 1: Download the Project from GitHub
Download the repository to your local machine via the following command (or by downloading the ZIP file directly):
```bash
git clone git@github.com:Cueerno/Book-Service.git
```


### Step 2: Import the project
Import the project into your favorite IDE:
1. Open your IDE.
2. Seect the project import option.
3. Specify the path to the root directory of your project.

*Ensure that the project is correctly imported and that all dependencies are properly downloaded.*


### Step 3: Database setup
This project uses PostgreSQL as the database. However, you can configure it to work with other RDBMS by modifying the
database connection settings in the application.properties file for each microservice.

#### Step 3.1
Open the application.properties file in both the book-storage-service and book-tracker-service directories.
Modify the following properties according to your environment:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/book_service
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver
```


### Step 4: Running docker container
The project uses Docker to simplify running the application.
1. Ensure that Docker and Docker Compose are installed on your system.
2. In your terminal, navigate to the root of the project directory (where the docker-compose.yml file is located).
3. Run the following command to build and start the containers:
```dockerfile
docker-compose up --build -d
```
This command will:

Build the Docker images.
Start the containers in detached mode (-d).
Automatically run the SQL script that will populate your database with initial tables and data.

Note:
If you need to restart the containers or if data was processed incorrectly, you can reset everything by running:
```dockerfile
docker-compose down -v
```
This command will stop and remove the containers, networks, and volumes.


### Step 5: Launching the project
You need to run both microservices to have a fully functional application.
You can do this in one of two ways:
1. Run via the IDE:
Navigate to the BookStorageServiceApplication and BookTrackerServiceApplication classes and run them directly.
2. Run via the Command Line:
- Open two terminal windows.
- In the first terminal, navigate to the `book-storage-service` directory and run:
```bash
./mvnw spring-boot:run
```
- In the second terminal, navigate to the `book-tracker-service` directory and run:
```bash
./mvnw spring-boot:run
```
Both services should now be running locally.


### Step 6: Testing
#### Step 6.1: Authentication
##### Postman Authentication:
1. Register a new user by sending a POST request to `http://localhost:8080/auth/registration` with the necessary data.
2. Then, authenticate by sending a POST request to `http://localhost:8080/auth/login` with your credentials.
3. After a successful login, a JWT token will be returned. Copy this token.
4. In Postman, paste the JWT token into the "Authorization" tab under Bearer Token.
5. You can now send authenticated requests to the `book-storage-service`.

##### Swagger Authentication:
1. Follow the link `http://localhost:8080/swagger-ui/index.html`
2. Register a new user by sending a POST request to `http://localhost:8080/auth/registration` with the necessary data.
3. Then, authenticate by sending a POST request to `http://localhost:8080/auth/login` with your credentials.
4. Click the "Authorize" button and paste the JWT token into the "Value" field.
5. You will now be able to send requests with authorization to the `book-storage-service` via Swagger.


### Step 6: Swagger UI
You can test the application via Swagger UI by visiting the following URLs:

book-storage-service: http://localhost:8080/swagger-ui/index.html
book-tracker-service: http://localhost:7070/swagger-ui/index.html

---

## Application Endpoints:

#### book-storage-service: http://localhost:8080
`GET    /api/books`             - **list of all books**<br/>
`GET    /api/books/{id}`        - **book by id**<br/>
`GET    /api/books/isbn/{isbn}` - **book by isbn**<br/>
`POST   /api/books`             - **new book**<br/>
`PATCH  /api/books/{id}`        - **update book**<br/>
`DELETE /api/books/{id}`        - **delete book**<br/>

`POST   /auth/registration`     - **registration in the book-service**<br/>
`POST   /auth/login`            - **login in the book-service**<br/>
---

#### book-tracker-service: http://localhost:7070
`GET    /api/tracker/books`   - **list of all available books**<br/>
`POST   /api/tracker/books`         - **new book entry**<br/>
`PATCH  /api/tracker/books/{id}/borrow` - **borrow book**<br/>
`PATCH  /api/tracker/books/{id}/return` - **return book**<br/>
`DELETE /api/tracker/books/{id}` - **delete book entry**<br/>
