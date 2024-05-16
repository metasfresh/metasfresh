-- 2021-09-16T12:35:25.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579865,0,'IsTakeWholeHU',TO_TIMESTAMP('2021-09-16 15:35:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Take the whole HU','Take the whole HU',TO_TIMESTAMP('2021-09-16 15:35:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-16T12:35:25.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579865 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-09-16T12:35:54.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,579865,0,540875,542095,20,'IsTakeWholeHU',TO_TIMESTAMP('2021-09-16 15:35:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','D',0,'Y','N','Y','N','Y','N','Take the whole HU',30,TO_TIMESTAMP('2021-09-16 15:35:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-16T12:35:54.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542095 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-09-16T12:40:35.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Pick whole HU', PrintName='Pick whole HU',Updated=TO_TIMESTAMP('2021-09-16 15:40:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579865
;

-- 2021-09-16T12:40:35.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsTakeWholeHU', Name='Pick whole HU', Description=NULL, Help=NULL WHERE AD_Element_ID=579865
;

-- 2021-09-16T12:40:35.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsTakeWholeHU', Name='Pick whole HU', Description=NULL, Help=NULL, AD_Element_ID=579865 WHERE UPPER(ColumnName)='ISTAKEWHOLEHU' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-09-16T12:40:35.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsTakeWholeHU', Name='Pick whole HU', Description=NULL, Help=NULL WHERE AD_Element_ID=579865 AND IsCentrallyMaintained='Y'
;

-- 2021-09-16T12:40:35.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Pick whole HU', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579865) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579865)
;

-- 2021-09-16T12:40:35.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Pick whole HU', Name='Pick whole HU' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579865)
;

-- 2021-09-16T12:40:35.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Pick whole HU', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579865
;

-- 2021-09-16T12:40:35.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Pick whole HU', Description=NULL, Help=NULL WHERE AD_Element_ID = 579865
;

-- 2021-09-16T12:40:35.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Pick whole HU', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579865
;

-- 2021-09-16T12:40:45.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Pick whole HU', PrintName='Pick whole HU',Updated=TO_TIMESTAMP('2021-09-16 15:40:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579865 AND AD_Language='en_US'
;

-- 2021-09-16T12:40:45.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579865,'en_US') 
;

-- 2021-09-16T12:40:48.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Pick whole HU', PrintName='Pick whole HU',Updated=TO_TIMESTAMP('2021-09-16 15:40:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579865 AND AD_Language='nl_NL'
;

-- 2021-09-16T12:40:48.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579865,'nl_NL') 
;

-- 2021-09-16T12:41:08.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ganze HU kommissionieren', PrintName='Ganze HU kommissionieren',Updated=TO_TIMESTAMP('2021-09-16 15:41:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579865 AND AD_Language='de_CH'
;

-- 2021-09-16T12:41:08.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579865,'de_CH') 
;

-- 2021-09-16T12:41:13.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ganze HU kommissionieren', PrintName='Ganze HU kommissionieren',Updated=TO_TIMESTAMP('2021-09-16 15:41:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579865 AND AD_Language='de_DE'
;

-- 2021-09-16T12:41:13.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579865,'de_DE') 
;

-- 2021-09-16T12:41:13.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579865,'de_DE') 
;

-- 2021-09-16T12:41:13.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsTakeWholeHU', Name='Ganze HU kommissionieren', Description=NULL, Help=NULL WHERE AD_Element_ID=579865
;

-- 2021-09-16T12:41:13.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsTakeWholeHU', Name='Ganze HU kommissionieren', Description=NULL, Help=NULL, AD_Element_ID=579865 WHERE UPPER(ColumnName)='ISTAKEWHOLEHU' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-09-16T12:41:13.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsTakeWholeHU', Name='Ganze HU kommissionieren', Description=NULL, Help=NULL WHERE AD_Element_ID=579865 AND IsCentrallyMaintained='Y'
;

-- 2021-09-16T12:41:13.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ganze HU kommissionieren', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579865) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579865)
;

-- 2021-09-16T12:41:13.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ganze HU kommissionieren', Name='Ganze HU kommissionieren' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579865)
;

-- 2021-09-16T12:41:13.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ganze HU kommissionieren', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579865
;

-- 2021-09-16T12:41:13.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ganze HU kommissionieren', Description=NULL, Help=NULL WHERE AD_Element_ID = 579865
;

-- 2021-09-16T12:41:13.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ganze HU kommissionieren', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579865
;

-- 2021-09-16T13:57:54.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='N',Updated=TO_TIMESTAMP('2021-09-16 16:57:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542095
;

