-- 2021-07-21T10:37:33.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579515,0,'LastSepaExport',TO_TIMESTAMP('2021-07-21 13:37:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','Last SEPA Export','Last SEPA Export',TO_TIMESTAMP('2021-07-21 13:37:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-21T10:37:33.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579515 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-07-21T10:38:26.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575174,579515,0,16,426,'LastSepaExport',TO_TIMESTAMP('2021-07-21 13:38:26','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sepa',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Last SEPA Export',0,0,TO_TIMESTAMP('2021-07-21 13:38:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-07-21T10:38:26.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575174 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-07-21T10:38:26.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579515) 
;

-- 2021-07-21T10:40:29.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_PaySelection','ALTER TABLE public.C_PaySelection ADD COLUMN LastSepaExport TIMESTAMP WITH TIME ZONE')
;

-- 2021-07-21T10:43:33.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579516,0,'LastSepaExportBy_ID',TO_TIMESTAMP('2021-07-21 13:43:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y','Last SEPA Export by','Last SEPA Export by',TO_TIMESTAMP('2021-07-21 13:43:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-21T10:43:33.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579516 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-07-21T10:44:06.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575175,579516,0,18,110,426,'LastSepaExportBy_ID',TO_TIMESTAMP('2021-07-21 13:44:05','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sepa',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Last SEPA Export by',0,0,TO_TIMESTAMP('2021-07-21 13:44:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-07-21T10:44:06.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575175 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-07-21T10:44:06.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579516) 
;

-- 2021-07-21T10:44:07.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_PaySelection','ALTER TABLE public.C_PaySelection ADD COLUMN LastSepaExportBy_ID NUMERIC(10)')
;

-- 2021-07-21T10:44:07.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_PaySelection ADD CONSTRAINT LastSepaExportBy_CPaySelection FOREIGN KEY (LastSepaExportBy_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- 2021-07-21T10:44:48.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579517,0,'LastRevolutExport',TO_TIMESTAMP('2021-07-21 13:44:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.revolut','Y','Last Revolut Export','Last Revolut Export',TO_TIMESTAMP('2021-07-21 13:44:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-21T10:44:48.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579517 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-07-21T10:45:18.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575176,579517,0,16,426,'LastRevolutExport',TO_TIMESTAMP('2021-07-21 13:45:18','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.revolut',0,30,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Last Revolut Export',0,0,TO_TIMESTAMP('2021-07-21 13:45:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-07-21T10:45:18.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575176 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-07-21T10:45:18.499Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579517) 
;

-- 2021-07-21T10:45:41.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_PaySelection','ALTER TABLE public.C_PaySelection ADD COLUMN LastRevolutExport TIMESTAMP WITH TIME ZONE')
;

-- 2021-07-21T10:46:16.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579518,0,'LastRevolutExportBy_ID',TO_TIMESTAMP('2021-07-21 13:46:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.revolut','Y','Last Revolut Export by','Last Revolut Export by',TO_TIMESTAMP('2021-07-21 13:46:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-21T10:46:16.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579518 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-07-21T10:46:43.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575177,579518,0,18,110,426,'LastRevolutExportBy_ID',TO_TIMESTAMP('2021-07-21 13:46:42','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.revolut',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Last Revolut Export by',0,0,TO_TIMESTAMP('2021-07-21 13:46:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-07-21T10:46:43.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575177 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-07-21T10:46:43.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579518) 
;

-- 2021-07-21T10:46:46.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_PaySelection','ALTER TABLE public.C_PaySelection ADD COLUMN LastRevolutExportBy_ID NUMERIC(10)')
;

-- 2021-07-21T10:46:46.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_PaySelection ADD CONSTRAINT LastRevolutExportBy_CPaySelection FOREIGN KEY (LastRevolutExportBy_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- 2021-07-21T10:51:24.486Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Letzter Revolut Export', PrintName='Letzter Revolut Export',Updated=TO_TIMESTAMP('2021-07-21 13:51:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579517 AND AD_Language='de_CH'
;

-- 2021-07-21T10:51:24.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579517,'de_CH') 
;

-- 2021-07-21T10:51:27.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Letzter Revolut Export', PrintName='Letzter Revolut Export',Updated=TO_TIMESTAMP('2021-07-21 13:51:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579517 AND AD_Language='de_DE'
;

-- 2021-07-21T10:51:27.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579517,'de_DE') 
;

-- 2021-07-21T10:51:27.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579517,'de_DE') 
;

-- 2021-07-21T10:51:27.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LastRevolutExport', Name='Letzter Revolut Export', Description=NULL, Help=NULL WHERE AD_Element_ID=579517
;

-- 2021-07-21T10:51:27.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastRevolutExport', Name='Letzter Revolut Export', Description=NULL, Help=NULL, AD_Element_ID=579517 WHERE UPPER(ColumnName)='LASTREVOLUTEXPORT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-21T10:51:27.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastRevolutExport', Name='Letzter Revolut Export', Description=NULL, Help=NULL WHERE AD_Element_ID=579517 AND IsCentrallyMaintained='Y'
;

-- 2021-07-21T10:51:27.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Letzter Revolut Export', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579517) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579517)
;

-- 2021-07-21T10:51:27.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Letzter Revolut Export', Name='Letzter Revolut Export' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579517)
;

-- 2021-07-21T10:51:27.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Letzter Revolut Export', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579517
;

-- 2021-07-21T10:51:27.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Letzter Revolut Export', Description=NULL, Help=NULL WHERE AD_Element_ID = 579517
;

-- 2021-07-21T10:51:27.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Letzter Revolut Export', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579517
;

-- 2021-07-21T10:52:08.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Letzter Revolut Export', PrintName='Letzter Revolut Export',Updated=TO_TIMESTAMP('2021-07-21 13:52:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579517 AND AD_Language='nl_NL'
;

-- 2021-07-21T10:52:08.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579517,'nl_NL') 
;

-- 2021-07-21T10:52:42.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Letzter Revolut Export von', PrintName='Letzter Revolut Export von',Updated=TO_TIMESTAMP('2021-07-21 13:52:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579518 AND AD_Language='de_CH'
;

-- 2021-07-21T10:52:42.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579518,'de_CH') 
;

-- 2021-07-21T10:52:45.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Letzter Revolut Export von', PrintName='Letzter Revolut Export von',Updated=TO_TIMESTAMP('2021-07-21 13:52:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579518 AND AD_Language='de_DE'
;

-- 2021-07-21T10:52:45.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579518,'de_DE') 
;

-- 2021-07-21T10:52:45.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579518,'de_DE') 
;

-- 2021-07-21T10:52:45.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LastRevolutExportBy_ID', Name='Letzter Revolut Export von', Description=NULL, Help=NULL WHERE AD_Element_ID=579518
;

-- 2021-07-21T10:52:45.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastRevolutExportBy_ID', Name='Letzter Revolut Export von', Description=NULL, Help=NULL, AD_Element_ID=579518 WHERE UPPER(ColumnName)='LASTREVOLUTEXPORTBY_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-21T10:52:45.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastRevolutExportBy_ID', Name='Letzter Revolut Export von', Description=NULL, Help=NULL WHERE AD_Element_ID=579518 AND IsCentrallyMaintained='Y'
;

-- 2021-07-21T10:52:45.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Letzter Revolut Export von', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579518) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579518)
;

-- 2021-07-21T10:52:45.756Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Letzter Revolut Export von', Name='Letzter Revolut Export von' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579518)
;

-- 2021-07-21T10:52:45.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Letzter Revolut Export von', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579518
;

-- 2021-07-21T10:52:45.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Letzter Revolut Export von', Description=NULL, Help=NULL WHERE AD_Element_ID = 579518
;

-- 2021-07-21T10:52:45.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Letzter Revolut Export von', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579518
;

-- 2021-07-21T10:52:49.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Letzter Revolut Export von', PrintName='Letzter Revolut Export von',Updated=TO_TIMESTAMP('2021-07-21 13:52:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579518 AND AD_Language='nl_NL'
;

-- 2021-07-21T10:52:49.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579518,'nl_NL') 
;

-- 2021-07-21T10:53:13.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Letzter SEPA Export', PrintName='Letzter SEPA Export',Updated=TO_TIMESTAMP('2021-07-21 13:53:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579515 AND AD_Language='de_CH'
;

-- 2021-07-21T10:53:13.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579515,'de_CH') 
;

-- 2021-07-21T10:53:17.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Letzter SEPA Export', PrintName='Letzter SEPA Export',Updated=TO_TIMESTAMP('2021-07-21 13:53:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579515 AND AD_Language='de_DE'
;

-- 2021-07-21T10:53:17.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579515,'de_DE') 
;

-- 2021-07-21T10:53:17.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579515,'de_DE') 
;

-- 2021-07-21T10:53:17.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LastSepaExport', Name='Letzter SEPA Export', Description=NULL, Help=NULL WHERE AD_Element_ID=579515
;

-- 2021-07-21T10:53:17.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastSepaExport', Name='Letzter SEPA Export', Description=NULL, Help=NULL, AD_Element_ID=579515 WHERE UPPER(ColumnName)='LASTSEPAEXPORT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-21T10:53:17.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastSepaExport', Name='Letzter SEPA Export', Description=NULL, Help=NULL WHERE AD_Element_ID=579515 AND IsCentrallyMaintained='Y'
;

-- 2021-07-21T10:53:17.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Letzter SEPA Export', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579515) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579515)
;

-- 2021-07-21T10:53:17.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Letzter SEPA Export', Name='Letzter SEPA Export' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579515)
;

-- 2021-07-21T10:53:17.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Letzter SEPA Export', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579515
;

-- 2021-07-21T10:53:17.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Letzter SEPA Export', Description=NULL, Help=NULL WHERE AD_Element_ID = 579515
;

-- 2021-07-21T10:53:17.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Letzter SEPA Export', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579515
;

-- 2021-07-21T10:53:20.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Letzter SEPA Export', PrintName='Letzter SEPA Export',Updated=TO_TIMESTAMP('2021-07-21 13:53:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579515 AND AD_Language='nl_NL'
;

-- 2021-07-21T10:53:20.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579515,'nl_NL') 
;

-- 2021-07-21T10:54:46.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Letzter SEPA Export von', PrintName='Letzter SEPA Export von',Updated=TO_TIMESTAMP('2021-07-21 13:54:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579516 AND AD_Language='de_CH'
;

-- 2021-07-21T10:54:46.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579516,'de_CH') 
;

-- 2021-07-21T10:54:50.235Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Letzter SEPA Export von', PrintName='Letzter SEPA Export von',Updated=TO_TIMESTAMP('2021-07-21 13:54:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579516 AND AD_Language='de_DE'
;

-- 2021-07-21T10:54:50.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579516,'de_DE') 
;

