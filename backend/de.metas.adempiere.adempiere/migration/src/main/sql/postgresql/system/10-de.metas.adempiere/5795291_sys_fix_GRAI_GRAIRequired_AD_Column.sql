--Add name to GRAIRequired ad_column

UPDATE ad_column
SET Name      = 'GRAIRequired',
    updated   = TO_TIMESTAMP('2026-05-07 12:00', 'YYYY-MM-DD HH24:MI'),
    updatedBy = 100
WHERE ad_column_id = 592261
;
