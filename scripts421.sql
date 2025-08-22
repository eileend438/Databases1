-- Возраст студента не может быть меньше 16
ALTER TABLE students
    ADD CONSTRAINT chk_student_age CHECK (age >= 16);

-- Имена студентов уникальны и не равны null
ALTER TABLE students
    ALTER COLUMN name SET NOT NULL;

ALTER TABLE students
    ADD CONSTRAINT uq_student_name UNIQUE (name);

-- Уникальность пары "название факультета - цвет"
ALTER TABLE faculties
    ADD CONSTRAINT uq_faculty_name_color UNIQUE (name, color);

-- Значение по умолчанию для возраста — 20 лет
ALTER TABLE students
    ALTER COLUMN age SET DEFAULT 20;
