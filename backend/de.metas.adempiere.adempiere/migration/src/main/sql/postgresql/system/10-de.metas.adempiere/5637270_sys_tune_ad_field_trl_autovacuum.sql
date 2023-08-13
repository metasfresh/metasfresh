
-- Thx to https://blog.2ndquadrant.com/autovacuum-tuning-basics/
ALTER TABLE ad_field_trl SET (autovacuum_vacuum_scale_factor = 0);
ALTER TABLE ad_field_trl SET (autovacuum_vacuum_threshold = 1000);
