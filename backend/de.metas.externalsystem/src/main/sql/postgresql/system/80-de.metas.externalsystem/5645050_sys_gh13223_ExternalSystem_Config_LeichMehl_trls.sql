-- 2022-06-24T06:34:40.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Wurzelverzeichnis, in dem alle PLU-Dateien enthalten sind', Name='Produkt-Verzeichnis', PrintName='Produkt-Verzeichnis',Updated=TO_TIMESTAMP('2022-06-24 09:34:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581059
;

-- 2022-06-24T06:34:40.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Product_BaseFolderName', Name='Produkt-Verzeichnis', Description='Wurzelverzeichnis, in dem alle PLU-Dateien enthalten sind', Help=NULL WHERE AD_Element_ID=581059
;

-- 2022-06-24T06:34:40.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Product_BaseFolderName', Name='Produkt-Verzeichnis', Description='Wurzelverzeichnis, in dem alle PLU-Dateien enthalten sind', Help=NULL, AD_Element_ID=581059 WHERE UPPER(ColumnName)='PRODUCT_BASEFOLDERNAME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-06-24T06:34:40.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Product_BaseFolderName', Name='Produkt-Verzeichnis', Description='Wurzelverzeichnis, in dem alle PLU-Dateien enthalten sind', Help=NULL WHERE AD_Element_ID=581059 AND IsCentrallyMaintained='Y'
;

-- 2022-06-24T06:34:40.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produkt-Verzeichnis', Description='Wurzelverzeichnis, in dem alle PLU-Dateien enthalten sind', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581059) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581059)
;

-- 2022-06-24T06:34:40.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Produkt-Verzeichnis', Name='Produkt-Verzeichnis' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581059)
;

-- 2022-06-24T06:34:40.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produkt-Verzeichnis', Description='Wurzelverzeichnis, in dem alle PLU-Dateien enthalten sind', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581059
;

-- 2022-06-24T06:34:40.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produkt-Verzeichnis', Description='Wurzelverzeichnis, in dem alle PLU-Dateien enthalten sind', Help=NULL WHERE AD_Element_ID = 581059
;

-- 2022-06-24T06:34:40.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Produkt-Verzeichnis', Description = 'Wurzelverzeichnis, in dem alle PLU-Dateien enthalten sind', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581059
;

-- 2022-06-24T06:35:00.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wurzelverzeichnis, in dem alle PLU-Dateien enthalten sind', Name='Produkt-Verzeichnis', PrintName='Produkt-Verzeichnis',Updated=TO_TIMESTAMP('2022-06-24 09:35:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581059 AND AD_Language='de_CH'
;

-- 2022-06-24T06:35:00.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581059,'de_CH') 
;

-- 2022-06-24T06:35:09.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wurzelverzeichnis, in dem alle PLU-Dateien enthalten sind', Name='Produkt-Verzeichnis', PrintName='Produkt-Verzeichnis',Updated=TO_TIMESTAMP('2022-06-24 09:35:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581059 AND AD_Language='de_DE'
;

-- 2022-06-24T06:35:09.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581059,'de_DE') 
;

-- 2022-06-24T06:35:09.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581059,'de_DE') 
;

-- 2022-06-24T06:35:09.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Product_BaseFolderName', Name='Produkt-Verzeichnis', Description='Wurzelverzeichnis, in dem alle PLU-Dateien enthalten sind', Help=NULL WHERE AD_Element_ID=581059
;

-- 2022-06-24T06:35:09.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Product_BaseFolderName', Name='Produkt-Verzeichnis', Description='Wurzelverzeichnis, in dem alle PLU-Dateien enthalten sind', Help=NULL, AD_Element_ID=581059 WHERE UPPER(ColumnName)='PRODUCT_BASEFOLDERNAME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-06-24T06:35:09.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Product_BaseFolderName', Name='Produkt-Verzeichnis', Description='Wurzelverzeichnis, in dem alle PLU-Dateien enthalten sind', Help=NULL WHERE AD_Element_ID=581059 AND IsCentrallyMaintained='Y'
;

