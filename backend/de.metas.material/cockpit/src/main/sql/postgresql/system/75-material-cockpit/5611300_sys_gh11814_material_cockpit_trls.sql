-- 2021-11-02T07:46:19.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', IsTranslated='Y', Name='Bedarfssumme', PrintName='Bedarfssumme',Updated=TO_TIMESTAMP('2021-11-02 08:46:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579898 AND AD_Language='de_DE'
;

-- 2021-11-02T07:46:19.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579898,'de_DE') 
;

-- 2021-11-02T07:46:19.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579898,'de_DE') 
;

-- 2021-11-02T07:46:19.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtySupplyRequired', Name='Bedarfssumme', Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', Help=NULL WHERE AD_Element_ID=579898
;

-- 2021-11-02T07:46:19.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtySupplyRequired', Name='Bedarfssumme', Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', Help=NULL, AD_Element_ID=579898 WHERE UPPER(ColumnName)='QTYSUPPLYREQUIRED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-02T07:46:19.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtySupplyRequired', Name='Bedarfssumme', Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', Help=NULL WHERE AD_Element_ID=579898 AND IsCentrallyMaintained='Y'
;

-- 2021-11-02T07:46:19.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Bedarfssumme', Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579898) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579898)
;

-- 2021-11-02T07:46:19.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Bedarfssumme', Name='Bedarfssumme' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579898)
;

-- 2021-11-02T07:46:19.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Bedarfssumme', Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579898
;

-- 2021-11-02T07:46:19.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Bedarfssumme', Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', Help=NULL WHERE AD_Element_ID = 579898
;

-- 2021-11-02T07:46:19.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Bedarfssumme', Description = 'Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579898
;

-- 2021-11-02T07:46:31.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Summe der benötigten Mengen, wo der geplante Bestand geringer als die geplanten Warenausgänge ist.',Updated=TO_TIMESTAMP('2021-11-02 08:46:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579898 AND AD_Language='de_CH'
;

-- 2021-11-02T07:46:31.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579898,'de_CH') 
;

-- 2021-11-02T07:46:41.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Bedarfssumme', PrintName='Bedarfssumme',Updated=TO_TIMESTAMP('2021-11-02 08:46:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579898 AND AD_Language='de_CH'
;

-- 2021-11-02T07:46:41.366Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579898,'de_CH') 
;

-- 2021-11-02T07:47:10.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-11-02 08:47:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579898 AND AD_Language='en_US'
;

-- 2021-11-02T07:47:10.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579898,'en_US') 
;

-- 2021-11-02T07:48:01.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Sum of all required supplies, where the planned stock is below the planned shippings',Updated=TO_TIMESTAMP('2021-11-02 08:48:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579898 AND AD_Language='en_US'
;

-- 2021-11-02T07:48:01.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579898,'en_US') 
;

-- 2021-11-02T07:49:58.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='More technically, this is the sum of all SupplyRequiredEvents that are fired by the material-dispo',Updated=TO_TIMESTAMP('2021-11-02 08:49:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579898 AND AD_Language='en_US'
;

-- 2021-11-02T07:49:58.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579898,'en_US') 
;

-- 2021-11-02T07:52:08.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zu disp. Zugänge', PrintName='Zu disp. Zugänge',Updated=TO_TIMESTAMP('2021-11-02 08:52:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579899 AND AD_Language='de_DE'
;

-- 2021-11-02T07:52:08.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579899,'de_DE') 
;

-- 2021-11-02T07:52:08.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579899,'de_DE') 
;

-- 2021-11-02T07:52:08.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtySupplyToSchedule', Name='Zu disp. Zugänge', Description=NULL, Help=NULL WHERE AD_Element_ID=579899
;

-- 2021-11-02T07:52:08.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtySupplyToSchedule', Name='Zu disp. Zugänge', Description=NULL, Help=NULL, AD_Element_ID=579899 WHERE UPPER(ColumnName)='QTYSUPPLYTOSCHEDULE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-02T07:52:08.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtySupplyToSchedule', Name='Zu disp. Zugänge', Description=NULL, Help=NULL WHERE AD_Element_ID=579899 AND IsCentrallyMaintained='Y'
;

