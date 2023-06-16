DELIMITER //

CREATE FUNCTION GetAdminAccountsJSON()
    RETURNS TEXT
BEGIN
    DECLARE jsonResult TEXT DEFAULT '[';
    DECLARE firstRow BOOLEAN DEFAULT TRUE;
    DECLARE accountId INT;
    DECLARE accountUsername VARCHAR(50);
    DECLARE accountPassword VARCHAR(255);

    DECLARE cur CURSOR FOR SELECT id, username, password FROM AdminAccounts;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET @finished = TRUE;

    OPEN cur;

    read_loop: LOOP
        FETCH cur INTO accountId, accountUsername, accountPassword;

        IF @finished THEN
            LEAVE read_loop;
        END IF;

        IF NOT firstRow THEN
            SET jsonResult = CONCAT(jsonResult, ',');
        END IF;

        SET jsonResult = CONCAT(jsonResult, '{ "id": ', accountId, ', "username": "', accountUsername, '", "password": "', accountPassword, '" }');

        SET firstRow = FALSE;
    END LOOP;

    CLOSE cur;

    SET jsonResult = CONCAT(jsonResult, ']');

    RETURN jsonResult;
END //

DELIMITER ;
