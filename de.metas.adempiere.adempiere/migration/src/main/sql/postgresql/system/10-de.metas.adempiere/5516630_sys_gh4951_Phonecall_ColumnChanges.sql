-- 2019-03-20T11:41:44.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576252,0,'Ordered',TO_TIMESTAMP('2019-03-20 11:41:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Ordered','Ordered',TO_TIMESTAMP('2019-03-20 11:41:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T11:41:44.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576252 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T11:41:53.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsOrdered',Updated=TO_TIMESTAMP('2019-03-20 11:41:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576252
;

-- 2019-03-20T11:41:53.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsOrdered', Name='Ordered', Description=NULL, Help=NULL WHERE AD_Element_ID=576252
;

-- 2019-03-20T11:41:53.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsOrdered', Name='Ordered', Description=NULL, Help=NULL, AD_Element_ID=576252 WHERE UPPER(ColumnName)='ISORDERED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-20T11:41:53.835
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsOrdered', Name='Ordered', Description=NULL, Help=NULL WHERE AD_Element_ID=576252 AND IsCentrallyMaintained='Y'
;

-- 2019-03-20T11:42:01.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='IsOrdered', PrintName='IsOrdered',Updated=TO_TIMESTAMP('2019-03-20 11:42:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576252 AND AD_Language='de_DE'
;

-- 2019-03-20T11:42:01.418
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576252,'de_DE') 
;

-- 2019-03-20T11:42:01.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576252,'de_DE') 
;

-- 2019-03-20T11:42:01.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsOrdered', Name='IsOrdered', Description=NULL, Help=NULL WHERE AD_Element_ID=576252
;

-- 2019-03-20T11:42:01.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsOrdered', Name='IsOrdered', Description=NULL, Help=NULL, AD_Element_ID=576252 WHERE UPPER(ColumnName)='ISORDERED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-20T11:42:01.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsOrdered', Name='IsOrdered', Description=NULL, Help=NULL WHERE AD_Element_ID=576252 AND IsCentrallyMaintained='Y'
;

-- 2019-03-20T11:42:01.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='IsOrdered', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576252) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576252)
;

-- 2019-03-20T11:42:01.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='IsOrdered', Name='IsOrdered' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576252)
;

-- 2019-03-20T11:42:01.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='IsOrdered', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576252
;

-- 2019-03-20T11:42:01.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='IsOrdered', Description=NULL, Help=NULL WHERE AD_Element_ID = 576252
;

-- 2019-03-20T11:42:01.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'IsOrdered', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576252
;

-- 2019-03-20T11:42:07.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Ordered', PrintName='Ordered',Updated=TO_TIMESTAMP('2019-03-20 11:42:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576252 AND AD_Language='de_DE'
;

-- 2019-03-20T11:42:07.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576252,'de_DE') 
;

-- 2019-03-20T11:42:07.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576252,'de_DE') 
;

-- 2019-03-20T11:42:07.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsOrdered', Name='Ordered', Description=NULL, Help=NULL WHERE AD_Element_ID=576252
;

-- 2019-03-20T11:42:07.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsOrdered', Name='Ordered', Description=NULL, Help=NULL, AD_Element_ID=576252 WHERE UPPER(ColumnName)='ISORDERED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-20T11:42:07.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsOrdered', Name='Ordered', Description=NULL, Help=NULL WHERE AD_Element_ID=576252 AND IsCentrallyMaintained='Y'
;

-- 2019-03-20T11:42:07.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ordered', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576252) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576252)
;

-- 2019-03-20T11:42:07.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ordered', Name='Ordered' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576252)
;

-- 2019-03-20T11:42:07.474
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ordered', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576252
;

-- 2019-03-20T11:42:07.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ordered', Description=NULL, Help=NULL WHERE AD_Element_ID = 576252
;

-- 2019-03-20T11:42:07.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ordered', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576252
;

-- 2019-03-20T11:42:21.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=576252, ColumnName='IsOrdered', Description=NULL, Help=NULL, Name='Ordered',Updated=TO_TIMESTAMP('2019-03-20 11:42:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564166
;

-- 2019-03-20T11:42:21.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ordered', Description=NULL, Help=NULL WHERE AD_Column_ID=564166
;















-- 2019-03-20T11:51:48.003
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576253,0,'IsCalled',TO_TIMESTAMP('2019-03-20 11:51:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','IsCalled','IsCalled',TO_TIMESTAMP('2019-03-20 11:51:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-20T11:51:48.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576253 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-20T11:51:56.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564555,576253,0,20,541176,'IsCalled',TO_TIMESTAMP('2019-03-20 11:51:56','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','IsCalled',0,0,TO_TIMESTAMP('2019-03-20 11:51:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T11:51:56.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564555 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;











-- 2019-03-20T11:54:58.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564556,1063,0,30,190,541176,'SalesRep_ID',TO_TIMESTAMP('2019-03-20 11:54:58','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',10,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Aussendienst',0,0,TO_TIMESTAMP('2019-03-20 11:54:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-20T11:54:58.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564556 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;











