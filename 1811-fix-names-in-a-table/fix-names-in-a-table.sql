/* Write your PL/SQL query statement below */
SELECT user_id,
       UPPER(SUBSTR(LOWER(name), 1, 1)) ||
       SUBSTR(LOWER(name), 2) AS name
FROM Users
ORDER BY user_id;