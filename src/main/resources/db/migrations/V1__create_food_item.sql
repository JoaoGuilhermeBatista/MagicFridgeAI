CREATE TABLE food_item (

                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           nome VARCHAR(255) NOT NULL,
                           categoria VARCHAR(100) NOT NULL,
                           quantidade INTEGER NOT NULL,
                           validade DATE  NOT NULL
);