-- 2021-11-02T07:52:08.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zu disp. Zugänge', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579899) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579899)
;

-- 2021-11-02T07:52:08.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zu disp. Zugänge', Name='Zu disp. Zugänge' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579899)
;

-- 2021-11-02T07:52:08.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zu disp. Zugänge', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579899
;

-- 2021-11-02T07:52:08.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zu disp. Zugänge', Description=NULL, Help=NULL WHERE AD_Element_ID = 579899
;

-- 2021-11-02T07:52:08.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zu disp. Zugänge', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579899
;

-- 2021-11-02T07:54:17.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist', Name='Zu disp. Bedarf', PrintName='Zu disp. Bedarf',Updated=TO_TIMESTAMP('2021-11-02 08:54:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579899 AND AD_Language='de_DE'
;

-- 2021-11-02T07:54:17.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579899,'de_DE') 
;

-- 2021-11-02T07:54:17.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579899,'de_DE') 
;

-- 2021-11-02T07:54:17.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtySupplyToSchedule', Name='Zu disp. Bedarf', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist', Help=NULL WHERE AD_Element_ID=579899
;

-- 2021-11-02T07:54:17.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtySupplyToSchedule', Name='Zu disp. Bedarf', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist', Help=NULL, AD_Element_ID=579899 WHERE UPPER(ColumnName)='QTYSUPPLYTOSCHEDULE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-02T07:54:17.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtySupplyToSchedule', Name='Zu disp. Bedarf', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist', Help=NULL WHERE AD_Element_ID=579899 AND IsCentrallyMaintained='Y'
;

-- 2021-11-02T07:54:17.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zu disp. Bedarf', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579899) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579899)
;

-- 2021-11-02T07:54:17.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zu disp. Bedarf', Name='Zu disp. Bedarf' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579899)
;

-- 2021-11-02T07:54:17.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zu disp. Bedarf', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579899
;

-- 2021-11-02T07:54:17.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zu disp. Bedarf', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist', Help=NULL WHERE AD_Element_ID = 579899
;

-- 2021-11-02T07:54:17.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zu disp. Bedarf', Description = 'Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579899
;

-- 2021-11-02T07:54:26.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.',Updated=TO_TIMESTAMP('2021-11-02 08:54:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579899 AND AD_Language='de_DE'
;

-- 2021-11-02T07:54:26.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579899,'de_DE') 
;

-- 2021-11-02T07:54:26.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579899,'de_DE') 
;

-- 2021-11-02T07:54:26.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtySupplyToSchedule', Name='Zu disp. Bedarf', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', Help=NULL WHERE AD_Element_ID=579899
;

-- 2021-11-02T07:54:26.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtySupplyToSchedule', Name='Zu disp. Bedarf', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', Help=NULL, AD_Element_ID=579899 WHERE UPPER(ColumnName)='QTYSUPPLYTOSCHEDULE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-02T07:54:26.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtySupplyToSchedule', Name='Zu disp. Bedarf', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', Help=NULL WHERE AD_Element_ID=579899 AND IsCentrallyMaintained='Y'
;

-- 2021-11-02T07:54:26.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zu disp. Bedarf', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579899) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579899)
;

-- 2021-11-02T07:54:26.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zu disp. Bedarf', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579899
;

-- 2021-11-02T07:54:26.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zu disp. Bedarf', Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', Help=NULL WHERE AD_Element_ID = 579899
;

-- 2021-11-02T07:54:26.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zu disp. Bedarf', Description = 'Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579899
;

-- 2021-11-02T07:54:34.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Teil der Bedarfssumme, der noch nicht durch Einkauf, Produktion oder Warenbewegung gedeckt ist.',Updated=TO_TIMESTAMP('2021-11-02 08:54:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579899 AND AD_Language='de_CH'
;

-- 2021-11-02T07:54:34.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579899,'de_CH') 
;

