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

book-storage-service: http://localhost:8080/swagger-ui/index.html
book-tracker-service: http://localhost:7070/swagger-ui/index.html

## Application Endpoints:

#### book-storage-service: http://localhost:8080
`GET    /api/book/storage/all`         - **list of all books**<br/>
`GET    /api/book/storage/{id}`        - **book by id**<br/>
`GET    /api/book/storage/isbn/{isbn}` - **book by isbn**<br/>
`POST   /api/book/storage/new`         - **new book**<br/>
`PATCH  /api/book/storage/{id}/update` - **update book**<br/>
`DELETE /api/book/storage/{id}/delete` - **delete book**<br/>
<br/>


#### book-tracker-service: http://localhost:7070
`GET    /api/book/tracker/available`   - **list of all available books**<br/>
`POST   /api/book/tracker/new`         - **new book entry**<br/>
`PATCH  /api/book/tracker/{id}/borrow` - **borrow book**<br/>
`PATCH  /api/book/tracker/{id}/return` - **return book**<br/>
`DELETE /api/book/tracker/{id}/delete` - **delete book entry**<br/>