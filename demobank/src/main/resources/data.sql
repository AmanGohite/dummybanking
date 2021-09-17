DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
  customer_id INT AUTO_INCREMENT  PRIMARY KEY,
  customer_name VARCHAR(250) NOT NULL,
  account_balance DOUBLE NOT NULL,
  customer_type VARCHAR(250) DEFAULT 'regular'
);

INSERT INTO customer (customer_name, account_balance, customer_type) VALUES
  ('Aman', 11000,'priority'),
  ('Sourabh', 2000 , 'regular'),
  ('Aditya', 3000, 'regular');