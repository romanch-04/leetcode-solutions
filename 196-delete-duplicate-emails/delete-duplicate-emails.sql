DELETE FROM Person p1
WHERE p1.id > (
    SELECT MIN(p2.id)
    FROM Person p2
    WHERE p1.email = p2.email
);