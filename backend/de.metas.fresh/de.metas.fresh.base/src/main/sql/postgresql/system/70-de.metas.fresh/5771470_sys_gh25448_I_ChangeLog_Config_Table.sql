INSERT INTO fix.I_ChangeLog_Config (I_ChangeLog_Config_id,
                                    AD_Client_ID,
                                    AD_Org_ID,
                                    AD_Table_ID,
                                    Created,
                                    CreatedBy,
                                    IsActive,
                                    KeepChangeLogsDays,
                                    Updated,
                                    UpdatedBy)

SELECT NEXTVAL('AD_ChangeLog_Config_seq'),
       1000000,
       1000000,
       t.ad_table_id,
       NOW(),
       100,
       'Y',
       10,
       NOW(),
       100
FROM ad_table t
WHERE t.ischangelog = 'N'
  AND t.isview = 'N'
  AND t.tablename NOT ILIKE 'T_%'
  AND t.ad_table_id NOT IN (SELECT cc.ad_table_id FROM AD_ChangeLog_Config cc)
;
