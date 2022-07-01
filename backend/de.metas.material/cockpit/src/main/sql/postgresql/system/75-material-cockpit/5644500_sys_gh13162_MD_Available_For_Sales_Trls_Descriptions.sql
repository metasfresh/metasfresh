-- 2022-06-21T10:11:19.200Z
UPDATE AD_Element_Trl SET Description='Wenn diese Option aktiviert ist, wird der aktuelle Bestand der Produkte von Shopware6 automatisch an Shopware6 gesendet.',Updated=TO_TIMESTAMP('2022-06-21 13:11:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581040 AND AD_Language='de_CH'
;

-- 2022-06-21T10:11:19.280Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581040,'de_CH') 
;

-- 2022-06-21T10:11:21.455Z
UPDATE AD_Element_Trl SET Description='Wenn diese Option aktiviert ist, wird der aktuelle Bestand der Produkte von Shopware6 automatisch an Shopware6 gesendet.',Updated=TO_TIMESTAMP('2022-06-21 13:11:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581040 AND AD_Language='de_DE'
;

-- 2022-06-21T10:11:21.458Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581040,'de_DE') 
;

-- 2022-06-21T10:11:21.484Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581040,'de_DE') 
;

-- 2022-06-21T10:11:21.485Z
UPDATE AD_Column SET ColumnName='IsSyncStockToShopware6', Name='Sync verfügb. Menge nach Shopware6', Description='Wenn diese Option aktiviert ist, wird der aktuelle Bestand der Produkte von Shopware6 automatisch an Shopware6 gesendet.', Help=NULL WHERE AD_Element_ID=581040
;

-- 2022-06-21T10:11:21.487Z
UPDATE AD_Process_Para SET ColumnName='IsSyncStockToShopware6', Name='Sync verfügb. Menge nach Shopware6', Description='Wenn diese Option aktiviert ist, wird der aktuelle Bestand der Produkte von Shopware6 automatisch an Shopware6 gesendet.', Help=NULL, AD_Element_ID=581040 WHERE UPPER(ColumnName)='ISSYNCSTOCKTOSHOPWARE6' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-06-21T10:11:21.488Z
UPDATE AD_Process_Para SET ColumnName='IsSyncStockToShopware6', Name='Sync verfügb. Menge nach Shopware6', Description='Wenn diese Option aktiviert ist, wird der aktuelle Bestand der Produkte von Shopware6 automatisch an Shopware6 gesendet.', Help=NULL WHERE AD_Element_ID=581040 AND IsCentrallyMaintained='Y'
;

-- 2022-06-21T10:11:21.489Z
UPDATE AD_Field SET Name='Sync verfügb. Menge nach Shopware6', Description='Wenn diese Option aktiviert ist, wird der aktuelle Bestand der Produkte von Shopware6 automatisch an Shopware6 gesendet.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581040) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581040)
;

-- 2022-06-21T10:11:21.514Z
UPDATE AD_Tab SET Name='Sync verfügb. Menge nach Shopware6', Description='Wenn diese Option aktiviert ist, wird der aktuelle Bestand der Produkte von Shopware6 automatisch an Shopware6 gesendet.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581040
;

-- 2022-06-21T10:11:21.517Z
UPDATE AD_WINDOW SET Name='Sync verfügb. Menge nach Shopware6', Description='Wenn diese Option aktiviert ist, wird der aktuelle Bestand der Produkte von Shopware6 automatisch an Shopware6 gesendet.', Help=NULL WHERE AD_Element_ID = 581040
;

-- 2022-06-21T10:11:21.518Z
UPDATE AD_Menu SET   Name = 'Sync verfügb. Menge nach Shopware6', Description = 'Wenn diese Option aktiviert ist, wird der aktuelle Bestand der Produkte von Shopware6 automatisch an Shopware6 gesendet.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581040
;

-- 2022-06-21T10:11:27.976Z
UPDATE AD_Element_Trl SET Description='If checked, the current stock of the Shopware6''s products is automatically sent to Shopware6.',Updated=TO_TIMESTAMP('2022-06-21 13:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581040 AND AD_Language='en_US'
;

-- 2022-06-21T10:11:27.977Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581040,'en_US') 
;

