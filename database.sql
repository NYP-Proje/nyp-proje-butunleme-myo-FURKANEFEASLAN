CREATE DATABASE IF NOT EXISTS devam_takip;
USE devam_takip;

CREATE TABLE IF NOT EXISTS users (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20)
    );

CREATE TABLE IF NOT EXISTS students (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        student_no VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    department VARCHAR(100)
    );

CREATE TABLE IF NOT EXISTS courses (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       course_name VARCHAR(100) NOT NULL,
    course_code VARCHAR(20) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS attendance (
                                          id INT AUTO_INCREMENT PRIMARY KEY,
                                          student_id INT,
                                          course_id INT,
                                          attendance_date DATE,
                                          status VARCHAR(20),
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
    );

INSERT IGNORE INTO users(username, password, role)
VALUES ('admin', '1234', 'ADMIN');

INSERT IGNORE INTO students(student_no, name, surname, department)
VALUES ('101', 'Nehir', 'Aslan', 'Bilgisayar');

INSERT IGNORE INTO courses(course_code, course_name)
VALUES ('VT101', 'Veritabanı');