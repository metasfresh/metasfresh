
-- 2019-07-25T14:25:46.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2019-07-25 16:25:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576938
;

-- 2019-07-25T14:25:56.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2019-07-25 16:25:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576939
;

-- 2019-07-25T14:26:03.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-07-25 16:26:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568459
;

-- 2019-07-25T14:26:06.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-07-25 16:26:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568460
;

-- 2019-07-25T15:44:12.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Menge in Produkt-Maßeinheit, die bereits in Rechnung gestellt wurde.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-25 17:44:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=529 AND AD_Language='de_CH'
;

-- 2019-07-25T15:44:12.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(529,'de_CH') 
;

-- 2019-07-25T15:44:31.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Menge in Produkt-Maßeinheit, die bereits in Rechnung gestellt wurde.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-25 17:44:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=529 AND AD_Language='de_DE'
;

-- 2019-07-25T15:44:31.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(529,'de_DE') 
;

-- 2019-07-25T15:44:31.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(529,'de_DE') 
;


-- 2019-07-25T15:44:31.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyInvoiced', Name='Berechn. Menge', Description='Menge in Produkt-Maßeinheit, die bereits in Rechnung gestellt wurde.', Help=NULL WHERE AD_Element_ID=529
;

-- 2019-07-25T15:44:31.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyInvoiced', Name='Berechn. Menge', Description='Menge in Produkt-Maßeinheit, die bereits in Rechnung gestellt wurde.', Help=NULL, AD_Element_ID=529 WHERE UPPER(ColumnName)='QTYINVOICED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-25T15:44:31.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyInvoiced', Name='Berechn. Menge', Description='Menge in Produkt-Maßeinheit, die bereits in Rechnung gestellt wurde.', Help=NULL WHERE AD_Element_ID=529 AND IsCentrallyMaintained='Y'
;

-- 2019-07-25T15:44:31.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Berechn. Menge', Description='Menge in Produkt-Maßeinheit, die bereits in Rechnung gestellt wurde.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=529) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 529)
;

-- 2019-07-25T15:44:31.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Berechn. Menge', Description='Menge in Produkt-Maßeinheit, die bereits in Rechnung gestellt wurde.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 529
;

-- 2019-07-25T15:44:31.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Berechn. Menge', Description='Menge in Produkt-Maßeinheit, die bereits in Rechnung gestellt wurde.', Help=NULL WHERE AD_Element_ID = 529
;

-- 2019-07-25T15:44:31.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name = 'Berechn. Menge', Description = 'Menge in Produkt-Maßeinheit, die bereits in Rechnung gestellt wurde.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 529
;

-- 2019-07-25T15:45:12.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Invoiced quantity in the product''s UOM.',Updated=TO_TIMESTAMP('2019-07-25 17:45:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=529 AND AD_Language='en_US'
;

-- 2019-07-25T15:45:12.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(529,'en_US') 
;
