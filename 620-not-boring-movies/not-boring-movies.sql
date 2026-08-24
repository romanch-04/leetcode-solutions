/* Write your PL/SQL query statement below */
select * from Cinema
where mod(id,2)=1 and description not in 'boring'
order by rating desc;