-- 2022-06-24T06:35:09.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produkt-Verzeichnis', Description='Wurzelverzeichnis, in dem alle PLU-Dateien enthalten sind', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581059) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581059)
;

-- 2022-06-24T06:35:09.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Produkt-Verzeichnis', Name='Produkt-Verzeichnis' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581059)
;

-- 2022-06-24T06:35:09.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produkt-Verzeichnis', Description='Wurzelverzeichnis, in dem alle PLU-Dateien enthalten sind', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581059
;

-- 2022-06-24T06:35:09.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produkt-Verzeichnis', Description='Wurzelverzeichnis, in dem alle PLU-Dateien enthalten sind', Help=NULL WHERE AD_Element_ID = 581059
;

-- 2022-06-24T06:35:09.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Produkt-Verzeichnis', Description = 'Wurzelverzeichnis, in dem alle PLU-Dateien enthalten sind', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581059
;

-- 2022-06-24T06:35:21.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wurzelverzeichnis, in dem alle PLU-Dateien enthalten sind', Name='Produkt-Verzeichnis', PrintName='Produkt-Verzeichnis',Updated=TO_TIMESTAMP('2022-06-24 09:35:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581059 AND AD_Language='nl_NL'
;

-- 2022-06-24T06:35:21.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581059,'nl_NL') 
;

-- 2022-06-24T06:35:36.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Root directly that contains all PLU Files', Name='Product directory', PrintName='Product directory',Updated=TO_TIMESTAMP('2022-06-24 09:35:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581059 AND AD_Language='en_US'
;

-- 2022-06-24T06:35:36.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581059,'en_US') 
;

-- 2022-06-24T06:37:32.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='IP Adresse oder Hostname des Leich + Mehl Geräts', Name='LANScale Adresse', PrintName='LANScale Adresse',Updated=TO_TIMESTAMP('2022-06-24 09:37:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581061
;

-- 2022-06-24T06:37:32.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='TCP_Host', Name='LANScale Adresse', Description='IP Adresse oder Hostname des Leich + Mehl Geräts', Help=NULL WHERE AD_Element_ID=581061
;

-- 2022-06-24T06:37:32.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TCP_Host', Name='LANScale Adresse', Description='IP Adresse oder Hostname des Leich + Mehl Geräts', Help=NULL, AD_Element_ID=581061 WHERE UPPER(ColumnName)='TCP_HOST' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-06-24T06:37:32.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TCP_Host', Name='LANScale Adresse', Description='IP Adresse oder Hostname des Leich + Mehl Geräts', Help=NULL WHERE AD_Element_ID=581061 AND IsCentrallyMaintained='Y'
;

-- 2022-06-24T06:37:32.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='LANScale Adresse', Description='IP Adresse oder Hostname des Leich + Mehl Geräts', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581061) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581061)
;

-- 2022-06-24T06:37:32.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='LANScale Adresse', Name='LANScale Adresse' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581061)
;

-- 2022-06-24T06:37:32.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='LANScale Adresse', Description='IP Adresse oder Hostname des Leich + Mehl Geräts', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581061
;

-- 2022-06-24T06:37:32.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='LANScale Adresse', Description='IP Adresse oder Hostname des Leich + Mehl Geräts', Help=NULL WHERE AD_Element_ID = 581061
;

-- 2022-06-24T06:37:32.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'LANScale Adresse', Description = 'IP Adresse oder Hostname des Leich + Mehl Geräts', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581061
;

-- 2022-06-24T06:37:52.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='IP Adresse oder Hostname des Leich + Mehl Geräts', Name='LANScale Adresse', PrintName='LANScale Adresse',Updated=TO_TIMESTAMP('2022-06-24 09:37:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581061 AND AD_Language='de_CH'
;

-- 2022-06-24T06:37:52.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581061,'de_CH') 
;

-- 2022-06-24T06:38:00.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='IP Adresse oder Hostname des Leich + Mehl Geräts', Name='LANScale Adresse', PrintName='LANScale Adresse',Updated=TO_TIMESTAMP('2022-06-24 09:38:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581061 AND AD_Language='de_DE'
;

-- 2022-06-24T06:38:00.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581061,'de_DE') 
;

