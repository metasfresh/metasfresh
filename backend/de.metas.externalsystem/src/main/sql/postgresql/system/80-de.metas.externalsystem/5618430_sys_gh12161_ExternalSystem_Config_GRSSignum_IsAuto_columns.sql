-- 2021-12-13T07:47:38.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580377,0,'IsAutoSendVendors',TO_TIMESTAMP('2021-12-13 09:47:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Auto-send vendors','Auto-send vendors',TO_TIMESTAMP('2021-12-13 09:47:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-13T07:47:38.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580377 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-12-13T07:48:01.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578922,580377,0,20,541882,'IsAutoSendVendors',TO_TIMESTAMP('2021-12-13 09:48:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','de.metas.externalsystem',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Auto-send vendors',0,0,TO_TIMESTAMP('2021-12-13 09:48:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-12-13T07:48:01.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578922 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-12-13T07:48:01.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580377) 
;

-- 2021-12-13T07:48:02.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_GRSSignum','ALTER TABLE public.ExternalSystem_Config_GRSSignum ADD COLUMN IsAutoSendVendors CHAR(1) DEFAULT ''Y'' CHECK (IsAutoSendVendors IN (''Y'',''N'')) NOT NULL')
;

-- 2021-12-13T07:48:40.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580378,0,'IsAutoSendCustomers',TO_TIMESTAMP('2021-12-13 09:48:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Auto-send customers','Auto-send customers',TO_TIMESTAMP('2021-12-13 09:48:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-13T07:48:40.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580378 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-12-13T07:48:56.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578923,580378,0,20,541882,'IsAutoSendCustomers',TO_TIMESTAMP('2021-12-13 09:48:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','de.metas.externalsystem',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Auto-send customers',0,0,TO_TIMESTAMP('2021-12-13 09:48:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-12-13T07:48:56.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578923 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-12-13T07:48:56.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580378) 
;

-- 2021-12-13T07:48:57.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_GRSSignum','ALTER TABLE public.ExternalSystem_Config_GRSSignum ADD COLUMN IsAutoSendCustomers CHAR(1) DEFAULT ''Y'' CHECK (IsAutoSendCustomers IN (''Y'',''N'')) NOT NULL')
;

-- 2021-12-13T07:49:18.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Kunden autom. senden', PrintName='Kunden autom. senden',Updated=TO_TIMESTAMP('2021-12-13 09:49:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580378 AND AD_Language='de_CH'
;

-- 2021-12-13T07:49:18.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580378,'de_CH') 
;

-- 2021-12-13T07:49:21.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Kunden autom. senden', PrintName='Kunden autom. senden',Updated=TO_TIMESTAMP('2021-12-13 09:49:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580378 AND AD_Language='de_DE'
;

-- 2021-12-13T07:49:21.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580378,'de_DE') 
;

-- 2021-12-13T07:49:21.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580378,'de_DE') 
;

-- 2021-12-13T07:49:21.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAutoSendCustomers', Name='Kunden autom. senden', Description=NULL, Help=NULL WHERE AD_Element_ID=580378
;

-- 2021-12-13T07:49:21.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAutoSendCustomers', Name='Kunden autom. senden', Description=NULL, Help=NULL, AD_Element_ID=580378 WHERE UPPER(ColumnName)='ISAUTOSENDCUSTOMERS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-13T07:49:21.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAutoSendCustomers', Name='Kunden autom. senden', Description=NULL, Help=NULL WHERE AD_Element_ID=580378 AND IsCentrallyMaintained='Y'
;

-- 2021-12-13T07:49:21.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kunden autom. senden', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580378) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580378)
;

-- 2021-12-13T07:49:21.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Kunden autom. senden', Name='Kunden autom. senden' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580378)
;

-- 2021-12-13T07:49:21.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kunden autom. senden', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580378
;

-- 2021-12-13T07:49:21.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kunden autom. senden', Description=NULL, Help=NULL WHERE AD_Element_ID = 580378
;

-- 2021-12-13T07:49:21.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kunden autom. senden', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580378
;

-- 2021-12-13T07:49:27.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Kunden autom. senden', PrintName='Kunden autom. senden',Updated=TO_TIMESTAMP('2021-12-13 09:49:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580378 AND AD_Language='nl_NL'
;

-- 2021-12-13T07:49:27.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580378,'nl_NL') 
;

-- 2021-12-13T07:50:02.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Lieferanten autom. senden', PrintName='Lieferanten autom. senden',Updated=TO_TIMESTAMP('2021-12-13 09:50:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580377 AND AD_Language='de_CH'
;

-- 2021-12-13T07:50:02.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580377,'de_CH') 
;

-- 2021-12-13T07:50:06.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Lieferanten autom. senden', PrintName='Lieferanten autom. senden',Updated=TO_TIMESTAMP('2021-12-13 09:50:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580377 AND AD_Language='de_DE'
;

-- 2021-12-13T07:50:06.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580377,'de_DE') 
;

-- 2021-12-13T07:50:06.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580377,'de_DE') 
;

-- 2021-12-13T07:50:06.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAutoSendVendors', Name='Lieferanten autom. senden', Description=NULL, Help=NULL WHERE AD_Element_ID=580377
;

-- 2021-12-13T07:50:06.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAutoSendVendors', Name='Lieferanten autom. senden', Description=NULL, Help=NULL, AD_Element_ID=580377 WHERE UPPER(ColumnName)='ISAUTOSENDVENDORS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-13T07:50:06.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAutoSendVendors', Name='Lieferanten autom. senden', Description=NULL, Help=NULL WHERE AD_Element_ID=580377 AND IsCentrallyMaintained='Y'
;

-- 2021-12-13T07:50:06.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferanten autom. senden', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580377) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580377)
;

-- 2021-12-13T07:50:06.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferanten autom. senden', Name='Lieferanten autom. senden' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580377)
;

-- 2021-12-13T07:50:06.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lieferanten autom. senden', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580377
;

-- 2021-12-13T07:50:06.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lieferanten autom. senden', Description=NULL, Help=NULL WHERE AD_Element_ID = 580377
;

-- 2021-12-13T07:50:06.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Lieferanten autom. senden', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580377
;

-- 2021-12-13T07:50:10.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Lieferanten autom. senden', PrintName='Lieferanten autom. senden',Updated=TO_TIMESTAMP('2021-12-13 09:50:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580377 AND AD_Language='nl_NL'
;

-- 2021-12-13T07:50:10.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580377,'nl_NL') 
;

