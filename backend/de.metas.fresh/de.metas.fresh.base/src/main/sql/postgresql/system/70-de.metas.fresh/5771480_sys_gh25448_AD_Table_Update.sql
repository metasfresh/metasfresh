UPDATE ad_table
SET ischangelog='Y'
WHERE ad_table_id IN (SELECT cc.ad_table_id FROM fix.AD_Table_Changelog_Config_26092025 cc)
;
