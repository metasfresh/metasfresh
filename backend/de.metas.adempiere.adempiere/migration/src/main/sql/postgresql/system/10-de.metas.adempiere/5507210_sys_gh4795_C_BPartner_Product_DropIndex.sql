-- 2018-11-27T13:43:28.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET IsActive='N',Updated=TO_TIMESTAMP('2018-11-27 13:43:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540378
;




drop index if exists C_BPartner_Product_Org_Unique;