-- 2022-09-04T09:56:18.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Übergeordneter Pack', PrintName='Übergeordneter Pack',Updated=TO_TIMESTAMP('2022-09-04 12:56:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581053 AND AD_Language='de_CH'
;

-- 2022-09-04T09:56:18.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581053,'de_CH') 
;

-- 2022-09-04T09:56:22.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Übergeordneter Pack', PrintName='Übergeordneter Pack',Updated=TO_TIMESTAMP('2022-09-04 12:56:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581053 AND AD_Language='de_DE'
;

-- 2022-09-04T09:56:22.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581053,'de_DE') 
;

-- 2022-09-04T09:56:22.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581053,'de_DE') 
;

-- 2022-09-04T09:56:22.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='EDI_Desadv_Parent_Pack_ID', Name='Übergeordneter Pack', Description=NULL, Help=NULL WHERE AD_Element_ID=581053
;

-- 2022-09-04T09:56:22.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EDI_Desadv_Parent_Pack_ID', Name='Übergeordneter Pack', Description=NULL, Help=NULL, AD_Element_ID=581053 WHERE UPPER(ColumnName)='EDI_DESADV_PARENT_PACK_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-04T09:56:22.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EDI_Desadv_Parent_Pack_ID', Name='Übergeordneter Pack', Description=NULL, Help=NULL WHERE AD_Element_ID=581053 AND IsCentrallyMaintained='Y'
;

-- 2022-09-04T09:56:22.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Übergeordneter Pack', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581053) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581053)
;

-- 2022-09-04T09:56:22.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Übergeordneter Pack', Name='Übergeordneter Pack' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581053)
;

-- 2022-09-04T09:56:22.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Übergeordneter Pack', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581053
;

-- 2022-09-04T09:56:22.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Übergeordneter Pack', Description=NULL, Help=NULL WHERE AD_Element_ID = 581053
;

-- 2022-09-04T09:56:22.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Übergeordneter Pack', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581053
;

-- 2022-09-04T09:56:27.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Übergeordneter Pack', PrintName='Übergeordneter Pack',Updated=TO_TIMESTAMP('2022-09-04 12:56:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581053 AND AD_Language='nl_NL'
;

-- 2022-09-04T09:56:27.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581053,'nl_NL') 
;

-- 2022-09-04T09:56:37.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Parent Pack', PrintName='Parent Pack',Updated=TO_TIMESTAMP('2022-09-04 12:56:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581053 AND AD_Language='en_US'
;

-- 2022-09-04T09:56:37.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581053,'en_US') 
;

-- 2022-09-04T09:59:48.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Pack Item', PrintName='Pack Item',Updated=TO_TIMESTAMP('2022-09-04 12:59:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581052 AND AD_Language='de_CH'
;

-- 2022-09-04T09:59:48.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581052,'de_CH') 
;

-- 2022-09-04T09:59:50.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Pack Item', PrintName='Pack Item',Updated=TO_TIMESTAMP('2022-09-04 12:59:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581052 AND AD_Language='de_DE'
;

-- 2022-09-04T09:59:50.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581052,'de_DE') 
;

-- 2022-09-04T09:59:50.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581052,'de_DE') 
;

-- 2022-09-04T09:59:50.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='EDI_Desadv_Pack_Item_ID', Name='Pack Item', Description=NULL, Help=NULL WHERE AD_Element_ID=581052
;

-- 2022-09-04T09:59:50.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EDI_Desadv_Pack_Item_ID', Name='Pack Item', Description=NULL, Help=NULL, AD_Element_ID=581052 WHERE UPPER(ColumnName)='EDI_DESADV_PACK_ITEM_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-04T09:59:50.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EDI_Desadv_Pack_Item_ID', Name='Pack Item', Description=NULL, Help=NULL WHERE AD_Element_ID=581052 AND IsCentrallyMaintained='Y'
;

-- 2022-09-04T09:59:50.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Pack Item', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581052) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581052)
;

-- 2022-09-04T09:59:50.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Pack Item', Name='Pack Item' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581052)
;

-- 2022-09-04T09:59:50.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Pack Item', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581052
;

-- 2022-09-04T09:59:50.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Pack Item', Description=NULL, Help=NULL WHERE AD_Element_ID = 581052
;

-- 2022-09-04T09:59:50.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Pack Item', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581052
;

-- 2022-09-04T09:59:53.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Pack Item', PrintName='Pack Item',Updated=TO_TIMESTAMP('2022-09-04 12:59:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581052 AND AD_Language='en_US'
;

-- 2022-09-04T09:59:53.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581052,'en_US') 
;

-- 2022-09-04T09:59:58.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Pack Item', PrintName='Pack Item',Updated=TO_TIMESTAMP('2022-09-04 12:59:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581052 AND AD_Language='nl_NL'
;

-- 2022-09-04T09:59:58.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581052,'nl_NL') 
;

