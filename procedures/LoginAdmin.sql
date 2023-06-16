DELIMITER //

CREATE FUNCTION my_login_admin(username VARCHAR(50), pw VARCHAR(255)) RETURNS BOOLEAN
BEGIN
    DECLARE result BOOLEAN;

    SELECT COUNT(*) INTO result
    FROM AdminAccounts
    WHERE AdminAccounts.username = username AND password = pw;

    RETURN result;
END //

DELIMITER ;
