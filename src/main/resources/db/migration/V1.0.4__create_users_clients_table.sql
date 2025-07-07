CREATE TABLE users_clients (
    user_id UUID NOT NULL,
    client_id UUID NOT NULL,

    PRIMARY KEY (user_id, client_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE
);
