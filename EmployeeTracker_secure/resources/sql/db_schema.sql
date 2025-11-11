--
-- PEPT Application Database Schema (Phase 1.2)
-- Designed for a Personnel Evaluation and Proficiency Testing System.
--

-- Table to define user roles (e.g., Admin, Instructor, Student)
CREATE TABLE roles (
    role_id INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

-- Table for all system users
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL, -- Store salted and hashed passwords
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role_id INT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

-- Table to store details about the tests/assessments
CREATE TABLE tests (
    test_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    duration_minutes INT NOT NULL,
    created_by_user_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_published BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (created_by_user_id) REFERENCES users(user_id)
);

-- Table for individual questions within a test
CREATE TABLE questions (
    question_id INT PRIMARY KEY AUTO_INCREMENT,
    test_id INT NOT NULL,
    question_text TEXT NOT NULL,
    question_type VARCHAR(50) NOT NULL, -- e.g., 'MCQ', 'TrueFalse', 'ShortAnswer'
    point_value INT DEFAULT 1,
    FOREIGN KEY (test_id) REFERENCES tests(test_id)
);

-- Table for multiple-choice options (answers) for a question
CREATE TABLE options (
    option_id INT PRIMARY KEY AUTO_INCREMENT,
    question_id INT NOT NULL,
    option_text VARCHAR(500) NOT NULL,
    is_correct BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (question_id) REFERENCES questions(question_id)
);

-- Table to track student attempts on a test
CREATE TABLE test_attempts (
    attempt_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    test_id INT NOT NULL,
    start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_time TIMESTAMP,
    score DECIMAL(5, 2),
    status VARCHAR(50) NOT NULL, -- e.g., 'InProgress', 'Submitted', 'Graded'
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (test_id) REFERENCES tests(test_id),
    UNIQUE (user_id, test_id) -- Prevents multiple concurrent attempts, adjust if multiple attempts are allowed
);

-- Table to store the user's selected answers for an attempt
CREATE TABLE answers (
    answer_id INT PRIMARY KEY AUTO_INCREMENT,
    attempt_id INT NOT NULL,
    question_id INT NOT NULL,
    selected_option_id INT, -- Stores the ID of the selected option for MCQ/TrueFalse
    answer_text TEXT, -- Stores text for ShortAnswer questions
    is_correct BOOLEAN, -- Will be NULL until graded
    FOREIGN KEY (attempt_id) REFERENCES test_attempts(attempt_id),
    FOREIGN KEY (question_id) REFERENCES questions(question_id),
    FOREIGN KEY (selected_option_id) REFERENCES options(option_id)
);

-- Insert initial roles
INSERT INTO roles (role_name) VALUES ('Admin'), ('Instructor'), ('Student');

-- Insert a default Admin user (password: 'adminpass' hashed)
-- NOTE: In a real application, replace 'hashed_password_for_admin' with a proper secure hash.
INSERT INTO users (username, password_hash, first_name, last_name, email, role_id)
VALUES ('admin', 'hashed_password_for_admin', 'System', 'Admin', 'admin@pept.com', 1);