-- 2022-08-02T08:46:31.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Guthaben verfügbar', Name='Gutschriften', PrintName='Gutschriften',Updated=TO_TIMESTAMP('2022-08-02 11:46:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542858 AND AD_Language='de_CH'
;

-- 2022-08-02T08:46:31.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542858,'de_CH') 
;

-- 2022-08-02T08:46:47.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Guthaben verfügbar', Name='Gutschriften', PrintName='Gutschriften',Updated=TO_TIMESTAMP('2022-08-02 11:46:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542858 AND AD_Language='de_DE'
;

-- 2022-08-02T08:46:47.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542858,'de_DE') 
;

-- 2022-08-02T08:46:47.649Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542858,'de_DE') 
;

-- 2022-08-02T08:46:47.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='HasOpenCreditMemos', Name='Gutschriften', Description='Guthaben verfügbar', Help=NULL WHERE AD_Element_ID=542858
;

-- 2022-08-02T08:46:47.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HasOpenCreditMemos', Name='Gutschriften', Description='Guthaben verfügbar', Help=NULL, AD_Element_ID=542858 WHERE UPPER(ColumnName)='HASOPENCREDITMEMOS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-02T08:46:47.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HasOpenCreditMemos', Name='Gutschriften', Description='Guthaben verfügbar', Help=NULL WHERE AD_Element_ID=542858 AND IsCentrallyMaintained='Y'
;

-- 2022-08-02T08:46:47.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Gutschriften', Description='Guthaben verfügbar', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542858) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542858)
;

-- 2022-08-02T08:46:47.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Gutschriften', Name='Gutschriften' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542858)
;

-- 2022-08-02T08:46:47.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Gutschriften', Description='Guthaben verfügbar', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542858
;

-- 2022-08-02T08:46:47.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Gutschriften', Description='Guthaben verfügbar', Help=NULL WHERE AD_Element_ID = 542858
;

-- 2022-08-02T08:46:47.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Gutschriften', Description = 'Guthaben verfügbar', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542858
;

-- 2022-08-02T08:47:13.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Credit memos', PrintName='Credit memos',Updated=TO_TIMESTAMP('2022-08-02 11:47:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542858 AND AD_Language='en_US'
;

-- 2022-08-02T08:47:13.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542858,'en_US') 
;

-- 2022-08-02T08:47:25.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Credit memos', PrintName='Credit memos',Updated=TO_TIMESTAMP('2022-08-02 11:47:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581196 AND AD_Language='en_US'
;

-- 2022-08-02T08:47:25.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581196,'en_US') 
;

-- 2022-08-02T08:47:33.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Gutschriften', PrintName='Gutschriften',Updated=TO_TIMESTAMP('2022-08-02 11:47:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581196 AND AD_Language='de_DE'
;

-- 2022-08-02T08:47:33.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581196,'de_DE') 
;

-- 2022-08-02T08:47:33.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581196,'de_DE') 
;

-- 2022-08-02T08:47:33.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='HasOpenCreditMemos_Color_ID', Name='Gutschriften', Description='', Help=NULL WHERE AD_Element_ID=581196
;

-- 2022-08-02T08:47:33.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HasOpenCreditMemos_Color_ID', Name='Gutschriften', Description='', Help=NULL, AD_Element_ID=581196 WHERE UPPER(ColumnName)='HASOPENCREDITMEMOS_COLOR_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-02T08:47:33.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HasOpenCreditMemos_Color_ID', Name='Gutschriften', Description='', Help=NULL WHERE AD_Element_ID=581196 AND IsCentrallyMaintained='Y'
;

-- 2022-08-02T08:47:33.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Gutschriften', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581196) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581196)
;

-- 2022-08-02T08:47:33.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Gutschriften', Name='Gutschriften' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581196)
;

-- 2022-08-02T08:47:33.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Gutschriften', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581196
;

-- 2022-08-02T08:47:33.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Gutschriften', Description='', Help=NULL WHERE AD_Element_ID = 581196
;

-- 2022-08-02T08:47:33.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Gutschriften', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581196
;

-- 2022-08-02T08:47:42.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Guthaben verfügbar', Name='Gutschriften', PrintName='Gutschriften',Updated=TO_TIMESTAMP('2022-08-02 11:47:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581196 AND AD_Language='de_CH'
;

-- 2022-08-02T08:47:42.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581196,'de_CH') 
;

-- 2022-08-02T08:47:47.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Guthaben verfügbar',Updated=TO_TIMESTAMP('2022-08-02 11:47:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581196 AND AD_Language='de_DE'
;

-- 2022-08-02T08:47:47.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581196,'de_DE') 
;

-- 2022-08-02T08:47:47.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581196,'de_DE') 
;

-- 2022-08-02T08:47:47.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='HasOpenCreditMemos_Color_ID', Name='Gutschriften', Description='Guthaben verfügbar', Help=NULL WHERE AD_Element_ID=581196
;

-- 2022-08-02T08:47:47.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HasOpenCreditMemos_Color_ID', Name='Gutschriften', Description='Guthaben verfügbar', Help=NULL, AD_Element_ID=581196 WHERE UPPER(ColumnName)='HASOPENCREDITMEMOS_COLOR_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-02T08:47:47.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HasOpenCreditMemos_Color_ID', Name='Gutschriften', Description='Guthaben verfügbar', Help=NULL WHERE AD_Element_ID=581196 AND IsCentrallyMaintained='Y'
;

-- 2022-08-02T08:47:47.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Gutschriften', Description='Guthaben verfügbar', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581196) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581196)
;

-- 2022-08-02T08:47:47.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Gutschriften', Description='Guthaben verfügbar', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581196
;

-- 2022-08-02T08:47:47.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Gutschriften', Description='Guthaben verfügbar', Help=NULL WHERE AD_Element_ID = 581196
;

-- 2022-08-02T08:47:47.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Gutschriften', Description = 'Guthaben verfügbar', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581196
;