-- 2022-06-21T10:11:34.375Z
UPDATE AD_Element_Trl SET Description='Wenn diese Option aktiviert ist, wird der aktuelle Bestand der Produkte von Shopware6 automatisch an Shopware6 gesendet.',Updated=TO_TIMESTAMP('2022-06-21 13:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581040 AND AD_Language='nl_NL'
;

-- 2022-06-21T10:11:34.377Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581040,'nl_NL') 
;

-- 2022-06-21T10:12:39.442Z
UPDATE AD_Process_Trl SET Description='Berechnet die aktuell für den Verkauf verfügbaren Mengen für alle gelagerten und verkauften Produkte.',Updated=TO_TIMESTAMP('2022-06-21 13:12:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585064
;

-- 2022-06-21T10:12:42.263Z
UPDATE AD_Process SET Description='Berechnet die aktuell für den Verkauf verfügbaren Mengen für alle gelagerten und verkauften Produkte.', Help=NULL, Name='Tabelle aktualisieren',Updated=TO_TIMESTAMP('2022-06-21 13:12:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585064
;

-- 2022-06-21T10:12:42.217Z
UPDATE AD_Process_Trl SET Description='Berechnet die aktuell für den Verkauf verfügbaren Mengen für alle gelagerten und verkauften Produkte.',Updated=TO_TIMESTAMP('2022-06-21 13:12:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585064
;

-- 2022-06-21T10:12:44.552Z
UPDATE AD_Process_Trl SET Description='Berechnet die aktuell für den Verkauf verfügbaren Mengen für alle gelagerten und verkauften Produkte.',Updated=TO_TIMESTAMP('2022-06-21 13:12:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585064
;

-- 2022-06-21T10:12:49.921Z
UPDATE AD_Process_Trl SET Description='Computes the current available for sales quantities for all stocked and sold products.',Updated=TO_TIMESTAMP('2022-06-21 13:12:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585064
;

-- 2022-06-21T10:14:51.303Z
UPDATE AD_Element_Trl SET Description='Menge, die laut der "Einstellungen zur verfügbaren Menge für den Verkauf" der jeweiligen Organisation noch auf Lager ist, aber bald versandt wird',Updated=TO_TIMESTAMP('2022-06-21 13:14:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576514 AND AD_Language='de_CH'
;

-- 2022-06-21T10:14:51.305Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576514,'de_CH') 
;

-- 2022-06-21T10:14:53.207Z
UPDATE AD_Element_Trl SET Description='Menge, die laut der "Einstellungen zur verfügbaren Menge für den Verkauf" der jeweiligen Organisation noch auf Lager ist, aber bald versandt wird',Updated=TO_TIMESTAMP('2022-06-21 13:14:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576514 AND AD_Language='de_DE'
;

-- 2022-06-21T10:14:53.210Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576514,'de_DE') 
;

-- 2022-06-21T10:14:53.229Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(576514,'de_DE') 
;

-- 2022-06-21T10:14:53.233Z
UPDATE AD_Column SET ColumnName='QtyToBeShipped', Name='QtyToBeShipped', Description='Menge, die laut der "Einstellungen zur verfügbaren Menge für den Verkauf" der jeweiligen Organisation noch auf Lager ist, aber bald versandt wird', Help=NULL WHERE AD_Element_ID=576514
;

-- 2022-06-21T10:14:53.235Z
UPDATE AD_Process_Para SET ColumnName='QtyToBeShipped', Name='QtyToBeShipped', Description='Menge, die laut der "Einstellungen zur verfügbaren Menge für den Verkauf" der jeweiligen Organisation noch auf Lager ist, aber bald versandt wird', Help=NULL, AD_Element_ID=576514 WHERE UPPER(ColumnName)='QTYTOBESHIPPED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-06-21T10:14:53.237Z
UPDATE AD_Process_Para SET ColumnName='QtyToBeShipped', Name='QtyToBeShipped', Description='Menge, die laut der "Einstellungen zur verfügbaren Menge für den Verkauf" der jeweiligen Organisation noch auf Lager ist, aber bald versandt wird', Help=NULL WHERE AD_Element_ID=576514 AND IsCentrallyMaintained='Y'
;

-- 2022-06-21T10:14:53.238Z
UPDATE AD_Field SET Name='QtyToBeShipped', Description='Menge, die laut der "Einstellungen zur verfügbaren Menge für den Verkauf" der jeweiligen Organisation noch auf Lager ist, aber bald versandt wird', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576514) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576514)
;

-- 2022-06-21T10:14:53.259Z
UPDATE AD_Tab SET Name='QtyToBeShipped', Description='Menge, die laut der "Einstellungen zur verfügbaren Menge für den Verkauf" der jeweiligen Organisation noch auf Lager ist, aber bald versandt wird', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576514
;

-- 2022-06-21T10:14:53.261Z
UPDATE AD_WINDOW SET Name='QtyToBeShipped', Description='Menge, die laut der "Einstellungen zur verfügbaren Menge für den Verkauf" der jeweiligen Organisation noch auf Lager ist, aber bald versandt wird', Help=NULL WHERE AD_Element_ID = 576514
;

-- 2022-06-21T10:14:53.263Z
UPDATE AD_Menu SET   Name = 'QtyToBeShipped', Description = 'Menge, die laut der "Einstellungen zur verfügbaren Menge für den Verkauf" der jeweiligen Organisation noch auf Lager ist, aber bald versandt wird', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576514
;

-- 2022-06-21T10:14:55.776Z
UPDATE AD_Element_Trl SET Description='Menge, die laut der "Einstellungen zur verfügbaren Menge für den Verkauf" der jeweiligen Organisation noch auf Lager ist, aber bald versandt wird',Updated=TO_TIMESTAMP('2022-06-21 13:14:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576514 AND AD_Language='nl_NL'
;

-- 2022-06-21T10:14:55.778Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576514,'nl_NL') 
;

-- 2022-06-21T10:15:00.881Z
UPDATE AD_Element_Trl SET Description='Quantity that according to the respective org''s "Available quantity for sales config" is still stocked, but is going to be shipped soon',Updated=TO_TIMESTAMP('2022-06-21 13:15:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576514 AND AD_Language='en_US'
;

-- 2022-06-21T10:15:00.882Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576514,'en_US') 
;

-- 2022-06-21T15:18:53.249Z
UPDATE AD_Element_Trl SET Description='Wenn diese Option aktiviert ist, wird automatisch die aktuell für den Verkauf der Produkte verfügbare Planmenge an Shopware6 gesendet.',Updated=TO_TIMESTAMP('2022-06-21 18:18:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581040 AND AD_Language='de_CH'
;

-- 2022-06-21T15:18:53.331Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581040,'de_CH')
;

-- 2022-06-21T15:18:56.954Z
UPDATE AD_Element_Trl SET Description='Wenn diese Option aktiviert ist, wird automatisch die aktuell für den Verkauf der Produkte verfügbare Planmenge an Shopware6 gesendet.',Updated=TO_TIMESTAMP('2022-06-21 18:18:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581040 AND AD_Language='de_DE'
;

-- 2022-06-21T15:18:56.957Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581040,'de_DE')
;

-- 2022-06-21T15:18:56.993Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581040,'de_DE')
;

-- 2022-06-21T15:18:56.995Z
UPDATE AD_Column SET ColumnName='IsSyncStockToShopware6', Name='Sync verfügb. Menge nach Shopware6', Description='Wenn diese Option aktiviert ist, wird automatisch die aktuell für den Verkauf der Produkte verfügbare Planmenge an Shopware6 gesendet.', Help=NULL WHERE AD_Element_ID=581040
;

-- 2022-06-21T15:18:56.998Z
UPDATE AD_Process_Para SET ColumnName='IsSyncStockToShopware6', Name='Sync verfügb. Menge nach Shopware6', Description='Wenn diese Option aktiviert ist, wird automatisch die aktuell für den Verkauf der Produkte verfügbare Planmenge an Shopware6 gesendet.', Help=NULL, AD_Element_ID=581040 WHERE UPPER(ColumnName)='ISSYNCSTOCKTOSHOPWARE6' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-06-21T15:18:57.001Z
UPDATE AD_Process_Para SET ColumnName='IsSyncStockToShopware6', Name='Sync verfügb. Menge nach Shopware6', Description='Wenn diese Option aktiviert ist, wird automatisch die aktuell für den Verkauf der Produkte verfügbare Planmenge an Shopware6 gesendet.', Help=NULL WHERE AD_Element_ID=581040 AND IsCentrallyMaintained='Y'
;

-- 2022-06-21T15:18:57.003Z
UPDATE AD_Field SET Name='Sync verfügb. Menge nach Shopware6', Description='Wenn diese Option aktiviert ist, wird automatisch die aktuell für den Verkauf der Produkte verfügbare Planmenge an Shopware6 gesendet.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581040) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581040)
;

-- 2022-06-21T15:18:57.048Z
UPDATE AD_Tab SET Name='Sync verfügb. Menge nach Shopware6', Description='Wenn diese Option aktiviert ist, wird automatisch die aktuell für den Verkauf der Produkte verfügbare Planmenge an Shopware6 gesendet.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581040
;

-- 2022-06-21T15:18:57.050Z
UPDATE AD_WINDOW SET Name='Sync verfügb. Menge nach Shopware6', Description='Wenn diese Option aktiviert ist, wird automatisch die aktuell für den Verkauf der Produkte verfügbare Planmenge an Shopware6 gesendet.', Help=NULL WHERE AD_Element_ID = 581040
;

-- 2022-06-21T15:18:57.052Z
UPDATE AD_Menu SET   Name = 'Sync verfügb. Menge nach Shopware6', Description = 'Wenn diese Option aktiviert ist, wird automatisch die aktuell für den Verkauf der Produkte verfügbare Planmenge an Shopware6 gesendet.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581040
;

-- 2022-06-21T15:18:59.391Z
UPDATE AD_Element_Trl SET Description='Wenn diese Option aktiviert ist, wird automatisch die aktuell für den Verkauf der Produkte verfügbare Planmenge an Shopware6 gesendet.',Updated=TO_TIMESTAMP('2022-06-21 18:18:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581040 AND AD_Language='nl_NL'
;

-- 2022-06-21T15:18:59.393Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581040,'nl_NL')
;

-- 2022-06-21T15:19:19.487Z
UPDATE AD_Element_Trl SET Description='If checked, the current planned quantity available for sales is automatically sent to Shopware6.',Updated=TO_TIMESTAMP('2022-06-21 18:19:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581040 AND AD_Language='en_US'
;

-- 2022-06-21T15:19:19.488Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581040,'en_US')
;

-- 2022-06-21T15:20:08.681Z
UPDATE AD_Process_Trl SET Description='Berechnet die aktuell für den Verkauf verfügbaren Mengen für alle gelagerten und verkauften Produkte. Hinweis: Die Mengen werden zwischen den Prozessausführungen automatisch synchronisiert, d.h. die Mengen in der Tabelle "Für den Verkauf verfügbar" sind immer aktuell.',Updated=TO_TIMESTAMP('2022-06-21 18:20:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585064
;

-- 2022-06-21T15:20:11.145Z
UPDATE AD_Process SET Description='Berechnet die aktuell für den Verkauf verfügbaren Mengen für alle gelagerten und verkauften Produkte. Hinweis: Die Mengen werden zwischen den Prozessausführungen automatisch synchronisiert, d.h. die Mengen in der Tabelle "Für den Verkauf verfügbar" sind immer aktuell.', Help=NULL, Name='Tabelle aktualisieren',Updated=TO_TIMESTAMP('2022-06-21 18:20:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585064
;

-- 2022-06-21T15:20:11.016Z
UPDATE AD_Process_Trl SET Description='Berechnet die aktuell für den Verkauf verfügbaren Mengen für alle gelagerten und verkauften Produkte. Hinweis: Die Mengen werden zwischen den Prozessausführungen automatisch synchronisiert, d.h. die Mengen in der Tabelle "Für den Verkauf verfügbar" sind immer aktuell.',Updated=TO_TIMESTAMP('2022-06-21 18:20:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585064
;

-- 2022-06-21T15:20:13.752Z
UPDATE AD_Process_Trl SET Description='Berechnet die aktuell für den Verkauf verfügbaren Mengen für alle gelagerten und verkauften Produkte. Hinweis: Die Mengen werden zwischen den Prozessausführungen automatisch synchronisiert, d.h. die Mengen in der Tabelle "Für den Verkauf verfügbar" sind immer aktuell.',Updated=TO_TIMESTAMP('2022-06-21 18:20:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585064
;

-- 2022-06-21T15:20:19.681Z
UPDATE AD_Process_Trl SET Description='Computes the current available for sales quantities for all stocked and sold products. Note: the quantities are automatically kept in sync between process executions, meaning the quantities in the available for sales table are always up-to-date.',Updated=TO_TIMESTAMP('2022-06-21 18:20:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585064
;
