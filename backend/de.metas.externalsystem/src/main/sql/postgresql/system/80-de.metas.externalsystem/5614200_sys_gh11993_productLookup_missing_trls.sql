-- 2021-11-18T06:19:16.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produkt-Zuordnung', PrintName='Produkt-Zuordnung',Updated=TO_TIMESTAMP('2021-11-18 08:19:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580241 AND AD_Language='de_CH'
;

-- 2021-11-18T06:19:16.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580241,'de_CH') 
;

-- 2021-11-18T06:19:19.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produkt-Zuordnung', PrintName='Produkt-Zuordnung',Updated=TO_TIMESTAMP('2021-11-18 08:19:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580241 AND AD_Language='de_DE'
;

-- 2021-11-18T06:19:19.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580241,'de_DE') 
;

-- 2021-11-18T06:19:19.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580241,'de_DE') 
;

-- 2021-11-18T06:19:19.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ProductLookup', Name='Produkt-Zuordnung', Description=NULL, Help=NULL WHERE AD_Element_ID=580241
;

-- 2021-11-18T06:19:19.756Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProductLookup', Name='Produkt-Zuordnung', Description=NULL, Help=NULL, AD_Element_ID=580241 WHERE UPPER(ColumnName)='PRODUCTLOOKUP' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-18T06:19:19.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProductLookup', Name='Produkt-Zuordnung', Description=NULL, Help=NULL WHERE AD_Element_ID=580241 AND IsCentrallyMaintained='Y'
;

-- 2021-11-18T06:19:19.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produkt-Zuordnung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580241) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580241)
;

-- 2021-11-18T06:19:19.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Produkt-Zuordnung', Name='Produkt-Zuordnung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580241)
;

-- 2021-11-18T06:19:19.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produkt-Zuordnung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580241
;

-- 2021-11-18T06:19:19.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produkt-Zuordnung', Description=NULL, Help=NULL WHERE AD_Element_ID = 580241
;

-- 2021-11-18T06:19:19.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Produkt-Zuordnung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580241
;

-- 2021-11-18T06:19:25.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produkt-Zuordnung', PrintName='Produkt-Zuordnung',Updated=TO_TIMESTAMP('2021-11-18 08:19:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580241 AND AD_Language='nl_NL'
;

-- 2021-11-18T06:19:25.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580241,'nl_NL') 
;

-- 2021-11-18T06:19:57.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest, auf welche Art in metasfresh das Produkt zu einem Shopware <code>line-item</code> ermittelt wird',Updated=TO_TIMESTAMP('2021-11-18 08:19:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580241 AND AD_Language='de_CH'
;

-- 2021-11-18T06:19:57.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580241,'de_CH') 
;

-- 2021-11-18T06:20:01.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest, auf welche Art in metasfresh das Produkt zu einem Shopware <code>line-item</code> ermittelt wird',Updated=TO_TIMESTAMP('2021-11-18 08:20:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580241 AND AD_Language='de_DE'
;

-- 2021-11-18T06:20:01.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580241,'de_DE') 
;

-- 2021-11-18T06:20:01.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580241,'de_DE') 
;

-- 2021-11-18T06:20:01.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ProductLookup', Name='Produkt-Zuordnung', Description='Legt fest, auf welche Art in metasfresh das Produkt zu einem Shopware <code>line-item</code> ermittelt wird', Help=NULL WHERE AD_Element_ID=580241
;

-- 2021-11-18T06:20:01.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProductLookup', Name='Produkt-Zuordnung', Description='Legt fest, auf welche Art in metasfresh das Produkt zu einem Shopware <code>line-item</code> ermittelt wird', Help=NULL, AD_Element_ID=580241 WHERE UPPER(ColumnName)='PRODUCTLOOKUP' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-18T06:20:01.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProductLookup', Name='Produkt-Zuordnung', Description='Legt fest, auf welche Art in metasfresh das Produkt zu einem Shopware <code>line-item</code> ermittelt wird', Help=NULL WHERE AD_Element_ID=580241 AND IsCentrallyMaintained='Y'
;

