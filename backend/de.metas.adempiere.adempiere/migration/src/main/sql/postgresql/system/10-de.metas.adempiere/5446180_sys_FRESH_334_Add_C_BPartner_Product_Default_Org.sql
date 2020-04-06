

-- 25.05.2016 16:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@AD_Org_ID@',Updated=TO_TIMESTAMP('2016-05-25 16:42:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=10165
;

-- 25.05.2016 16:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DefaultValue='@SQL=select ad_org_id from M_Product where M_Product_ID = @M_Product_ID/-1@',Updated=TO_TIMESTAMP('2016-05-25 16:43:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=8379
;


