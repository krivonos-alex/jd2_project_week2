DROP TABLE IF EXISTS Document;
CREATE TABLE IF NOT EXISTS Document
(
  id            BIGINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
  unique_number VARCHAR(36)                    NOT NULL UNIQUE,
  description   VARCHAR(100)                   NOT NULL,
  deleted       BOOLEAN DEFAULT FALSE          NOT NULL
);