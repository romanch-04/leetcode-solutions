/* Write your PL/SQL query statement below */
SELECT p.product_name, SUM(o.unit) AS unit
FROM Products p
JOIN Orders o
ON p.product_id = o.product_id
WHERE o.order_date >= TO_DATE('01-02-2020', 'DD-MM-YYYY')
  AND o.order_date <= TO_DATE('29-02-2020', 'DD-MM-YYYY')
GROUP BY p.product_id, p.product_name
HAVING SUM(o.unit) >= 100;