DELETE FROM dish;
DELETE FROM menu;
DELETE FROM restaurant;
DELETE FROM user_role;
DELETE FROM user;
DELETE FROM vote;

INSERT INTO user (id, name, email, password)
VALUES (1, 'Admin', 'admin@gmail.com', '{noop}adminPassword'),
       (2, 'UserOne', 'user1@yandex.ru', '{noop}user1Password'),
       (3, 'UserTwo', 'user2@yandex.ru', '{noop}user2Password'),
       (4, 'UserThree', 'user3@yandex.ru', '{noop}user3Password');

INSERT INTO restaurant (id, name)
VALUES (1, 'Gaggan Restaurant'),
       (2, 'Geranium Restaurant'),
       (3, 'Central Restaurant');

INSERT INTO dish (id, name, price)
VALUES (1, 'Steak', 1056),
       (2, 'Salad', 500),
       (3, 'Ice cream', 780),
       (4, 'French fries', 655),
       (5, 'Coffee', 998),
       (6, 'Cake', 1300),
       (7, 'Pizza', 760);

INSERT INTO user_role (role, user_id)
VALUES ('ROLE_ADMIN', 1),
       ('ROLE_USER', 2),
       ('ROLE_USER', 3);

INSERT INTO menu (id, restaurant_id, date, dish_id)
VALUES (1, 1, current_date, 4),
       (2, 1, current_date, 5),
       (3, 1, current_date, 6),
       (4, 1, current_date, 1),
       (5, 1, current_date, 2),
       (6, 1, current_date, 3),
       (7, 2, current_date, 3),
       (8, 2, current_date, 4),
       (9, 2, current_date, 5),
       (10, 2, current_date, 6),
       (11, 2, current_date, 7);

INSERT INTO vote (id, user_id, date, restaurant_id)
VALUES (1, 2, current_date, 1),
       (2, 2, current_date, 2);