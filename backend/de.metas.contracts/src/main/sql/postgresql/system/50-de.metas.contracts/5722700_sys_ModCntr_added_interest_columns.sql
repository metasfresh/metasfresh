-- Run mode: SWING_CLIENT

-- 2024-04-24T07:30:55.981Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583089,0,'AddInterestDays',TO_TIMESTAMP('2024-04-24 10:30:55.704','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Zusätzlicher Zinstage','Zusätzlicher Zinstage',TO_TIMESTAMP('2024-04-24 10:30:55.704','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-24T07:30:56.002Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583089 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: AddInterestDays
-- 2024-04-24T07:31:45.497Z
UPDATE AD_Element_Trl SET Name='Additional interest days', PrintName='Additional interest days',Updated=TO_TIMESTAMP('2024-04-24 10:31:45.497','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583089 AND AD_Language='en_US'
;

-- 2024-04-24T07:31:45.532Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583089,'en_US')
;

-- Element: InterestAmt
-- 2024-04-24T07:34:40.400Z
UPDATE AD_Element_Trl SET Description='Zinsbetrag', Name='Zinsbetrag', PrintName='Zinsbetrag',Updated=TO_TIMESTAMP('2024-04-24 10:34:40.4','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=1457 AND AD_Language='de_DE'
;

-- 2024-04-24T07:34:40.401Z
UPDATE AD_Element SET Description='Zinsbetrag', Name='Zinsbetrag', PrintName='Zinsbetrag' WHERE AD_Element_ID=1457
;

-- 2024-04-24T07:34:40.654Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(1457,'de_DE')
;

-- 2024-04-24T07:34:40.655Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1457,'de_DE')
;

-- Element: InterestAmt
-- 2024-04-24T07:35:10.600Z
UPDATE AD_Element_Trl SET Description='Zinsbetrag', Name='Zinsbetrag', PrintName='Zinsbetrag',Updated=TO_TIMESTAMP('2024-04-24 10:35:10.6','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=1457 AND AD_Language='de_CH'
;

-- 2024-04-24T07:35:10.601Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1457,'de_CH')
;

-- Element: InterestAmt
-- 2024-04-24T07:35:13.467Z
UPDATE AD_Element_Trl SET Help='',Updated=TO_TIMESTAMP('2024-04-24 10:35:13.467','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=1457 AND AD_Language='de_DE'
;

-- 2024-04-24T07:35:13.468Z
UPDATE AD_Element SET Help='' WHERE AD_Element_ID=1457
;

-- 2024-04-24T07:35:13.746Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(1457,'de_DE')
;

-- 2024-04-24T07:35:13.748Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1457,'de_DE')
;

-- Element: InterestAmt
-- 2024-04-24T07:35:21.202Z
UPDATE AD_Element_Trl SET Help='',Updated=TO_TIMESTAMP('2024-04-24 10:35:21.202','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=1457 AND AD_Language='de_CH'
;

-- 2024-04-24T07:35:21.204Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1457,'de_CH')
;

-- 2024-04-24T07:37:15.172Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583090,0,'InterestRate',TO_TIMESTAMP('2024-04-24 10:37:15.052','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Zinssatz','Zinssatz',TO_TIMESTAMP('2024-04-24 10:37:15.052','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-24T07:37:15.173Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583090 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: InterestRate
-- 2024-04-24T07:38:23.101Z
UPDATE AD_Element_Trl SET Name='Interest rate', PrintName='Interest rate',Updated=TO_TIMESTAMP('2024-04-24 10:38:23.101','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583090 AND AD_Language='en_US'
;

-- 2024-04-24T07:38:23.102Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583090,'en_US')
;

-- Column: ModCntr_Settings.InterestRate
-- 2024-04-24T07:40:58.300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,ValueMax,ValueMin,Version) VALUES (0,588193,583090,0,22,542339,'InterestRate',TO_TIMESTAMP('2024-04-24 10:40:58.142','YYYY-MM-DD HH24:MI:SS.US'),100,'N','0','de.metas.contracts',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Zinssatz',0,0,TO_TIMESTAMP('2024-04-24 10:40:58.142','YYYY-MM-DD HH24:MI:SS.US'),100,'','',0)
;

-- 2024-04-24T07:40:58.302Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588193 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-24T07:40:58.304Z
/* DDL */  select update_Column_Translation_From_AD_Element(583090)
;

-- 2024-04-24T07:41:00.488Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Settings','ALTER TABLE public.ModCntr_Settings ADD COLUMN InterestRate NUMERIC DEFAULT 0 NOT NULL')
;

-- Column: ModCntr_Settings.AddInterestDays
-- 2024-04-24T07:41:46.919Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588194,583089,0,22,542339,'AddInterestDays',TO_TIMESTAMP('2024-04-24 10:41:46.798','YYYY-MM-DD HH24:MI:SS.US'),100,'N','30','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Zusätzlicher Zinstage',0,0,TO_TIMESTAMP('2024-04-24 10:41:46.798','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-24T07:41:46.920Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588194 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-24T07:41:46.922Z
/* DDL */  select update_Column_Translation_From_AD_Element(583089)
;

-- 2024-04-24T07:41:49.228Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Settings','ALTER TABLE public.ModCntr_Settings ADD COLUMN AddInterestDays NUMERIC DEFAULT 30 NOT NULL')
;

-- Column: ModCntr_InvoicingGroup.C_Harvesting_Calendar_ID
-- 2024-04-24T08:02:28.399Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588195,581157,0,30,540260,542359,'C_Harvesting_Calendar_ID',TO_TIMESTAMP('2024-04-24 11:02:28.26','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Erntekalender',0,0,TO_TIMESTAMP('2024-04-24 11:02:28.26','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-24T08:02:28.400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588195 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-24T08:02:28.402Z
/* DDL */  select update_Column_Translation_From_AD_Element(581157)
;

-- Column: ModCntr_InvoicingGroup.C_Harvesting_Calendar_ID
-- 2024-04-24T08:02:32.715Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-04-24 11:02:32.715','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588195
;

-- 2024-04-24T08:02:34.371Z
/* DDL */ SELECT public.db_alter_table('ModCntr_InvoicingGroup','ALTER TABLE public.ModCntr_InvoicingGroup ADD COLUMN C_Harvesting_Calendar_ID NUMERIC(10)')
;


UPDATE modcntr_invoicinggroup
SET c_harvesting_calendar_id=1000000
;

COMMIT
;

-- 2024-04-01T12:24:46.186Z
INSERT INTO t_alter_column values('ModCntr_InvoicingGroup','C_Harvesting_Calendar_ID','NUMERIC(10)',null,null)
;

-- 2024-04-01T12:24:46.186Z
INSERT INTO t_alter_column values('ModCntr_InvoicingGroup','C_Harvesting_Calendar_ID',null,'NOT NULL',null)
;

-- 2024-04-24T08:02:34.441Z
ALTER TABLE ModCntr_InvoicingGroup ADD CONSTRAINT CHarvestingCalendar_ModCntrInvoicingGroup FOREIGN KEY (C_Harvesting_Calendar_ID) REFERENCES public.C_Calendar DEFERRABLE INITIALLY DEFERRED
;

-- Column: ModCntr_InvoicingGroup.Harvesting_Year_ID
-- 2024-04-24T08:03:51.662Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588196,582471,0,30,540133,542359,'Harvesting_Year_ID',TO_TIMESTAMP('2024-04-24 11:03:51.51','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Erntejahr',0,0,TO_TIMESTAMP('2024-04-24 11:03:51.51','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-24T08:03:51.663Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588196 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-24T08:03:51.665Z
/* DDL */  select update_Column_Translation_From_AD_Element(582471)
;

-- 2024-04-24T08:03:54.065Z
/* DDL */ SELECT public.db_alter_table('ModCntr_InvoicingGroup','ALTER TABLE public.ModCntr_InvoicingGroup ADD COLUMN Harvesting_Year_ID NUMERIC(10)')
;

UPDATE modcntr_invoicinggroup
SET harvesting_year_id=540017
;

COMMIT
;

-- 2024-04-01T12:24:46.186Z
INSERT INTO t_alter_column values('ModCntr_InvoicingGroup','Harvesting_Year_ID','NUMERIC(10)',null,null)
;

-- 2024-04-01T12:24:46.186Z
INSERT INTO t_alter_column values('ModCntr_InvoicingGroup','Harvesting_Year_ID',null,'NOT NULL',null)
;

-- 2024-04-24T08:03:54.135Z
ALTER TABLE ModCntr_InvoicingGroup ADD CONSTRAINT HarvestingYear_ModCntrInvoicingGroup FOREIGN KEY (Harvesting_Year_ID) REFERENCES public.C_Year DEFERRABLE INITIALLY DEFERRED
;

-- 2024-04-24T08:52:06.572Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583091,0,'TotalInterest',TO_TIMESTAMP('2024-04-24 11:52:06.324','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Total Zins','Total Zins',TO_TIMESTAMP('2024-04-24 11:52:06.324','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-24T08:52:06.574Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583091 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: TotalInterest
-- 2024-04-24T08:52:16.256Z
UPDATE AD_Element_Trl SET Name='Total interest', PrintName='Total interest',Updated=TO_TIMESTAMP('2024-04-24 11:52:16.256','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583091 AND AD_Language='en_US'
;

-- 2024-04-24T08:52:16.258Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583091,'en_US')
;

-- Column: ModCntr_InvoicingGroup.TotalInterest
-- 2024-04-24T08:54:10.103Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588197,583091,0,12,542359,'TotalInterest',TO_TIMESTAMP('2024-04-24 11:54:09.94','YYYY-MM-DD HH24:MI:SS.US'),100,'N','0','de.metas.contracts',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Total Zins',0,0,TO_TIMESTAMP('2024-04-24 11:54:09.94','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-24T08:54:10.105Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588197 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-24T08:54:10.107Z
/* DDL */  select update_Column_Translation_From_AD_Element(583091)
;

-- 2024-04-24T08:54:13.801Z
/* DDL */ SELECT public.db_alter_table('ModCntr_InvoicingGroup','ALTER TABLE public.ModCntr_InvoicingGroup ADD COLUMN TotalInterest NUMERIC DEFAULT 0 NOT NULL')
;

-- Table: ModCntr_InvoicingGroup
-- 2024-04-24T08:57:14.176Z
UPDATE AD_Table SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:57:14.175','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542359
;

-- Table: ModCntr_InvoicingGroup_Product
-- 2024-04-24T08:57:21.635Z
UPDATE AD_Table SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:57:21.633','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542360
;

-- Column: ModCntr_InvoicingGroup_Product.AD_Client_ID
-- 2024-04-24T08:57:44.223Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:57:44.223','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587291
;

-- Column: ModCntr_InvoicingGroup_Product.AD_Org_ID
-- 2024-04-24T08:57:46.676Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:57:46.676','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587292
;

-- Column: ModCntr_InvoicingGroup_Product.Created
-- 2024-04-24T08:57:48.826Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:57:48.826','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587293
;

-- Column: ModCntr_InvoicingGroup_Product.UpdatedBy
-- 2024-04-24T08:57:49.830Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:57:49.83','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587297
;

-- Column: ModCntr_InvoicingGroup_Product.Updated
-- 2024-04-24T08:57:50.885Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:57:50.885','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587296
;

-- Column: ModCntr_InvoicingGroup_Product.M_Product_ID
-- 2024-04-24T08:57:51.748Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:57:51.748','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587300
;

-- Column: ModCntr_InvoicingGroup_Product.ModCntr_InvoicingGroup_Product_ID
-- 2024-04-24T08:57:52.624Z
UPDATE AD_Column SET EntityType='de.metas.contracts', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-04-24 11:57:52.624','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587298
;

-- Column: ModCntr_InvoicingGroup_Product.ModCntr_InvoicingGroup_ID
-- 2024-04-24T08:57:53.587Z
UPDATE AD_Column SET EntityType='de.metas.contracts', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-04-24 11:57:53.587','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587299
;

-- Column: ModCntr_InvoicingGroup_Product.IsActive
-- 2024-04-24T08:57:54.483Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:57:54.483','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587295
;

-- Column: ModCntr_InvoicingGroup_Product.CreatedBy
-- 2024-04-24T08:57:58.785Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:57:58.785','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587294
;

-- Column: ModCntr_InvoicingGroup.AD_Client_ID
-- 2024-04-24T08:58:08.383Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:58:08.383','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587279
;

-- Column: ModCntr_InvoicingGroup.AD_Org_ID
-- 2024-04-24T08:58:09.764Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:58:09.764','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587280
;

-- Column: ModCntr_InvoicingGroup.ValidTo
-- 2024-04-24T08:58:10.677Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:58:10.677','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587289
;

-- Column: ModCntr_InvoicingGroup.ValidFrom
-- 2024-04-24T08:58:11.574Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:58:11.574','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587288
;

-- Column: ModCntr_InvoicingGroup.UpdatedBy
-- 2024-04-24T08:58:12.432Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:58:12.432','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587285
;

-- Column: ModCntr_InvoicingGroup.Updated
-- 2024-04-24T08:58:13.312Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:58:13.312','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587284
;

-- Column: ModCntr_InvoicingGroup.Name
-- 2024-04-24T08:58:14.357Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:58:14.357','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587287
;

-- Column: ModCntr_InvoicingGroup.ModCntr_InvoicingGroup_ID
-- 2024-04-24T08:58:15.145Z
UPDATE AD_Column SET EntityType='de.metas.contracts', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-04-24 11:58:15.145','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587286
;

-- Column: ModCntr_InvoicingGroup.IsActive
-- 2024-04-24T08:58:15.970Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:58:15.97','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587283
;

-- Column: ModCntr_InvoicingGroup.Harvesting_Year_ID
-- 2024-04-24T08:58:16.933Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:58:16.933','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588196
;

-- Column: ModCntr_InvoicingGroup.Group_Product_ID
-- 2024-04-24T08:58:17.845Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:58:17.845','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587290
;

-- Column: ModCntr_InvoicingGroup.CreatedBy
-- 2024-04-24T08:58:18.725Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:58:18.725','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587282
;

-- Column: ModCntr_InvoicingGroup.Created
-- 2024-04-24T08:58:19.561Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:58:19.561','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587281
;

-- Column: ModCntr_InvoicingGroup.C_Harvesting_Calendar_ID
-- 2024-04-24T08:58:22.613Z
UPDATE AD_Column SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-24 11:58:22.613','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588195
;

