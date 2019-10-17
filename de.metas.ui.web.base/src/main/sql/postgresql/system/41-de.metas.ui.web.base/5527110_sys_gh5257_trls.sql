-- 2019-07-11T15:06:07.124
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Import - Datensätze Löschen',Updated=TO_TIMESTAMP('2019-07-11 15:06:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541161
;

-- 2019-07-11T15:06:11.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Import - Datensätze Löschen',Updated=TO_TIMESTAMP('2019-07-11 15:06:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541161
;

-- 2019-07-11T15:06:20.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Delete Import Data',Updated=TO_TIMESTAMP('2019-07-11 15:06:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541161
;

-- 2019-07-11T15:06:24.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Import - Datensätze Löschen',Updated=TO_TIMESTAMP('2019-07-11 15:06:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=541161
;

-- 2019-07-11T15:06:32.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Delete Import Data',Updated=TO_TIMESTAMP('2019-07-11 15:06:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=541161
;

-- 2019-07-11T15:07:32.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Löschumfang', PrintName='Löschumfang',Updated=TO_TIMESTAMP('2019-07-11 15:07:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576902 AND AD_Language='de_CH'
;

-- 2019-07-11T15:07:32.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576902,'de_CH') 
;

-- 2019-07-11T15:07:39.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Löschumfang', PrintName='Löschumfang',Updated=TO_TIMESTAMP('2019-07-11 15:07:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576902 AND AD_Language='de_DE'
;

-- 2019-07-11T15:07:39.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576902,'de_DE') 
;

-- 2019-07-11T15:07:39.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576902,'de_DE') 
;

-- 2019-07-11T15:07:39.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ImportDeleteMode', Name='Löschumfang', Description=NULL, Help=NULL WHERE AD_Element_ID=576902
;

-- 2019-07-11T15:07:39.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ImportDeleteMode', Name='Löschumfang', Description=NULL, Help=NULL, AD_Element_ID=576902 WHERE UPPER(ColumnName)='IMPORTDELETEMODE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-11T15:07:39.432
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ImportDeleteMode', Name='Löschumfang', Description=NULL, Help=NULL WHERE AD_Element_ID=576902 AND IsCentrallyMaintained='Y'
;

-- 2019-07-11T15:07:39.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Löschumfang', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576902) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576902)
;

-- 2019-07-11T15:07:39.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Löschumfang', Name='Löschumfang' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576902)
;

-- 2019-07-11T15:07:39.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Löschumfang', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576902
;

-- 2019-07-11T15:07:39.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Löschumfang', Description=NULL, Help=NULL WHERE AD_Element_ID = 576902
;

-- 2019-07-11T15:07:39.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Löschumfang', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576902
;

-- 2019-07-11T15:07:49.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Scope', PrintName='Scope',Updated=TO_TIMESTAMP('2019-07-11 15:07:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576902 AND AD_Language='en_US'
;

-- 2019-07-11T15:07:49.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576902,'en_US') 
;

-- 2019-07-11T15:07:55.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Scope', PrintName='Scope',Updated=TO_TIMESTAMP('2019-07-11 15:07:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576902 AND AD_Language='nl_NL'
;

-- 2019-07-11T15:07:55.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576902,'nl_NL') 
;

-- 2019-07-11T15:11:29.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Alle die dem derzeitigen Filter entsprechen',Updated=TO_TIMESTAMP('2019-07-11 15:11:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541983
;

-- 2019-07-11T15:11:39.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Alle die dem derzeitigen Filter entsprechen',Updated=TO_TIMESTAMP('2019-07-11 15:11:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=541983
;

-- 2019-07-11T15:12:07.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='All which are matched by the current Filter',Updated=TO_TIMESTAMP('2019-07-11 15:12:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=541983
;

-- 2019-07-11T15:15:08.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Alle importieren die dem Filter entsprechen',Updated=TO_TIMESTAMP('2019-07-11 15:15:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541984
;

-- 2019-07-11T15:15:23.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='All which are matched by the filter and were imported',Updated=TO_TIMESTAMP('2019-07-11 15:15:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=541984
;

-- 2019-07-11T15:15:27.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='All which are matched by the filter and were imported',Updated=TO_TIMESTAMP('2019-07-11 15:15:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=541984
;

-- 2019-07-11T15:15:38.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Alle importieren die dem Filter entsprechen',Updated=TO_TIMESTAMP('2019-07-11 15:15:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=541984
;

-- 2019-07-11T15:15:54.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Nur die ausgewählten Datensätze',Updated=TO_TIMESTAMP('2019-07-11 15:15:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541985
;

-- 2019-07-11T15:16:02.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Nur die ausgewählten Datensätze',Updated=TO_TIMESTAMP('2019-07-11 15:16:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=541985
;

-- 2019-07-11T15:16:12.580
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Selected only',Updated=TO_TIMESTAMP('2019-07-11 15:16:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=541985
;

-- 2019-07-11T15:16:17.211
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Selected only',Updated=TO_TIMESTAMP('2019-07-11 15:16:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=541985
;

