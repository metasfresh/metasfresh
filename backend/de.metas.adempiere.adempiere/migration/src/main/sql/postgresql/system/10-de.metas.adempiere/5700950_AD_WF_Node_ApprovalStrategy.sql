-- 2023-08-31T06:40:36.425349700Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582671,0,'ApprovalStrategy',TO_TIMESTAMP('2023-08-31 09:40:36.085','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Approval Strategy','Approval Strategy',TO_TIMESTAMP('2023-08-31 09:40:36.085','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T06:40:36.800279700Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582671 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: ApprovalStrategy
-- 2023-08-31T06:43:52.637608Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541818,TO_TIMESTAMP('2023-08-31 09:43:52.472','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','ApprovalStrategy',TO_TIMESTAMP('2023-08-31 09:43:52.472','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;

-- 2023-08-31T06:43:52.639691Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541818 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: ApprovalStrategy
-- Value: STD
-- ValueName: Standard
-- 2023-08-31T06:44:26.618038Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541818,543543,TO_TIMESTAMP('2023-08-31 09:44:26.46','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Standard',TO_TIMESTAMP('2023-08-31 09:44:26.46','YYYY-MM-DD HH24:MI:SS.US'),100,'STD','Standard')
;

-- 2023-08-31T06:44:26.619607800Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543543 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: ApprovalStrategy
-- Value: RH+PM+CTO
-- ValueName: Requestor Hierarcy / Project Manager + CTO
-- 2023-08-31T06:45:09.160940100Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541818,543544,TO_TIMESTAMP('2023-08-31 09:45:09.005','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Requestor Hierarcy / Project Manager + CTO',TO_TIMESTAMP('2023-08-31 09:45:09.005','YYYY-MM-DD HH24:MI:SS.US'),100,'RH+PM+CTO','Requestor Hierarcy / Project Manager + CTO')
;

-- 2023-08-31T06:45:09.163029600Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543544 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: AD_WF_Node.ApprovalStrategy
-- 2023-08-31T06:45:43.498693600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587351,582671,0,17,541818,129,'ApprovalStrategy',TO_TIMESTAMP('2023-08-31 09:45:43.294','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,40,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Approval Strategy',0,0,TO_TIMESTAMP('2023-08-31 09:45:43.294','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-31T06:45:43.501306Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587351 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-31T06:45:44.069529100Z
/* DDL */  select update_Column_Translation_From_AD_Element(582671) 
;

-- 2023-08-31T06:45:45.506308100Z
/* DDL */ SELECT public.db_alter_table('AD_WF_Node','ALTER TABLE public.AD_WF_Node ADD COLUMN ApprovalStrategy VARCHAR(40)')
;

-- Field: Workflow(113,D) -> Knoten(122,D) -> Approval Strategy
-- Column: AD_WF_Node.ApprovalStrategy
-- 2023-08-31T07:58:23.991276100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587351,720322,0,122,TO_TIMESTAMP('2023-08-31 10:58:23.68','YYYY-MM-DD HH24:MI:SS.US'),100,40,'D','Y','N','N','N','N','N','N','N','Approval Strategy',TO_TIMESTAMP('2023-08-31 10:58:23.68','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T07:58:23.993388900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720322 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T07:58:23.995999Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582671) 
;

-- 2023-08-31T07:58:23.999652100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720322
;

-- 2023-08-31T07:58:24.001246600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720322)
;

-- Field: Workflow(113,D) -> Knoten(122,D) -> Approval Strategy
-- Column: AD_WF_Node.ApprovalStrategy
-- 2023-08-31T08:01:42.056207800Z
UPDATE AD_Field SET DisplayLogic='@Action/-@=C', IsDisplayed='Y', SeqNo=215,Updated=TO_TIMESTAMP('2023-08-31 11:01:42.055','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720322
;

-- Field: Workflow(113,D) -> Knoten(122,D) -> Approval Strategy
-- Column: AD_WF_Node.ApprovalStrategy
-- 2023-08-31T08:01:46.813630Z
UPDATE AD_Field SET DisplayLength=10,Updated=TO_TIMESTAMP('2023-08-31 11:01:46.813','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720322
;

