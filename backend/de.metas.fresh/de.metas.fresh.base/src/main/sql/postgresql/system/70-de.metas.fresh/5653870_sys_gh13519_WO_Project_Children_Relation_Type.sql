-- Name: C_Project (workOrderProject)
-- 2022-08-30T17:24:59.613Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541650,TO_TIMESTAMP('2022-08-30 18:24:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Project (workOrderProject)',TO_TIMESTAMP('2022-08-30 18:24:58','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-08-30T17:24:59.757Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541650 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Name: C_Project (Work Order)
-- 2022-08-30T17:25:19.203Z
UPDATE AD_Reference SET Name='C_Project (Work Order)',Updated=TO_TIMESTAMP('2022-08-30 18:25:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541650
;

-- Reference: C_Project (Work Order)
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-30T17:26:52.348Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,1349,0,541650,203,TO_TIMESTAMP('2022-08-30 18:26:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-08-30 18:26:51','YYYY-MM-DD HH24:MI:SS'),100,'ProjectCategory=''W''')
;

-- Name: C_Project (Work Order Children)
-- 2022-08-30T17:27:42.342Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541651,TO_TIMESTAMP('2022-08-30 18:27:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Project (Work Order Children)',TO_TIMESTAMP('2022-08-30 18:27:41','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-08-30T17:27:42.427Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541651 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Project (Work Order Children)
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-30T17:30:27.932Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,1349,0,541651,203,TO_TIMESTAMP('2022-08-30 18:30:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-08-30 18:30:27','YYYY-MM-DD HH24:MI:SS'),100,'C_Project_Parent_ID=@C_Project_ID/-1@ AND IsActive = ''Y'' AND ProjectCategory=''W''')
;

-- 2022-08-30T17:32:53.123Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541650,541651,540363,TO_TIMESTAMP('2022-08-30 18:32:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Project WO (Parent) -> C_Project WO (Children)',TO_TIMESTAMP('2022-08-30 18:32:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-30T17:38:51.824Z
UPDATE AD_Reference_Trl SET Name='C_Project (Work Order)',Updated=TO_TIMESTAMP('2022-08-30 18:38:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541650
;

-- 2022-08-30T17:38:54.870Z
UPDATE AD_Reference_Trl SET Name='C_Project (Work Order)',Updated=TO_TIMESTAMP('2022-08-30 18:38:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541650
;

-- 2022-08-30T17:38:58.600Z
UPDATE AD_Reference_Trl SET Name='C_Project (Work Order)',Updated=TO_TIMESTAMP('2022-08-30 18:38:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541650
;

-- 2022-08-30T17:39:01.770Z
UPDATE AD_Reference_Trl SET Name='C_Project (Work Order)',Updated=TO_TIMESTAMP('2022-08-30 18:39:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Reference_ID=541650
;

-- 2022-08-30T17:39:06.176Z
UPDATE AD_Reference_Trl SET Name='C_Project (Work Order)',Updated=TO_TIMESTAMP('2022-08-30 18:39:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Reference_ID=541650
;

-- Reference: C_Project (Work Order)
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-30T19:20:43.413Z
UPDATE AD_Ref_Table SET AD_Window_ID=541512,Updated=TO_TIMESTAMP('2022-08-30 20:20:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541650
;

-- Reference: C_Project (Work Order Children)
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-30T19:21:00.743Z
UPDATE AD_Ref_Table SET AD_Window_ID=541512,Updated=TO_TIMESTAMP('2022-08-30 20:21:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541651
;