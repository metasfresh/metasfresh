-- 2019-01-17T15:43:55.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET InternalName='MaterialWithdrawal',Updated=TO_TIMESTAMP('2019-01-17 15:43:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541070
;

-- 2019-01-17T15:44:44.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='M_Inventory_MaterialWithdrawal',Updated=TO_TIMESTAMP('2019-01-17 15:44:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540429
;

-- 2019-01-17T15:44:52.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET InternalName='M_Inventory_MaterialWithdrawal',Updated=TO_TIMESTAMP('2019-01-17 15:44:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541070
;

-- 2019-01-17T15:45:45.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 15:45:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Materialentnahme',PrintName='Materialentnahme' WHERE AD_Element_ID=574498 AND AD_Language='de_CH'
;

-- 2019-01-17T15:45:45.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574498,'de_CH') 
;

-- 2019-01-17T15:46:27.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 15:46:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Material Withdrawal',PrintName='Material Withdrawal' WHERE AD_Element_ID=574498 AND AD_Language='en_US'
;

-- 2019-01-17T15:46:27.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574498,'en_US') 
;

-- 2019-01-17T15:46:38.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 15:46:38','YYYY-MM-DD HH24:MI:SS'),PrintName='Materialentnahme' WHERE AD_Element_ID=574498 AND AD_Language='nl_NL'
;

-- 2019-01-17T15:46:38.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574498,'nl_NL') 
;

-- 2019-01-17T15:46:41.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 15:46:41','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',PrintName='Materialentnahme' WHERE AD_Element_ID=574498 AND AD_Language='de_DE'
;

-- 2019-01-17T15:46:41.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574498,'de_DE') 
;

-- 2019-01-17T15:46:41.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(574498,'de_DE') 
;

-- 2019-01-17T15:46:41.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Materialentnahme', Name='Materialnahme' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=574498)
;

-- 2019-01-17T15:46:54.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 15:46:54','YYYY-MM-DD HH24:MI:SS'),Name='Materialentnahme' WHERE AD_Element_ID=574498 AND AD_Language='de_DE'
;

-- 2019-01-17T15:46:54.230
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574498,'de_DE') 
;

-- 2019-01-17T15:46:54.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(574498,'de_DE') 
;

-- 2019-01-17T15:46:54.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Materialentnahme', Description=NULL, Help=NULL WHERE AD_Element_ID=574498
;

-- 2019-01-17T15:46:54.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Materialentnahme', Description=NULL, Help=NULL WHERE AD_Element_ID=574498 AND IsCentrallyMaintained='Y'
;

-- 2019-01-17T15:46:54.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Materialentnahme', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=574498) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 574498)
;

-- 2019-01-17T15:46:54.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Materialentnahme', Name='Materialentnahme' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=574498)
;

-- 2019-01-17T15:46:54.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Materialentnahme', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 574498
;

-- 2019-01-17T15:46:54.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--UPDATE AD_WINDOW SET Name='Materialentnahme', Description=NULL, Help=NULL WHERE AD_Element_ID = 574498
--;

-- 2019-01-17T15:47:15.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 15:47:15','YYYY-MM-DD HH24:MI:SS'),Name='Materialentnahme' WHERE AD_Element_ID=574498 AND AD_Language='nl_NL'
;

-- 2019-01-17T15:47:15.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574498,'nl_NL') 
;

-- 2019-01-17T15:47:30.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-17 15:47:30','YYYY-MM-DD HH24:MI:SS') WHERE AD_Element_ID=574498 AND AD_Language='de_CH'
;

-- 2019-01-17T15:47:30.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574498,'de_CH') 
;

