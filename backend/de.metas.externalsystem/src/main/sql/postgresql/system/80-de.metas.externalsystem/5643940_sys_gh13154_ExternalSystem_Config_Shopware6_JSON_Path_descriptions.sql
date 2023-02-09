-- 2022-06-17T11:13:49.151Z
UPDATE AD_Element_Trl SET Description='JSON path indicating where the customer''s metasfresh ID (C_BPartner_ID) can be read when importing a customized Shopware document that contains a customer. Currently, there are 2 workflows where we deal with such payloads: during a sales order import, and during the explicit customer import. For more details see: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md',Updated=TO_TIMESTAMP('2022-06-17 14:13:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580786 AND AD_Language='en_US'
;

-- 2022-06-17T11:13:49.322Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580786,'en_US') 
;

-- 2022-06-17T11:13:58.129Z
UPDATE AD_Element_Trl SET Description='JSON-Pfad, der angibt, wo die Metasfresh-ID des Kunden (C_BPartner_ID) gelesen werden kann, wenn ein angepasstes Shopware-Dokument importiert wird, das einen Kunden enthält. Derzeit gibt es 2 Arbeitsabläufe in denen solche Payloads verarbeitet werden: beim Import eines Bestellung und beim expliziten Kundenimport. Für weitere Details siehe: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md',Updated=TO_TIMESTAMP('2022-06-17 14:13:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580786 AND AD_Language='de_DE'
;

-- 2022-06-17T11:13:58.133Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580786,'de_DE') 
;

-- 2022-06-17T11:13:58.147Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(580786,'de_DE') 
;

-- 2022-06-17T11:13:58.149Z
UPDATE AD_Column SET ColumnName='JSONPathMetasfreshID', Name='Geschäftspartner-Zuordnung - metasfresh-ID JSON-Path', Description='JSON-Pfad, der angibt, wo die Metasfresh-ID des Kunden (C_BPartner_ID) gelesen werden kann, wenn ein angepasstes Shopware-Dokument importiert wird, das einen Kunden enthält. Derzeit gibt es 2 Arbeitsabläufe in denen solche Payloads verarbeitet werden: beim Import eines Bestellung und beim expliziten Kundenimport. Für weitere Details siehe: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md', Help=NULL WHERE AD_Element_ID=580786
;

-- 2022-06-17T11:13:58.154Z
UPDATE AD_Process_Para SET ColumnName='JSONPathMetasfreshID', Name='Geschäftspartner-Zuordnung - metasfresh-ID JSON-Path', Description='JSON-Pfad, der angibt, wo die Metasfresh-ID des Kunden (C_BPartner_ID) gelesen werden kann, wenn ein angepasstes Shopware-Dokument importiert wird, das einen Kunden enthält. Derzeit gibt es 2 Arbeitsabläufe in denen solche Payloads verarbeitet werden: beim Import eines Bestellung und beim expliziten Kundenimport. Für weitere Details siehe: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md', Help=NULL, AD_Element_ID=580786 WHERE UPPER(ColumnName)='JSONPATHMETASFRESHID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-06-17T11:13:58.157Z
UPDATE AD_Process_Para SET ColumnName='JSONPathMetasfreshID', Name='Geschäftspartner-Zuordnung - metasfresh-ID JSON-Path', Description='JSON-Pfad, der angibt, wo die Metasfresh-ID des Kunden (C_BPartner_ID) gelesen werden kann, wenn ein angepasstes Shopware-Dokument importiert wird, das einen Kunden enthält. Derzeit gibt es 2 Arbeitsabläufe in denen solche Payloads verarbeitet werden: beim Import eines Bestellung und beim expliziten Kundenimport. Für weitere Details siehe: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md', Help=NULL WHERE AD_Element_ID=580786 AND IsCentrallyMaintained='Y'
;

-- 2022-06-17T11:13:58.158Z
UPDATE AD_Field SET Name='Geschäftspartner-Zuordnung - metasfresh-ID JSON-Path', Description='JSON-Pfad, der angibt, wo die Metasfresh-ID des Kunden (C_BPartner_ID) gelesen werden kann, wenn ein angepasstes Shopware-Dokument importiert wird, das einen Kunden enthält. Derzeit gibt es 2 Arbeitsabläufe in denen solche Payloads verarbeitet werden: beim Import eines Bestellung und beim expliziten Kundenimport. Für weitere Details siehe: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580786) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580786)
;

-- 2022-06-17T11:13:58.179Z
UPDATE AD_Tab SET Name='Geschäftspartner-Zuordnung - metasfresh-ID JSON-Path', Description='JSON-Pfad, der angibt, wo die Metasfresh-ID des Kunden (C_BPartner_ID) gelesen werden kann, wenn ein angepasstes Shopware-Dokument importiert wird, das einen Kunden enthält. Derzeit gibt es 2 Arbeitsabläufe in denen solche Payloads verarbeitet werden: beim Import eines Bestellung und beim expliziten Kundenimport. Für weitere Details siehe: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580786
;