-- 2022-06-24T06:38:00.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581061,'de_DE') 
;

-- 2022-06-24T06:38:00.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='TCP_Host', Name='LANScale Adresse', Description='IP Adresse oder Hostname des Leich + Mehl Geräts', Help=NULL WHERE AD_Element_ID=581061
;

-- 2022-06-24T06:38:00.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TCP_Host', Name='LANScale Adresse', Description='IP Adresse oder Hostname des Leich + Mehl Geräts', Help=NULL, AD_Element_ID=581061 WHERE UPPER(ColumnName)='TCP_HOST' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-06-24T06:38:00.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TCP_Host', Name='LANScale Adresse', Description='IP Adresse oder Hostname des Leich + Mehl Geräts', Help=NULL WHERE AD_Element_ID=581061 AND IsCentrallyMaintained='Y'
;

-- 2022-06-24T06:38:00.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='LANScale Adresse', Description='IP Adresse oder Hostname des Leich + Mehl Geräts', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581061) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581061)
;

-- 2022-06-24T06:38:00.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='LANScale Adresse', Name='LANScale Adresse' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581061)
;

-- 2022-06-24T06:38:00.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='LANScale Adresse', Description='IP Adresse oder Hostname des Leich + Mehl Geräts', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581061
;

-- 2022-06-24T06:38:00.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='LANScale Adresse', Description='IP Adresse oder Hostname des Leich + Mehl Geräts', Help=NULL WHERE AD_Element_ID = 581061
;

-- 2022-06-24T06:38:00.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'LANScale Adresse', Description = 'IP Adresse oder Hostname des Leich + Mehl Geräts', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581061
;

-- 2022-06-24T06:38:09.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='IP Adresse oder Hostname des Leich + Mehl Geräts', Name='LANScale Adresse', PrintName='LANScale Adresse',Updated=TO_TIMESTAMP('2022-06-24 09:38:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581061 AND AD_Language='nl_NL'
;

-- 2022-06-24T06:38:09.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581061,'nl_NL') 
;

-- 2022-06-24T06:38:20.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='IP-address or hostname of the Leich + Mehl device', Name='LANScale Address', PrintName='LANScale Address',Updated=TO_TIMESTAMP('2022-06-24 09:38:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581061 AND AD_Language='en_US'
;

-- 2022-06-24T06:38:20.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581061,'en_US') 
;

-- 2022-06-24T06:38:50.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Portnummer des Leich + Mehl Geräts', Name='LANScale Port', PrintName='LANScale Port',Updated=TO_TIMESTAMP('2022-06-24 09:38:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581060
;

-- 2022-06-24T06:38:50.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='TCP_PortNumber', Name='LANScale Port', Description='Portnummer des Leich + Mehl Geräts', Help=NULL WHERE AD_Element_ID=581060
;

-- 2022-06-24T06:38:50.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TCP_PortNumber', Name='LANScale Port', Description='Portnummer des Leich + Mehl Geräts', Help=NULL, AD_Element_ID=581060 WHERE UPPER(ColumnName)='TCP_PORTNUMBER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-06-24T06:38:50.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TCP_PortNumber', Name='LANScale Port', Description='Portnummer des Leich + Mehl Geräts', Help=NULL WHERE AD_Element_ID=581060 AND IsCentrallyMaintained='Y'
;

-- 2022-06-24T06:38:50.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='LANScale Port', Description='Portnummer des Leich + Mehl Geräts', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581060) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581060)
;

-- 2022-06-24T06:38:50.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='LANScale Port', Name='LANScale Port' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581060)
;

-- 2022-06-24T06:38:50.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='LANScale Port', Description='Portnummer des Leich + Mehl Geräts', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581060
;

-- 2022-06-24T06:38:50.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='LANScale Port', Description='Portnummer des Leich + Mehl Geräts', Help=NULL WHERE AD_Element_ID = 581060
;

