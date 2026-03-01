USE triapp_db;

-- Insert users (10)
INSERT INTO users (first_name, last_name, email, password, height_inches, weight_pounds) VALUES
('Alice','Meyer','alice@example.com','pass1',65,135.0),
('Bob','Ng','bob@example.com','pass2',70,180.0),
('Carlos','Diaz','carlos@example.com','pass3',68,165.5),
('Dana','Smith','dana@example.com','pass4',64,125.2),
('Eve','Johnson','eve@example.com','pass5',66,140.0),
('Frank','O''Neill','frank@example.com','pass6',72,195.5),
('Gina','Khan','gina@example.com','pass7',63,120.0),
('Henry','Lopez','henry@example.com','pass8',69,175.0),
('Ivy','Wong','ivy@example.com','pass9',62,115.0),
('Jake','Martinez','jake@example.com','pass10',71,185.0);

-- We'll insert a bunch of workouts. For simplicity use dates across 2025-10-01 to 2026-02-15
-- Helper note: moving_time and elapsed_time in seconds

-- Alice (user_id = 1): 6 workouts
INSERT INTO workouts (user_id, workout_type, date, moving_time_sec, elapsed_time_sec, avg_heart_rate, calories) VALUES
(1,'RUN','2026-02-15 07:30:00', 3600, 3700, 150, 600),
(1,'BIKE','2026-02-13 09:00:00', 5400, 5500, 135, 900),
(1,'SWIM','2026-02-10 06:00:00', 1500, 1600, 130, 250),
(1,'RUN','2026-01-30 18:00:00', 1800, 1850, 155, 320),
(1,'BIKE','2026-01-25 07:00:00', 7200, 7300, 140, 1100),
(1,'SWIM','2026-01-20 06:30:00', 1200, 1250, 120, 200);

-- Bob (user_id = 2): 6 workouts
INSERT INTO workouts (user_id, workout_type, date, moving_time_sec, elapsed_time_sec, avg_heart_rate, calories) VALUES
(2,'RUN','2026-02-14 07:00:00', 2700, 2750, 148, 420),
(2,'BIKE','2026-02-09 10:00:00', 4000, 4100, 132, 700),
(2,'SWIM','2026-02-07 06:15:00', 1000, 1050, 125, 190),
(2,'RUN','2026-01-28 19:00:00', 3300, 3350, 152, 520),
(2,'BIKE','2026-01-15 08:00:00', 3600, 3700, 130, 650),
(2,'RUN','2026-01-10 07:00:00', 2400, 2450, 149, 380);

-- Carlos (user_id = 3): 6 workouts
INSERT INTO workouts (user_id, workout_type, date, moving_time_sec, elapsed_time_sec, avg_heart_rate, calories) VALUES
(3,'SWIM','2026-02-12 06:30:00', 1800, 1850, 128, 230),
(3,'RUN','2026-02-10 18:30:00', 4500, 4600, 155, 720),
(3,'BIKE','2026-02-05 09:15:00', 5400, 5500, 138, 920),
(3,'RUN','2026-01-22 07:15:00', 3600, 3700, 150, 600),
(3,'SWIM','2026-01-12 06:00:00', 900, 950, 122, 160),
(3,'BIKE','2026-01-05 10:00:00', 3000, 3050, 128, 520);

-- Dana (user_id = 4): 6 workouts
INSERT INTO workouts (user_id, workout_type, date, moving_time_sec, elapsed_time_sec, avg_heart_rate, calories) VALUES
(4,'RUN','2026-02-11 07:30:00', 2100, 2150, 151, 350),
(4,'SWIM','2026-02-09 06:45:00', 1300, 1350, 120, 210),
(4,'BIKE','2026-02-04 08:30:00', 4800, 4850, 137, 800),
(4,'RUN','2026-01-18 18:15:00', 3000, 3050, 153, 520),
(4,'BIKE','2026-01-10 07:45:00', 4200, 4300, 135, 760),
(4,'SWIM','2025-12-26 06:20:00', 1600, 1650, 125, 230);

