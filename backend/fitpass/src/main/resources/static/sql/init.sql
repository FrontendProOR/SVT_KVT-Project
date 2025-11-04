INSERT INTO users (type, address, birthday, city, created_at, email, name, password, phone_number, surname, zip_code, image_id)
VALUES
    ('USER', '98 Street', '1996-06-06', 'Pozarevac', '2023-01-10', 'user@example.com', 'Strahinja', '$2a$10$5K84Bw.mT.86vfEvPPGRnOTE6HktEnqR2//QRfFPNiWpcHa2N5XkW', '333-666-9999', 'Djokovic', '32000', null),
    ('ADMIN', '12 Street', '1995-05-05', 'Belgrade', '2023-01-10', 'admin@example.com', 'Gavrilo', '$2a$10$B1j3XdyHm34CdXqNZKdeuewKeqat0YZJo9pBPhyI1vVxl7lMUbcKy', '123-123-1234', 'Petrovic', '14000', null);


-- Insert new facilities with updated details
INSERT INTO facilities (active, address, city, created_at, description, name, total_rating)
VALUES
    (1, '789 proleterska Street', 'Belgrade', '2024-01-01', 'A top-tier CrossFit gym specializing in high-intensity functional fitness training, equipped with the latest CrossFit gear and personalized coaching.', 'Elite CrossFit Hub', 9.2),
    (1, '123 glavna Avenue', 'Novi Sad', '2024-01-01', 'A modern fitness center offering a range of state-of-the-art exercise equipment and group fitness classes in a vibrant environment.', 'Ultimate Fitness Center', 8.7),
    (1, '456 miki Avenue', 'Niš', '2024-01-01', 'A premium yoga studio providing a variety of yoga classes, including Hatha, Vinyasa, and restorative sessions in a serene setting.', 'Teretana za Bildanje', 9.5),
    (1, '234 Pera jaksic Lane', 'Kragujevac', '2024-01-01', 'A comprehensive fitness club catering to all ages, featuring both indoor and outdoor exercise facilities and a range of family-oriented programs.', 'Misici Fitness Arena', 8.3),
    (1, '567 Jovan Road', 'Subotica', '2024-01-01', 'A high-energy interval training center designed for athletes looking to push their limits with intense workouts and personalized training plans.', 'Vip Premium Snaga Facility', 9.6);

-- Insert corresponding ratings with values from 1 to 10 not connected still
INSERT INTO ratings (equipment, staff, hygene, space, facility_id)
VALUES
    (10, 8, 9, 9,1),
    (8, 10, 8, 9,2),
    (9, 9, 10, 8,2),
    (8, 7, 10, 8,3),
    (10, 10, 10, 10,3);


# INSERT INTO manages (end_date, start_date, facility_id, user_id)
# VALUES
#     ('2024-12-31', '2024-01-01', 1, 1),
#     ('2024-12-31', '2024-01-01', 2, 1),
#     ('2024-12-31', '2024-01-01', 3, 1),
#     ('2024-12-31', '2024-01-01', 4, 1),
#     ('2024-12-31', '2024-01-01', 5, 1);

-- Facility 1: 07:00 to 22:00 (No Sunday)
INSERT INTO work_days (day_of_week, from_time, until_time, valid_from, facility_id)
VALUES
    ('MONDAY', '07:00:00', '22:00:00', '2024-01-01', 1),
    ('TUESDAY', '07:00:00', '22:00:00', '2024-01-01', 1),
    ('WEDNESDAY', '07:00:00', '22:00:00', '2024-01-01', 1),
    ('THURSDAY', '07:00:00', '22:00:00', '2024-01-01', 1),
    ('FRIDAY', '07:00:00', '22:00:00', '2024-01-01', 1),
    ('SATURDAY', '07:00:00', '22:00:00', '2024-01-01', 1),

-- Facility 2: 08:00 to 22:00 (No Sunday)
    ('MONDAY', '08:00:00', '22:00:00', '2024-01-01', 2),
    ('TUESDAY', '08:00:00', '22:00:00', '2024-01-01', 2),
    ('WEDNESDAY', '08:00:00', '22:00:00', '2024-01-01', 2),
    ('THURSDAY', '08:00:00', '22:00:00', '2024-01-01', 2),
    ('FRIDAY', '08:00:00', '22:00:00', '2024-01-01', 2),
    ('SATURDAY', '08:00:00', '22:00:00', '2024-01-01', 2),

-- Facility 3: 09:00 to 22:00 (No Sunday)
    ('MONDAY', '09:00:00', '22:00:00', '2024-01-01', 3),
    ('TUESDAY', '09:00:00', '22:00:00', '2024-01-01', 3),
    ('WEDNESDAY', '09:00:00', '22:00:00', '2024-01-01', 3),
    ('THURSDAY', '09:00:00', '22:00:00', '2024-01-01', 3),
    ('FRIDAY', '09:00:00', '22:00:00', '2024-01-01', 3),
    ('SATURDAY', '09:00:00', '22:00:00', '2024-01-01', 3),

-- Facility 4: 06:00 to 22:00 (No Sunday)
    ('MONDAY', '06:00:00', '22:00:00', '2024-01-01', 4),
    ('TUESDAY', '06:00:00', '22:00:00', '2024-01-01', 4),
    ('WEDNESDAY', '06:00:00', '22:00:00', '2024-01-01', 4),
    ('THURSDAY', '06:00:00', '22:00:00', '2024-01-01', 4),
    ('FRIDAY', '06:00:00', '22:00:00', '2024-01-01', 4),
    ('SATURDAY', '06:00:00', '22:00:00', '2024-01-01', 4),

-- Facility 5: 07:00 to 22:00 (No Sunday)
    ('MONDAY', '07:00:00', '22:00:00', '2024-01-01', 5),
    ('TUESDAY', '07:00:00', '22:00:00', '2024-01-01', 5),
    ('WEDNESDAY', '07:00:00', '22:00:00', '2024-01-01', 5),
    ('THURSDAY', '07:00:00', '22:00:00', '2024-01-01', 5),
    ('FRIDAY', '07:00:00', '22:00:00', '2024-01-01', 5),
    ('SATURDAY', '07:00:00', '22:00:00', '2024-01-01', 5);


INSERT INTO disciplines (name, facility_id) VALUES
                                                ('Zumba', 1),
                                                ('Tai Chi', 1),
                                                ('Kickboxing', 2),
                                                ('Bodybuilding', 2),
                                                ('Meditation', 3),
                                                ('Aqua Aerobics', 3),
                                                ('Self-Defense', 4),
                                                ('Cardio Kickboxing', 4),
                                                ('Gymnastics', 5),
                                                ('Circuit Training', 5);