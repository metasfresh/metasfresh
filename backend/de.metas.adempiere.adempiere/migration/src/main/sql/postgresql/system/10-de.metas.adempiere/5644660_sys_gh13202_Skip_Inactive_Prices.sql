-- 2022-06-22T07:33:17.431Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581054,0,'IsSkipInactivePrices',TO_TIMESTAMP('2022-06-22 08:33:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Inaktive Preise überspringen','Inaktive Preise überspringen',TO_TIMESTAMP('2022-06-22 08:33:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-22T07:33:18.051Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581054 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-06-22T07:33:58.507Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Skip Inactive Prices', PrintName='Skip Inactive Prices',Updated=TO_TIMESTAMP('2022-06-22 08:33:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581054 AND AD_Language='en_US'
;

-- 2022-06-22T07:33:58.610Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581054,'en_US') 
;

-- 2022-06-22T07:36:29.816Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583468,581054,0,20,475,'IsSkipInactivePrices',TO_TIMESTAMP('2022-06-22 08:36:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Inaktive Preise überspringen',0,0,TO_TIMESTAMP('2022-06-22 08:36:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-22T07:36:30.145Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583468 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-22T07:36:30.352Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(581054) 
;

-- 2022-06-22T07:36:45.183Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_DiscountSchema','ALTER TABLE public.M_DiscountSchema ADD COLUMN IsSkipInactivePrices CHAR(1) DEFAULT ''Y'' CHECK (IsSkipInactivePrices IN (''Y'',''N'')) NOT NULL')
;

-- 2022-06-22T07:42:06.221Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583468,700733,0,675,0,TO_TIMESTAMP('2022-06-22 08:42:04','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Inaktive Preise überspringen',0,100,0,1,1,TO_TIMESTAMP('2022-06-22 08:42:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-22T07:42:06.647Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=700733 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-22T07:42:06.734Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581054) 
;

-- 2022-06-22T07:42:06.812Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=700733
;

-- 2022-06-22T07:42:06.880Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(700733)
;

-- 2022-06-22T07:52:22.567Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,700733,0,675,540843,609615,'F',TO_TIMESTAMP('2022-06-22 08:52:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Inaktive Preise überspringen',30,0,0,TO_TIMESTAMP('2022-06-22 08:52:21','YYYY-MM-DD HH24:MI:SS'),100)
;