-- Eve (user_id = 5): 6 workouts
INSERT INTO workouts (user_id, workout_type, date, moving_time_sec, elapsed_time_sec, avg_heart_rate, calories) VALUES
(5,'BIKE','2026-02-14 09:30:00', 6000, 6100, 134, 980),
(5,'RUN','2026-02-12 07:00:00', 2500, 2550, 149, 400),
(5,'SWIM','2026-02-08 06:00:00', 1400, 1450, 123, 220),
(5,'RUN','2026-01-27 18:00:00', 3600, 3650, 154, 600),
(5,'BIKE','2026-01-19 09:00:00', 5400, 5450, 136, 910),
(5,'SWIM','2026-01-15 06:10:00', 1100, 1150, 121, 180);

-- Frank (user_id = 6): 6 workouts
INSERT INTO workouts (user_id, workout_type, date, moving_time_sec, elapsed_time_sec, avg_heart_rate, calories) VALUES
(6,'RUN','2026-02-13 07:40:00', 4200, 4250, 150, 670),
(6,'BIKE','2026-02-10 08:20:00', 5400, 5500, 139, 920),
(6,'SWIM','2026-02-07 06:30:00', 900, 950, 124, 170),
(6,'RUN','2026-01-30 19:00:00', 1800, 1850, 148, 320),
(6,'BIKE','2026-01-20 09:00:00', 7200, 7300, 142, 1120),
(6,'SWIM','2026-01-11 06:00:00', 1500, 1550, 126, 210);

-- Gina (user_id = 7): 6 workouts
INSERT INTO workouts (user_id, workout_type, date, moving_time_sec, elapsed_time_sec, avg_heart_rate, calories) VALUES
(7,'SWIM','2026-02-12 06:45:00', 1200, 1250, 118, 180),
(7,'RUN','2026-02-09 07:15:00', 2400, 2450, 147, 370),
(7,'BIKE','2026-02-06 08:00:00', 3600, 3650, 131, 640),
(7,'RUN','2026-01-24 18:30:00', 3000, 3050, 150, 520),
(7,'BIKE','2026-01-16 09:30:00', 4500, 4600, 133, 780),
(7,'SWIM','2025-12-30 06:10:00', 1000, 1050, 119, 160);

-- Henry (user_id = 8): 6 workouts
INSERT INTO workouts (user_id, workout_type, date, moving_time_sec, elapsed_time_sec, avg_heart_rate, calories) VALUES
(8,'RUN','2026-02-15 06:00:00', 3600, 3650, 151, 600),
(8,'BIKE','2026-02-12 09:20:00', 4200, 4250, 134, 780),
(8,'SWIM','2026-02-08 06:30:00', 1400, 1450, 122, 210),
(8,'RUN','2026-01-21 07:00:00', 2700, 2750, 149, 420),
(8,'BIKE','2026-01-12 08:10:00', 3000, 3050, 127, 500),
(8,'SWIM','2025-12-22 06:00:00', 1600, 1650, 125, 230);

-- Ivy (user_id = 9): 6 workouts
INSERT INTO workouts (user_id, workout_type, date, moving_time_sec, elapsed_time_sec, avg_heart_rate, calories) VALUES
(9,'BIKE','2026-02-11 08:00:00', 4800, 4850, 132, 800),
(9,'RUN','2026-02-09 07:30:00', 2100, 2150, 146, 350),
(9,'SWIM','2026-02-05 06:00:00', 1100, 1150, 121, 190),
(9,'RUN','2026-01-16 18:00:00', 3300, 3350, 150, 520),
(9,'BIKE','2026-01-08 09:00:00', 5400, 5450, 135, 900),
(9,'SWIM','2025-12-28 06:30:00', 1000, 1050, 118, 160);

-- Jake (user_id = 10): 6 workouts
INSERT INTO workouts (user_id, workout_type, date, moving_time_sec, elapsed_time_sec, avg_heart_rate, calories) VALUES
(10,'RUN','2026-02-14 07:20:00', 3000, 3050, 152, 520),
(10,'BIKE','2026-02-10 09:10:00', 4200, 4250, 133, 780),
(10,'SWIM','2026-02-06 06:10:00', 1300, 1350, 123, 200),
(10,'RUN','2026-01-26 18:20:00', 2400, 2450, 149, 380),
(10,'BIKE','2026-01-14 07:50:00', 3600, 3650, 130, 660),
(10,'SWIM','2025-12-20 06:00:00', 900, 950, 120, 150);

