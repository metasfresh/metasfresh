
-- 2024-10-11T07:09:52.476Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583328,0,'IsAveragePrice',TO_TIMESTAMP('2024-10-11 09:09:52.343','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Durchschnittspreis','Durchschnittspreis',TO_TIMESTAMP('2024-10-11 09:09:52.343','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-11T07:09:52.489Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583328 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsAveragePrice
-- 2024-10-11T07:10:10.395Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Average Price', PrintName='Average Price',Updated=TO_TIMESTAMP('2024-10-11 09:10:10.395','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583328 AND AD_Language='en_US'
;

-- 2024-10-11T07:10:10.409Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583328,'en_US')
;

-- Element: IsAveragePrice
-- 2024-10-11T07:10:11.041Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-11 09:10:11.041','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583328 AND AD_Language='de_CH'
;

-- 2024-10-11T07:10:11.045Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583328,'de_CH')
;

-- Element: IsAveragePrice
-- 2024-10-11T07:10:11.791Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-11 09:10:11.791','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583328 AND AD_Language='de_DE'
;

-- 2024-10-11T07:10:11.793Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583328,'de_DE')
;

-- 2024-10-11T07:10:11.801Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583328,'de_DE')
;

-- Column: ModCntr_Specific_Price.IsAveragePrice
-- 2024-10-11T07:18:49.758Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589303,583328,0,20,542405,'IsAveragePrice',TO_TIMESTAMP('2024-10-11 09:18:49.628','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','de.metas.contracts',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Durchschnittspreis',0,0,TO_TIMESTAMP('2024-10-11 09:18:49.628','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-10-11T07:18:49.763Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589303 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-11T07:18:49.770Z
/* DDL */  select update_Column_Translation_From_AD_Element(583328)
;

-- 2024-10-11T07:18:51.712Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Specific_Price','ALTER TABLE public.ModCntr_Specific_Price ADD COLUMN IsAveragePrice CHAR(1) DEFAULT ''N'' CHECK (IsAveragePrice IN (''Y'',''N'')) NOT NULL')
;