-- 2022-06-24T06:38:50.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'LANScale Port', Description = 'Portnummer des Leich + Mehl Geräts', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581060
;

-- 2022-06-24T06:39:04.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Portnummer des Leich + Mehl Geräts', Name='LANScale Port', PrintName='LANScale Port',Updated=TO_TIMESTAMP('2022-06-24 09:39:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581060 AND AD_Language='de_CH'
;

-- 2022-06-24T06:39:04.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581060,'de_CH') 
;

-- 2022-06-24T06:39:19.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Portnummer des Leich + Mehl Geräts', Name='LANScale Port', PrintName='LANScale Port',Updated=TO_TIMESTAMP('2022-06-24 09:39:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581060 AND AD_Language='de_DE'
;

-- 2022-06-24T06:39:19.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581060,'de_DE') 
;

-- 2022-06-24T06:39:19.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581060,'de_DE') 
;

-- 2022-06-24T06:39:19.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='TCP_PortNumber', Name='LANScale Port', Description='Portnummer des Leich + Mehl Geräts', Help=NULL WHERE AD_Element_ID=581060
;

-- 2022-06-24T06:39:19.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TCP_PortNumber', Name='LANScale Port', Description='Portnummer des Leich + Mehl Geräts', Help=NULL, AD_Element_ID=581060 WHERE UPPER(ColumnName)='TCP_PORTNUMBER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-06-24T06:39:19.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='TCP_PortNumber', Name='LANScale Port', Description='Portnummer des Leich + Mehl Geräts', Help=NULL WHERE AD_Element_ID=581060 AND IsCentrallyMaintained='Y'
;

-- 2022-06-24T06:39:19.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='LANScale Port', Description='Portnummer des Leich + Mehl Geräts', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581060) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581060)
;

-- 2022-06-24T06:39:19.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='LANScale Port', Name='LANScale Port' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581060)
;

-- 2022-06-24T06:39:19.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='LANScale Port', Description='Portnummer des Leich + Mehl Geräts', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581060
;

-- 2022-06-24T06:39:19.499Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='LANScale Port', Description='Portnummer des Leich + Mehl Geräts', Help=NULL WHERE AD_Element_ID = 581060
;

-- 2022-06-24T06:39:19.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'LANScale Port', Description = 'Portnummer des Leich + Mehl Geräts', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581060
;

-- 2022-06-24T06:39:28.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Portnummer des Leich + Mehl Geräts', Name='LANScale Port', PrintName='LANScale Port',Updated=TO_TIMESTAMP('2022-06-24 09:39:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581060 AND AD_Language='nl_NL'
;

-- 2022-06-24T06:39:28.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581060,'nl_NL') 
;

-- 2022-06-24T06:39:51.477Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Port number of the Leich + Mehl device', Name='LANScale port', PrintName='LANScale port',Updated=TO_TIMESTAMP('2022-06-24 09:39:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581060 AND AD_Language='en_US'
;

-- 2022-06-24T06:39:51.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581060,'en_US') 
;

-- 2022-06-24T06:43:29.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Leich + Mehl', PrintName='Leich + Mehl',Updated=TO_TIMESTAMP('2022-06-24 09:43:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580811
;

-- 2022-06-24T06:43:29.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalSystem_Config_LeichMehl_ID', Name='Leich + Mehl', Description=NULL, Help=NULL WHERE AD_Element_ID=580811
;

-- 2022-06-24T06:43:29.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Config_LeichMehl_ID', Name='Leich + Mehl', Description=NULL, Help=NULL, AD_Element_ID=580811 WHERE UPPER(ColumnName)='EXTERNALSYSTEM_CONFIG_LEICHMEHL_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-06-24T06:43:29.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Config_LeichMehl_ID', Name='Leich + Mehl', Description=NULL, Help=NULL WHERE AD_Element_ID=580811 AND IsCentrallyMaintained='Y'
;

-- 2022-06-24T06:43:29.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Leich + Mehl', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580811) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580811)
;

-- 2022-06-24T06:43:29.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Leich + Mehl', Name='Leich + Mehl' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580811)
;

