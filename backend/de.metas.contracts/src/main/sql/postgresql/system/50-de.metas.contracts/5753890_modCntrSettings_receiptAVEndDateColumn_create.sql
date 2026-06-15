-- Run mode: SWING_CLIENT

-- 2025-05-08T08:55:48.320Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583615,0,'ReceiptAVEndDate',TO_TIMESTAMP('2025-05-08 10:55:48.069','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Frühablieferungsabzug Enddatum','Frühablieferungsabzug Enddatum',TO_TIMESTAMP('2025-05-08 10:55:48.069','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-08T08:55:48.383Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583615 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: ReceiptAVEndDate
-- 2025-05-08T08:57:45.693Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Receipt AV End Date', PrintName='Receipt AV End Date',Updated=TO_TIMESTAMP('2025-05-08 10:57:45.693','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583615 AND AD_Language='en_US'
;

-- 2025-05-08T08:57:45.706Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583615,'en_US')
;

-- Element: ReceiptAVEndDate
-- 2025-05-08T08:58:01.442Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-08 10:58:01.442','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583615 AND AD_Language='de_CH'
;

-- 2025-05-08T08:58:01.444Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583615,'de_CH')
;

-- Element: ReceiptAVEndDate
-- 2025-05-08T08:58:20.966Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-08 10:58:20.966','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583615 AND AD_Language='de_DE'
;

-- 2025-05-08T08:58:20.968Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583615,'de_DE')
;

-- 2025-05-08T08:58:20.970Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583615,'de_DE')
;

-- Column: ModCntr_Settings.ReceiptAVEndDate
-- 2025-05-08T08:59:55.137Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589958,583615,0,15,542339,'ReceiptAVEndDate',TO_TIMESTAMP('2025-05-08 10:59:54.98','YYYY-MM-DD HH24:MI:SS.US'),100,'N','@#Date@','de.metas.contracts',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Frühablieferungsabzug Enddatum',0,0,TO_TIMESTAMP('2025-05-08 10:59:54.98','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-08T08:59:55.144Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589958 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-08T08:59:55.152Z
/* DDL */  select update_Column_Translation_From_AD_Element(583615)
;

-- Column: ModCntr_Settings.ReceiptAVEndDate
-- 2025-05-08T09:11:23.051Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-05-08 11:11:23.051','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589958
;

-- 2025-05-08T09:11:25.237Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Settings','ALTER TABLE public.ModCntr_Settings ADD COLUMN ReceiptAVEndDate TIMESTAMP WITHOUT TIME ZONE')
;
