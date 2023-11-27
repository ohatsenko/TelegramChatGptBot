INSERT INTO users (id, email, password, first_name, last_name)
VALUES (3, 'user1@mail.com', 'password1', 'TestUserName1', 'TestUserLastName1');

INSERT INTO user_role (user_id, role_id)
VALUES (3, 2);