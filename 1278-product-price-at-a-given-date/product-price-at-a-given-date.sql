/* Write your PL/SQL query statement below */
SELECT product_id, new_price AS price
FROM Products p
WHERE change_date = (
    SELECT MAX(change_date)
    FROM Products
    WHERE product_id = p.product_id
      AND change_date <= DATE '2019-08-16'
)

UNION

SELECT product_id, 10 AS price
FROM Products
WHERE product_id NOT IN (
    SELECT product_id
    FROM Products
    WHERE change_date <= DATE '2019-08-16'
);
