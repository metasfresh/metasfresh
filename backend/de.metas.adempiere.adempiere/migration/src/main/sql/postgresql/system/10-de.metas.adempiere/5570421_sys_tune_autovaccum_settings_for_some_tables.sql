

-- Thx to https://medium.com/contactually-engineering/postgres-at-scale-query-performance-and-autovacuuming-for-large-tables-d7e8ad40b16b
-- this ends up in postgresql.auto.conf. 
-- note "postgresql.auto.conf override those in postgresql.conf." (from https://www.postgresql.org/docs/9.5/config-setting.html)
-- but whenever we manually edited and postgresql.conf, we set these to settings to 0.05 there as well
ALTER SYSTEM SET autovacuum_vacuum_scale_factor = 0.05;

