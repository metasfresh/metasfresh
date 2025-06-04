
UPDATE ad_record_warning

SET root_ad_table_id = ad_table_id,
    root_record_id   = record_id
WHERE root_ad_table_id IS NULL
   OR root_record_id IS NULL
;
