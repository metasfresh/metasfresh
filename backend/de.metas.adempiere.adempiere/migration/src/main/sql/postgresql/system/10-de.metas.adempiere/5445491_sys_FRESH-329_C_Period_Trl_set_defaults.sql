ALTER TABLE c_period_trl ALTER COLUMN created SET DEFAULT now();
ALTER TABLE c_period_trl ALTER COLUMN isactive SET DEFAULT 'Y';
ALTER TABLE c_period_trl ALTER COLUMN updated SET DEFAULT now();
