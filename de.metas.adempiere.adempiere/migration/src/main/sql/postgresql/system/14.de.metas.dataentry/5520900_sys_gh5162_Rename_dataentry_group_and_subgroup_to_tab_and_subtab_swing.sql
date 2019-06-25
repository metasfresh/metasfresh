-- 2019-05-06T12:54:58.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='DataEntry_Tab', TableName='DataEntry_Tab',Updated=TO_TIMESTAMP('2019-05-06 12:54:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541165
;

-- 2019-05-06T12:54:58.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Name='DataEntry_Tab',Updated=TO_TIMESTAMP('2019-05-06 12:54:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=554783
;

-- 2019-05-06T12:54:58.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER SEQUENCE IF EXISTS DataEntry_Group_SEQ RENAME TO DataEntry_Tab_SEQ
;

-- 2019-05-06T12:59:58.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Data entry tab',Updated=TO_TIMESTAMP('2019-05-06 12:59:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576035 AND AD_Language='en_US'
;

-- 2019-05-06T12:59:58.353
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576035,'en_US') 
;

-- 2019-05-06T13:00:03.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Data entry tab',Updated=TO_TIMESTAMP('2019-05-06 13:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576035 AND AD_Language='en_US'
;

-- 2019-05-06T13:00:03.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576035,'en_US') 
;

-- 2019-05-06T13:00:15.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='DataEntry_Tab_ID',Updated=TO_TIMESTAMP('2019-05-06 13:00:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576035
;

-- 2019-05-06T13:00:15.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DataEntry_Tab_ID', Name='Eingabegruppe', Description=NULL, Help=NULL WHERE AD_Element_ID=576035
;

-- 2019-05-06T13:00:15.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DataEntry_Tab_ID', Name='Eingabegruppe', Description=NULL, Help=NULL, AD_Element_ID=576035 WHERE UPPER(ColumnName)='DATAENTRY_TAB_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-05-06T13:00:15.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DataEntry_Tab_ID', Name='Eingabegruppe', Description=NULL, Help=NULL WHERE AD_Element_ID=576035 AND IsCentrallyMaintained='Y'
;

-- 2019-05-06T13:08:37.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Eingaberegister',Updated=TO_TIMESTAMP('2019-05-06 13:08:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576035 AND AD_Language='de_CH'
;

-- 2019-05-06T13:08:37.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576035,'de_CH') 
;

-- 2019-05-06T13:08:37.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Eingaberegister',Updated=TO_TIMESTAMP('2019-05-06 13:08:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576035 AND AD_Language='de_DE'
;

-- 2019-05-06T13:08:37.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576035,'de_DE') 
;

-- 2019-05-06T13:08:37.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576035,'de_DE') 
;

-- 2019-05-06T13:08:37.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DataEntry_Tab_ID', Name='Eingaberegister', Description=NULL, Help=NULL WHERE AD_Element_ID=576035
;

-- 2019-05-06T13:08:37.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DataEntry_Tab_ID', Name='Eingaberegister', Description=NULL, Help=NULL, AD_Element_ID=576035 WHERE UPPER(ColumnName)='DATAENTRY_TAB_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-05-06T13:08:37.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DataEntry_Tab_ID', Name='Eingaberegister', Description=NULL, Help=NULL WHERE AD_Element_ID=576035 AND IsCentrallyMaintained='Y'
;

-- 2019-05-06T13:08:37.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Eingaberegister', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576035) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576035)
;

-- 2019-05-06T13:08:37.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Eingabegruppe', Name='Eingaberegister' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576035)
;

-- 2019-05-06T13:08:37.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Eingaberegister', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576035
;

-- 2019-05-06T13:08:37.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Eingaberegister', Description=NULL, Help=NULL WHERE AD_Element_ID = 576035
;

-- 2019-05-06T13:08:37.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Eingaberegister', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576035
;

-- 2019-05-06T13:08:38.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Eingaberegister',Updated=TO_TIMESTAMP('2019-05-06 13:08:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576035 AND AD_Language='de_CH'
;

-- 2019-05-06T13:08:38.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576035,'de_CH') 
;

-- 2019-05-06T13:08:40.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Eingaberegister',Updated=TO_TIMESTAMP('2019-05-06 13:08:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576035 AND AD_Language='de_DE'
;

-- 2019-05-06T13:08:40.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576035,'de_DE') 
;

-- 2019-05-06T13:08:41.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576035,'de_DE') 
;

-- 2019-05-06T13:08:41.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Eingaberegister', Name='Eingaberegister' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576035)
;

-- 2019-05-06T13:09:25.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='DataEntry_SubTab_ID',Updated=TO_TIMESTAMP('2019-05-06 13:09:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576037
;

-- 2019-05-06T13:09:25.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DataEntry_SubTab_ID', Name='Unterregister', Description=NULL, Help=NULL WHERE AD_Element_ID=576037
;

-- 2019-05-06T13:09:25.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DataEntry_SubTab_ID', Name='Unterregister', Description=NULL, Help=NULL, AD_Element_ID=576037 WHERE UPPER(ColumnName)='DATAENTRY_SUBTAB_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-05-06T13:09:25.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DataEntry_SubTab_ID', Name='Unterregister', Description=NULL, Help=NULL WHERE AD_Element_ID=576037 AND IsCentrallyMaintained='Y'
;

-- 2019-05-06T13:09:30.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='DataEntry_SubTab', TableName='DataEntry_SubTab',Updated=TO_TIMESTAMP('2019-05-06 13:09:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541166
;

-- 2019-05-06T13:09:30.764
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Name='DataEntry_SubTab',Updated=TO_TIMESTAMP('2019-05-06 13:09:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=554784
;

-- 2019-05-06T13:09:30.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER SEQUENCE IF EXISTS DataEntry_SubGroup_SEQ RENAME TO DataEntry_SubTab_SEQ
;