-- 2021-11-02T07:54:44.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zu disp. Bedarf', PrintName='Zu disp. Bedarf',Updated=TO_TIMESTAMP('2021-11-02 08:54:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579899 AND AD_Language='de_CH'
;

-- 2021-11-02T07:54:44.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579899,'de_CH') 
;

-- 2021-11-02T07:56:43.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Required supplies that are not yet covered by purchase, production or distribution.', IsTranslated='Y', Name='Open requriements', PrintName='Open requriements',Updated=TO_TIMESTAMP('2021-11-02 08:56:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579899 AND AD_Language='en_US'
;

-- 2021-11-02T07:56:43.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579899,'en_US') 
;

-- 2021-11-02T08:09:59.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-11-02 09:09:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579900 AND AD_Language='en_US'
;

-- 2021-11-02T08:09:59.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579900,'en_US') 
;

-- 2021-11-02T08:10:27.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Menge laut "grober" Zählung.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-11-02 09:10:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579900 AND AD_Language='de_DE'
;

-- 2021-11-02T08:10:27.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579900,'de_DE') 
;

-- 2021-11-02T08:10:27.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579900,'de_DE') 
;

-- 2021-11-02T08:10:27.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyStockEstimateCount', Name='Zählbestand', Description='Menge laut "grober" Zählung.', Help=NULL WHERE AD_Element_ID=579900
;

-- 2021-11-02T08:10:27.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateCount', Name='Zählbestand', Description='Menge laut "grober" Zählung.', Help=NULL, AD_Element_ID=579900 WHERE UPPER(ColumnName)='QTYSTOCKESTIMATECOUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-02T08:10:27.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateCount', Name='Zählbestand', Description='Menge laut "grober" Zählung.', Help=NULL WHERE AD_Element_ID=579900 AND IsCentrallyMaintained='Y'
;

-- 2021-11-02T08:10:27.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zählbestand', Description='Menge laut "grober" Zählung.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579900) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579900)
;

-- 2021-11-02T08:10:27.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zählbestand', Description='Menge laut "grober" Zählung.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579900
;

-- 2021-11-02T08:10:27.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zählbestand', Description='Menge laut "grober" Zählung.', Help=NULL WHERE AD_Element_ID = 579900
;

-- 2021-11-02T08:10:27.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zählbestand', Description = 'Menge laut "grober" Zählung.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579900
;

-- 2021-11-02T08:10:43.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Menge laut "grober" Zählung.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-11-02 09:10:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579900 AND AD_Language='de_CH'
;

-- 2021-11-02T08:10:43.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579900,'de_CH') 
;

-- 2021-11-02T08:12:18.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zeitpunkt der Zählung', PrintName='Zeitpunkt der Zählung',Updated=TO_TIMESTAMP('2021-11-02 09:12:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579902 AND AD_Language='de_CH'
;

-- 2021-11-02T08:12:18.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579902,'de_CH') 
;

-- 2021-11-02T08:12:24.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zeitpunkt der Zählung', PrintName='Zeitpunkt der Zählung',Updated=TO_TIMESTAMP('2021-11-02 09:12:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579902 AND AD_Language='de_DE'
;

-- 2021-11-02T08:12:24.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579902,'de_DE') 
;

-- 2021-11-02T08:12:24.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579902,'de_DE') 
;

-- 2021-11-02T08:12:24.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyStockEstimateTime', Name='Zeitpunkt der Zählung', Description=NULL, Help=NULL WHERE AD_Element_ID=579902
;

-- 2021-11-02T08:12:24.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateTime', Name='Zeitpunkt der Zählung', Description=NULL, Help=NULL, AD_Element_ID=579902 WHERE UPPER(ColumnName)='QTYSTOCKESTIMATETIME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-02T08:12:24.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyStockEstimateTime', Name='Zeitpunkt der Zählung', Description=NULL, Help=NULL WHERE AD_Element_ID=579902 AND IsCentrallyMaintained='Y'
;

