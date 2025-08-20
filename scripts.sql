-- 1. Все студенты с возрастом между 10 и 20
SELECT * FROM students
WHERE age BETWEEN 10 AND 20;

-- 2. Только имена всех студентов
SELECT name FROM students;

-- 3. Студенты, в имени которых есть буква 'о' (регистр игнорируется)
SELECT * FROM students
WHERE LOWER(name) LIKE '%о%';

-- 4. Студенты, у которых возраст меньше идентификатора
SELECT * FROM students
WHERE age < id;

-- 5. Студенты, отсортированные по возрасту
SELECT * FROM students
ORDER BY age;