-- 2022-06-17T11:13:58.181Z
UPDATE AD_WINDOW SET Name='Geschäftspartner-Zuordnung - metasfresh-ID JSON-Path', Description='JSON-Pfad, der angibt, wo die Metasfresh-ID des Kunden (C_BPartner_ID) gelesen werden kann, wenn ein angepasstes Shopware-Dokument importiert wird, das einen Kunden enthält. Derzeit gibt es 2 Arbeitsabläufe in denen solche Payloads verarbeitet werden: beim Import eines Bestellung und beim expliziten Kundenimport. Für weitere Details siehe: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md', Help=NULL WHERE AD_Element_ID = 580786
;

-- 2022-06-17T11:13:58.182Z
UPDATE AD_Menu SET   Name = 'Geschäftspartner-Zuordnung - metasfresh-ID JSON-Path', Description = 'JSON-Pfad, der angibt, wo die Metasfresh-ID des Kunden (C_BPartner_ID) gelesen werden kann, wenn ein angepasstes Shopware-Dokument importiert wird, das einen Kunden enthält. Derzeit gibt es 2 Arbeitsabläufe in denen solche Payloads verarbeitet werden: beim Import eines Bestellung und beim expliziten Kundenimport. Für weitere Details siehe: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580786
;

-- 2022-06-17T11:14:01.345Z
UPDATE AD_Element_Trl SET Description='JSON-Pfad, der angibt, wo die Metasfresh-ID des Kunden (C_BPartner_ID) gelesen werden kann, wenn ein angepasstes Shopware-Dokument importiert wird, das einen Kunden enthält. Derzeit gibt es 2 Arbeitsabläufe in denen solche Payloads verarbeitet werden: beim Import eines Bestellung und beim expliziten Kundenimport. Für weitere Details siehe: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md',Updated=TO_TIMESTAMP('2022-06-17 14:14:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580786 AND AD_Language='de_CH'
;

-- 2022-06-17T11:14:01.348Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580786,'de_CH') 
;

-- 2022-06-17T11:14:05.879Z
UPDATE AD_Element_Trl SET Description='JSON-Pfad, der angibt, wo die Metasfresh-ID des Kunden (C_BPartner_ID) gelesen werden kann, wenn ein angepasstes Shopware-Dokument importiert wird, das einen Kunden enthält. Derzeit gibt es 2 Arbeitsabläufe in denen solche Payloads verarbeitet werden: beim Import eines Bestellung und beim expliziten Kundenimport. Für weitere Details siehe: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md',Updated=TO_TIMESTAMP('2022-06-17 14:14:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580786 AND AD_Language='nl_NL'
;

-- 2022-06-17T11:14:05.881Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580786,'nl_NL') 
;

-- 2022-06-17T11:14:34.216Z
UPDATE AD_Element_Trl SET Description='JSON path indicating where the customer''s Shopware6 reference can be read when importing a customized Shopware document that contains a customer. Currently, there are 2 workflows where we deal with such payloads: during a sales order import, and during the explicit customer import. For more details see: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md',Updated=TO_TIMESTAMP('2022-06-17 14:14:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580787 AND AD_Language='en_US'
;

-- 2022-06-17T11:14:34.218Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580787,'en_US') 
;

-- 2022-06-17T11:14:40.816Z
UPDATE AD_Element_Trl SET Description='JSON-Pfad, der angibt, wo die Shopware6-Referenz des Kunden gelesen werden kann, wenn ein angepasstes Shopware-Dokument importiert wird, das einen Kunden enthält. Derzeit gibt es 2 Arbeitsabläufe, in denen solche Payloads verarbeitet werden: beim Import eines Bestellung und beim expliziten Kundenimport. Für weitere Details siehe: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md',Updated=TO_TIMESTAMP('2022-06-17 14:14:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580787 AND AD_Language='de_DE'
;

-- 2022-06-17T11:14:40.818Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580787,'de_DE') 
;

-- 2022-06-17T11:14:40.828Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(580787,'de_DE') 
;

-- 2022-06-17T11:14:40.829Z
UPDATE AD_Column SET ColumnName='JSONPathShopwareID', Name='Geschäftspartner-Zuordnung - Shopware6-ID JSON-Path', Description='JSON-Pfad, der angibt, wo die Shopware6-Referenz des Kunden gelesen werden kann, wenn ein angepasstes Shopware-Dokument importiert wird, das einen Kunden enthält. Derzeit gibt es 2 Arbeitsabläufe, in denen solche Payloads verarbeitet werden: beim Import eines Bestellung und beim expliziten Kundenimport. Für weitere Details siehe: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md', Help=NULL WHERE AD_Element_ID=580787
;

