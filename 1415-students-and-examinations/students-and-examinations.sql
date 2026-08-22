# Write your MySQL query statement below
select st.student_id, st.student_name, sb.subject_name, count(e.student_id) attended_exams
from Students st cross join Subjects sb left join Examinations e
on st.student_id = e.student_id and sb.subject_name = e.subject_name
group by st.student_id, st.student_name, sb.subject_name
order by st.student_id, st.student_name, sb.subject_name;
