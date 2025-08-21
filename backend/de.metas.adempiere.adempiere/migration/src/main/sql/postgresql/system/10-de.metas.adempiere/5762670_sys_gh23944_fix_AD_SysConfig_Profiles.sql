

UPDATE ad_sysconfig
SET value=REPLACE(value, '.', '-'), updated='2025-08-13 21:03', updatedby=100
WHERE name ILIKE '%spring.profiles.active%';