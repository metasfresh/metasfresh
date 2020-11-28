-- 16.12.2015 17:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Saldo Fremdwährung anzeigen', PrintName='Saldo Fremdwährung anzeigen',Updated=TO_TIMESTAMP('2015-12-16 17:02:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542933
;

-- 16.12.2015 17:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542933
;

-- 16.12.2015 17:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='showCurrencyExchange', Name='Saldo Fremdwährung anzeigen', Description=NULL, Help=NULL WHERE AD_Element_ID=542933
;

-- 16.12.2015 17:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='showCurrencyExchange', Name='Saldo Fremdwährung anzeigen', Description=NULL, Help=NULL, AD_Element_ID=542933 WHERE UPPER(ColumnName)='SHOWCURRENCYEXCHANGE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 16.12.2015 17:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='showCurrencyExchange', Name='Saldo Fremdwährung anzeigen', Description=NULL, Help=NULL WHERE AD_Element_ID=542933 AND IsCentrallyMaintained='Y'
;

-- 16.12.2015 17:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Saldo Fremdwährung anzeigen', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542933) AND IsCentrallyMaintained='Y'
;

-- 16.12.2015 17:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Saldo Fremdwährung anzeigen', Name='Saldo Fremdwährung anzeigen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542933)
;