-- 2021-11-02T08:12:24.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zeitpunkt der Zählung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579902) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579902)
;

-- 2021-11-02T08:12:24.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zeitpunkt der Zählung', Name='Zeitpunkt der Zählung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579902)
;

-- 2021-11-02T08:12:24.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zeitpunkt der Zählung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579902
;

-- 2021-11-02T08:12:24.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zeitpunkt der Zählung', Description=NULL, Help=NULL WHERE AD_Element_ID = 579902
;

-- 2021-11-02T08:12:24.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zeitpunkt der Zählung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579902
;

-- 2021-11-02T08:13:44.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Inventurbestand', PrintName='Inventurbestand',Updated=TO_TIMESTAMP('2021-11-02 09:13:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579903 AND AD_Language='de_CH'
;

-- 2021-11-02T08:13:44.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579903,'de_CH') 
;

-- 2021-11-02T08:13:56.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Inventurbestand', PrintName='Inventurbestand',Updated=TO_TIMESTAMP('2021-11-02 09:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579903 AND AD_Language='de_DE'
;

-- 2021-11-02T08:13:56.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579903,'de_DE') 
;

-- 2021-11-02T08:13:56.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579903,'de_DE') 
;

-- 2021-11-02T08:13:56.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyInventoryCount', Name='Inventurbestand', Description=NULL, Help=NULL WHERE AD_Element_ID=579903
;

-- 2021-11-02T08:13:56.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyInventoryCount', Name='Inventurbestand', Description=NULL, Help=NULL, AD_Element_ID=579903 WHERE UPPER(ColumnName)='QTYINVENTORYCOUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-02T08:13:56.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyInventoryCount', Name='Inventurbestand', Description=NULL, Help=NULL WHERE AD_Element_ID=579903 AND IsCentrallyMaintained='Y'
;

-- 2021-11-02T08:13:56.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Inventurbestand', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579903) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579903)
;

-- 2021-11-02T08:13:56.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Inventurbestand', Name='Inventurbestand' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579903)
;

-- 2021-11-02T08:13:56.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Inventurbestand', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579903
;

-- 2021-11-02T08:13:56.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Inventurbestand', Description=NULL, Help=NULL WHERE AD_Element_ID = 579903
;

-- 2021-11-02T08:13:56.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Inventurbestand', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579903
;

-- 2021-11-02T08:14:00.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-11-02 09:14:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579903 AND AD_Language='en_US'
;

-- 2021-11-02T08:14:00.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579903,'en_US') 
;

-- 2021-11-02T08:14:49.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Bestand laut der letzten Inventur',Updated=TO_TIMESTAMP('2021-11-02 09:14:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579903 AND AD_Language='de_DE'
;

-- 2021-11-02T08:14:49.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579903,'de_DE') 
;

-- 2021-11-02T08:14:49.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579903,'de_DE') 
;

-- 2021-11-02T08:14:49.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyInventoryCount', Name='Inventurbestand', Description='Bestand laut der letzten Inventur', Help=NULL WHERE AD_Element_ID=579903
;

-- 2021-11-02T08:14:49.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyInventoryCount', Name='Inventurbestand', Description='Bestand laut der letzten Inventur', Help=NULL, AD_Element_ID=579903 WHERE UPPER(ColumnName)='QTYINVENTORYCOUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-02T08:14:49.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyInventoryCount', Name='Inventurbestand', Description='Bestand laut der letzten Inventur', Help=NULL WHERE AD_Element_ID=579903 AND IsCentrallyMaintained='Y'
;

-- 2021-11-02T08:14:49.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Inventurbestand', Description='Bestand laut der letzten Inventur', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579903) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579903)
;

-- 2021-11-02T08:14:49.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Inventurbestand', Description='Bestand laut der letzten Inventur', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579903
;

-- 2021-11-02T08:14:49.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Inventurbestand', Description='Bestand laut der letzten Inventur', Help=NULL WHERE AD_Element_ID = 579903
;

