-- 2021-12-15T17:11:36.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Abweichende Lieferadresse',Updated=TO_TIMESTAMP('2021-12-15 18:11:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2466 AND AD_Language='de_DE'
;

-- 2021-12-15T17:11:36.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2466,'de_DE') 
;

-- 2021-12-15T17:11:36.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(2466,'de_DE') 
;

-- 2021-12-15T17:11:36.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsDropShip', Name='Abweichende Lieferadresse', Description='Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert', Help='Drop Shipments do not cause any Inventory reservations or movements as the Shipment is from the Vendor''s inventory. The Shipment of the Vendor to the Customer must be confirmed.' WHERE AD_Element_ID=2466
;

-- 2021-12-15T17:11:36.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsDropShip', Name='Abweichende Lieferadresse', Description='Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert', Help='Drop Shipments do not cause any Inventory reservations or movements as the Shipment is from the Vendor''s inventory. The Shipment of the Vendor to the Customer must be confirmed.', AD_Element_ID=2466 WHERE UPPER(ColumnName)='ISDROPSHIP' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-15T17:11:36.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsDropShip', Name='Abweichende Lieferadresse', Description='Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert', Help='Drop Shipments do not cause any Inventory reservations or movements as the Shipment is from the Vendor''s inventory. The Shipment of the Vendor to the Customer must be confirmed.' WHERE AD_Element_ID=2466 AND IsCentrallyMaintained='Y'
;

-- 2021-12-15T17:11:36.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abweichende Lieferadresse', Description='Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert', Help='Drop Shipments do not cause any Inventory reservations or movements as the Shipment is from the Vendor''s inventory. The Shipment of the Vendor to the Customer must be confirmed.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2466) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 2466)
;

-- 2021-12-15T17:11:36.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Strecke', Name='Abweichende Lieferadresse' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2466)
;

-- 2021-12-15T17:11:36.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Abweichende Lieferadresse', Description='Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert', Help='Drop Shipments do not cause any Inventory reservations or movements as the Shipment is from the Vendor''s inventory. The Shipment of the Vendor to the Customer must be confirmed.', CommitWarning = NULL WHERE AD_Element_ID = 2466
;

-- 2021-12-15T17:11:36.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Abweichende Lieferadresse', Description='Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert', Help='Drop Shipments do not cause any Inventory reservations or movements as the Shipment is from the Vendor''s inventory. The Shipment of the Vendor to the Customer must be confirmed.' WHERE AD_Element_ID = 2466
;

-- 2021-12-15T17:11:36.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Abweichende Lieferadresse', Description = 'Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 2466
;

-- 2021-12-15T17:12:10.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Different shipping address',Updated=TO_TIMESTAMP('2021-12-15 18:12:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2466 AND AD_Language='en_US'
;

-- 2021-12-15T17:12:10.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2466,'en_US') 
;

