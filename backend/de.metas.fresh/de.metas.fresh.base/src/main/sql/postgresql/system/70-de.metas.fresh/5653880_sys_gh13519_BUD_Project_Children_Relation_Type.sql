-- Name: C_Project (Budget)
-- 2022-08-30T20:17:03.469Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541652,TO_TIMESTAMP('2022-08-30 21:17:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Project (Budget)',TO_TIMESTAMP('2022-08-30 21:17:02','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-08-30T20:17:03.559Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541652 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Project (Budget)
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-30T20:20:59.284Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,1349,0,541652,203,TO_TIMESTAMP('2022-08-30 21:20:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-08-30 21:20:58','YYYY-MM-DD HH24:MI:SS'),100,'ProjectCategory=''B''')
;

-- Reference: C_Project (Budget)
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-30T20:21:18.154Z
UPDATE AD_Ref_Table SET AD_Window_ID=541506,Updated=TO_TIMESTAMP('2022-08-30 21:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541652
;

-- Name: C_Project (Budget Project Children)
-- 2022-08-30T20:22:10.288Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541653,TO_TIMESTAMP('2022-08-30 21:22:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Project (Budget Project Children)',TO_TIMESTAMP('2022-08-30 21:22:08','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-08-30T20:22:10.382Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541653 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Project (Budget Project Children)
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-30T20:27:42.781Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,1349,0,541653,203,541506,TO_TIMESTAMP('2022-08-30 21:27:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-08-30 21:27:42','YYYY-MM-DD HH24:MI:SS'),100,'C_Project_Parent_ID=@C_Project_ID/-1@ AND IsActive = ''Y'' AND ProjectCategory=''B''')
;

-- 2022-08-30T20:29:47.003Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541652,541653,540364,TO_TIMESTAMP('2022-08-30 21:29:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Project BUD (Parent) -> C_Project BUD (Children)',TO_TIMESTAMP('2022-08-30 21:29:46','YYYY-MM-DD HH24:MI:SS'),100)
;