-- Студенты с названиями факультетов
SELECT s.name, s.age, f.name AS faculty_name
FROM students s
JOIN faculties f ON s.faculty_id = f.id;

-- Студенты, у которых есть аватарки
SELECT s.name
FROM students s
JOIN avatar a ON a.student_id = s.id;
