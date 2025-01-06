-- Run mode: SWING_CLIENT

-- 2024-04-24T07:07:57.855Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583088,0,'StorageDays',TO_TIMESTAMP('2024-04-24 09:07:57.701','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Lagertage','Lagertage',TO_TIMESTAMP('2024-04-24 09:07:57.701','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-24T07:07:57.862Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583088 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: StorageDays
-- 2024-04-24T07:08:04.893Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-04-24 09:08:04.893','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583088 AND AD_Language='de_DE'
;

-- 2024-04-24T07:08:04.910Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583088,'de_DE')
;

-- 2024-04-24T07:08:04.913Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583088,'de_DE')
;

-- Element: StorageDays
-- 2024-04-24T07:08:10.406Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-04-24 09:08:10.406','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583088 AND AD_Language='de_CH'
;

-- 2024-04-24T07:08:10.408Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583088,'de_CH')
;

-- Element: StorageDays
-- 2024-04-24T10:09:41.364Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Storage days', PrintName='Storage days',Updated=TO_TIMESTAMP('2024-04-24 12:09:41.364','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583088 AND AD_Language='en_US'
;

-- 2024-04-24T10:09:41.367Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583088,'en_US')
;

-- Column: ModCntr_Log.StorageDays
-- 2024-04-24T11:02:53.617Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588198,583088,0,11,542338,'StorageDays',TO_TIMESTAMP('2024-04-24 13:02:53.419','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','de.metas.contracts',0,3,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lagertage',0,0,TO_TIMESTAMP('2024-04-24 13:02:53.419','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-24T11:02:53.621Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588198 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-24T11:02:53.625Z
/* DDL */  select update_Column_Translation_From_AD_Element(583088)
;

-- 2024-04-24T11:02:57.097Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Log','ALTER TABLE public.ModCntr_Log ADD COLUMN StorageDays NUMERIC(10)')
;

-- Field: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> Lagertage
-- Column: ModCntr_Log.StorageDays
-- 2024-04-24T11:12:43.037Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588198,728054,0,547012,0,TO_TIMESTAMP('2024-04-24 13:12:42.892','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Lagertage',0,30,0,1,1,TO_TIMESTAMP('2024-04-24 13:12:42.892','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-24T11:12:43.039Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728054 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-24T11:12:43.041Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583088)
;

-- 2024-04-24T11:12:43.048Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728054
;

-- 2024-04-24T11:12:43.049Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728054)
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> dates.Lagertage
-- Column: ModCntr_Log.StorageDays
-- 2024-04-24T11:13:31.714Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728054,0,547012,550779,624526,'F',TO_TIMESTAMP('2024-04-24 13:13:31.576','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Lagertage',20,0,0,TO_TIMESTAMP('2024-04-24 13:13:31.576','YYYY-MM-DD HH24:MI:SS.US'),100)
;

