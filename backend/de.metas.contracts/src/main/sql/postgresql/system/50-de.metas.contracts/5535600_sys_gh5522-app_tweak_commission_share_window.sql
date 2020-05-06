-- 2019-11-01T14:01:48.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-11-01 15:01:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=591219
;

-- 2019-11-01T14:02:08.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2019-11-01 15:02:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563747
;

-- 2019-11-01T14:02:08.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-11-01 15:02:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561858
;

-- 2019-11-01T14:02:08.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-11-01 15:02:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561859
;

-- 2019-11-01T14:02:08.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-11-01 15:02:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563585
;

-- 2019-11-01T14:02:08.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-11-01 15:02:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561860
;

-- 2019-11-01T14:02:52.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Buchauszug', PrintName='Buchauszug',Updated=TO_TIMESTAMP('2019-11-01 15:02:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577080 AND AD_Language='de_CH'
;

-- 2019-11-01T14:02:52.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577080,'de_CH') 
;

-- 2019-11-01T14:02:58.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Buchauszug', PrintName='Buchauszug',Updated=TO_TIMESTAMP('2019-11-01 15:02:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577080 AND AD_Language='de_DE'
;

-- 2019-11-01T14:02:58.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577080,'de_DE') 
;

-- 2019-11-01T14:02:58.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577080,'de_DE') 
;

-- 2019-11-01T14:02:58.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Commission_Share_ID', Name='Buchauszug', Description=NULL, Help=NULL WHERE AD_Element_ID=577080
;

-- 2019-11-01T14:02:58.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Commission_Share_ID', Name='Buchauszug', Description=NULL, Help=NULL, AD_Element_ID=577080 WHERE UPPER(ColumnName)='C_COMMISSION_SHARE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-01T14:02:58.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Commission_Share_ID', Name='Buchauszug', Description=NULL, Help=NULL WHERE AD_Element_ID=577080 AND IsCentrallyMaintained='Y'
;

-- 2019-11-01T14:02:58.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Buchauszug', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577080) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577080)
;

-- 2019-11-01T14:02:58.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Buchauszug', Name='Buchauszug' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577080)
;

-- 2019-11-01T14:02:58.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Buchauszug', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577080
;

-- 2019-11-01T14:02:58.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Buchauszug', Description=NULL, Help=NULL WHERE AD_Element_ID = 577080
;

-- 2019-11-01T14:02:58.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Buchauszug', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577080
;

-- 2019-11-01T14:03:05.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Commission share', PrintName='Commission share',Updated=TO_TIMESTAMP('2019-11-01 15:03:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577080 AND AD_Language='en_US'
;

-- 2019-11-01T14:03:05.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577080,'en_US') 
;

