-- 2020-05-04T09:48:51.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,AD_Org_ID,Updated,UpdatedBy,AD_Element_ID,ColumnName,PrintName,Name,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2020-05-04 12:48:51','YYYY-MM-DD HH24:MI:SS'),100,0,TO_TIMESTAMP('2020-05-04 12:48:51','YYYY-MM-DD HH24:MI:SS'),100,577700,'Statement','Statement','Statement','D')
;

-- 2020-05-04T09:48:51.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Description,PO_Help,PO_Name,PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Help,PrintName,WEBUI_NameNewBreadcrumb,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Help,t.PrintName,t.WEBUI_NameNewBreadcrumb,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577700 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-05-04T09:49:05.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Auszug', IsTranslated='Y', Name='Auszug',Updated=TO_TIMESTAMP('2020-05-04 12:49:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577700
;

-- 2020-05-04T09:49:05.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577700,'de_CH') 
;

-- 2020-05-04T09:49:08.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Auszug', IsTranslated='Y', Name='Auszug',Updated=TO_TIMESTAMP('2020-05-04 12:49:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577700
;

-- 2020-05-04T09:49:08.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577700,'de_DE') 
;

-- 2020-05-04T09:49:08.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577700,'de_DE') 
;

-- 2020-05-04T09:49:08.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Statement', Name='Auszug', Description=NULL, Help=NULL WHERE AD_Element_ID=577700
;

-- 2020-05-04T09:49:08.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Statement', Name='Auszug', Description=NULL, Help=NULL, AD_Element_ID=577700 WHERE UPPER(ColumnName)='STATEMENT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-05-04T09:49:08.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Statement', Name='Auszug', Description=NULL, Help=NULL WHERE AD_Element_ID=577700 AND IsCentrallyMaintained='Y'
;

-- 2020-05-04T09:49:08.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Auszug', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577700) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577700)
;

-- 2020-05-04T09:49:08.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Auszug', Name='Auszug' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577700)
;

-- 2020-05-04T09:49:08.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Auszug', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577700
;

-- 2020-05-04T09:49:08.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Auszug', Description=NULL, Help=NULL WHERE AD_Element_ID = 577700
;

-- 2020-05-04T09:49:08.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Auszug', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577700
;

-- 2020-05-04T09:49:10.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-05-04 12:49:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577700
;

-- 2020-05-04T09:49:10.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577700,'en_US') 
;

-- 2020-05-04T09:49:38.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Help=NULL, Name='Auszug', Description=NULL, AD_Name_ID=577700,Updated=TO_TIMESTAMP('2020-05-04 12:49:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556391
;

-- 2020-05-04T09:49:38.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577700) 
;

-- 2020-05-04T09:49:38.477Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=556391
;

-- 2020-05-04T09:49:38.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(556391)
;