-- By now we inserted 10 users + 10*6 = 60 workouts.

-- Now insert specialized rows by matching workouts via subselect.
-- For each RUN workout: insert into runs
INSERT INTO runs (workout_id, distance_miles, avg_pace_sec_per_mile, elevation_gain_feet, steps, avg_cadence_spm)
SELECT w.id,
       CASE
         WHEN w.moving_time_sec IS NOT NULL AND w.moving_time_sec > 0 THEN ROUND((w.moving_time_sec / 60.0) / GREATEST(1.0,  (w.moving_time_sec/600.0) ), 2) -- dummy, we'll use simpler static
         ELSE 0 END,
       480, -- default 8:00 / mile = 480s
       100,
       6000,
       170
FROM workouts w WHERE w.workout_type='RUN';

-- The above SELECT used a placeholder expression; better replace with static realistic values per-run:
-- Let's delete those auto inserted runs and reinsert explicit per-run rows for clarity:

DELETE FROM runs;

-- We'll insert runs corresponding to all RUN workouts. For simplicity we pick varied distances and paces.
INSERT INTO runs (workout_id, distance_miles, avg_pace_sec_per_mile, elevation_gain_feet, steps, avg_cadence_spm) VALUES
( (SELECT id FROM workouts WHERE user_id=1 AND workout_type='RUN' ORDER BY date DESC LIMIT 1), 6.0, 600, 120, 7200, 165),
( (SELECT id FROM workouts WHERE user_id=1 AND workout_type='RUN' ORDER BY date ASC LIMIT 1), 1.125, 1600, 30, 1400, 150),
( (SELECT id FROM workouts WHERE user_id=2 AND workout_type='RUN' ORDER BY date DESC LIMIT 1), 4.5, 600, 60, 5400, 170),
( (SELECT id FROM workouts WHERE user_id=2 AND workout_type='RUN' ORDER BY date ASC LIMIT 1), 2.5, 960, 40, 3000, 162),
( (SELECT id FROM workouts WHERE user_id=3 AND workout_type='RUN' ORDER BY date DESC LIMIT 1), 8.2, 550, 200, 9800, 168),
( (SELECT id FROM workouts WHERE user_id=3 AND workout_type='RUN' ORDER BY date ASC LIMIT 1), 3.5, 720, 80, 4200, 164),
( (SELECT id FROM workouts WHERE user_id=4 AND workout_type='RUN' ORDER BY date DESC LIMIT 1), 3.5, 600, 50, 4200, 165),
( (SELECT id FROM workouts WHERE user_id=4 AND workout_type='RUN' ORDER BY date ASC LIMIT 1), 5.0, 640, 90, 6000, 166),
( (SELECT id FROM workouts WHERE user_id=5 AND workout_type='RUN' ORDER BY date DESC LIMIT 1), 6.0, 600, 125, 7200, 168),
( (SELECT id FROM workouts WHERE user_id=5 AND workout_type='RUN' ORDER BY date ASC LIMIT 1), 3.7, 650, 70, 4440, 163),
( (SELECT id FROM workouts WHERE user_id=6 AND workout_type='RUN' ORDER BY date DESC LIMIT 1), 7.0, 600, 140, 8400, 167),
( (SELECT id FROM workouts WHERE user_id=6 AND workout_type='RUN' ORDER BY date ASC LIMIT 1), 1.0, 1800, 10, 1300, 150),
( (SELECT id FROM workouts WHERE user_id=7 AND workout_type='RUN' ORDER BY date DESC LIMIT 1), 3.0, 800, 40, 3600, 160),
( (SELECT id FROM workouts WHERE user_id=7 AND workout_type='RUN' ORDER BY date ASC LIMIT 1), 5.0, 700, 95, 6000, 165),
( (SELECT id FROM workouts WHERE user_id=8 AND workout_type='RUN' ORDER BY date DESC LIMIT 1), 6.0, 600, 120, 7200, 169),
( (SELECT id FROM workouts WHERE user_id=8 AND workout_type='RUN' ORDER BY date ASC LIMIT 1), 2.5, 720, 30, 3000, 162),
( (SELECT id FROM workouts WHERE user_id=9 AND workout_type='RUN' ORDER BY date DESC LIMIT 1), 3.5, 700, 60, 4200, 165),
( (SELECT id FROM workouts WHERE user_id=9 AND workout_type='RUN' ORDER BY date ASC LIMIT 1), 5.5, 600, 110, 6600, 167),
( (SELECT id FROM workouts WHERE user_id=10 AND workout_type='RUN' ORDER BY date DESC LIMIT 1), 5.0, 600, 90, 6000, 166),
( (SELECT id FROM workouts WHERE user_id=10 AND workout_type='RUN' ORDER BY date ASC LIMIT 1), 2.4, 1000, 45, 2880, 160);

