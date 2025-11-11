-- create_admin_bcrypt.sql
-- Run this in your existing database (trackerdb)

CREATE TABLE IF NOT EXISTS admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE,
    password VARCHAR(255)
);

INSERT INTO admin (username, password)
VALUES ('admin@gmail.com', '$2b$12$lips.qdcPnuCwrt6utwDIuc8E/oswSodxnki9beXEb8YqTPUik9Ee')
ON DUPLICATE KEY UPDATE password=VALUES(password);