-- 2021-11-18T06:20:01.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produkt-Zuordnung', Description='Legt fest, auf welche Art in metasfresh das Produkt zu einem Shopware <code>line-item</code> ermittelt wird', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580241) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580241)
;

-- 2021-11-18T06:20:01.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produkt-Zuordnung', Description='Legt fest, auf welche Art in metasfresh das Produkt zu einem Shopware <code>line-item</code> ermittelt wird', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580241
;

-- 2021-11-18T06:20:01.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produkt-Zuordnung', Description='Legt fest, auf welche Art in metasfresh das Produkt zu einem Shopware <code>line-item</code> ermittelt wird', Help=NULL WHERE AD_Element_ID = 580241
;

-- 2021-11-18T06:20:01.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Produkt-Zuordnung', Description = 'Legt fest, auf welche Art in metasfresh das Produkt zu einem Shopware <code>line-item</code> ermittelt wird', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580241
;

-- 2021-11-18T06:20:11.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest, auf welche Art in metasfresh das Produkt zu einem Shopware <code>line-item</code> ermittelt wird',Updated=TO_TIMESTAMP('2021-11-18 08:20:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580241 AND AD_Language='nl_NL'
;

-- 2021-11-18T06:20:11.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580241,'nl_NL') 
;

-- 2021-11-18T06:20:14.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Determines how the product for a Shopware <code>line-item</code> is determined in metasfresh.',Updated=TO_TIMESTAMP('2021-11-18 08:20:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580241 AND AD_Language='en_US'
;

-- 2021-11-18T06:20:14.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580241,'en_US') 
;

-- 2021-11-18T06:21:05.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Erfordert, dass die Shopware-ID des Produktes in metasfresh als externe Referenz hinterlegt ist', Name='productId',Updated=TO_TIMESTAMP('2021-11-18 08:21:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542944
;

-- 2021-11-18T06:21:11.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Erfordert, dass die Shopware-ID des Produktes in metasfresh als externe Referenz hinterlegt ist', Name='productId',Updated=TO_TIMESTAMP('2021-11-18 08:21:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542944
;

-- 2021-11-18T06:21:22.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Erfordert, dass die Shopware-ID des Produktes in metasfresh als externe Referenz hinterlegt ist', Name='productId',Updated=TO_TIMESTAMP('2021-11-18 08:21:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542944
;

-- 2021-11-18T06:21:36.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Requires that the Shopware ID of the product is stored in metasfresh as an external reference.', Name='productId',Updated=TO_TIMESTAMP('2021-11-18 08:21:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542944
;

-- 2021-11-18T06:22:04.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Erfordert, dass die Shopware-ID des Produktes in metasfresh als externe Referenz hinterlegt ist', Name='productId',Updated=TO_TIMESTAMP('2021-11-18 08:22:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542944
;

