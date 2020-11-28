-- 2018-04-24T10:25:42.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DefaultValue='@SQL=select coalesce((select ad_org_id from M_Product where M_Product_ID = @M_Product_ID/-1@), 0)',Updated=TO_TIMESTAMP('2018-04-24 10:25:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563661
;

