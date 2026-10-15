ALTER TABLE ticket_details
ADD COLUMN ticket_number VARCHAR(30) NULL,
ADD CONSTRAINT uk_ticket_number UNIQUE (ticket_number);