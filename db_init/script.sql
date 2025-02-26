CREATE TABLE IF NOT EXISTS book_storage (
    id SERIAL PRIMARY KEY,
    isbn VARCHAR(17) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    description TEXT,
    author VARCHAR(255) NOT NULL
);


INSERT INTO book_storage(isbn, title, genre, description, author)
values
('978-3-16-148410-0', 'The Great Gatsby', 'Fiction', 'A novel about the American dream.', 'Francis Scott Fitzgerald'),
('978-0-7432-7356-5', 'To Kill a Mockingbird', 'Fiction', 'A novel about racial injustice.', 'Harper Lee'),
('978-0-670-03237-1', '1984', 'Dystopian', 'A novel about a dystopian future under totalitarian rule.', 'George Orwell'),
('978-0-7432-1111-5', 'Brave New World', 'Dystopian', 'A novel set in a dystopian future where technology controls every aspect of life, written by Aldous Huxley.', 'Aldous Huxley'),
('978-0-452-28423-4', 'Fahrenheit 451', 'Dystopian', 'A novel about a future society where books are banned and "firemen" burn them, written by Ray Bradbury.', 'Ray Bradbury'),
('978-0-374-53370-1', 'The Handmaids Tale', 'Dystopian', 'A novel set in a theocratic dystopia where women have few rights, written by Margaret Atwood.', 'Margaret Atwood');



CREATE TABLE IF NOT EXISTS book_tracker (
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
(3, 'Available'),
(4, 'Available'),
(5, 'Available'),
(6, 'Available');




CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    role VARCHAR(100) NOT NULl,
    password TEXT NOT NULL
);