--
-- M_Product_Cantegory identifier shall be Value & Name because name isn't unique
----------------------------------------------------
--
-- 2019-01-18T21:54:01.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30, IsAutocomplete='Y',Updated=TO_TIMESTAMP('2019-01-18 21:54:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559617
;

-- 2019-01-18T21:55:15.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET SeqNo=10,Updated=TO_TIMESTAMP('2019-01-18 21:55:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1776
;

-- 2019-01-18T21:55:35.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET SeqNo=20,Updated=TO_TIMESTAMP('2019-01-18 21:55:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1776
;

-- 2019-01-18T21:55:48.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2019-01-18 21:55:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3017
;

-----------------------------------------------------

--- additional trls, descriptions etc

-- 2019-01-21T13:57:28.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-21 13:57:28','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Produktkategorien',PrintName='Produktkategorien' WHERE AD_Element_ID=573883 AND AD_Language='de_CH'
;

-- 2019-01-21T13:57:28.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573883,'de_CH') 
;

-- 2019-01-21T13:57:31.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-21 13:57:31','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=573883 AND AD_Language='en_US'
;

-- 2019-01-21T13:57:31.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573883,'en_US') 
;

-- 2019-01-21T13:57:37.642
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-21 13:57:37','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Produktkategorien',PrintName='Produktkategorien' WHERE AD_Element_ID=573883 AND AD_Language='de_DE'
;

-- 2019-01-21T13:57:37.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573883,'de_DE') 
;

-- 2019-01-21T13:57:37.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(573883,'de_DE') 
;

-- 2019-01-21T13:57:37.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Produktkategorien', Description=NULL, Help=NULL WHERE AD_Element_ID=573883
;

-- 2019-01-21T13:57:37.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Produktkategorien', Description=NULL, Help=NULL WHERE AD_Element_ID=573883 AND IsCentrallyMaintained='Y'
;

-- 2019-01-21T13:57:37.733
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produktkategorien', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=573883) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 573883)
;

-- 2019-01-21T13:57:37.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Produktkategorien', Name='Produktkategorien' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=573883)
;

-- 2019-01-21T13:57:37.763
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produktkategorien', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 573883
;

-- 2019-01-21T13:57:37.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produktkategorien', Description=NULL, Help=NULL WHERE AD_Element_ID = 573883
;

-- 2019-01-21T13:57:37.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Produktkategorien', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 573883
;

-- 2019-01-21T14:01:42.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-21 14:01:42','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Kommissionier-Lagergruppe ',PrintName='Kommissionier-Lagergruppe ' WHERE AD_Element_ID=543497 AND AD_Language='de_CH'
;

-- 2019-01-21T14:01:42.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543497,'de_CH') 
;

-- 2019-01-21T14:01:45.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-21 14:01:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=543497 AND AD_Language='en_US'
;

-- 2019-01-21T14:01:45.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543497,'en_US') 
;

-- 2019-01-21T14:01:48.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-21 14:01:48','YYYY-MM-DD HH24:MI:SS') WHERE AD_Element_ID=543497 AND AD_Language='nl_NL'
;

-- 2019-01-21T14:01:48.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543497,'nl_NL') 
;

-- 2019-01-21T14:01:51.835
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-21 14:01:51','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Kommissionier-Lagergruppe ',PrintName='Kommissionier-Lagergruppe ' WHERE AD_Element_ID=543497 AND AD_Language='de_DE'
;

-- 2019-01-21T14:01:51.850
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543497,'de_DE') 
;

-- 2019-01-21T14:01:51.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543497,'de_DE') 
;

-- 2019-01-21T14:01:51.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_Warehouse_PickingGroup_ID', Name='Kommissionier-Lagergruppe ', Description=NULL, Help=NULL WHERE AD_Element_ID=543497
;

-- 2019-01-21T14:01:51.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Warehouse_PickingGroup_ID', Name='Kommissionier-Lagergruppe ', Description=NULL, Help=NULL, AD_Element_ID=543497 WHERE UPPER(ColumnName)='M_WAREHOUSE_PICKINGGROUP_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-01-21T14:01:51.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Warehouse_PickingGroup_ID', Name='Kommissionier-Lagergruppe ', Description=NULL, Help=NULL WHERE AD_Element_ID=543497 AND IsCentrallyMaintained='Y'
;

-- 2019-01-21T14:01:51.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kommissionier-Lagergruppe ', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543497) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543497)
;

-- 2019-01-21T14:01:51.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Kommissionier-Lagergruppe ', Name='Kommissionier-Lagergruppe ' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543497)
;

-- 2019-01-21T14:01:51.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kommissionier-Lagergruppe ', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543497
;

-- 2019-01-21T14:01:52.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kommissionier-Lagergruppe ', Description=NULL, Help=NULL WHERE AD_Element_ID = 543497
;

-- 2019-01-21T14:01:52.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Kommissionier-Lagergruppe ', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543497
;

-- 2019-01-21T14:02:46.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=622181
;

-- 2019-01-21T14:06:02.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=626233
;
