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
       cc.AD_Client_ID,
       cc.AD_Org_ID,
       cc.AD_Table_ID,
       cc.Created,
       cc.CreatedBy,
       cc.IsActive,
       cc.KeepChangeLogsDays,
       cc.Updated,
       cc.UpdatedBy
FROM fix.I_ChangeLog_Config cc
;
