UPDATE ad_process_trl
SET name = 'Preislisten-Schema Positionen exportieren', Updated=TO_TIMESTAMP('2025-02-06 14:20:26', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE ad_process_id = 541210
  AND ad_language IN ('de_DE', 'fr_CH')
;

UPDATE ad_process_trl
SET name = 'Preislisten-Schema Positionen importieren', Updated=TO_TIMESTAMP('2025-02-06 14:20:26', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE ad_process_id = 541211
  AND ad_language IN ('de_DE', 'fr_CH')
;
