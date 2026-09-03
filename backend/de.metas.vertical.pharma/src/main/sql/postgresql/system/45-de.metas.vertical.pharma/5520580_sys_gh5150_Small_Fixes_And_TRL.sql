-- 2019-04-25T17:04:40.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsReadOnly='Y', ReadOnlyLogic='',Updated=TO_TIMESTAMP('2019-04-25 17:04:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541750
;

-- 2019-04-25T17:07:36.229
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Max. Belegzeilen pro Dokument', PrintName='Max. Belegzeilen pro Dokument',Updated=TO_TIMESTAMP('2019-04-25 17:07:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576678 AND AD_Language='de_CH'
;

-- 2019-04-25T17:07:36.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576678,'de_CH') 
;

-- 2019-04-25T17:07:40.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Max. Belegzeilen pro Dokument', PrintName='Max. Belegzeilen pro Dokument',Updated=TO_TIMESTAMP('2019-04-25 17:07:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576678 AND AD_Language='de_DE'
;

-- 2019-04-25T17:07:40.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576678,'de_DE') 
;

-- 2019-04-25T17:07:40.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576678,'de_DE') 
;

-- 2019-04-25T17:07:40.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DocumentLinesNumber', Name='Max. Belegzeilen pro Dokument', Description=NULL, Help=NULL WHERE AD_Element_ID=576678
;

-- 2019-04-25T17:07:40.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DocumentLinesNumber', Name='Max. Belegzeilen pro Dokument', Description=NULL, Help=NULL, AD_Element_ID=576678 WHERE UPPER(ColumnName)='DOCUMENTLINESNUMBER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-25T17:07:40.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DocumentLinesNumber', Name='Max. Belegzeilen pro Dokument', Description=NULL, Help=NULL WHERE AD_Element_ID=576678 AND IsCentrallyMaintained='Y'
;

-- 2019-04-25T17:07:40.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Max. Belegzeilen pro Dokument', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576678) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576678)
;

-- 2019-04-25T17:07:40.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Max. Belegzeilen pro Dokument', Name='Max. Belegzeilen pro Dokument' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576678)
;

-- 2019-04-25T17:07:40.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Max. Belegzeilen pro Dokument', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576678
;

-- 2019-04-25T17:07:40.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Max. Belegzeilen pro Dokument', Description=NULL, Help=NULL WHERE AD_Element_ID = 576678
;

-- 2019-04-25T17:07:40.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Max. Belegzeilen pro Dokument', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576678
;

-- 2019-04-25T17:08:04.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Max. Document Lines per Document', PrintName='Max. Document Lines per Document',Updated=TO_TIMESTAMP('2019-04-25 17:08:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576678 AND AD_Language='en_US'
;

-- 2019-04-25T17:08:04.665
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576678,'en_US') 
;

-- 2019-04-25T17:08:12.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-25 17:08:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576678 AND AD_Language='en_US'
;

-- 2019-04-25T17:08:12.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576678,'en_US') 
;

-- 2019-04-25T17:08:16.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Max. Document Lines per Document', PrintName='Max. Document Lines per Document',Updated=TO_TIMESTAMP('2019-04-25 17:08:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576678 AND AD_Language='nl_NL'
;

-- 2019-04-25T17:08:16.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576678,'nl_NL') 
;

-- 2019-04-25T17:08:49.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Betäubungsmittel', PrintName='Betäubungsmittel',Updated=TO_TIMESTAMP('2019-04-25 17:08:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576677 AND AD_Language='de_CH'
;

-- 2019-04-25T17:08:49.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576677,'de_CH') 
;

-- 2019-04-25T17:08:53.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Betäubungsmittel', PrintName='Betäubungsmittel',Updated=TO_TIMESTAMP('2019-04-25 17:08:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576677 AND AD_Language='de_DE'
;

-- 2019-04-25T17:08:53.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576677,'de_DE') 
;

-- 2019-04-25T17:08:53.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576677,'de_DE') 
;

-- 2019-04-25T17:08:53.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsOnlyNarcoticProducts', Name='Betäubungsmittel', Description=NULL, Help=NULL WHERE AD_Element_ID=576677
;

-- 2019-04-25T17:08:53.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsOnlyNarcoticProducts', Name='Betäubungsmittel', Description=NULL, Help=NULL, AD_Element_ID=576677 WHERE UPPER(ColumnName)='ISONLYNARCOTICPRODUCTS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-25T17:08:53.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsOnlyNarcoticProducts', Name='Betäubungsmittel', Description=NULL, Help=NULL WHERE AD_Element_ID=576677 AND IsCentrallyMaintained='Y'
;

-- 2019-04-25T17:08:53.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Betäubungsmittel', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576677) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576677)
;

-- 2019-04-25T17:08:53.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Betäubungsmittel', Name='Betäubungsmittel' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576677)
;

-- 2019-04-25T17:08:53.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Betäubungsmittel', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576677
;

-- 2019-04-25T17:08:53.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Betäubungsmittel', Description=NULL, Help=NULL WHERE AD_Element_ID = 576677
;

-- 2019-04-25T17:08:53.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Betäubungsmittel', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576677
;

-- 2019-04-25T17:09:07.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Only Narcotic Products', PrintName='Only Narcotic Products',Updated=TO_TIMESTAMP('2019-04-25 17:09:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576677 AND AD_Language='en_US'
;

-- 2019-04-25T17:09:07.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576677,'en_US') 
;

-- 2019-04-25T17:09:11.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Only Narcotic Products', PrintName='Only Narcotic Products',Updated=TO_TIMESTAMP('2019-04-25 17:09:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576677 AND AD_Language='nl_NL'
;

-- 2019-04-25T17:09:11.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576677,'nl_NL') 
;

-- 2019-04-25T17:10:13.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET Name='Betäubungsmittel Abgabemeldung', PrintName='Betäubungsmittel Abgabemeldung',Updated=TO_TIMESTAMP('2019-04-25 17:10:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540970
;