-- 2021-11-02T08:14:49.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Inventurbestand', Description = 'Bestand laut der letzten Inventur', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579903
;

-- 2021-11-02T08:14:54.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Bestand laut der letzten Inventur',Updated=TO_TIMESTAMP('2021-11-02 09:14:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579903 AND AD_Language='de_CH'
;

-- 2021-11-02T08:14:54.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579903,'de_CH') 
;

-- 2021-11-02T08:15:45.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Zeipunkt, an dem die Inventur fertig gestellt wurde', IsTranslated='Y', Name='Inventur-Zeit', PrintName='Inventur-Zeit',Updated=TO_TIMESTAMP('2021-11-02 09:15:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579904 AND AD_Language='de_DE'
;

-- 2021-11-02T08:15:45.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579904,'de_DE') 
;

-- 2021-11-02T08:15:45.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579904,'de_DE') 
;

-- 2021-11-02T08:15:45.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyInventoryTime', Name='Inventur-Zeit', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde', Help=NULL WHERE AD_Element_ID=579904
;

-- 2021-11-02T08:15:45.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyInventoryTime', Name='Inventur-Zeit', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde', Help=NULL, AD_Element_ID=579904 WHERE UPPER(ColumnName)='QTYINVENTORYTIME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-02T08:15:45.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyInventoryTime', Name='Inventur-Zeit', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde', Help=NULL WHERE AD_Element_ID=579904 AND IsCentrallyMaintained='Y'
;

-- 2021-11-02T08:15:45.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Inventur-Zeit', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579904) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579904)
;

-- 2021-11-02T08:15:45.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Inventur-Zeit', Name='Inventur-Zeit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579904)
;

-- 2021-11-02T08:15:45.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Inventur-Zeit', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579904
;

-- 2021-11-02T08:15:45.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Inventur-Zeit', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde', Help=NULL WHERE AD_Element_ID = 579904
;

-- 2021-11-02T08:15:45.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Inventur-Zeit', Description = 'Zeipunkt, an dem die Inventur fertig gestellt wurde', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579904
;

-- 2021-11-02T08:15:54.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.',Updated=TO_TIMESTAMP('2021-11-02 09:15:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579904 AND AD_Language='de_CH'
;

-- 2021-11-02T08:15:54.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579904,'de_CH') 
;

-- 2021-11-02T08:15:58.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.',Updated=TO_TIMESTAMP('2021-11-02 09:15:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579904 AND AD_Language='de_DE'
;

-- 2021-11-02T08:15:58.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579904,'de_DE') 
;

-- 2021-11-02T08:15:58.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579904,'de_DE') 
;

-- 2021-11-02T08:15:58.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyInventoryTime', Name='Inventur-Zeit', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.', Help=NULL WHERE AD_Element_ID=579904
;

-- 2021-11-02T08:15:58.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyInventoryTime', Name='Inventur-Zeit', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.', Help=NULL, AD_Element_ID=579904 WHERE UPPER(ColumnName)='QTYINVENTORYTIME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-02T08:15:58.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyInventoryTime', Name='Inventur-Zeit', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.', Help=NULL WHERE AD_Element_ID=579904 AND IsCentrallyMaintained='Y'
;

-- 2021-11-02T08:15:58.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Inventur-Zeit', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579904) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579904)
;

-- 2021-11-02T08:15:58.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Inventur-Zeit', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579904
;

-- 2021-11-02T08:15:58.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Inventur-Zeit', Description='Zeipunkt, an dem die Inventur fertig gestellt wurde.', Help=NULL WHERE AD_Element_ID = 579904
;

-- 2021-11-02T08:15:58.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Inventur-Zeit', Description = 'Zeipunkt, an dem die Inventur fertig gestellt wurde.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579904
;

-- 2021-11-02T08:16:15.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Inventur-Zeit', PrintName='Inventur-Zeit',Updated=TO_TIMESTAMP('2021-11-02 09:16:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579904 AND AD_Language='de_CH'
;

-- 2021-11-02T08:16:15.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579904,'de_CH') 
;

