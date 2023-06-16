DELIMITER //

CREATE PROCEDURE Create_Dummy_Data()
BEGIN
    INSERT INTO Workplace (Name, Address) VALUES
    ('Office A', '123 Main Street'),
    ('Office B', '456 Elm Street'),
    ('Office C', '789 Oak Street'),
    ('Office D', '321 Pine Street'),
    ('Office E', '654 Maple Street'),
    ('Office F', '987 Cedar Street'),
    ('Office G', '135 Birch Street'),
    ('Office H', '864 Walnut Street'),
    ('Office I', '246 Cherry Street'),
    ('Office J', '579 Ash Street'),
    ('Office K', '908 Poplar Street'),
    ('Office L', '357 Willow Street'),
    ('Office M', '680 Spruce Street'),
    ('Office N', '913 Fir Street'),
    ('Office O', '254 Sycamore Street'),
    ('Office P', '587 Birchwood Lane'),
    ('Office Q', '920 Oakwood Lane'),
    ('Office R', '263 Elmwood Lane'),
    ('Office S', '596 Pinecrest Lane'),
    ('Office T', '829 Cedarwood Lane');

-- Employee-Daten einfügen
    INSERT INTO Employee (Name, Surname, Birthdate, Email, Workplace) VALUES
    ('John', 'Doe', '1990-05-15', 'john.doe@example.com', 1),
    ('Jane', 'Smith', '1985-08-20', 'jane.smith@example.com', 2),
    ('Michael', 'Johnson', '1992-03-10', 'michael.johnson@example.com', 3),
    ('Emily', 'Williams', '1988-11-25', 'emily.williams@example.com', 4),
    ('Daniel', 'Brown', '1991-07-05', 'daniel.brown@example.com', 5),
    ('Olivia', 'Jones', '1987-02-18', 'olivia.jones@example.com', 6),
    ('James', 'Taylor', '1993-09-30', 'james.taylor@example.com', 7),
    ('Sophia', 'Anderson', '1989-04-12', 'sophia.anderson@example.com', 8),
    ('William', 'Thomas', '1994-01-07', 'william.thomas@example.com', 9),
    ('Isabella', 'Clark', '1986-06-23', 'isabella.clark@example.com', 10),
    ('Joseph', 'Hall', '1995-02-28', 'joseph.hall@example.com', 11),
    ('Ava', 'Lee', '1984-10-14', 'ava.lee@example.com', 12),
    ('David', 'Lewis', '1996-07-19', 'david.lewis@example.com', 13),
    ('Mia', 'Walker', '1983-03-03', 'mia.walker@example.com', 14),
    ('Alexander', 'Green', '1997-11-08', 'alexander.green@example.com', 15),
    ('Sofia', 'Hall', '1988-05-24', 'sofia.hall@example.com', 16),
    ('Benjamin', 'Young', '1998-01-29', 'benjamin.young@example.com', 17),
    ('Charlotte', 'King', '1985-09-13', 'charlotte.king@example.com', 18),
    ('Daniel', 'Wright', '1999-06-08', 'daniel.wright@example.com', 19),
    ('Emma', 'Harris', '1986-12-02', 'emma.harris@example.com', 20);
END //

DELIMITER ;
