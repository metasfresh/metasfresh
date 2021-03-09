
-- 2020-01-10T19:21:15.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadv','FulFillmentPercent',null,'NOT NULL',null)
;

-- 2020-01-10T19:38:42.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='FulfillmentPercent',Updated=TO_TIMESTAMP('2020-01-10 20:38:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542906
;

-- 2020-01-10T19:38:42.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FulfillmentPercent', Name='Geliefert %', Description=NULL, Help=NULL WHERE AD_Element_ID=542906
;

-- 2020-01-10T19:38:42.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FulfillmentPercent', Name='Geliefert %', Description=NULL, Help=NULL, AD_Element_ID=542906 WHERE UPPER(ColumnName)='FULFILLMENTPERCENT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-10T19:38:42.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FulfillmentPercent', Name='Geliefert %', Description=NULL, Help=NULL WHERE AD_Element_ID=542906 AND IsCentrallyMaintained='Y'
;

ALTER TABLE EDI_Desadv RENAME COLUMN EDI_DESADV_MinimumSumPercentage TO FulfillmentPercentMin;
-- 2020-01-10T19:51:29.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='FulfillmentPercentMin',Updated=TO_TIMESTAMP('2020-01-10 20:51:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542908
;

-- 2020-01-10T19:51:29.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FulfillmentPercentMin', Name='Geliefert % Minimum', Description=NULL, Help=NULL WHERE AD_Element_ID=542908
;

-- 2020-01-10T19:51:29.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FulfillmentPercentMin', Name='Geliefert % Minimum', Description=NULL, Help=NULL, AD_Element_ID=542908 WHERE UPPER(ColumnName)='FULFILLMENTPERCENTMIN' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-10T19:51:29.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FulfillmentPercentMin', Name='Geliefert % Minimum', Description=NULL, Help=NULL WHERE AD_Element_ID=542908 AND IsCentrallyMaintained='Y'
;

-- 2020-01-10T20:17:11.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-01-10 21:17:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549207
;

