CREATE TABLE book_storage (
    id SERIAL PRIMARY KEY,
    isbn VARCHAR(17) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    description TEXT,
    author VARCHAR(255) NOT NULL
);


INSERT INTO book_storage(isbn, title, genre, description, author)
values ('978-3-16-148410-0', 'The Great Gatsby', 'Fiction', 'A novel about the American dream.', 'Francis Scott Fitzgerald'),
('978-0-7432-7356-5', 'To Kill a Mockingbird', 'Fiction', 'A novel about racial injustice.', 'Harper Lee'),
('978-0-670-03237-1', '1984', 'Dystopian', 'A novel about a dystopian future under totalitarian rule.', 'George Orwell');


CREATE TABLE book_tracker (
    id SERIAL PRIMARY KEY,
    book_id INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    borrowed_at TIMESTAMP,
    return_at TIMESTAMP,

    FOREIGN KEY (book_id) REFERENCES book_storage(id)
);

INSERT INTO book_tracker(book_id, status)
VALUES (1, 'Available'),
(2, 'Available'),
(3, 'Available');


create table users (
    id serial primary key,
    username varchar(100) not null,
    role varchar(100) not null,
    password varchar(100) not null
);