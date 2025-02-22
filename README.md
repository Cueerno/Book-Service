# Project Launch Instructions

### Step 1: Download the project from GitHub
Download this repository to your computer (via archive or git clone):

### Step 2: Import the project
Import the project into your favorite IDE:
Open your IDE.
Select the project import option.
Specify the path to the root directory of your project.

### Step 3: Database setup
I used PostgreSQL, but you can use any other RDBMS.
For each microservice, edit the application.properties files and enter your data (url, driver, username, and password) in the fields.
And you need to create tables in the database, the file with the SQL code is in the folder **database**

### Step 4: Launching the project
To work correctly, run the two modules simultaneously through the terminal or through the BookStorageServiceApplication and BookTrackerServiceApplication classes.

### Step 5: Testing
The project can be tested via Postman or via Swagger UI at the following URLs:

book-storage-service: http://localhost:8080/swagger-ui/index.html<br/>
book-tracker-service: http://localhost:7070/swagger-ui/index.html

## Application Endpoints:

#### book-storage-service: http://localhost:8080
`GET    /api/books`         - **list of all books**<br/>
`GET    /api/books/{id}`        - **book by id**<br/>
`GET    /api/books/isbn/{isbn}` - **book by isbn**<br/>
`POST   /api/books`         - **new book**<br/>
`PATCH  /api/books/{id}` - **update book**<br/>
`DELETE /api/books/{id}` - **delete book**<br/>
<br/>


#### book-tracker-service: http://localhost:7070
`GET    /api/tracker/books`   - **list of all available books**<br/>
`POST   /api/tracker/books`         - **new book entry**<br/>
`PATCH  /api/tracker/books/{id}/borrow` - **borrow book**<br/>
`PATCH  /api/tracker/books/{id}/return` - **return book**<br/>
`DELETE /api/tracker/books/{id}` - **delete book entry**<br/>
