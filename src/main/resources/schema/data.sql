INSERT INTO USERS (USERNAME, EMAIL, FIRST_NAME, LAST_NAME, AGE, LAST_ACTIVE_AT) VALUES ('nadeem.arif', 'nadeem.arif@yopmail.com', 'nadeem', 'arif', 25, CURRENT_TIMESTAMP) ON CONFLICT DO NOTHING;
INSERT INTO USERS (USERNAME, EMAIL, FIRST_NAME, LAST_NAME, AGE, LAST_ACTIVE_AT) VALUES ('umer.arif', 'umer.arif@yopmail.com', 'umer', 'arif', 22, CURRENT_TIMESTAMP) ON CONFLICT DO NOTHING;