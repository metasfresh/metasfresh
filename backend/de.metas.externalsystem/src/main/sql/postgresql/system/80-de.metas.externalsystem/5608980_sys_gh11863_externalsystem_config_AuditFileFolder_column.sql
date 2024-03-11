-- 2021-10-12T11:16:19.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580035,0,'AuditFileFolder',TO_TIMESTAMP('2021-10-12 14:16:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Audit log folder','Audit log folder',TO_TIMESTAMP('2021-10-12 14:16:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-12T11:16:19.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580035 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-10-12T11:16:29.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Auditdatei-Ordner', PrintName='Auditdatei-Ordner',Updated=TO_TIMESTAMP('2021-10-12 14:16:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580035 AND AD_Language='de_CH'
;

-- 2021-10-12T11:16:29.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580035,'de_CH') 
;

-- 2021-10-12T11:16:33.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Auditdatei-Ordner', PrintName='Auditdatei-Ordner',Updated=TO_TIMESTAMP('2021-10-12 14:16:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580035 AND AD_Language='de_DE'
;

-- 2021-10-12T11:16:33.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580035,'de_DE') 
;

-- 2021-10-12T11:16:33.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580035,'de_DE') 
;

-- 2021-10-12T11:16:33.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AuditFileFolder', Name='Auditdatei-Ordner', Description=NULL, Help=NULL WHERE AD_Element_ID=580035
;

-- 2021-10-12T11:16:33.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AuditFileFolder', Name='Auditdatei-Ordner', Description=NULL, Help=NULL, AD_Element_ID=580035 WHERE UPPER(ColumnName)='AUDITFILEFOLDER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-10-12T11:16:33.057Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AuditFileFolder', Name='Auditdatei-Ordner', Description=NULL, Help=NULL WHERE AD_Element_ID=580035 AND IsCentrallyMaintained='Y'
;

-- 2021-10-12T11:16:33.057Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Auditdatei-Ordner', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580035) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580035)
;

-- 2021-10-12T11:16:33.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Auditdatei-Ordner', Name='Auditdatei-Ordner' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580035)
;

-- 2021-10-12T11:16:33.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Auditdatei-Ordner', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580035
;

-- 2021-10-12T11:16:33.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Auditdatei-Ordner', Description=NULL, Help=NULL WHERE AD_Element_ID = 580035
;

-- 2021-10-12T11:16:33.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Auditdatei-Ordner', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580035
;

-- 2021-10-12T11:16:36.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Auditdatei-Ordner', PrintName='Auditdatei-Ordner',Updated=TO_TIMESTAMP('2021-10-12 14:16:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580035 AND AD_Language='nl_NL'
;

-- 2021-10-12T11:16:36.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580035,'nl_NL') 
;

-- 2021-10-12T11:26:30.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,577718,580035,0,10,541576,'AuditFileFolder',TO_TIMESTAMP('2021-10-12 14:26:30','YYYY-MM-DD HH24:MI:SS'),100,'N','/app/data/audit','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auditdatei-Ordner',0,0,TO_TIMESTAMP('2021-10-12 14:26:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-10-12T11:26:30.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=577718 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-10-12T11:26:30.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580035) 
;


-- 2021-10-12T11:26:33.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config','ALTER TABLE public.ExternalSystem_Config ADD COLUMN AuditFileFolder VARCHAR(255) DEFAULT ''/app/data/audit''')
;

-- 2021-10-12T11:28:52.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-10-12 14:28:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577718
;

-- 2021-10-12T11:28:53.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config','AuditFileFolder','VARCHAR(255)',null,'/app/data/audit')
;

-- 2021-10-12T11:28:53.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE ExternalSystem_Config SET AuditFileFolder='/app/data/audit' WHERE AuditFileFolder IS NULL
;

-- 2021-10-12T11:28:53.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config','AuditFileFolder',null,'NOT NULL',null)
;