-- 2021-07-21T10:54:50.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579516,'de_DE') 
;

-- 2021-07-21T10:54:50.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LastSepaExportBy_ID', Name='Letzter SEPA Export von', Description=NULL, Help=NULL WHERE AD_Element_ID=579516
;

-- 2021-07-21T10:54:50.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastSepaExportBy_ID', Name='Letzter SEPA Export von', Description=NULL, Help=NULL, AD_Element_ID=579516 WHERE UPPER(ColumnName)='LASTSEPAEXPORTBY_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-21T10:54:50.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastSepaExportBy_ID', Name='Letzter SEPA Export von', Description=NULL, Help=NULL WHERE AD_Element_ID=579516 AND IsCentrallyMaintained='Y'
;

-- 2021-07-21T10:54:50.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Letzter SEPA Export von', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579516) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579516)
;

-- 2021-07-21T10:54:50.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Letzter SEPA Export von', Name='Letzter SEPA Export von' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579516)
;

-- 2021-07-21T10:54:50.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Letzter SEPA Export von', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579516
;

-- 2021-07-21T10:54:50.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Letzter SEPA Export von', Description=NULL, Help=NULL WHERE AD_Element_ID = 579516
;

-- 2021-07-21T10:54:50.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Letzter SEPA Export von', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579516
;

