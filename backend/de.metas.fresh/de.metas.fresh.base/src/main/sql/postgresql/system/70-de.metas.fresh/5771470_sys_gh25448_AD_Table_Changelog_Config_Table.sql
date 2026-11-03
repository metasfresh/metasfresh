INSERT INTO fix.AD_Table_Changelog_Config_26092025 (AD_Table_ID)

SELECT t.ad_table_id
FROM ad_table t
WHERE t.ischangelog = 'N'
  AND t.isview = 'N'
  AND t.tablename NOT ILIKE 'T_%'
  AND t.tablename NOT ILIKE 'AD_ChangeLog'
  AND t.ad_table_id NOT IN (SELECT cc.ad_table_id FROM AD_ChangeLog_Config cc)
;