-- 2022-06-24T06:43:29.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Leich + Mehl', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580811
;

-- 2022-06-24T06:43:29.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Leich + Mehl', Description=NULL, Help=NULL WHERE AD_Element_ID = 580811
;

-- 2022-06-24T06:43:29.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Leich + Mehl', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580811
;

-- 2022-06-24T06:43:40.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Leich + Mehl', PrintName='Leich + Mehl',Updated=TO_TIMESTAMP('2022-06-24 09:43:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580811 AND AD_Language='de_CH'
;

-- 2022-06-24T06:43:40.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580811,'de_CH') 
;

-- 2022-06-24T06:43:42.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Leich + Mehl',Updated=TO_TIMESTAMP('2022-06-24 09:43:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580811 AND AD_Language='de_DE'
;

-- 2022-06-24T06:43:42.517Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580811,'de_DE') 
;

-- 2022-06-24T06:43:42.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580811,'de_DE') 
;

-- 2022-06-24T06:43:42.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalSystem_Config_LeichMehl_ID', Name='Leich + Mehl', Description=NULL, Help=NULL WHERE AD_Element_ID=580811
;

-- 2022-06-24T06:43:42.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Config_LeichMehl_ID', Name='Leich + Mehl', Description=NULL, Help=NULL, AD_Element_ID=580811 WHERE UPPER(ColumnName)='EXTERNALSYSTEM_CONFIG_LEICHMEHL_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-06-24T06:43:42.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Config_LeichMehl_ID', Name='Leich + Mehl', Description=NULL, Help=NULL WHERE AD_Element_ID=580811 AND IsCentrallyMaintained='Y'
;

-- 2022-06-24T06:43:42.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Leich + Mehl', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580811) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580811)
;

-- 2022-06-24T06:43:42.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='LeichUndMehl', Name='Leich + Mehl' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580811)
;

-- 2022-06-24T06:43:42.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Leich + Mehl', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580811
;

-- 2022-06-24T06:43:42.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Leich + Mehl', Description=NULL, Help=NULL WHERE AD_Element_ID = 580811
;

-- 2022-06-24T06:43:42.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Leich + Mehl', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580811
;

-- 2022-06-24T06:43:48.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Leich + Mehl',Updated=TO_TIMESTAMP('2022-06-24 09:43:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580811 AND AD_Language='de_DE'
;

-- 2022-06-24T06:43:48.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580811,'de_DE') 
;

-- 2022-06-24T06:43:48.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580811,'de_DE') 
;

-- 2022-06-24T06:43:48.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Leich + Mehl', Name='Leich + Mehl' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580811)
;

-- 2022-06-24T06:43:51.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Leich + Mehl', PrintName='Leich + Mehl',Updated=TO_TIMESTAMP('2022-06-24 09:43:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580811 AND AD_Language='en_US'
;

-- 2022-06-24T06:43:51.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580811,'en_US') 
;

-- 2022-06-24T06:43:53.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Leich + Mehl', PrintName='Leich + Mehl',Updated=TO_TIMESTAMP('2022-06-24 09:43:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580811 AND AD_Language='nl_NL'
;

-- 2022-06-24T06:43:53.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580811,'nl_NL') 
;

