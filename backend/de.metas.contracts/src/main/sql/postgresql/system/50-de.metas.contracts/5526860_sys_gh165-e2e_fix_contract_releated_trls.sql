-- 2019-07-08T13:15:48.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vertrag autom. verlängern',Updated=TO_TIMESTAMP('2019-07-08 13:15:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543934 AND AD_Language='de_CH'
;

-- 2019-07-08T13:15:48.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543934,'de_CH') 
;

-- 2019-07-08T13:15:51.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vertrag autom. verlängern',Updated=TO_TIMESTAMP('2019-07-08 13:15:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543934 AND AD_Language='de_DE'
;

-- 2019-07-08T13:15:51.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543934,'de_DE') 
;

-- 2019-07-08T13:15:51.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543934,'de_DE') 
;

-- 2019-07-08T13:15:51.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExtensionType', Name='Vertrag autom. verlängern', Description=NULL, Help=NULL WHERE AD_Element_ID=543934
;

-- 2019-07-08T13:15:51.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExtensionType', Name='Vertrag autom. verlängern', Description=NULL, Help=NULL, AD_Element_ID=543934 WHERE UPPER(ColumnName)='EXTENSIONTYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-08T13:15:51.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExtensionType', Name='Vertrag autom. verlängern', Description=NULL, Help=NULL WHERE AD_Element_ID=543934 AND IsCentrallyMaintained='Y'
;

-- 2019-07-08T13:15:51.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vertrag autom. verlängern', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543934) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543934)
;

-- 2019-07-08T13:15:51.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Vertrag autom. verlängern', Name='Vertrag autom. verlängern' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543934)
;

-- 2019-07-08T13:15:51.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vertrag autom. verlängern', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543934
;

-- 2019-07-08T13:15:51.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vertrag autom. verlängern', Description=NULL, Help=NULL WHERE AD_Element_ID = 543934
;

-- 2019-07-08T13:15:51.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vertrag autom. verlängern', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543934
;

-- 2019-07-08T13:15:57.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Extension Type',Updated=TO_TIMESTAMP('2019-07-08 13:15:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543934 AND AD_Language='en_US'
;

-- 2019-07-08T13:15:57.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543934,'en_US') 
;

-- 2019-07-08T13:16:03.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-08 13:16:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543934 AND AD_Language='de_CH'
;

-- 2019-07-08T13:16:03.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543934,'de_CH') 
;

-- 2019-07-08T13:16:03.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-08 13:16:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543934 AND AD_Language='en_US'
;

-- 2019-07-08T13:16:03.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543934,'en_US') 
;

-- 2019-07-08T13:16:05.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-08 13:16:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543934 AND AD_Language='de_DE'
;

-- 2019-07-08T13:16:05.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543934,'de_DE') 
;

-- 2019-07-08T13:16:05.394
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543934,'de_DE') 
;

-- 2019-07-08T13:16:34.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-08 13:16:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=541602
;

-- 2019-07-08T13:18:14.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Sofort alle Vertragsperioden verlängern',Updated=TO_TIMESTAMP('2019-07-08 13:18:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=541602
;

-- 2019-07-08T13:19:08.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Die die aktuelle und alle folgenden Vertragsperioden automatisch.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-08 13:19:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=541602
;

-- 2019-07-08T13:19:14.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Die die aktuelle und alle folgenden Vertragsperioden automatisch.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-08 13:19:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=541602
;

-- 2019-07-08T13:19:56.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Verlängert die aktuelle und alle folgenden Vertragsperioden automatisch beim Fertig stellen.',Updated=TO_TIMESTAMP('2019-07-08 13:19:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=541602
;

-- 2019-07-08T13:20:05.764
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Verlängert die aktuelle und alle folgenden Vertragsperioden automatisch beim Fertig stellen.',Updated=TO_TIMESTAMP('2019-07-08 13:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=541602
;

-- 2019-07-08T13:24:16.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Erste Vertragsperiode verlängern',Updated=TO_TIMESTAMP('2019-07-08 13:24:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=541603
;

-- 2019-07-08T13:24:23.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Erste Vertragsperiode verlängern',Updated=TO_TIMESTAMP('2019-07-08 13:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=541603
;

-- 2019-07-08T13:24:26.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-08 13:24:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=541603
;

-- 2019-07-08T13:56:34.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-08 13:56:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=541189
;

-- 2019-07-08T13:56:39.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-08 13:56:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=541189
;

-- 2019-07-08T13:57:45.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Procurement contract',Updated=TO_TIMESTAMP('2019-07-08 13:57:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=541189
;

-- 2019-07-08T13:59:20.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List_Trl WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=540461
;

-- 2019-07-08T13:59:23.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List_Trl WHERE AD_Language='en_GB' AND AD_Ref_List_ID=540461
;

-- 2019-07-08T13:59:31.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Regelmäßige Lieferungen ohne explizite Beauftragung jeder einzelnen Lieferung',Updated=TO_TIMESTAMP('2019-07-08 13:59:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=540461
;

-- 2019-07-08T13:59:35.365
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-08 13:59:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=540461
;

-- 2019-07-08T13:59:47.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='', IsTranslated='Y', Name='Subscription',Updated=TO_TIMESTAMP('2019-07-08 13:59:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=540461
;

-- 2019-07-08T13:59:52.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-08 13:59:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=540461
;

-- 2019-07-08T13:59:57.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List_Trl WHERE AD_Language='it_CH' AND AD_Ref_List_ID=540461
;

-- 2019-07-08T14:01:17.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Gemäß Lagerkonferenz',Updated=TO_TIMESTAMP('2019-07-08 14:01:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=540826
;

-- 2019-07-08T14:01:21.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List_Trl WHERE AD_Language='it_CH' AND AD_Ref_List_ID=540826
;

-- 2019-07-08T14:01:25.235
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List_Trl WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=540826
;

-- 2019-07-08T14:01:27.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List_Trl WHERE AD_Language='en_GB' AND AD_Ref_List_ID=540826
;

-- 2019-07-08T14:01:35.367
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Gemäß Lagerkonferenz',Updated=TO_TIMESTAMP('2019-07-08 14:01:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=540826
;

-- 2019-07-08T14:02:18.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Quality-based',Updated=TO_TIMESTAMP('2019-07-08 14:02:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=540826
;

