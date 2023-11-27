INSERT INTO telegram_users (id, telegram_id)
VALUES (3, 3);

INSERT INTO messages (id, date_time, request, response, telegram_user_id)
VALUES (1, '2023-10-27 03:23:37.000000', 'Test', 'How can I help you', 3);

INSERT INTO messages (id, date_time, request, response, telegram_user_id)
VALUES (2, '2023-10-27 03:23:37.000000', 'Hey', 'Hello, what''s your question?', 3);