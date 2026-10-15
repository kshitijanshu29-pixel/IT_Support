CREATE TABLE user_details (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at DATETIME(6) NOT NULL,

    CONSTRAINT pk_user_details PRIMARY KEY (id),
    CONSTRAINT uk_user_details_email UNIQUE (email)
);