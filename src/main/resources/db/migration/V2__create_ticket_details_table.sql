CREATE TABLE ticket_details (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    status VARCHAR(255) NOT NULL,
    priority VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,

    created_by BIGINT NOT NULL,
    assigned_to BIGINT NULL,

    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,

    CONSTRAINT pk_ticket_details PRIMARY KEY (id),

    CONSTRAINT fk_ticket_created_by
        FOREIGN KEY (created_by)
        REFERENCES user_details(id),

    CONSTRAINT fk_ticket_assigned_to
        FOREIGN KEY (assigned_to)
        REFERENCES user_details(id)
);