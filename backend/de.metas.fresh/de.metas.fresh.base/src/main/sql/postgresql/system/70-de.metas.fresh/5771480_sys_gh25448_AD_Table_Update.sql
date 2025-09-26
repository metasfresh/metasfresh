UPDATE ad_table
SET ischangelog='Y'
WHERE ad_table_id IN (SELECT cc.ad_table_id FROM fix.I_ChangeLog_Config cc)
;
