-- 2019-07-15T15:31:58.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='QtyDeliveredInPriceUOM',Updated=TO_TIMESTAMP('2019-07-15 15:31:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576908
;

-- 2019-07-15T15:31:58.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyDeliveredInPriceUOM', Name='Gelieferte Menge in Preiseinheit', Description=NULL, Help=NULL WHERE AD_Element_ID=576908
;

-- 2019-07-15T15:31:58.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInPriceUOM', Name='Gelieferte Menge in Preiseinheit', Description=NULL, Help=NULL, AD_Element_ID=576908 WHERE UPPER(ColumnName)='QTYDELIVEREDINPRICEUOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-15T15:31:58.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInPriceUOM', Name='Gelieferte Menge in Preiseinheit', Description=NULL, Help=NULL WHERE AD_Element_ID=576908 AND IsCentrallyMaintained='Y'
;

