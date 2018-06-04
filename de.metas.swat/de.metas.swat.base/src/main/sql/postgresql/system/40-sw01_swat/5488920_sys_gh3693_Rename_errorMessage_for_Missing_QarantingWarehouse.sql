-- 2018-03-20T14:47:28.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='M_Warehouse_NoQuarantineWarehouse',Updated=TO_TIMESTAMP('2018-03-20 14:47:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544656
;

-- 2018-03-20T14:47:39.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-03-20 14:47:39','YYYY-MM-DD HH24:MI:SS'),MsgText='No Quarantine Warehouse Found' WHERE AD_Message_ID=544656 AND AD_Language='en_US'
;

