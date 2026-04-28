-- Run mode: SWING_CLIENT

-- Name: C_BPartner SalesRep (Trx)
-- 2025-04-03T11:04:03.756Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541935,TO_TIMESTAMP('2025-04-03 11:04:03.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Business Partner selection sales rep (no Summary)','D','Y','N','C_BPartner SalesRep (Trx)',TO_TIMESTAMP('2025-04-03 11:04:03.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-04-03T11:04:03.757Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541935 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_BPartner SalesRep (Trx)
-- Table: C_BPartner
-- Key: C_BPartner.C_BPartner_ID
-- 2025-04-03T11:04:45.725Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,2902,2893,0,541935,291,TO_TIMESTAMP('2025-04-03 11:04:45.705000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2025-04-03 11:04:45.705000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_BPartner.IsSummary=''N'' AND C_BPartner.IsActive=''Y'' and C_BPartner.IsSalesRep=''Y''')
;

-- Column: C_Order.C_BPartner_SalesRep_ID
-- 2025-04-03T11:05:08.320Z
UPDATE AD_Column SET AD_Reference_Value_ID=541935,Updated=TO_TIMESTAMP('2025-04-03 11:05:08.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=568785
;

-- Name: C_BPartner NOT BiIl_Partner
-- 2025-04-03T11:06:00.598Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540703,'C_BPartner.C_BPartner_ID!=@Bill_BPartner_ID/-1@',TO_TIMESTAMP('2025-04-03 11:06:00.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','C_BPartner NOT BiIl_Partner','S',TO_TIMESTAMP('2025-04-03 11:06:00.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_Order.C_BPartner_SalesRep_ID
-- 2025-04-03T11:06:13.716Z
UPDATE AD_Column SET AD_Val_Rule_ID=540703,Updated=TO_TIMESTAMP('2025-04-03 11:06:13.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=568785
;

