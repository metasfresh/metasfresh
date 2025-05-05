-- Run mode: SWING_CLIENT

-- 2024-04-23T12:35:19.870Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583082,0,'StorageCostStartDate',TO_TIMESTAMP('2024-04-23 14:35:19.658','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Storage Cost Start Date','Storage Cost Start Date',TO_TIMESTAMP('2024-04-23 14:35:19.658','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-23T12:35:19.876Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583082 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: StorageCostStartDate
-- 2024-04-23T12:35:26.907Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-04-23 14:35:26.907','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583082 AND AD_Language='en_US'
;

-- 2024-04-23T12:35:26.927Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583082,'en_US')
;

-- Element: StorageCostStartDate
-- 2024-04-23T12:35:50.445Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lagerkosten Startdatum', PrintName='Lagerkosten Startdatum',Updated=TO_TIMESTAMP('2024-04-23 14:35:50.445','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583082 AND AD_Language='de_CH'
;

-- 2024-04-23T12:35:50.447Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583082,'de_CH')
;

-- Element: StorageCostStartDate
-- 2024-04-23T12:35:57.149Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lagerkosten Startdatum', PrintName='Lagerkosten Startdatum',Updated=TO_TIMESTAMP('2024-04-23 14:35:57.149','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583082 AND AD_Language='de_DE'
;

-- 2024-04-23T12:35:57.150Z
UPDATE AD_Element SET Name='Lagerkosten Startdatum', PrintName='Lagerkosten Startdatum' WHERE AD_Element_ID=583082
;

-- 2024-04-23T12:35:57.389Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583082,'de_DE')
;

-- 2024-04-23T12:35:57.390Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583082,'de_DE')
;

-- Column: ModCntr_Settings.StorageCostStartDate
-- 2024-04-23T12:38:59.167Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588191,583082,0,15,542339,'StorageCostStartDate',TO_TIMESTAMP('2024-04-23 14:38:59.023','YYYY-MM-DD HH24:MI:SS.US'),100,'N','@#Date@','de.metas.contracts',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Lagerkosten Startdatum',0,0,TO_TIMESTAMP('2024-04-23 14:38:59.023','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-23T12:38:59.169Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588191 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-23T12:38:59.174Z
/* DDL */  select update_Column_Translation_From_AD_Element(583082)
;

-- Column: ModCntr_Settings.StorageCostStartDate
-- 2024-04-23T12:41:14.856Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2024-04-23 14:41:14.856','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588191
;

-- 2024-04-23T12:41:15.393Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Settings','ALTER TABLE public.ModCntr_Settings ADD COLUMN StorageCostStartDate TIMESTAMP WITHOUT TIME ZONE')
;

