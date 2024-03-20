-- Column: C_Doc_Approval_Strategy_Line.Type
-- 2023-12-08T06:22:46.257530300Z
UPDATE AD_Column SET FieldLength=20,Updated=TO_TIMESTAMP('2023-12-08 08:22:46.257','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587709
;

-- 2023-12-08T06:22:47.978035100Z
INSERT INTO t_alter_column values('c_doc_approval_strategy_line','Type','VARCHAR(20)',null,null)
;

-- Reference: C_Doc_Approval_Strategy_Line_Type
-- Value: USER
-- ValueName: User
-- 2023-12-08T06:23:29.747239900Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541843,543604,TO_TIMESTAMP('2023-12-08 08:23:29.608','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','User',TO_TIMESTAMP('2023-12-08 08:23:29.608','YYYY-MM-DD HH24:MI:SS.US'),100,'USER','User')
;

-- 2023-12-08T06:23:29.753366900Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543604 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2023-12-08T06:46:17.989360500Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582844,0,'SupervisorCheckStrategy',TO_TIMESTAMP('2023-12-08 08:46:17.854','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Check Supervisor','Check Supervisor',TO_TIMESTAMP('2023-12-08 08:46:17.854','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-08T06:46:17.991964Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582844 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: C_Doc_Approval_Strategy_Line_SupervisorCheckStrategy
-- 2023-12-08T06:46:50.492690400Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541844,TO_TIMESTAMP('2023-12-08 08:46:50.342','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','C_Doc_Approval_Strategy_Line_SupervisorCheckStrategy',TO_TIMESTAMP('2023-12-08 08:46:50.342','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;

-- 2023-12-08T06:46:50.494764500Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541844 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Doc_Approval_Strategy_Line_SupervisorCheckStrategy
-- Value: N
-- ValueName: DoNotCheck
-- 2023-12-08T06:47:15.351273200Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541844,543605,TO_TIMESTAMP('2023-12-08 08:47:15.206','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Don''t check',TO_TIMESTAMP('2023-12-08 08:47:15.206','YYYY-MM-DD HH24:MI:SS.US'),100,'N','DoNotCheck')
;

-- 2023-12-08T06:47:15.353944800Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543605 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: C_Doc_Approval_Strategy_Line_SupervisorCheckStrategy
-- Value: A
-- ValueName: AllMatching
-- 2023-12-08T06:49:53.880538100Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541844,543606,TO_TIMESTAMP('2023-12-08 08:49:53.746','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','All matching',TO_TIMESTAMP('2023-12-08 08:49:53.746','YYYY-MM-DD HH24:MI:SS.US'),100,'A','AllMatching')
;

-- 2023-12-08T06:49:53.882083900Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543606 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: C_Doc_Approval_Strategy_Line_SupervisorCheckStrategy
-- Value: F
-- ValueName: FirstMatching
-- 2023-12-08T06:50:05.795667400Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541844,543607,TO_TIMESTAMP('2023-12-08 08:50:05.662','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','First Matching',TO_TIMESTAMP('2023-12-08 08:50:05.662','YYYY-MM-DD HH24:MI:SS.US'),100,'F','FirstMatching')
;

-- 2023-12-08T06:50:05.797790Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543607 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: C_Doc_Approval_Strategy_Line.SupervisorCheckStrategy
-- 2023-12-08T06:51:14.601896500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587715,582844,0,17,541844,542381,'SupervisorCheckStrategy',TO_TIMESTAMP('2023-12-08 08:51:14.4','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Check Supervisor',0,0,TO_TIMESTAMP('2023-12-08 08:51:14.4','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-08T06:51:14.603986500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587715 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-08T06:51:15.208589900Z
/* DDL */  select update_Column_Translation_From_AD_Element(582844) 
;

-- 2023-12-08T06:51:19.332785900Z
/* DDL */ SELECT public.db_alter_table('C_Doc_Approval_Strategy_Line','ALTER TABLE public.C_Doc_Approval_Strategy_Line ADD COLUMN SupervisorCheckStrategy CHAR(1)')
;

-- Reference: C_Doc_Approval_Strategy_Line_Type
-- Value: USER
-- ValueName: User
-- 2023-12-08T07:22:54.757605200Z
DELETE FROM  AD_Ref_List_Trl WHERE AD_Ref_List_ID=543604
;

-- 2023-12-08T07:22:54.763856600Z
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID=543604
;

-- Reference: C_Doc_Approval_Strategy_Line_Type
-- Value: REQUESTOR
-- ValueName: Requestor
-- 2023-12-08T07:23:23.295462300Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541843,543608,TO_TIMESTAMP('2023-12-08 09:23:22.923','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Requestor',TO_TIMESTAMP('2023-12-08 09:23:22.923','YYYY-MM-DD HH24:MI:SS.US'),100,'REQUESTOR','Requestor')
;

-- 2023-12-08T07:23:23.297554900Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543608 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: C_Doc_Approval_Strategy_Line_Type
-- Value: INVOKER
-- ValueName: WorkflowInvoker
-- 2023-12-08T07:24:01.210668Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541843,543609,TO_TIMESTAMP('2023-12-08 09:24:01.077','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Workflow Invoker',TO_TIMESTAMP('2023-12-08 09:24:01.077','YYYY-MM-DD HH24:MI:SS.US'),100,'INVOKER','WorkflowInvoker')
;

-- 2023-12-08T07:24:01.212804600Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543609 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Field: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> Check Supervisor
-- Column: C_Doc_Approval_Strategy_Line.SupervisorCheckStrategy
-- 2023-12-08T07:32:02.645069500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587715,723121,0,547340,TO_TIMESTAMP('2023-12-08 09:32:02.435','YYYY-MM-DD HH24:MI:SS.US'),100,1,'D','Y','N','N','N','N','N','N','N','Check Supervisor',TO_TIMESTAMP('2023-12-08 09:32:02.435','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-08T07:32:02.647661100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723121 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-08T07:32:02.649216800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582844) 
;

-- 2023-12-08T07:32:02.666196800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723121
;

-- 2023-12-08T07:32:02.667964600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723121)
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 10 -> main.Check Supervisor
-- Column: C_Doc_Approval_Strategy_Line.SupervisorCheckStrategy
-- 2023-12-08T07:36:59.546025100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723121,0,547340,551393,621970,'F',TO_TIMESTAMP('2023-12-08 09:36:59.33','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Check Supervisor',50,0,0,TO_TIMESTAMP('2023-12-08 09:36:59.33','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 10 -> main.Is Project Manager Set
-- Column: C_Doc_Approval_Strategy_Line.IsProjectManagerSet
-- 2023-12-08T07:37:48.419225500Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2023-12-08 09:37:48.418','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621964
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 10 -> main.Check Supervisor
-- Column: C_Doc_Approval_Strategy_Line.SupervisorCheckStrategy
-- 2023-12-08T07:37:53.141118600Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2023-12-08 09:37:53.14','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621970
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 10 -> main.Check Supervisor
-- Column: C_Doc_Approval_Strategy_Line.SupervisorCheckStrategy
-- 2023-12-08T07:42:47.892971300Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-12-08 09:42:47.892','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621970
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 10 -> main.Is Project Manager Set
-- Column: C_Doc_Approval_Strategy_Line.IsProjectManagerSet
-- 2023-12-08T07:42:47.901278300Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-12-08 09:42:47.901','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621964
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 10 -> amount.Währung
-- Column: C_Doc_Approval_Strategy_Line.C_Currency_ID
-- 2023-12-08T07:42:47.909917300Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-12-08 09:42:47.909','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621966
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 10 -> amount.Minimum Amt
-- Column: C_Doc_Approval_Strategy_Line.MinimumAmt
-- 2023-12-08T07:42:47.917701200Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-12-08 09:42:47.917','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621965
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 20 -> flags.Aktiv
-- Column: C_Doc_Approval_Strategy_Line.IsActive
-- 2023-12-08T07:42:47.926019700Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-12-08 09:42:47.925','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621967
;

-- Reference: C_Doc_Approval_Strategy_Line_Type
-- Value: SH
-- ValueName: SupervisorsHierarchy
-- 2023-12-08T07:44:37.507732Z
DELETE FROM  AD_Ref_List_Trl WHERE AD_Ref_List_ID=543601
;

-- 2023-12-08T07:44:37.512019400Z
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID=543601
;

-- Field: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> Check Supervisor
-- Column: C_Doc_Approval_Strategy_Line.SupervisorCheckStrategy
-- 2023-12-08T07:44:49.414354300Z
UPDATE AD_Field SET DisplayLogic='@Type/-@=INVOKER | @Type/-@=REQUESTOR | @Type/-@=PM',Updated=TO_TIMESTAMP('2023-12-08 09:44:49.414','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=723121
;

