
-- 2019-04-03T14:52:17.765
-- #298 changing anz. stellen
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,
FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,
IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,
IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES 
(0,567628,544091,0,10,533,'URL3',TO_TIMESTAMP('2019-04-03 14:52:17','YYYY-MM-DD HH24:MI:SS'),100,'N','placeholder','D',255,
'placeholder','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',
'URL3',0,0,TO_TIMESTAMP('2019-04-03 14:52:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-03T14:52:17.775
-- #298 changing anz. stellen
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
 SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t 
 WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567628 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt 
 WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;




-- 2019-04-08T14:36:29.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='',Updated=TO_TIMESTAMP('2019-04-08 14:36:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544091 AND AD_Language='de_CH'
;

-- 2019-04-08T14:36:29.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544091,'de_CH') 
;

-- 2019-04-08T14:36:36.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='',Updated=TO_TIMESTAMP('2019-04-08 14:36:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544091 AND AD_Language='en_US'
;

-- 2019-04-08T14:36:36.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544091,'en_US') 
;

-- 2019-04-08T14:36:40.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='',Updated=TO_TIMESTAMP('2019-04-08 14:36:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544091 AND AD_Language='nl_NL'
;

-- 2019-04-08T14:36:40.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544091,'nl_NL') 
;

-- 2019-04-08T14:36:45.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='',Updated=TO_TIMESTAMP('2019-04-08 14:36:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544091 AND AD_Language='de_DE'
;

-- 2019-04-08T14:36:45.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544091,'de_DE') 
;

-- 2019-04-08T14:36:45.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(544091,'de_DE') 
;

-- 2019-04-08T14:36:45.354
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='URL3', Name='URL3', Description='', Help='' WHERE AD_Element_ID=544091
;

-- 2019-04-08T14:36:45.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='URL3', Name='URL3', Description='', Help='', AD_Element_ID=544091 WHERE UPPER(ColumnName)='URL3' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-08T14:36:45.363
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='URL3', Name='URL3', Description='', Help='' WHERE AD_Element_ID=544091 AND IsCentrallyMaintained='Y'
;

-- 2019-04-08T14:36:45.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='URL3', Description='', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544091) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544091)
;

-- 2019-04-08T14:36:45.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='URL3', Description='', Help='', CommitWarning = NULL WHERE AD_Element_ID = 544091
;

-- 2019-04-08T14:36:45.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='URL3', Description='', Help='' WHERE AD_Element_ID = 544091
;

-- 2019-04-08T14:36:45.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'URL3', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 544091
;

