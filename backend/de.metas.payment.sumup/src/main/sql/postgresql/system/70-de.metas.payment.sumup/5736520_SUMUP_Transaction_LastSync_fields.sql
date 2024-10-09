-- 2024-10-09T19:05:55.018Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583320,0,'SUMUP_LastSync_Status',TO_TIMESTAMP('2024-10-09 22:05:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y','Last Sync Status','Last Sync Status',TO_TIMESTAMP('2024-10-09 22:05:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T19:05:55.021Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583320 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-10-09T19:06:20.560Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583321,0,'SUMUP_LastSync_Error_ID',TO_TIMESTAMP('2024-10-09 22:06:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y','Last Sync Error','Last Sync Error',TO_TIMESTAMP('2024-10-09 22:06:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T19:06:20.563Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583321 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: SUMUP_LastSync_Status
-- 2024-10-09T19:07:42.682Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541901,TO_TIMESTAMP('2024-10-09 22:07:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y','N','SUMUP_LastSync_Status',TO_TIMESTAMP('2024-10-09 22:07:42','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2024-10-09T19:07:42.685Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541901 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: SUMUP_LastSync_Status
-- Value: OK
-- ValueName: OK
-- 2024-10-09T19:08:15.039Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541901,543758,TO_TIMESTAMP('2024-10-09 22:08:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y','OK',TO_TIMESTAMP('2024-10-09 22:08:14','YYYY-MM-DD HH24:MI:SS'),100,'OK','OK')
;

-- 2024-10-09T19:08:15.042Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543758 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: SUMUP_LastSync_Status
-- Value: ERR
-- ValueName: Error
-- 2024-10-09T19:08:31.353Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541901,543759,TO_TIMESTAMP('2024-10-09 22:08:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y','Error',TO_TIMESTAMP('2024-10-09 22:08:31','YYYY-MM-DD HH24:MI:SS'),100,'ERR','Error')
;

-- 2024-10-09T19:08:31.355Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543759 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: SUMUP_Transaction.SUMUP_LastSync_Status
-- Column: SUMUP_Transaction.SUMUP_LastSync_Status
-- 2024-10-09T19:09:34.753Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589292,583320,0,17,541901,542443,'XX','SUMUP_LastSync_Status',TO_TIMESTAMP('2024-10-09 22:09:34','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,3,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Last Sync Status',0,0,TO_TIMESTAMP('2024-10-09 22:09:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-09T19:09:34.756Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589292 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-09T19:09:34.759Z
/* DDL */  select update_Column_Translation_From_AD_Element(583320) 
;

-- 2024-10-09T19:09:36.335Z
/* DDL */ SELECT public.db_alter_table('SUMUP_Transaction','ALTER TABLE public.SUMUP_Transaction ADD COLUMN SUMUP_LastSync_Status VARCHAR(3)')
;

-- Column: SUMUP_Transaction.SUMUP_LastSync_Error_ID
-- Column: SUMUP_Transaction.SUMUP_LastSync_Error_ID
-- 2024-10-09T19:10:00.345Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589293,583321,0,30,540991,542443,'XX','SUMUP_LastSync_Error_ID',TO_TIMESTAMP('2024-10-09 22:10:00','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Last Sync Error',0,0,TO_TIMESTAMP('2024-10-09 22:10:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-09T19:10:00.348Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589293 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-09T19:10:00.353Z
/* DDL */  select update_Column_Translation_From_AD_Element(583321) 
;

-- 2024-10-09T19:10:00.948Z
/* DDL */ SELECT public.db_alter_table('SUMUP_Transaction','ALTER TABLE public.SUMUP_Transaction ADD COLUMN SUMUP_LastSync_Error_ID NUMERIC(10)')
;

-- 2024-10-09T19:10:00.956Z
ALTER TABLE SUMUP_Transaction ADD CONSTRAINT SUMUPLastSyncError_SUMUPTransaction FOREIGN KEY (SUMUP_LastSync_Error_ID) REFERENCES public.AD_Issue DEFERRABLE INITIALLY DEFERRED
;

-- 2024-10-09T19:10:35.903Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583322,0,'SUMUP_LastSync_Timestamp',TO_TIMESTAMP('2024-10-09 22:10:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y','Last Sync Timestamp','Last Sync Timestamp',TO_TIMESTAMP('2024-10-09 22:10:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T19:10:35.906Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583322 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: SUMUP_Transaction.SUMUP_LastSync_Timestamp
-- Column: SUMUP_Transaction.SUMUP_LastSync_Timestamp
-- 2024-10-09T19:11:09.420Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589294,583322,0,16,542443,'XX','SUMUP_LastSync_Timestamp',TO_TIMESTAMP('2024-10-09 22:11:09','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.payment.sumup',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Last Sync Timestamp',0,0,TO_TIMESTAMP('2024-10-09 22:11:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-09T19:11:09.422Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589294 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-09T19:11:09.425Z
/* DDL */  select update_Column_Translation_From_AD_Element(583322) 
;

-- 2024-10-09T19:11:10.016Z
/* DDL */ SELECT public.db_alter_table('SUMUP_Transaction','ALTER TABLE public.SUMUP_Transaction ADD COLUMN SUMUP_LastSync_Timestamp TIMESTAMP WITH TIME ZONE')
;

-- UI Column: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 20
-- UI Element Group: sync
-- 2024-10-09T19:11:43.857Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547590,552041,TO_TIMESTAMP('2024-10-09 22:11:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','sync',980,TO_TIMESTAMP('2024-10-09 22:11:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: SumUp Transaction -> SumUp Transaction -> Last Sync Status
-- Column: SUMUP_Transaction.SUMUP_LastSync_Status
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> Last Sync Status
-- Column: SUMUP_Transaction.SUMUP_LastSync_Status
-- 2024-10-09T19:12:01.393Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589292,731896,0,547617,TO_TIMESTAMP('2024-10-09 22:12:01','YYYY-MM-DD HH24:MI:SS'),100,3,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','Last Sync Status',TO_TIMESTAMP('2024-10-09 22:12:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T19:12:01.397Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731896 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-09T19:12:01.401Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583320) 
;

-- 2024-10-09T19:12:01.406Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731896
;

-- 2024-10-09T19:12:01.410Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731896)
;

-- Field: SumUp Transaction -> SumUp Transaction -> Last Sync Error
-- Column: SUMUP_Transaction.SUMUP_LastSync_Error_ID
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> Last Sync Error
-- Column: SUMUP_Transaction.SUMUP_LastSync_Error_ID
-- 2024-10-09T19:12:01.554Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589293,731897,0,547617,TO_TIMESTAMP('2024-10-09 22:12:01','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','Last Sync Error',TO_TIMESTAMP('2024-10-09 22:12:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T19:12:01.556Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731897 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-09T19:12:01.559Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583321) 
;

-- 2024-10-09T19:12:01.561Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731897
;

-- 2024-10-09T19:12:01.562Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731897)
;

-- Field: SumUp Transaction -> SumUp Transaction -> Last Sync Timestamp
-- Column: SUMUP_Transaction.SUMUP_LastSync_Timestamp
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> Last Sync Timestamp
-- Column: SUMUP_Transaction.SUMUP_LastSync_Timestamp
-- 2024-10-09T19:12:01.678Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589294,731898,0,547617,TO_TIMESTAMP('2024-10-09 22:12:01','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','Last Sync Timestamp',TO_TIMESTAMP('2024-10-09 22:12:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T19:12:01.679Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731898 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-09T19:12:01.681Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583322) 
;

-- 2024-10-09T19:12:01.686Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731898
;

-- 2024-10-09T19:12:01.687Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731898)
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Last Sync Status
-- Column: SUMUP_Transaction.SUMUP_LastSync_Status
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 20 -> sync.Last Sync Status
-- Column: SUMUP_Transaction.SUMUP_LastSync_Status
-- 2024-10-09T19:12:20.117Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731896,0,547617,552041,626178,'F',TO_TIMESTAMP('2024-10-09 22:12:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Last Sync Status',10,0,0,TO_TIMESTAMP('2024-10-09 22:12:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Last Sync Timestamp
-- Column: SUMUP_Transaction.SUMUP_LastSync_Timestamp
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 20 -> sync.Last Sync Timestamp
-- Column: SUMUP_Transaction.SUMUP_LastSync_Timestamp
-- 2024-10-09T19:12:28.151Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731898,0,547617,552041,626179,'F',TO_TIMESTAMP('2024-10-09 22:12:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Last Sync Timestamp',20,0,0,TO_TIMESTAMP('2024-10-09 22:12:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Last Sync Error
-- Column: SUMUP_Transaction.SUMUP_LastSync_Error_ID
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 20 -> sync.Last Sync Error
-- Column: SUMUP_Transaction.SUMUP_LastSync_Error_ID
-- 2024-10-09T19:12:35Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731897,0,547617,552041,626180,'F',TO_TIMESTAMP('2024-10-09 22:12:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Last Sync Error',30,0,0,TO_TIMESTAMP('2024-10-09 22:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: SumUp Transaction -> SumUp Transaction -> Last Sync Error
-- Column: SUMUP_Transaction.SUMUP_LastSync_Error_ID
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> Last Sync Error
-- Column: SUMUP_Transaction.SUMUP_LastSync_Error_ID
-- 2024-10-09T19:13:06.930Z
UPDATE AD_Field SET DisplayLogic='@SUMUP_LastSync_Error_ID/0@>0',Updated=TO_TIMESTAMP('2024-10-09 22:13:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731897
;

-- Column: SUMUP_Transaction.SUMUP_LastSync_Status
-- Column: SUMUP_Transaction.SUMUP_LastSync_Status
-- 2024-10-09T19:42:03.599Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-09 22:42:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589292
;

