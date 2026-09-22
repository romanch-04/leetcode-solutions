/* Write your PL/SQL query statement below */
SELECT E.NAME AS Employee
FROM Employee E, Employee M
WHERE E.managerId = M.id
AND E.salary > M.salary;