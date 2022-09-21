-- 2022-09-15T07:48:38.100Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581460,0,'IsOverBudget',TO_TIMESTAMP('2022-09-15 10:48:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','Over budget','Over budget',TO_TIMESTAMP('2022-09-15 10:48:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T07:48:38.105Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581460 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: S_EffortControl.IsOverBudget
-- 2022-09-15T07:48:46.982Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584352,581460,0,20,542215,'IsOverBudget',TO_TIMESTAMP('2022-09-15 10:48:46','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.serviceprovider',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Over budget',0,0,TO_TIMESTAMP('2022-09-15 10:48:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-09-15T07:48:46.983Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584352 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-09-15T07:48:46.988Z
/* DDL */  select update_Column_Translation_From_AD_Element(581460) 
;

-- 2022-09-15T07:48:49.394Z
/* DDL */ SELECT public.db_alter_table('S_EffortControl','ALTER TABLE public.S_EffortControl ADD COLUMN IsOverBudget CHAR(1) DEFAULT ''N'' CHECK (IsOverBudget IN (''Y'',''N'')) NOT NULL')
;

-- Field: Effort control(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> Over budget
-- Column: S_EffortControl.IsOverBudget
-- 2022-09-15T07:49:09.223Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584352,707305,0,546631,TO_TIMESTAMP('2022-09-15 10:49:09','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Over budget',TO_TIMESTAMP('2022-09-15 10:49:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T07:49:09.228Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707305 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T07:49:09.236Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581460) 
;

-- 2022-09-15T07:49:09.241Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707305
;


-- 2022-09-15T07:49:09.242Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707305)
;

-- UI Element: Effort control(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> main -> 20 -> flags.Over budget
-- Column: S_EffortControl.IsOverBudget
-- 2022-09-15T07:49:31.343Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707305,0,546631,613007,549929,'F',TO_TIMESTAMP('2022-09-15 10:49:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Over budget',40,0,0,TO_TIMESTAMP('2022-09-15 10:49:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: S_EffortControl.Budget
-- 2022-09-15T07:49:52.997Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2022-09-15 10:49:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584343
;

-- Column: S_EffortControl.IsOverBudget
-- 2022-09-15T07:49:53.561Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2022-09-15 10:49:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584352
;

-- Column: S_EffortControl.IsIssueClosed
-- 2022-09-15T07:49:54.162Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2022-09-15 10:49:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584346
;

-- Column: S_EffortControl.IsProcessed
-- 2022-09-15T07:49:54.726Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2022-09-15 10:49:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584347
;

