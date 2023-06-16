DELIMITER //

DROP PROCEDURE IF EXISTS Reset_All;

CREATE PROCEDURE Reset_All()
BEGIN
    SET FOREIGN_KEY_CHECKS = 0; -- Deaktiviere die Prüfung der Fremdschlüssel

    -- Tabellen in umgekehrter Reihenfolge der Erstellung zurücksetzen
    -- (Beginnend mit den Tabellen, die Fremdschlüsselreferenzen haben)
    DROP TABLE Employee;
    DROP TABLE Workplace;

    SET FOREIGN_KEY_CHECKS = 1; -- Aktiviere die Prüfung der Fremdschlüssel

    CREATE TABLE Workplace
    (
        id      int auto_increment
            primary key,
        Name    varchar(255) null,
        Address varchar(255) null
    );

    CREATE TABLE Employee
    (
        id        int auto_increment
            primary key,
        Name      varchar(255) null,
        Surname   varchar(255) null,
        Birthdate date         null,
        Email     varchar(255) null,
        Workplace int          null,
        constraint Employee_ibfk_1
            foreign key (Workplace) references Workplace (id)
    );

    create index Workplace
        on Employee (Workplace);


END //

DELIMITER ;
