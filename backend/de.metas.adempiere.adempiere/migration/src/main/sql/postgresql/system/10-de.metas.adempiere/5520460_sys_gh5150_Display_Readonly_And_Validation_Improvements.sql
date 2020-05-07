-- 2019-04-24T19:34:40.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsReadOnly='N', ReadOnlyLogic='@C_DocType_ID/0@ = 540970',Updated=TO_TIMESTAMP('2019-04-24 19:34:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541750
;

-- 2019-04-24T19:39:55.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Abgabemeldung', PrintName='Abgabemeldung',Updated=TO_TIMESTAMP('2019-04-24 19:39:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576686 AND AD_Language='de_CH'
;

-- 2019-04-24T19:39:55.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576686,'de_CH') 
;

-- 2019-04-24T19:40:00.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Abgabemeldung', PrintName='Abgabemeldung',Updated=TO_TIMESTAMP('2019-04-24 19:40:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576686 AND AD_Language='de_DE'
;

-- 2019-04-24T19:40:00.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576686,'de_DE') 
;

-- 2019-04-24T19:40:00.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576686,'de_DE') 
;

-- 2019-04-24T19:40:00.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_Shipment_Declaration_Base_ID', Name='Abgabemeldung', Description=NULL, Help=NULL WHERE AD_Element_ID=576686
;

-- 2019-04-24T19:40:00.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Shipment_Declaration_Base_ID', Name='Abgabemeldung', Description=NULL, Help=NULL, AD_Element_ID=576686 WHERE UPPER(ColumnName)='M_SHIPMENT_DECLARATION_BASE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-24T19:40:00.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Shipment_Declaration_Base_ID', Name='Abgabemeldung', Description=NULL, Help=NULL WHERE AD_Element_ID=576686 AND IsCentrallyMaintained='Y'
;

-- 2019-04-24T19:40:00.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abgabemeldung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576686) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576686)
;

-- 2019-04-24T19:40:00.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Abgabemeldung', Name='Abgabemeldung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576686)
;

-- 2019-04-24T19:40:00.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Abgabemeldung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576686
;

-- 2019-04-24T19:40:00.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Abgabemeldung', Description=NULL, Help=NULL WHERE AD_Element_ID = 576686
;

-- 2019-04-24T19:40:00.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Abgabemeldung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576686
;

-- 2019-04-24T19:40:36.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferscheindoppel', PrintName='Lieferscheindoppel',Updated=TO_TIMESTAMP('2019-04-24 19:40:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576687 AND AD_Language='de_CH'
;

-- 2019-04-24T19:40:36.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576687,'de_CH') 
;

-- 2019-04-24T19:40:41.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferscheindoppel', PrintName='Lieferscheindoppel',Updated=TO_TIMESTAMP('2019-04-24 19:40:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576687 AND AD_Language='de_DE'
;

-- 2019-04-24T19:40:41.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576687,'de_DE') 
;

-- 2019-04-24T19:40:41.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576687,'de_DE') 
;

-- 2019-04-24T19:40:41.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_Shipment_Declaration_Correction_ID', Name='Lieferscheindoppel', Description=NULL, Help=NULL WHERE AD_Element_ID=576687
;

-- 2019-04-24T19:40:41.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Shipment_Declaration_Correction_ID', Name='Lieferscheindoppel', Description=NULL, Help=NULL, AD_Element_ID=576687 WHERE UPPER(ColumnName)='M_SHIPMENT_DECLARATION_CORRECTION_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-24T19:40:41.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Shipment_Declaration_Correction_ID', Name='Lieferscheindoppel', Description=NULL, Help=NULL WHERE AD_Element_ID=576687 AND IsCentrallyMaintained='Y'
;

-- 2019-04-24T19:40:41.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferscheindoppel', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576687) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576687)
;

-- 2019-04-24T19:40:41.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferscheindoppel', Name='Lieferscheindoppel' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576687)
;

-- 2019-04-24T19:40:41.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lieferscheindoppel', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576687
;

-- 2019-04-24T19:40:41.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lieferscheindoppel', Description=NULL, Help=NULL WHERE AD_Element_ID = 576687
;

-- 2019-04-24T19:40:41.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Lieferscheindoppel', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576687
;

-- 2019-04-24T19:41:03.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Shipment Declaration Correction', PrintName='Shipment Declaration Correction',Updated=TO_TIMESTAMP('2019-04-24 19:41:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576687 AND AD_Language='en_US'
;

-- 2019-04-24T19:41:03.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576687,'en_US') 
;

-- 2019-04-24T19:41:09.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Shipment Declaration Correction', PrintName='Shipment Declaration Correction',Updated=TO_TIMESTAMP('2019-04-24 19:41:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576687 AND AD_Language='nl_NL'
;

-- 2019-04-24T19:41:09.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576687,'nl_NL') 
;

-- 2019-04-24T19:41:28.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Shipment Declaration', PrintName='Shipment Declaration',Updated=TO_TIMESTAMP('2019-04-24 19:41:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576686 AND AD_Language='en_US'
;

-- 2019-04-24T19:41:28.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576686,'en_US') 
;

-- 2019-04-24T19:41:31.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-24 19:41:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576686 AND AD_Language='en_US'
;

-- 2019-04-24T19:41:31.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576686,'en_US') 
;

-- 2019-04-24T19:41:35.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Shipment Declaration', PrintName='Shipment Declaration',Updated=TO_TIMESTAMP('2019-04-24 19:41:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576686 AND AD_Language='nl_NL'
;

-- 2019-04-24T19:41:35.101
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576686,'nl_NL') 
;

-- 2019-04-24T19:46:07.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsReadOnly='N', ReadOnlyLogic='@C_DocType_ID/0@ = 540970',Updated=TO_TIMESTAMP('2019-04-24 19:46:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541751
;

-- 2019-04-24T19:46:46.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='Y',Updated=TO_TIMESTAMP('2019-04-24 19:46:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541751
;

-- 2019-04-24T19:47:52.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2019-04-24 19:47:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567849
;

-- 2019-04-24T19:47:54.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration_line','M_InOutLine_ID','NUMERIC(10)',null,null)
;

-- 2019-04-24T19:47:54.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration_line','M_InOutLine_ID',null,'NULL',null)
;

-- 2019-04-24T19:49:32.101
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Product.M_Product_ID = (select M_Product_ID from M_InOutLine where M_InOutLine_ID = @M_InOutLine_ID/0@) OR @M_InOutLine_ID/0@=0',Updated=TO_TIMESTAMP('2019-04-24 19:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540436
;

-- 2019-04-24T19:51:43.245
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(LineNo),0)+10 AS DefaultValue FROM M_Shipment_Declaration_Line WHERE M_Shipment_Declaration_ID=@M_Shipment_Declaration_ID@',Updated=TO_TIMESTAMP('2019-04-24 19:51:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567888
;

-- 2019-04-24T19:51:45.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipment_declaration_line','LineNo','NUMERIC(10)',null,null)
;

