
-- Name: C_BPartner_User_Projects
-- 2022-08-30T10:25:54.398Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541649,TO_TIMESTAMP('2022-08-30 11:25:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_BPartner_User_Projects',TO_TIMESTAMP('2022-08-30 11:25:53','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-08-30T10:25:54.480Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541649 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_BPartner_User_Projects
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-30T10:26:40.073Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,1349,0,541649,203,TO_TIMESTAMP('2022-08-30 11:26:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-08-30 11:26:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: C_BPartner_User_Projects
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-30T10:28:51.454Z
UPDATE AD_Ref_Table SET WhereClause='AD_User_ID = (SELECT b.AD_User_ID from AD_User b where b.C_BPartner_ID = @C_BPartner_ID/-1@)  OR AD_User_InCharge_ID =  (SELECT b.AD_User_ID from AD_User b where b.C_BPartner_ID = @C_BPartner_ID/-1@)  OR SalesRep_ID =  (SELECT b.AD_User_ID from AD_User b where b.C_BPartner_ID = @C_BPartner_ID/-1@)  AND IsActive = ''Y''',Updated=TO_TIMESTAMP('2022-08-30 11:28:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541649
;

-- 2022-08-30T10:29:46.136Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,110,541649,540362,TO_TIMESTAMP('2022-08-30 11:29:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_BPartner_User -> C_Project',TO_TIMESTAMP('2022-08-30 11:29:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-30T10:30:29.657Z
UPDATE AD_RelationType SET AD_Reference_Source_ID=541252,Updated=TO_TIMESTAMP('2022-08-30 11:30:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540362
;