-- 2021-11-18T06:22:33.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='payload/productNumber',Updated=TO_TIMESTAMP('2021-11-18 08:22:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542945
;

-- 2021-11-18T06:22:41.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='payload/productNumber',Updated=TO_TIMESTAMP('2021-11-18 08:22:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542945
;

-- 2021-11-18T06:22:43.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='payload/productNumber',Updated=TO_TIMESTAMP('2021-11-18 08:22:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542945
;

-- 2021-11-18T06:22:45.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='payload/productNumber',Updated=TO_TIMESTAMP('2021-11-18 08:22:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542945
;

-- 2021-11-18T06:22:49.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='payload/productNumber',Updated=TO_TIMESTAMP('2021-11-18 08:22:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542945
;

-- 2021-11-18T06:23:03.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Requires that the product number is stored as the search key of the product in metasfresh',Updated=TO_TIMESTAMP('2021-11-18 08:23:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542945
;

-- 2021-11-18T06:23:05.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Erfordert, dass die Produkt-Nummer als Suchschlüssel des Produktes in metasfresh hinterlegt ist',Updated=TO_TIMESTAMP('2021-11-18 08:23:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542945
;

-- 2021-11-18T06:23:09.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Erfordert, dass die Produkt-Nummer als Suchschlüssel des Produktes in metasfresh hinterlegt ist',Updated=TO_TIMESTAMP('2021-11-18 08:23:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542945
;

-- 2021-11-18T06:23:11.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Erfordert, dass die Produkt-Nummer als Suchschlüssel des Produktes in metasfresh hinterlegt ist',Updated=TO_TIMESTAMP('2021-11-18 08:23:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542945
;

-- 2021-11-18T06:27:35.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest, auf welche Art in metasfresh das Produkt zu einem Shopware line-item ermittelt wird',Updated=TO_TIMESTAMP('2021-11-18 08:27:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580241 AND AD_Language='de_CH'
;

-- 2021-11-18T06:27:35.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580241,'de_CH') 
;

-- 2021-11-18T06:27:49.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest, auf welche Art in metasfresh das Produkt zu einem Shopware line-item ermittelt wird',Updated=TO_TIMESTAMP('2021-11-18 08:27:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580241 AND AD_Language='de_DE'
;

-- 2021-11-18T06:27:49.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580241,'de_DE') 
;

-- 2021-11-18T06:27:49.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580241,'de_DE') 
;

-- 2021-11-18T06:27:49.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ProductLookup', Name='Produkt-Zuordnung', Description='Legt fest, auf welche Art in metasfresh das Produkt zu einem Shopware line-item ermittelt wird', Help=NULL WHERE AD_Element_ID=580241
;

-- 2021-11-18T06:27:49.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProductLookup', Name='Produkt-Zuordnung', Description='Legt fest, auf welche Art in metasfresh das Produkt zu einem Shopware line-item ermittelt wird', Help=NULL, AD_Element_ID=580241 WHERE UPPER(ColumnName)='PRODUCTLOOKUP' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-18T06:27:49.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProductLookup', Name='Produkt-Zuordnung', Description='Legt fest, auf welche Art in metasfresh das Produkt zu einem Shopware line-item ermittelt wird', Help=NULL WHERE AD_Element_ID=580241 AND IsCentrallyMaintained='Y'
;

-- 2021-11-18T06:27:49.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produkt-Zuordnung', Description='Legt fest, auf welche Art in metasfresh das Produkt zu einem Shopware line-item ermittelt wird', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580241) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580241)
;

-- 2021-11-18T06:27:49.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produkt-Zuordnung', Description='Legt fest, auf welche Art in metasfresh das Produkt zu einem Shopware line-item ermittelt wird', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580241
;

-- 2021-11-18T06:27:49.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produkt-Zuordnung', Description='Legt fest, auf welche Art in metasfresh das Produkt zu einem Shopware line-item ermittelt wird', Help=NULL WHERE AD_Element_ID = 580241
;

-- 2021-11-18T06:27:49.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Produkt-Zuordnung', Description = 'Legt fest, auf welche Art in metasfresh das Produkt zu einem Shopware line-item ermittelt wird', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580241
;

-- 2021-11-18T06:28:00.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Determines how the product for a Shopware line-item is determined in metasfresh.',Updated=TO_TIMESTAMP('2021-11-18 08:28:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580241 AND AD_Language='en_US'
;

-- 2021-11-18T06:28:00.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580241,'en_US') 
;

-- 2021-11-18T06:28:09.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest, auf welche Art in metasfresh das Produkt zu einem Shopware line-item ermittelt wird',Updated=TO_TIMESTAMP('2021-11-18 08:28:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580241 AND AD_Language='nl_NL'
;

-- 2021-11-18T06:28:09.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580241,'nl_NL') 
;

-- 2021-11-18T06:35:21.499Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Erfordert, dass die Produkt-Nummer als Suchschlüssel des Produktes in metasfresh hinterlegt ist',Updated=TO_TIMESTAMP('2021-11-18 08:35:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542945
;