-- 2022-06-17T11:14:40.829Z
UPDATE AD_Process_Para SET ColumnName='JSONPathShopwareID', Name='Geschäftspartner-Zuordnung - Shopware6-ID JSON-Path', Description='JSON-Pfad, der angibt, wo die Shopware6-Referenz des Kunden gelesen werden kann, wenn ein angepasstes Shopware-Dokument importiert wird, das einen Kunden enthält. Derzeit gibt es 2 Arbeitsabläufe, in denen solche Payloads verarbeitet werden: beim Import eines Bestellung und beim expliziten Kundenimport. Für weitere Details siehe: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md', Help=NULL, AD_Element_ID=580787 WHERE UPPER(ColumnName)='JSONPATHSHOPWAREID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-06-17T11:14:40.830Z
UPDATE AD_Process_Para SET ColumnName='JSONPathShopwareID', Name='Geschäftspartner-Zuordnung - Shopware6-ID JSON-Path', Description='JSON-Pfad, der angibt, wo die Shopware6-Referenz des Kunden gelesen werden kann, wenn ein angepasstes Shopware-Dokument importiert wird, das einen Kunden enthält. Derzeit gibt es 2 Arbeitsabläufe, in denen solche Payloads verarbeitet werden: beim Import eines Bestellung und beim expliziten Kundenimport. Für weitere Details siehe: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md', Help=NULL WHERE AD_Element_ID=580787 AND IsCentrallyMaintained='Y'
;

-- 2022-06-17T11:14:40.831Z
UPDATE AD_Field SET Name='Geschäftspartner-Zuordnung - Shopware6-ID JSON-Path', Description='JSON-Pfad, der angibt, wo die Shopware6-Referenz des Kunden gelesen werden kann, wenn ein angepasstes Shopware-Dokument importiert wird, das einen Kunden enthält. Derzeit gibt es 2 Arbeitsabläufe, in denen solche Payloads verarbeitet werden: beim Import eines Bestellung und beim expliziten Kundenimport. Für weitere Details siehe: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580787) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580787)
;

-- 2022-06-17T11:14:40.842Z
UPDATE AD_Tab SET Name='Geschäftspartner-Zuordnung - Shopware6-ID JSON-Path', Description='JSON-Pfad, der angibt, wo die Shopware6-Referenz des Kunden gelesen werden kann, wenn ein angepasstes Shopware-Dokument importiert wird, das einen Kunden enthält. Derzeit gibt es 2 Arbeitsabläufe, in denen solche Payloads verarbeitet werden: beim Import eines Bestellung und beim expliziten Kundenimport. Für weitere Details siehe: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580787
;

-- 2022-06-17T11:14:40.845Z
UPDATE AD_WINDOW SET Name='Geschäftspartner-Zuordnung - Shopware6-ID JSON-Path', Description='JSON-Pfad, der angibt, wo die Shopware6-Referenz des Kunden gelesen werden kann, wenn ein angepasstes Shopware-Dokument importiert wird, das einen Kunden enthält. Derzeit gibt es 2 Arbeitsabläufe, in denen solche Payloads verarbeitet werden: beim Import eines Bestellung und beim expliziten Kundenimport. Für weitere Details siehe: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md', Help=NULL WHERE AD_Element_ID = 580787
;

-- 2022-06-17T11:14:40.846Z
UPDATE AD_Menu SET   Name = 'Geschäftspartner-Zuordnung - Shopware6-ID JSON-Path', Description = 'JSON-Pfad, der angibt, wo die Shopware6-Referenz des Kunden gelesen werden kann, wenn ein angepasstes Shopware-Dokument importiert wird, das einen Kunden enthält. Derzeit gibt es 2 Arbeitsabläufe, in denen solche Payloads verarbeitet werden: beim Import eines Bestellung und beim expliziten Kundenimport. Für weitere Details siehe: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580787
;

-- 2022-06-17T11:14:44.080Z
UPDATE AD_Element_Trl SET Description='JSON-Pfad, der angibt, wo die Shopware6-Referenz des Kunden gelesen werden kann, wenn ein angepasstes Shopware-Dokument importiert wird, das einen Kunden enthält. Derzeit gibt es 2 Arbeitsabläufe, in denen solche Payloads verarbeitet werden: beim Import eines Bestellung und beim expliziten Kundenimport. Für weitere Details siehe: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md',Updated=TO_TIMESTAMP('2022-06-17 14:14:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580787 AND AD_Language='de_CH'
;

-- 2022-06-17T11:14:44.081Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580787,'de_CH') 
;

-- 2022-06-17T11:14:47.766Z
UPDATE AD_Element_Trl SET Description='JSON-Pfad, der angibt, wo die Shopware6-Referenz des Kunden gelesen werden kann, wenn ein angepasstes Shopware-Dokument importiert wird, das einen Kunden enthält. Derzeit gibt es 2 Arbeitsabläufe, in denen solche Payloads verarbeitet werden: beim Import eines Bestellung und beim expliziten Kundenimport. Für weitere Details siehe: https://github.com/metasfresh/metasfresh/blob/master/misc/services/camel/de-metas-camel-externalsystems/de-metas-camel-shopware6/README.md',Updated=TO_TIMESTAMP('2022-06-17 14:14:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580787 AND AD_Language='nl_NL'
;

-- 2022-06-17T11:14:47.768Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580787,'nl_NL') 
;

