-- 2021-10-12T11:09:01.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580034,0,'WriteAudit',TO_TIMESTAMP('2021-10-12 14:09:01','YYYY-MM-DD HH24:MI:SS'),100,'When activated, all messages that are exchanged between metasfresh and the external systems are logged.','de.metas.externalsystem','Y','Audit log','Audit log',TO_TIMESTAMP('2021-10-12 14:09:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-12T11:09:01.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580034 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-10-12T11:09:24.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn aktiviert, dann werden alle Nachrichten zwischen metasfresh und den externen Systemen geloggt.', Name='Auditprotokoll', PrintName='Auditprotokoll',Updated=TO_TIMESTAMP('2021-10-12 14:09:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580034 AND AD_Language='de_CH'
;

-- 2021-10-12T11:09:24.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580034,'de_CH') 
;

-- 2021-10-12T11:09:34.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn aktiviert, dann werden alle Nachrichten zwischen metasfresh und den externen Systemen geloggt.', Name='Auditprotokoll', PrintName='Auditprotokoll',Updated=TO_TIMESTAMP('2021-10-12 14:09:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580034 AND AD_Language='de_DE'
;

-- 2021-10-12T11:09:34.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580034,'de_DE') 
;

-- 2021-10-12T11:09:34.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580034,'de_DE') 
;

-- 2021-10-12T11:09:34.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='WriteAudit', Name='Auditprotokoll', Description='Wenn aktiviert, dann werden alle Nachrichten zwischen metasfresh und den externen Systemen geloggt.', Help=NULL WHERE AD_Element_ID=580034
;

-- 2021-10-12T11:09:34.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='WriteAudit', Name='Auditprotokoll', Description='Wenn aktiviert, dann werden alle Nachrichten zwischen metasfresh und den externen Systemen geloggt.', Help=NULL, AD_Element_ID=580034 WHERE UPPER(ColumnName)='WRITEAUDIT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-10-12T11:09:34.272Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='WriteAudit', Name='Auditprotokoll', Description='Wenn aktiviert, dann werden alle Nachrichten zwischen metasfresh und den externen Systemen geloggt.', Help=NULL WHERE AD_Element_ID=580034 AND IsCentrallyMaintained='Y'
;

-- 2021-10-12T11:09:34.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Auditprotokoll', Description='Wenn aktiviert, dann werden alle Nachrichten zwischen metasfresh und den externen Systemen geloggt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580034) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580034)
;

-- 2021-10-12T11:09:34.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Auditprotokoll', Name='Auditprotokoll' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580034)
;

-- 2021-10-12T11:09:34.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Auditprotokoll', Description='Wenn aktiviert, dann werden alle Nachrichten zwischen metasfresh und den externen Systemen geloggt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580034
;

-- 2021-10-12T11:09:34.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Auditprotokoll', Description='Wenn aktiviert, dann werden alle Nachrichten zwischen metasfresh und den externen Systemen geloggt.', Help=NULL WHERE AD_Element_ID = 580034
;

-- 2021-10-12T11:09:34.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Auditprotokoll', Description = 'Wenn aktiviert, dann werden alle Nachrichten zwischen metasfresh und den externen Systemen geloggt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580034
;

-- 2021-10-12T11:09:44.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn aktiviert, dann werden alle Nachrichten zwischen metasfresh und den externen Systemen geloggt.', Name='Auditprotokoll', PrintName='Auditprotokoll',Updated=TO_TIMESTAMP('2021-10-12 14:09:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580034 AND AD_Language='nl_NL'
;

-- 2021-10-12T11:09:44.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580034,'nl_NL') 
;

-- 2021-10-12T11:10:34.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,577717,580034,0,20,541576,'WriteAudit',TO_TIMESTAMP('2021-10-12 14:10:33','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Wenn aktiviert, dann werden alle Nachrichten zwischen metasfresh und den externen Systemen geloggt.','de.metas.externalsystem',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auditprotokoll',0,0,TO_TIMESTAMP('2021-10-12 14:10:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-10-12T11:10:34.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=577717 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-10-12T11:10:34.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580034) 
;

-- 2021-10-12T11:10:47.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config','ALTER TABLE public.ExternalSystem_Config ADD COLUMN WriteAudit CHAR(1) DEFAULT ''N'' CHECK (WriteAudit IN (''Y'',''N''))')
;


-- 2021-10-12T11:13:02.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-10-12 14:13:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577717
;

-- 2021-10-12T11:13:04.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config','WriteAudit','CHAR(1)',null,'N')
;

-- 2021-10-12T11:13:04.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE ExternalSystem_Config SET WriteAudit='N' WHERE WriteAudit IS NULL
;

-- 2021-10-12T11:13:04.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config','WriteAudit',null,'NOT NULL',null)
;

-- 2021-10-12T11:14:49.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config','WriteAudit','CHAR(1)',null,'N')
;

-- 2021-10-12T11:14:49.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE ExternalSystem_Config SET WriteAudit='N' WHERE WriteAudit IS NULL
;