-- 2022-06-24T06:47:18.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581062,0,TO_TIMESTAMP('2022-06-24 09:47:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','External system config Leich + Mehl','External system config Leich + Mehl',TO_TIMESTAMP('2022-06-24 09:47:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-24T06:47:18.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581062 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-06-24T06:47:31.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET AD_Element_ID=581062, Description=NULL, Help=NULL, Name='External system config Leich + Mehl',Updated=TO_TIMESTAMP('2022-06-24 09:47:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541540
;

-- 2022-06-24T06:47:31.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='External system config Leich + Mehl',Updated=TO_TIMESTAMP('2022-06-24 09:47:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541966
;

-- 2022-06-24T06:47:32.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(581062) 
;

-- 2022-06-24T06:47:32.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541540
;

-- 2022-06-24T06:47:32.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Window(541540)
;

-- 2022-06-24T06:50:32.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='External system config Leich + Mehl', PrintName='External system config Leich + Mehl',Updated=TO_TIMESTAMP('2022-06-24 09:50:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580811
;

-- 2022-06-24T06:50:32.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalSystem_Config_LeichMehl_ID', Name='External system config Leich + Mehl', Description=NULL, Help=NULL WHERE AD_Element_ID=580811
;

-- 2022-06-24T06:50:32.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Config_LeichMehl_ID', Name='External system config Leich + Mehl', Description=NULL, Help=NULL, AD_Element_ID=580811 WHERE UPPER(ColumnName)='EXTERNALSYSTEM_CONFIG_LEICHMEHL_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-06-24T06:50:32.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Config_LeichMehl_ID', Name='External system config Leich + Mehl', Description=NULL, Help=NULL WHERE AD_Element_ID=580811 AND IsCentrallyMaintained='Y'
;

-- 2022-06-24T06:50:32.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='External system config Leich + Mehl', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580811) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580811)
;

-- 2022-06-24T06:50:32.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='External system config Leich + Mehl', Name='External system config Leich + Mehl' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580811)
;

-- 2022-06-24T06:50:32.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='External system config Leich + Mehl', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580811
;

-- 2022-06-24T06:50:32.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='External system config Leich + Mehl', Description=NULL, Help=NULL WHERE AD_Element_ID = 580811
;

-- 2022-06-24T06:50:32.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'External system config Leich + Mehl', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580811
;

-- 2022-06-24T07:01:16.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581063,0,TO_TIMESTAMP('2022-06-24 10:01:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Produkt-Zuordnung','Produkt-Zuordnung',TO_TIMESTAMP('2022-06-24 10:01:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-24T07:01:16.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581063 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-06-24T07:01:35.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Product mapping', PrintName='Product mapping',Updated=TO_TIMESTAMP('2022-06-24 10:01:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581063 AND AD_Language='en_US'
;

-- 2022-06-24T07:01:35.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581063,'en_US') 
;

-- 2022-06-24T07:02:14.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Element_ID=581063, CommitWarning=NULL, Description=NULL, Help=NULL, Name='Produkt-Zuordnung',Updated=TO_TIMESTAMP('2022-06-24 10:02:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546389
;

-- 2022-06-24T07:02:14.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(581063) 
;

/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- 2022-06-24T07:02:14.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546389)
;

-- 2022-06-24T07:04:03.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET AD_Element_ID=581062, Description=NULL, Name='External system config Leich + Mehl', WEBUI_NameBrowse=NULL, WEBUI_NameNew=NULL, WEBUI_NameNewBreadcrumb=NULL,Updated=TO_TIMESTAMP('2022-06-24 10:04:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541966
;

-- 2022-06-24T07:04:03.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(581062) 
;

-- 2022-06-30T15:40:04.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Sendet die ausgewählten Produktionsaufträge an die entsprechende Leich & Mehl TCP-Server Konfiguration.',Updated=TO_TIMESTAMP('2022-06-30 18:40:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585045
;

-- 2022-06-30T15:40:11.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Sendet die ausgewählten Produktionsaufträge an die entsprechende Leich & Mehl TCP-Server Konfiguration.', Help=NULL, Name='An Leich + Mehl PAW senden',Updated=TO_TIMESTAMP('2022-06-30 18:40:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585045
;

-- 2022-06-30T15:40:11.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Sendet die ausgewählten Produktionsaufträge an die entsprechende Leich & Mehl TCP-Server Konfiguration.',Updated=TO_TIMESTAMP('2022-06-30 18:40:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585045
;

-- 2022-06-30T15:40:15.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Sends the selected manufacturing orders to the corresponding Leich & Mehl TCP-Server configuration.',Updated=TO_TIMESTAMP('2022-06-30 18:40:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585045
;

-- 2022-06-30T15:40:18.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Sendet die ausgewählten Produktionsaufträge an die entsprechende Leich & Mehl TCP-Server Konfiguration.',Updated=TO_TIMESTAMP('2022-06-30 18:40:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585045
;
