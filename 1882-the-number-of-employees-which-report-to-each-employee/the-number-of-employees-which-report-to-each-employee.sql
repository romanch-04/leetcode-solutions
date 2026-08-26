/* Write your PL/SQL query statement below */
SELECT M.employee_id, M.name, COUNT(e.reports_to) AS reports_count, ROUND(AVG(e.age)) as average_age
FROM EMPLOYEES E, EMPLOYEES M
WHERE E.reports_to = M.employee_id
GROUP BY M.employee_id, M.name
ORDER BY M.employee_id;