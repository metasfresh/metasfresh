-- 2017-08-26T16:26:19.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-08-26 16:26:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540485
;

-- 2017-08-26T16:28:56.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Preis Abweichung Toleranz', Name='Preis Abweichung Toleranz', PrintName='Preis Abweichung Toleranz',Updated=TO_TIMESTAMP('2017-08-26 16:28:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2884
;

-- 2017-08-26T16:28:56.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PriceMatchTolerance', Name='Preis Abweichung Toleranz', Description='Preis Abweichung Toleranz', Help='Tolerance in Percent of matching the purchase order price to the invoice price.  The difference is posted as Invoice Price Tolerance for Standard Costing.  If defined, the PO-Invoice match must be explicitly approved, if the matching difference is greater then the tolerance.<br>
Example: if the purchase price is $100 and the tolerance is 1 (percent), the invoice price must be between $99 and 101 to be automatically approved.' WHERE AD_Element_ID=2884
;

-- 2017-08-26T16:28:56.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PriceMatchTolerance', Name='Preis Abweichung Toleranz', Description='Preis Abweichung Toleranz', Help='Tolerance in Percent of matching the purchase order price to the invoice price.  The difference is posted as Invoice Price Tolerance for Standard Costing.  If defined, the PO-Invoice match must be explicitly approved, if the matching difference is greater then the tolerance.<br>
Example: if the purchase price is $100 and the tolerance is 1 (percent), the invoice price must be between $99 and 101 to be automatically approved.', AD_Element_ID=2884 WHERE UPPER(ColumnName)='PRICEMATCHTOLERANCE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-26T16:28:56.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PriceMatchTolerance', Name='Preis Abweichung Toleranz', Description='Preis Abweichung Toleranz', Help='Tolerance in Percent of matching the purchase order price to the invoice price.  The difference is posted as Invoice Price Tolerance for Standard Costing.  If defined, the PO-Invoice match must be explicitly approved, if the matching difference is greater then the tolerance.<br>
Example: if the purchase price is $100 and the tolerance is 1 (percent), the invoice price must be between $99 and 101 to be automatically approved.' WHERE AD_Element_ID=2884 AND IsCentrallyMaintained='Y'
;

-- 2017-08-26T16:28:56.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Preis Abweichung Toleranz', Description='Preis Abweichung Toleranz', Help='Tolerance in Percent of matching the purchase order price to the invoice price.  The difference is posted as Invoice Price Tolerance for Standard Costing.  If defined, the PO-Invoice match must be explicitly approved, if the matching difference is greater then the tolerance.<br>
Example: if the purchase price is $100 and the tolerance is 1 (percent), the invoice price must be between $99 and 101 to be automatically approved.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2884) AND IsCentrallyMaintained='Y'
;

-- 2017-08-26T16:28:56.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Preis Abweichung Toleranz', Name='Preis Abweichung Toleranz' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2884)
;

-- 2017-08-26T16:29:26.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verkauf Steuer Drucken',Updated=TO_TIMESTAMP('2017-08-26 16:29:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556271
;

-- 2017-08-26T16:29:35.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vertraulich',Updated=TO_TIMESTAMP('2017-08-26 16:29:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12001
;

-- 2017-08-26T16:29:46.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Priorität',Updated=TO_TIMESTAMP('2017-08-26 16:29:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12002
;

-- 2017-08-26T16:30:37.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-08-26 16:30:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=778
;

-- 2017-08-26T16:49:51.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-08-26 16:49:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540478
;

-- 2017-08-26T16:50:56.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2017-08-26 16:50:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544659
;