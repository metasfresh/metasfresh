-- 2019-02-14T11:18:28.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='BPartner Product InOut statistics Online View', TableName='C_BPartner_Product_Stats_InOut_Online_v',Updated=TO_TIMESTAMP('2019-02-14 11:18:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541172
;

-- 2019-02-14T11:18:41.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-14 11:18:41','YYYY-MM-DD HH24:MI:SS'),Name='BPartner Product InOut statistics Online View' WHERE AD_Table_ID=541172 AND AD_Language='en_US'
;

-- 2019-02-14T11:18:52.296
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-14 11:18:52','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Table_ID=541172 AND AD_Language='en_US'
;

-- 2019-02-14T11:18:58.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-14 11:18:58','YYYY-MM-DD HH24:MI:SS'),Name='BPartner Product InOut statistics Online View' WHERE AD_Table_ID=541172 AND AD_Language='de_DE'
;

-- 2019-02-14T11:19:03.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-14 11:19:03','YYYY-MM-DD HH24:MI:SS'),Name='BPartner Product InOut statistics Online View' WHERE AD_Table_ID=541172 AND AD_Language='de_CH'
;

delete from AD_Sequence where name='C_BPartner_Product_Stats_Invoice_Online_V';

