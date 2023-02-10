-- Column: C_BPartner.DeliveryCreditLimitIndicator
-- Column SQL (old): (CASE WHEN C_BPartner.IsSectionGroupPartner = 'Y' THEN (SELECT CASE WHEN v.Delivery_CreditUsed = 0 THEN '0%' ELSE round((v.Delivery_CreditUsed*100/v.CreditLimit),3 )     || '%' END from C_BPartner_CreditLimit_Departments_V v where C_BPartner.C_BPartner_ID = v.C_BPartner_CreditLimit_Departments_V_ID)     ELSE (SELECT MAX(DeliveryCreditLimitIndicator) from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID) END)
-- 2023-02-09T14:49:06.521Z
UPDATE AD_Column SET ColumnSQL='(CASE WHEN C_BPartner.IsSectionGroupPartner = ''Y'' THEN (SELECT CASE WHEN v.Delivery_CreditUsed = 0 THEN ''0%'' ELSE round((v.Delivery_CreditUsed*100/v.CreditLimit),3 )     || ''%'' END from C_BPartner_CreditLimit_Departments_V v where C_BPartner.C_BPartner_ID = v.Section_Group_Partner_ID limit 1)     ELSE (SELECT MAX(DeliveryCreditLimitIndicator) from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID) END)',Updated=TO_TIMESTAMP('2023-02-09 16:49:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585625
;

-- Column: C_BPartner.CreditLimitIndicator
-- Column SQL (old): (CASE WHEN C_BPartner.IsSectionGroupPartner = 'Y' THEN (SELECT CASE WHEN v.SO_CreditUsed = 0 THEN '0%'     ELSE round((v.SO_CreditUsed*100/v.CreditLimit),3 ) || '%' END from C_BPartner_CreditLimit_Departments_V v where C_BPartner.C_BPartner_ID = v.C_BPartner_CreditLimit_Departments_V_ID limit 1)     ELSE (SELECT MAX(CreditLimitIndicator)     from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID) END)
-- 2023-02-09T14:49:24.836Z
UPDATE AD_Column SET ColumnSQL='(CASE WHEN C_BPartner.IsSectionGroupPartner = ''Y'' THEN (SELECT CASE WHEN v.SO_CreditUsed = 0 THEN ''0%''     ELSE round((v.SO_CreditUsed*100/v.CreditLimit),3 ) || ''%'' END from C_BPartner_CreditLimit_Departments_V v where C_BPartner.C_BPartner_ID = v.Section_Group_Partner_ID limit 1)     ELSE (SELECT MAX(CreditLimitIndicator)     from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID) END)',Updated=TO_TIMESTAMP('2023-02-09 16:49:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558986
;

-- Column: C_BPartner.DeliveryCreditLimitIndicator
-- Source Table: C_BPartner_Stats
-- 2023-02-09T15:31:40.804Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,585625,0,540130,291,TO_TIMESTAMP('2023-02-09 17:31:40','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',554367,540763,TO_TIMESTAMP('2023-02-09 17:31:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_BPartner.CreditLimitIndicator
-- Source Table: C_BPartner
-- 2023-02-09T15:32:34.024Z
DELETE FROM AD_SQLColumn_SourceTableColumn WHERE AD_SQLColumn_SourceTableColumn_ID=540117
;

-- Column: C_BPartner.CreditLimitIndicator
-- Source Table: C_BPartner
-- 2023-02-09T15:33:04.255Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,558986,0,540131,291,TO_TIMESTAMP('2023-02-09 17:33:04','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',584077,291,TO_TIMESTAMP('2023-02-09 17:33:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_BPartner.DeliveryCreditLimitIndicator
-- Source Table: C_BPartner
-- 2023-02-09T15:33:19.704Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,585625,0,540132,291,TO_TIMESTAMP('2023-02-09 17:33:19','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',584077,291,TO_TIMESTAMP('2023-02-09 17:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_BPartner.CreditLimitIndicator
-- Source Table: C_BPartner
-- 2023-02-09T15:34:32.661Z
UPDATE AD_SQLColumn_SourceTableColumn SET Link_Column_ID=584076,Updated=TO_TIMESTAMP('2023-02-09 17:34:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540131
;

-- Column: C_BPartner.DeliveryCreditLimitIndicator
-- Source Table: C_BPartner
-- 2023-02-09T15:34:47.904Z
UPDATE AD_SQLColumn_SourceTableColumn SET Link_Column_ID=584076,Updated=TO_TIMESTAMP('2023-02-09 17:34:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540132
;

