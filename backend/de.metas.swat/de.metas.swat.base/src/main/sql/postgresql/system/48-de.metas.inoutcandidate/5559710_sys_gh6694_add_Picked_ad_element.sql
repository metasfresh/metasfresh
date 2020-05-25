-- 2020-05-19T15:39:50.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577735,0,TO_TIMESTAMP('2020-05-19 18:39:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Picked','Picked',TO_TIMESTAMP('2020-05-19 18:39:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-05-19T15:39:50.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577735 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-05-19T15:40:25.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Kommissioniert', PrintName='Kommissioniert',Updated=TO_TIMESTAMP('2020-05-19 18:40:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577735 AND AD_Language='nl_NL'
;

-- 2020-05-19T15:40:25.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577735,'nl_NL') 
;

-- 2020-05-19T15:40:33.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Kommissioniert', PrintName='Kommissioniert',Updated=TO_TIMESTAMP('2020-05-19 18:40:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577735 AND AD_Language='de_DE'
;

-- 2020-05-19T15:40:33.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577735,'de_DE') 
;

-- 2020-05-19T15:40:33.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577735,'de_DE') 
;

-- 2020-05-19T15:40:33.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Kommissioniert', Description=NULL, Help=NULL WHERE AD_Element_ID=577735
;

-- 2020-05-19T15:40:33.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Kommissioniert', Description=NULL, Help=NULL WHERE AD_Element_ID=577735 AND IsCentrallyMaintained='Y'
;

-- 2020-05-19T15:40:33.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kommissioniert', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577735) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577735)
;

-- 2020-05-19T15:40:33.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Kommissioniert', Name='Kommissioniert' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577735)
;

-- 2020-05-19T15:40:33.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kommissioniert', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577735
;

-- 2020-05-19T15:40:33.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kommissioniert', Description=NULL, Help=NULL WHERE AD_Element_ID = 577735
;

-- 2020-05-19T15:40:33.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kommissioniert', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577735
;

-- 2020-05-19T15:40:37.725Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Kommissioniert', PrintName='Kommissioniert',Updated=TO_TIMESTAMP('2020-05-19 18:40:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577735 AND AD_Language='de_CH'
;

-- 2020-05-19T15:40:37.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577735,'de_CH') 
;

-- 2020-05-20T06:33:45.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='Picked',Updated=TO_TIMESTAMP('2020-05-20 09:33:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577735
;

-- 2020-05-20T06:33:45.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Picked', Name='Kommissioniert', Description=NULL, Help=NULL WHERE AD_Element_ID=577735
;

-- 2020-05-20T06:33:45.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Picked', Name='Kommissioniert', Description=NULL, Help=NULL, AD_Element_ID=577735 WHERE UPPER(ColumnName)='PICKED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-05-20T06:33:45.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Picked', Name='Kommissioniert', Description=NULL, Help=NULL WHERE AD_Element_ID=577735 AND IsCentrallyMaintained='Y'
;

