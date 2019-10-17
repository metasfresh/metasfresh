-- 2019-06-07T11:55:18.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Darf Anhänge löschen', PrintName='Darf Anhänge löschen',Updated=TO_TIMESTAMP('2019-06-07 11:55:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576782 AND AD_Language='de_DE'
;

-- 2019-06-07T11:55:18.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576782,'de_DE') 
;

-- 2019-06-07T11:55:18.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576782,'de_DE') 
;

-- 2019-06-07T11:55:18.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAttachmentDeletionAllowed', Name='Darf Anhänge löschen', Description=NULL, Help=NULL WHERE AD_Element_ID=576782
;

-- 2019-06-07T11:55:18.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAttachmentDeletionAllowed', Name='Darf Anhänge löschen', Description=NULL, Help=NULL, AD_Element_ID=576782 WHERE UPPER(ColumnName)='ISATTACHMENTDELETIONALLOWED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-06-07T11:55:18.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAttachmentDeletionAllowed', Name='Darf Anhänge löschen', Description=NULL, Help=NULL WHERE AD_Element_ID=576782 AND IsCentrallyMaintained='Y'
;

-- 2019-06-07T11:55:18.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Darf Anhänge löschen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576782) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576782)
;

-- 2019-06-07T11:55:18.586
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Darf Anhänge löschen', Name='Darf Anhänge löschen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576782)
;

-- 2019-06-07T11:55:18.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Darf Anhänge löschen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576782
;

-- 2019-06-07T11:55:18.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Darf Anhänge löschen', Description=NULL, Help=NULL WHERE AD_Element_ID = 576782
;

-- 2019-06-07T11:55:18.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Darf Anhänge löschen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576782
;

-- 2019-06-07T11:55:48.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Attachment Deletion Allowed', PrintName='Attachment Deletion Allowed',Updated=TO_TIMESTAMP('2019-06-07 11:55:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576782 AND AD_Language='en_US'
;

-- 2019-06-07T11:55:48.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576782,'en_US') 
;

-- 2019-06-07T11:56:27.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Attachment Deletion Allowed', PrintName='Attachment Deletion Allowed',Updated=TO_TIMESTAMP('2019-06-07 11:56:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576782 AND AD_Language='nl_NL'
;

-- 2019-06-07T11:56:27.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576782,'nl_NL') 
;

