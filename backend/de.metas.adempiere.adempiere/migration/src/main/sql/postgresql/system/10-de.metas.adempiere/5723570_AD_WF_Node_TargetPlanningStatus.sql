-- 2024-05-13T12:41:29.057Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583106,0,'TargetPlanningStatus',TO_TIMESTAMP('2024-05-13 15:41:28','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Target Planning status','Target Planning status',TO_TIMESTAMP('2024-05-13 15:41:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-05-13T12:41:29.072Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583106 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: PP_Order_TargetPlanningStatus
-- 2024-05-13T12:42:32.545Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541869,TO_TIMESTAMP('2024-05-13 15:42:32','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','PP_Order_TargetPlanningStatus',TO_TIMESTAMP('2024-05-13 15:42:32','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2024-05-13T12:42:32.549Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541869 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: PP_Order_TargetPlanningStatus
-- Value: C
-- ValueName: Complete
-- 2024-05-13T12:42:54.685Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541869,543680,TO_TIMESTAMP('2024-05-13 15:42:54','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Fertig',TO_TIMESTAMP('2024-05-13 15:42:54','YYYY-MM-DD HH24:MI:SS'),100,'C','Complete')
;

-- 2024-05-13T12:42:54.688Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543680 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: PP_Order_TargetPlanningStatus
-- Value: R
-- ValueName: Review
-- 2024-05-13T12:43:10.188Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541869,543681,TO_TIMESTAMP('2024-05-13 15:43:09','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Prüfung',TO_TIMESTAMP('2024-05-13 15:43:09','YYYY-MM-DD HH24:MI:SS'),100,'R','Review')
;

-- 2024-05-13T12:43:10.190Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543681 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: PP_Order_TargetPlanningStatus -> C_Complete
-- 2024-05-13T12:43:45.678Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-13 15:43:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543680
;

-- Reference Item: PP_Order_TargetPlanningStatus -> C_Complete
-- 2024-05-13T12:43:47.179Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-13 15:43:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543680
;

-- Reference Item: PP_Order_TargetPlanningStatus -> C_Complete
-- 2024-05-13T12:43:51.171Z
UPDATE AD_Ref_List_Trl SET Name='Complete',Updated=TO_TIMESTAMP('2024-05-13 15:43:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543680
;

-- Reference Item: PP_Order_TargetPlanningStatus -> C_Complete
-- 2024-05-13T12:43:53.147Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-13 15:43:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543680
;

-- Reference Item: PP_Order_TargetPlanningStatus -> R_Review
-- 2024-05-13T12:44:01.860Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Review',Updated=TO_TIMESTAMP('2024-05-13 15:44:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543681
;

-- Reference Item: PP_Order_TargetPlanningStatus -> R_Review
-- 2024-05-13T12:44:02.729Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-13 15:44:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543681
;

-- Reference Item: PP_Order_TargetPlanningStatus -> R_Review
-- 2024-05-13T12:44:04.062Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-13 15:44:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543681
;

-- Column: AD_WF_Node.TargetPlanningStatus
-- Column: AD_WF_Node.TargetPlanningStatus
-- 2024-05-13T12:44:31.339Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588260,583106,0,17,541869,129,'TargetPlanningStatus',TO_TIMESTAMP('2024-05-13 15:44:31','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Target Planning status',0,0,TO_TIMESTAMP('2024-05-13 15:44:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-05-13T12:44:31.343Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588260 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-13T12:44:31.382Z
/* DDL */  select update_Column_Translation_From_AD_Element(583106) 
;

-- 2024-05-13T12:44:32.672Z
/* DDL */ SELECT public.db_alter_table('AD_WF_Node','ALTER TABLE public.AD_WF_Node ADD COLUMN TargetPlanningStatus CHAR(1)')
;

-- Field: Produktion Arbeitsablauf -> Arbeitsschritt -> Target Planning status
-- Column: AD_WF_Node.TargetPlanningStatus
-- Field: Produktion Arbeitsablauf(53005,EE01) -> Arbeitsschritt(53025,EE01) -> Target Planning status
-- Column: AD_WF_Node.TargetPlanningStatus
-- 2024-05-13T12:45:09.449Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588260,728703,0,53025,TO_TIMESTAMP('2024-05-13 15:45:09','YYYY-MM-DD HH24:MI:SS'),100,1,'EE01','Y','N','N','N','N','N','N','N','Target Planning status',TO_TIMESTAMP('2024-05-13 15:45:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-05-13T12:45:09.453Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=728703 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-13T12:45:09.457Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583106) 
;

-- 2024-05-13T12:45:09.474Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728703
;

-- 2024-05-13T12:45:09.479Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728703)
;

-- UI Element: Produktion Arbeitsablauf -> Arbeitsschritt.Target Planning status
-- Column: AD_WF_Node.TargetPlanningStatus
-- UI Element: Produktion Arbeitsablauf(53005,EE01) -> Arbeitsschritt(53025,EE01) -> main -> 10 -> default.Target Planning status
-- Column: AD_WF_Node.TargetPlanningStatus
-- 2024-05-13T12:45:47.280Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728703,0,53025,540370,624733,'F',TO_TIMESTAMP('2024-05-13 15:45:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Target Planning status',170,0,0,TO_TIMESTAMP('2024-05-13 15:45:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produktion Arbeitsablauf -> Arbeitsschritt.Target Planning status
-- Column: AD_WF_Node.TargetPlanningStatus
-- UI Element: Produktion Arbeitsablauf(53005,EE01) -> Arbeitsschritt(53025,EE01) -> main -> 10 -> default.Target Planning status
-- Column: AD_WF_Node.TargetPlanningStatus
-- 2024-05-13T12:45:55.930Z
UPDATE AD_UI_Element SET SeqNo=145,Updated=TO_TIMESTAMP('2024-05-13 15:45:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624733
;

-- Field: Produktion Arbeitsablauf -> Arbeitsschritt -> Target Planning status
-- Column: AD_WF_Node.TargetPlanningStatus
-- Field: Produktion Arbeitsablauf(53005,EE01) -> Arbeitsschritt(53025,EE01) -> Target Planning status
-- Column: AD_WF_Node.TargetPlanningStatus
-- 2024-05-13T12:46:46.337Z
UPDATE AD_Field SET DisplayLogic='@PP_Activity_Type/XX@=AC',Updated=TO_TIMESTAMP('2024-05-13 15:46:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=728703
;

-- Field: Produktion Arbeitsablauf -> Arbeitsschritt -> Target Planning status
-- Column: AD_WF_Node.TargetPlanningStatus
-- Field: Produktion Arbeitsablauf(53005,EE01) -> Arbeitsschritt(53025,EE01) -> Target Planning status
-- Column: AD_WF_Node.TargetPlanningStatus
-- 2024-05-13T12:46:52.489Z
UPDATE AD_Field SET DisplayLogic='@PP_Activity_Type/-@=AC',Updated=TO_TIMESTAMP('2024-05-13 15:46:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=728703
;

