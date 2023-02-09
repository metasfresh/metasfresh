-- 2022-08-29T16:09:29.239Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541136,540360,TO_TIMESTAMP('2022-08-29 17:09:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Project (Parent) -> C_Project (Children)',TO_TIMESTAMP('2022-08-29 17:09:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: C_Project (Children)
-- 2022-08-29T16:10:18.445Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541647,TO_TIMESTAMP('2022-08-29 17:10:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Project (Children)',TO_TIMESTAMP('2022-08-29 17:10:17','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-08-29T16:10:18.520Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541647 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Project (Children)
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-29T16:13:34.394Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,1356,1349,0,541647,203,TO_TIMESTAMP('2022-08-29 17:13:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-08-29 17:13:34','YYYY-MM-DD HH24:MI:SS'),100,'EXISTS (SELECT 1 from C_Project p where p.c_project_parent_id =@C_Project_ID/-1@ AND p.isactive = ''Y'')')
;

-- Reference: C_Project (Children)
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-29T16:13:50.454Z
UPDATE AD_Ref_Table SET AD_Window_ID=140,Updated=TO_TIMESTAMP('2022-08-29 17:13:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541647
;

-- 2022-08-29T16:16:01.698Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541647,Updated=TO_TIMESTAMP('2022-08-29 17:16:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540360
;

-- Reference: C_Project (Children)
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-29T16:20:15.657Z
UPDATE AD_Ref_Table SET WhereClause='C_Project.C_Project_Parent_ID =@C_Project_ID/-1@ AND C_Project.IsActive = ''Y''',Updated=TO_TIMESTAMP('2022-08-29 17:20:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541647
;

-- Reference: C_Project (Children)
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-29T16:24:44.707Z
UPDATE AD_Ref_Table SET AD_Display=NULL,Updated=TO_TIMESTAMP('2022-08-29 17:24:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541647
;

-- Reference: C_Project (Children)
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-29T16:30:08.377Z
UPDATE AD_Ref_Table SET WhereClause='C_Project.C_Project_Parent_ID=@C_Project_ID/-1@ AND C_Project.IsActive = ''Y''',Updated=TO_TIMESTAMP('2022-08-29 17:30:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541647
;

-- Reference: C_Project (Children)
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-29T16:31:00.460Z
UPDATE AD_Ref_Table SET WhereClause='C_Project_Parent_ID=@C_Project_ID/-1@ AND IsActive = ''Y''',Updated=TO_TIMESTAMP('2022-08-29 17:31:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541647
;

-- Reference: C_Project (Children)
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-29T16:33:14.713Z
UPDATE AD_Ref_Table SET AD_Window_ID=130,Updated=TO_TIMESTAMP('2022-08-29 17:33:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541647
;

-- Reference: C_Project (Children)
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-29T16:38:02.469Z
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2022-08-29 17:38:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541647
;

