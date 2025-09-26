INSERT INTO AD_ChangeLog_Config (AD_ChangeLog_Config_ID,
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
       cc.AD_Table_ID,
       NOW(),
       99,
       'Y',
       10,
       NOW(),
       99
FROM fix.AD_Table_Changelog_Config_26092025 cc
;
