-- Column: SUMUP_Transaction.SUMUP_CardReader_ID
-- Column: SUMUP_Transaction.SUMUP_CardReader_ID
-- 2024-10-10T12:32:28.558Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589297,583300,0,30,542443,'XX','SUMUP_CardReader_ID',TO_TIMESTAMP('2024-10-10 15:32:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','de.metas.payment.sumup',0,10,'E','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Card Reader',0,0,TO_TIMESTAMP('2024-10-10 15:32:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-10T12:32:28.568Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589297 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-10T12:32:28.610Z
/* DDL */  select update_Column_Translation_From_AD_Element(583300) 
;

-- 2024-10-10T12:32:33.689Z
/* DDL */ SELECT public.db_alter_table('SUMUP_Transaction','ALTER TABLE public.SUMUP_Transaction ADD COLUMN SUMUP_CardReader_ID NUMERIC(10)')
;

-- 2024-10-10T12:33:50.054Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583324,0,'SUMUP_CardReader_ExternalId',TO_TIMESTAMP('2024-10-10 15:33:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y','Card Reader External ID','Card Reader External ID',TO_TIMESTAMP('2024-10-10 15:33:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-10T12:33:50.059Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583324 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-10-10T12:34:25.246Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583325,0,'SUMUP_CardReader_Name',TO_TIMESTAMP('2024-10-10 15:34:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y','Card Reader Name','Card Reader Name',TO_TIMESTAMP('2024-10-10 15:34:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-10T12:34:25.248Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583325 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-10-10T12:34:33.475Z
INSERT INTO t_alter_column values('sumup_transaction','SUMUP_CardReader_ID','NUMERIC(10)',null,null)
;

-- Column: SUMUP_Transaction.SUMUP_CardReader_ExternalId
-- Column: SUMUP_Transaction.SUMUP_CardReader_ExternalId
-- 2024-10-10T12:34:48.828Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589298,583324,0,10,542443,'XX','SUMUP_CardReader_ExternalId',TO_TIMESTAMP('2024-10-10 15:34:48','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,255,'E','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Card Reader External ID',0,0,TO_TIMESTAMP('2024-10-10 15:34:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-10T12:34:48.831Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589298 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-10T12:34:48.837Z
/* DDL */  select update_Column_Translation_From_AD_Element(583324) 
;

-- 2024-10-10T12:34:52.175Z
/* DDL */ SELECT public.db_alter_table('SUMUP_Transaction','ALTER TABLE public.SUMUP_Transaction ADD COLUMN SUMUP_CardReader_ExternalId VARCHAR(255)')
;

-- Column: SUMUP_Transaction.SUMUP_CardReader_Name
-- Column: SUMUP_Transaction.SUMUP_CardReader_Name
-- 2024-10-10T12:35:08.598Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589299,583325,0,10,542443,'XX','SUMUP_CardReader_Name',TO_TIMESTAMP('2024-10-10 15:35:07','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Card Reader Name',0,0,TO_TIMESTAMP('2024-10-10 15:35:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-10T12:35:08.600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589299 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-10T12:35:08.605Z
/* DDL */  select update_Column_Translation_From_AD_Element(583325) 
;

-- 2024-10-10T12:35:09.626Z
/* DDL */ SELECT public.db_alter_table('SUMUP_Transaction','ALTER TABLE public.SUMUP_Transaction ADD COLUMN SUMUP_CardReader_Name VARCHAR(255)')
;

-- Field: SumUp Transaction -> SumUp Transaction -> Card Reader
-- Column: SUMUP_Transaction.SUMUP_CardReader_ID
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> Card Reader
-- Column: SUMUP_Transaction.SUMUP_CardReader_ID
-- 2024-10-10T12:35:31.628Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589297,731901,0,547617,TO_TIMESTAMP('2024-10-10 15:35:30','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','Card Reader',TO_TIMESTAMP('2024-10-10 15:35:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-10T12:35:31.632Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731901 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-10T12:35:31.637Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583300) 
;

-- 2024-10-10T12:35:31.656Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731901
;

-- 2024-10-10T12:35:31.662Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731901)
;

-- Field: SumUp Transaction -> SumUp Transaction -> Card Reader External ID
-- Column: SUMUP_Transaction.SUMUP_CardReader_ExternalId
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> Card Reader External ID
-- Column: SUMUP_Transaction.SUMUP_CardReader_ExternalId
-- 2024-10-10T12:35:31.850Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589298,731902,0,547617,TO_TIMESTAMP('2024-10-10 15:35:31','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','Card Reader External ID',TO_TIMESTAMP('2024-10-10 15:35:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-10T12:35:31.852Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731902 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-10T12:35:31.854Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583324) 
;

-- 2024-10-10T12:35:31.859Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731902
;

-- 2024-10-10T12:35:31.860Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731902)
;

-- Field: SumUp Transaction -> SumUp Transaction -> Card Reader Name
-- Column: SUMUP_Transaction.SUMUP_CardReader_Name
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> Card Reader Name
-- Column: SUMUP_Transaction.SUMUP_CardReader_Name
-- 2024-10-10T12:35:32.024Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589299,731903,0,547617,TO_TIMESTAMP('2024-10-10 15:35:31','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','Card Reader Name',TO_TIMESTAMP('2024-10-10 15:35:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-10T12:35:32.026Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731903 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-10T12:35:32.029Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583325) 
;

-- 2024-10-10T12:35:32.034Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731903
;

-- 2024-10-10T12:35:32.035Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731903)
;

-- UI Column: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 20
-- UI Element Group: card reader
-- 2024-10-10T12:37:39.105Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547590,552042,TO_TIMESTAMP('2024-10-10 15:37:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','card reader',15,TO_TIMESTAMP('2024-10-10 15:37:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Card Reader
-- Column: SUMUP_Transaction.SUMUP_CardReader_ID
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 20 -> card reader.Card Reader
-- Column: SUMUP_Transaction.SUMUP_CardReader_ID
-- 2024-10-10T12:37:59.207Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731901,0,547617,552042,626183,'F',TO_TIMESTAMP('2024-10-10 15:37:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Card Reader',10,0,0,TO_TIMESTAMP('2024-10-10 15:37:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Card Reader External ID
-- Column: SUMUP_Transaction.SUMUP_CardReader_ExternalId
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 20 -> card reader.Card Reader External ID
-- Column: SUMUP_Transaction.SUMUP_CardReader_ExternalId
-- 2024-10-10T12:38:06.778Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731902,0,547617,552042,626184,'F',TO_TIMESTAMP('2024-10-10 15:38:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Card Reader External ID',20,0,0,TO_TIMESTAMP('2024-10-10 15:38:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Card Reader Name
-- Column: SUMUP_Transaction.SUMUP_CardReader_Name
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 20 -> card reader.Card Reader Name
-- Column: SUMUP_Transaction.SUMUP_CardReader_Name
-- 2024-10-10T12:38:14.560Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731903,0,547617,552042,626185,'F',TO_TIMESTAMP('2024-10-10 15:38:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Card Reader Name',30,0,0,TO_TIMESTAMP('2024-10-10 15:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

