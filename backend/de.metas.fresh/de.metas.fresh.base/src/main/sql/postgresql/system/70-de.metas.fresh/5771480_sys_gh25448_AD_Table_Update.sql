UPDATE ad_table
SET ischangelog='Y'
WHERE ad_table_id IN (SELECT cc.ad_table_id FROM fix.AD_Table_Changelog_Config_26092025 cc)
;

UPDATE AD_SysConfig SET Value='N', Updatedby=100, Updated='2025-09-26 20:09'  WHERE Name='SYSTEM_INSERT_CHANGELOG' and Value!='N';
