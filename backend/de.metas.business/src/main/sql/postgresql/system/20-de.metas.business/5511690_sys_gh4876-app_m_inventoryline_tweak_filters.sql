-- 2019-02-01T14:16:25.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=542140 AND AD_Language='it_CH'
;

-- 2019-02-01T14:16:26.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=542140 AND AD_Language='fr_CH'
;

-- 2019-02-01T14:16:26.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=542140 AND AD_Language='en_GB'
;

-- 2019-02-01T14:16:32.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-01 14:16:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Handling Unit',PrintName='Handling Unit' WHERE AD_Element_ID=542140 AND AD_Language='de_CH'
;

-- 2019-02-01T14:16:32.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542140,'de_CH') 
;

-- 2019-02-01T14:16:36.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-01 14:16:36','YYYY-MM-DD HH24:MI:SS'),PrintName='Handling Unit' WHERE AD_Element_ID=542140 AND AD_Language='en_US'
;

-- 2019-02-01T14:16:36.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542140,'en_US') 
;

-- 2019-02-01T14:16:41.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-01 14:16:41','YYYY-MM-DD HH24:MI:SS'),Name='Handling Unit',PrintName='Handling Unit' WHERE AD_Element_ID=542140 AND AD_Language='nl_NL'
;

-- 2019-02-01T14:16:41.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542140,'nl_NL') 
;

-- 2019-02-01T14:16:45.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-01 14:16:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Handling Unit',PrintName='Handling Unit' WHERE AD_Element_ID=542140 AND AD_Language='de_DE'
;

-- 2019-02-01T14:16:45.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542140,'de_DE') 
;

-- 2019-02-01T14:16:45.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542140,'de_DE') 
;

-- 2019-02-01T14:16:45.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_HU_ID', Name='Handling Unit', Description=NULL, Help=NULL WHERE AD_Element_ID=542140
;

-- 2019-02-01T14:16:45.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_HU_ID', Name='Handling Unit', Description=NULL, Help=NULL, AD_Element_ID=542140 WHERE UPPER(ColumnName)='M_HU_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-02-01T14:16:45.199
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_HU_ID', Name='Handling Unit', Description=NULL, Help=NULL WHERE AD_Element_ID=542140 AND IsCentrallyMaintained='Y'
;

-- 2019-02-01T14:16:45.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Handling Unit', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542140) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542140)
;

-- 2019-02-01T14:16:45.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Handling Unit', Name='Handling Unit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542140)
;

-- 2019-02-01T14:16:45.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Handling Unit', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542140
;

-- 2019-02-01T14:16:45.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Handling Unit', Description=NULL, Help=NULL WHERE AD_Element_ID = 542140
;

-- 2019-02-01T14:16:45.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Handling Unit', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542140
;

-- 2019-02-01T14:17:21.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2019-02-01 14:17:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559545
;

-- 2019-02-01T14:20:10.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30, IsUpdateable='N',Updated=TO_TIMESTAMP('2019-02-01 14:20:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3563
;

