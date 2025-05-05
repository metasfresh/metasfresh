-- Column: C_BPartner.CreditLimitIndicator
-- Column SQL (old): (select MAX(CreditLimitIndicator) from C_BPartner_Stats s where s.C_BPartner_ID = C_BPartner.C_BPartner_ID)
-- 2023-02-01T23:26:12.314Z
UPDATE AD_Column SET ColumnSQL='(CASE WHEN C_BPartner.Section_Group_Partner_ID IS NOT NULL               THEN (SELECT CASE                             WHEN v.SO_CreditUsed = 0                             THEN ''0%''                             ELSE round((v.SO_CreditUsed*100/v.CreditLimit),3 )  || ''%''                             END                             from C_BPartner_CreditLimit_Departments_V v)                             ELSE (SELECT MAX(CreditLimitIndicator) from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID) END)',Updated=TO_TIMESTAMP('2023-02-02 01:26:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558986
;

-- Column: C_BPartner.DeliveryCreditLimitIndicator
-- Column SQL (old): (select MAX(DeliveryCreditLimitIndicator) from C_BPartner_Stats s where s.C_BPartner_ID = C_BPartner.C_BPartner_ID)
-- 2023-02-01T23:27:23.051Z
UPDATE AD_Column SET ColumnSQL='(CASE WHEN C_BPartner.Section_Group_Partner_ID IS NOT NULL     THEN (SELECT CASE     WHEN v.Delivery_CreditUsed = 0     THEN ''0%''     ELSE round((v.Delivery_CreditUsed*100/v.CreditLimit),3 )  || ''%''     END     from C_BPartner_CreditLimit_Departments_V v)     ELSE (SELECT MAX(DeliveryCreditLimitIndicator) from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID)     END)',Updated=TO_TIMESTAMP('2023-02-02 01:27:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585625
;

-- Column: C_BPartner_CreditLimit_Department_Lines_V.Section_Group_Partner_ID
-- 2023-02-02T00:27:04.949Z
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2023-02-02 02:27:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585737
;

-- Column: C_BPartner_CreditLimit_Department_Lines_V.M_Department_ID
-- 2023-02-02T00:27:14.772Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=1,Updated=TO_TIMESTAMP('2023-02-02 02:27:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585723
;

-- Column: C_BPartner.CreditLimitIndicator
-- Column SQL (old): (CASE WHEN C_BPartner.Section_Group_Partner_ID IS NOT NULL               THEN (SELECT CASE                             WHEN v.SO_CreditUsed = 0                             THEN '0%'                             ELSE round((v.SO_CreditUsed*100/v.CreditLimit),3 )  || '%'                             END                             from C_BPartner_CreditLimit_Departments_V v)                             ELSE (SELECT MAX(CreditLimitIndicator) from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID) END)
-- 2023-02-02T00:35:06.424Z
UPDATE AD_Column SET ColumnSQL='(CASE WHEN C_BPartner.Section_Group_Partner_ID IS NOT NULL               THEN (SELECT CASE                             WHEN v.SO_CreditUsed = 0                             THEN ''0%''                             ELSE round((v.SO_CreditUsed*100/v.CreditLimit),3 )  || ''%''                             END                             from C_BPartner_CreditLimit_Departments_V v where C_BPartner.M_SectionCode_ID = v.M_SectionCode_ID)                             ELSE (SELECT MAX(CreditLimitIndicator) from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID) END)',Updated=TO_TIMESTAMP('2023-02-02 02:35:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558986
;

-- Column: C_BPartner.DeliveryCreditLimitIndicator
-- Column SQL (old): (CASE WHEN C_BPartner.Section_Group_Partner_ID IS NOT NULL     THEN (SELECT CASE     WHEN v.Delivery_CreditUsed = 0     THEN '0%'     ELSE round((v.Delivery_CreditUsed*100/v.CreditLimit),3 )  || '%'     END     from C_BPartner_CreditLimit_Departments_V v)     ELSE (SELECT MAX(DeliveryCreditLimitIndicator) from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID)     END)
-- 2023-02-02T00:35:23.008Z
UPDATE AD_Column SET ColumnSQL='(CASE WHEN C_BPartner.Section_Group_Partner_ID IS NOT NULL     THEN (SELECT CASE     WHEN v.Delivery_CreditUsed = 0     THEN ''0%''     ELSE round((v.Delivery_CreditUsed*100/v.CreditLimit),3 )  || ''%''     END     from C_BPartner_CreditLimit_Departments_V v where C_BPartner.M_SectionCode_ID = v.M_SectionCode_ID)     ELSE (SELECT MAX(DeliveryCreditLimitIndicator) from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID)     END)',Updated=TO_TIMESTAMP('2023-02-02 02:35:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585625
;

-- Column: C_BPartner.DeliveryCreditLimitIndicator
-- Column SQL (old): (CASE WHEN C_BPartner.Section_Group_Partner_ID IS NOT NULL     THEN (SELECT CASE     WHEN v.Delivery_CreditUsed = 0     THEN '0%'     ELSE round((v.Delivery_CreditUsed*100/v.CreditLimit),3 )  || '%'     END     from C_BPartner_CreditLimit_Departments_V v where C_BPartner.M_SectionCode_ID = v.M_SectionCode_ID)     ELSE (SELECT MAX(DeliveryCreditLimitIndicator) from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID)     END)
-- 2023-02-02T00:38:29.282Z
UPDATE AD_Column SET ColumnSQL='(CASE WHEN C_BPartner.Section_Group_Partner_ID IS NOT NULL     THEN (SELECT CASE     WHEN v.Delivery_CreditUsed = 0     THEN ''0%''     ELSE round((v.Delivery_CreditUsed*100/v.CreditLimit),3 )  || ''%''     END     from C_BPartner_CreditLimit_Departments_V v where C_BPartner.C_BPartner_ID = v.C_BPartner_CreditLimit_Departments_V_ID)     ELSE (SELECT MAX(DeliveryCreditLimitIndicator) from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID)     END)',Updated=TO_TIMESTAMP('2023-02-02 02:38:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585625
;

-- Column: C_BPartner.CreditLimitIndicator
-- Column SQL (old): (CASE WHEN C_BPartner.Section_Group_Partner_ID IS NOT NULL               THEN (SELECT CASE                             WHEN v.SO_CreditUsed = 0                             THEN '0%'                             ELSE round((v.SO_CreditUsed*100/v.CreditLimit),3 )  || '%'                             END                             from C_BPartner_CreditLimit_Departments_V v where C_BPartner.M_SectionCode_ID = v.M_SectionCode_ID)                             ELSE (SELECT MAX(CreditLimitIndicator) from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID) END)
-- 2023-02-02T00:38:52.970Z
UPDATE AD_Column SET ColumnSQL='(CASE WHEN C_BPartner.Section_Group_Partner_ID IS NOT NULL               THEN (SELECT CASE                             WHEN v.SO_CreditUsed = 0                             THEN ''0%''                             ELSE round((v.SO_CreditUsed*100/v.CreditLimit),3 )  || ''%''                             END                             from C_BPartner_CreditLimit_Departments_V v where C_BPartner.C_BPartner_ID = v.C_BPartner_CreditLimit_Departments_V_ID)                             ELSE (SELECT MAX(CreditLimitIndicator) from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID) END)',Updated=TO_TIMESTAMP('2023-02-02 02:38:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558986
;

-- Column: C_BPartner.CreditLimitIndicator
-- Column SQL (old): (CASE WHEN C_BPartner.Section_Group_Partner_ID IS NOT NULL               THEN (SELECT CASE                             WHEN v.SO_CreditUsed = 0                             THEN '0%'                             ELSE round((v.SO_CreditUsed*100/v.CreditLimit),3 )  || '%'                             END                             from C_BPartner_CreditLimit_Departments_V v where C_BPartner.C_BPartner_ID = v.C_BPartner_CreditLimit_Departments_V_ID)                             ELSE (SELECT MAX(CreditLimitIndicator) from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID) END)
-- 2023-02-02T00:43:28.157Z
UPDATE AD_Column SET ColumnSQL='(CASE WHEN C_BPartner.Section_Group_Partner_ID IS NOT NULL               THEN (SELECT CASE                             WHEN v.SO_CreditUsed = 0                             THEN ''0%''                             ELSE round((v.SO_CreditUsed*100/v.CreditLimit),3 )  || ''%''                             END from C_BPartner_CreditLimit_Departments_V v where C_BPartner.C_BPartner_ID = v.C_BPartner_CreditLimit_Departments_V_ID)                             ELSE (SELECT MAX(CreditLimitIndicator) from C_BPartner_Stats S where S.C_BPartner_ID = C_BPartner.C_BPartner_ID) END)',Updated=TO_TIMESTAMP('2023-02-02 02:43:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558986
;



-- Column: C_BPartner.CreditLimitIndicator
-- Source Table: C_BPartner
-- 2023-02-02T19:04:51.152Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,558986,0,540117,291,TO_TIMESTAMP('2023-02-02 21:04:50','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',584076,291,TO_TIMESTAMP('2023-02-02 21:04:50','YYYY-MM-DD HH24:MI:SS'),100)
;