-- 2021-07-21T10:54:55.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Letzter SEPA Export von', PrintName='Letzter SEPA Export von',Updated=TO_TIMESTAMP('2021-07-21 13:54:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579516 AND AD_Language='nl_NL'
;

-- 2021-07-21T10:54:55.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579516,'nl_NL') 
;

-- 2021-07-21T10:57:01.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541372,TO_TIMESTAMP('2021-07-21 13:57:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.revolut','Y','N','RecipientTypeList',TO_TIMESTAMP('2021-07-21 13:57:01','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-07-21T10:57:01.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541372 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-07-21T10:58:56.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542831,541372,TO_TIMESTAMP('2021-07-21 13:58:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.revolut','Y','COMPANY',TO_TIMESTAMP('2021-07-21 13:58:56','YYYY-MM-DD HH24:MI:SS'),100,'COMPANY','COMPANY')
;

-- 2021-07-21T10:58:56.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542831 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;


-- 2021-07-21T10:59:10.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542832,541372,TO_TIMESTAMP('2021-07-21 13:59:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.revolut','Y','INDIVIDUAL',TO_TIMESTAMP('2021-07-21 13:59:10','YYYY-MM-DD HH24:MI:SS'),100,'INDIVIDUAL','INDIVIDUAL')
;

-- 2021-07-21T10:59:10.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542832 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-07-21T10:59:24.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=541372,Updated=TO_TIMESTAMP('2021-07-21 13:59:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575126
;

-- 2021-07-21T11:56:09.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_payselection','LastRevolutExport','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2021-07-21T11:56:29.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_payselection','LastRevolutExportBy_ID','NUMERIC(10)',null,null)
;

-- 2021-07-21T11:56:37.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_payselection','LastSepaExport','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2021-07-21T11:56:44.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_payselection','LastSepaExportBy_ID','NUMERIC(10)',null,null)
;