-- Insert bikes: clear any auto rows then insert
DELETE FROM bikes;
INSERT INTO bikes (workout_id, distance_miles, avg_speed_mph, max_speed_mph, elevation_gain_feet, avg_pedal_rate_rpm) VALUES
( (SELECT id FROM workouts WHERE user_id=1 AND workout_type='BIKE' ORDER BY date DESC LIMIT 1), 22.5, 13.5, 28.0, 400, 85),
( (SELECT id FROM workouts WHERE user_id=1 AND workout_type='BIKE' ORDER BY date ASC LIMIT 1), 45.0, 14.8, 32.0, 1200, 90),
( (SELECT id FROM workouts WHERE user_id=2 AND workout_type='BIKE' ORDER BY date DESC LIMIT 1), 30.0, 15.0, 30.0, 600, 88),
( (SELECT id FROM workouts WHERE user_id=2 AND workout_type='BIKE' ORDER BY date ASC LIMIT 1), 20.0, 13.0, 27.0, 250, 82),
( (SELECT id FROM workouts WHERE user_id=3 AND workout_type='BIKE' ORDER BY date DESC LIMIT 1), 35.0, 14.0, 31.0, 800, 87),
( (SELECT id FROM workouts WHERE user_id=3 AND workout_type='BIKE' ORDER BY date ASC LIMIT 1), 17.0, 13.5, 26.0, 300, 80),
( (SELECT id FROM workouts WHERE user_id=4 AND workout_type='BIKE' ORDER BY date DESC LIMIT 1), 28.0, 14.2, 29.5, 550, 86),
( (SELECT id FROM workouts WHERE user_id=4 AND workout_type='BIKE' ORDER BY date ASC LIMIT 1), 22.0, 13.8, 28.0, 420, 84),
( (SELECT id FROM workouts WHERE user_id=5 AND workout_type='BIKE' ORDER BY date DESC LIMIT 1), 38.0, 14.5, 30.5, 900, 89),
( (SELECT id FROM workouts WHERE user_id=5 AND workout_type='BIKE' ORDER BY date ASC LIMIT 1), 45.0, 15.0, 33.0, 1250, 92),
( (SELECT id FROM workouts WHERE user_id=6 AND workout_type='BIKE' ORDER BY date DESC LIMIT 1), 40.0, 14.8, 31.2, 1100, 90),
( (SELECT id FROM workouts WHERE user_id=6 AND workout_type='BIKE' ORDER BY date ASC LIMIT 1), 18.0, 12.0, 24.0, 200, 78),
( (SELECT id FROM workouts WHERE user_id=7 AND workout_type='BIKE' ORDER BY date DESC LIMIT 1), 20.0, 13.0, 26.5, 300, 81),
( (SELECT id FROM workouts WHERE user_id=7 AND workout_type='BIKE' ORDER BY date ASC LIMIT 1), 27.0, 13.9, 28.8, 480, 85),
( (SELECT id FROM workouts WHERE user_id=8 AND workout_type='BIKE' ORDER BY date DESC LIMIT 1), 30.5, 14.3, 29.8, 650, 86),
( (SELECT id FROM workouts WHERE user_id=8 AND workout_type='BIKE' ORDER BY date ASC LIMIT 1), 16.5, 11.9, 23.5, 190, 76),
( (SELECT id FROM workouts WHERE user_id=9 AND workout_type='BIKE' ORDER BY date DESC LIMIT 1), 28.0, 14.2, 29.0, 540, 85),
( (SELECT id FROM workouts WHERE user_id=9 AND workout_type='BIKE' ORDER BY date ASC LIMIT 1), 36.0, 14.7, 31.0, 880, 88),
( (SELECT id FROM workouts WHERE user_id=10 AND workout_type='BIKE' ORDER BY date DESC LIMIT 1), 26.0, 13.5, 29.0, 470, 84),
( (SELECT id FROM workouts WHERE user_id=10 AND workout_type='BIKE' ORDER BY date ASC LIMIT 1), 19.0, 12.6, 25.0, 260, 80);

