-- 2019-03-20T17:38:38.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Phonecall Schema Version', PrintName='Phonecall Schema Version',Updated=TO_TIMESTAMP('2019-03-20 17:38:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576100 AND AD_Language='en_US'
;

-- 2019-03-20T17:38:39.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576100,'en_US') 
;

-- 2019-03-20T17:46:34.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2019-03-20 17:46:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564474
;

-- 2019-03-20T17:56:33.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-03-20 17:56:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557980
;

-- 2019-03-20T17:56:33.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-03-20 17:56:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557982
;

-- 2019-03-20T17:56:33.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-03-20 17:56:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557951
;

-- 2019-03-20T17:56:33.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-03-20 17:56:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557946
;

-- 2019-03-20T17:56:33.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-03-20 17:56:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557986
;

-- 2019-03-20T17:56:33.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-03-20 17:56:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557987
;

-- 2019-03-20T17:56:33.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2019-03-20 17:56:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557945
;

-- 2019-03-20T18:31:05.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Erreichbar von', PrintName='Erreichbar von',Updated=TO_TIMESTAMP('2019-03-20 18:31:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576122 AND AD_Language='de_DE'
;

-- 2019-03-20T18:31:05.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576122,'de_DE') 
;

-- 2019-03-20T18:31:05.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576122,'de_DE') 
;

-- 2019-03-20T18:31:05.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PhonecallTimeMin', Name='Erreichbar von', Description=NULL, Help=NULL WHERE AD_Element_ID=576122
;

-- 2019-03-20T18:31:05.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PhonecallTimeMin', Name='Erreichbar von', Description=NULL, Help=NULL, AD_Element_ID=576122 WHERE UPPER(ColumnName)='PHONECALLTIMEMIN' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-20T18:31:05.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PhonecallTimeMin', Name='Erreichbar von', Description=NULL, Help=NULL WHERE AD_Element_ID=576122 AND IsCentrallyMaintained='Y'
;

-- 2019-03-20T18:31:05.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Erreichbar von', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576122) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576122)
;

-- 2019-03-20T18:31:05.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Erreichbar von', Name='Erreichbar von' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576122)
;

-- 2019-03-20T18:31:05.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Erreichbar von', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576122
;

-- 2019-03-20T18:31:05.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Erreichbar von', Description=NULL, Help=NULL WHERE AD_Element_ID = 576122
;

-- 2019-03-20T18:31:05.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Erreichbar von', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576122
;

-- 2019-03-20T18:31:30.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Minimum Call Time', PrintName='Minimum Call Time',Updated=TO_TIMESTAMP('2019-03-20 18:31:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576122 AND AD_Language='en_US'
;

-- 2019-03-20T18:31:30.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576122,'en_US') 
;

-- 2019-03-20T18:31:33.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-03-20 18:31:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576122 AND AD_Language='en_US'
;

-- 2019-03-20T18:31:33.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576122,'en_US') 
;

-- 2019-03-20T18:31:43.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Erreichbar von', PrintName='Erreichbar von',Updated=TO_TIMESTAMP('2019-03-20 18:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576122 AND AD_Language='nl_NL'
;

-- 2019-03-20T18:31:43.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576122,'nl_NL') 
;

-- 2019-03-20T18:31:49.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Erreichbar von', PrintName='Erreichbar von',Updated=TO_TIMESTAMP('2019-03-20 18:31:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576122 AND AD_Language='de_CH'
;

-- 2019-03-20T18:31:49.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576122,'de_CH') 
;

-- 2019-03-20T18:32:11.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Erreichbar bis', PrintName='Erreichbar bis',Updated=TO_TIMESTAMP('2019-03-20 18:32:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576123 AND AD_Language='de_CH'
;

-- 2019-03-20T18:32:11.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576123,'de_CH') 
;

-- 2019-03-20T18:32:15.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Erreichbar bis', PrintName='Erreichbar bis',Updated=TO_TIMESTAMP('2019-03-20 18:32:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576123 AND AD_Language='de_DE'
;

-- 2019-03-20T18:32:15.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576123,'de_DE') 
;

-- 2019-03-20T18:32:15.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576123,'de_DE') 
;

-- 2019-03-20T18:32:15.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PhonecallTimeMax', Name='Erreichbar bis', Description=NULL, Help=NULL WHERE AD_Element_ID=576123
;

-- 2019-03-20T18:32:15.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PhonecallTimeMax', Name='Erreichbar bis', Description=NULL, Help=NULL, AD_Element_ID=576123 WHERE UPPER(ColumnName)='PHONECALLTIMEMAX' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-20T18:32:15.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PhonecallTimeMax', Name='Erreichbar bis', Description=NULL, Help=NULL WHERE AD_Element_ID=576123 AND IsCentrallyMaintained='Y'
;

-- 2019-03-20T18:32:15.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Erreichbar bis', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576123) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576123)
;

-- 2019-03-20T18:32:15.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Erreichbar bis', Name='Erreichbar bis' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576123)
;

-- 2019-03-20T18:32:15.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Erreichbar bis', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576123
;

-- 2019-03-20T18:32:15.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Erreichbar bis', Description=NULL, Help=NULL WHERE AD_Element_ID = 576123
;

-- 2019-03-20T18:32:15.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Erreichbar bis', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576123
;

-- 2019-03-20T18:32:28.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Maximum Call Time', PrintName='Maximum Call Time',Updated=TO_TIMESTAMP('2019-03-20 18:32:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576123 AND AD_Language='en_US'
;

-- 2019-03-20T18:32:28.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576123,'en_US') 
;

-- 2019-03-20T18:32:33.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Erreichbar bis', PrintName='Erreichbar bis',Updated=TO_TIMESTAMP('2019-03-20 18:32:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576123 AND AD_Language='nl_NL'
;

-- 2019-03-20T18:32:33.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576123,'nl_NL') 
;

