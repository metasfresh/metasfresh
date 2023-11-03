-- I_ModCntr_Log
-- Align Import Window with ModCntr_Log Window
UPDATE ad_column
SET ismandatory = 'N'
WHERE ad_column_id in (587518, 587511, 587512 )
;
