-- 2018-09-18T13:29:49.231
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='M_Product_LotNumber_Quarantine', TableName='M_Product_LotNumber_Quarantine',Updated=TO_TIMESTAMP('2018-09-18 13:29:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540954
;

-- 2018-09-18T13:29:49.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Name='M_Product_LotNumber_Quarantine',Updated=TO_TIMESTAMP('2018-09-18 13:29:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=554518
;










-- 2018-09-18T14:07:37.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='M_Product_LotNumber_Quarantine_ID', Name='M_Product_LotNumber_Quarantine', PrintName='M_Product_LotNumber_Quarantine',Updated=TO_TIMESTAMP('2018-09-18 14:07:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543930
;

-- 2018-09-18T14:07:37.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_Product_LotNumber_Quarantine_ID', Name='M_Product_LotNumber_Quarantine', Description=NULL, Help=NULL WHERE AD_Element_ID=543930
;

-- 2018-09-18T14:07:37.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Product_LotNumber_Quarantine_ID', Name='M_Product_LotNumber_Quarantine', Description=NULL, Help=NULL, AD_Element_ID=543930 WHERE UPPER(ColumnName)='M_PRODUCT_LOTNUMBER_QUARANTINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-09-18T14:07:37.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Product_LotNumber_Quarantine_ID', Name='M_Product_LotNumber_Quarantine', Description=NULL, Help=NULL WHERE AD_Element_ID=543930 AND IsCentrallyMaintained='Y'
;

-- 2018-09-18T14:07:37.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='M_Product_LotNumber_Quarantine', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543930) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543930)
;

-- 2018-09-18T14:07:37.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='M_Product_LotNumber_Quarantine', Name='M_Product_LotNumber_Quarantine' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543930)
;