-- Insert swims: clear then insert
DELETE FROM swims;
INSERT INTO swims (workout_id, distance_yards, avg_pace_sec_per_100y, avg_stroke_rate_spm) VALUES
( (SELECT id FROM workouts WHERE user_id=1 AND workout_type='SWIM' ORDER BY date DESC LIMIT 1), 1500, 80, 24),
( (SELECT id FROM workouts WHERE user_id=1 AND workout_type='SWIM' ORDER BY date ASC LIMIT 1), 1200, 95, 22),
( (SELECT id FROM workouts WHERE user_id=2 AND workout_type='SWIM' ORDER BY date DESC LIMIT 1), 1000, 88, 23),
( (SELECT id FROM workouts WHERE user_id=2 AND workout_type='SWIM' ORDER BY date ASC LIMIT 1), 900, 100, 21),
( (SELECT id FROM workouts WHERE user_id=3 AND workout_type='SWIM' ORDER BY date DESC LIMIT 1), 1800, 78, 25),
( (SELECT id FROM workouts WHERE user_id=3 AND workout_type='SWIM' ORDER BY date ASC LIMIT 1), 900, 110, 20),
( (SELECT id FROM workouts WHERE user_id=4 AND workout_type='SWIM' ORDER BY date DESC LIMIT 1), 1300, 92, 22),
( (SELECT id FROM workouts WHERE user_id=4 AND workout_type='SWIM' ORDER BY date ASC LIMIT 1), 1600, 85, 24),
( (SELECT id FROM workouts WHERE user_id=5 AND workout_type='SWIM' ORDER BY date DESC LIMIT 1), 1400, 86, 23),
( (SELECT id FROM workouts WHERE user_id=5 AND workout_type='SWIM' ORDER BY date ASC LIMIT 1), 1100, 98, 21),
( (SELECT id FROM workouts WHERE user_id=6 AND workout_type='SWIM' ORDER BY date DESC LIMIT 1), 900, 100, 22),
( (SELECT id FROM workouts WHERE user_id=6 AND workout_type='SWIM' ORDER BY date ASC LIMIT 1), 1500, 82, 24),
( (SELECT id FROM workouts WHERE user_id=7 AND workout_type='SWIM' ORDER BY date DESC LIMIT 1), 1000, 95, 22),
( (SELECT id FROM workouts WHERE user_id=7 AND workout_type='SWIM' ORDER BY date ASC LIMIT 1), 1000, 98, 21),
( (SELECT id FROM workouts WHERE user_id=8 AND workout_type='SWIM' ORDER BY date DESC LIMIT 1), 1400, 86, 23),
( (SELECT id FROM workouts WHERE user_id=8 AND workout_type='SWIM' ORDER BY date ASC LIMIT 1), 1600, 84, 24),
( (SELECT id FROM workouts WHERE user_id=9 AND workout_type='SWIM' ORDER BY date DESC LIMIT 1), 1100, 98, 21),
( (SELECT id FROM workouts WHERE user_id=9 AND workout_type='SWIM' ORDER BY date ASC LIMIT 1), 1000, 95, 22),
( (SELECT id FROM workouts WHERE user_id=10 AND workout_type='SWIM' ORDER BY date DESC LIMIT 1), 1300, 92, 23),
( (SELECT id FROM workouts WHERE user_id=10 AND workout_type='SWIM' ORDER BY date ASC LIMIT 1), 900, 110, 20);