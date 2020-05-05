-- 2020-05-05T08:46:25.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=-1.000000000000,Updated=TO_TIMESTAMP('2020-05-05 11:46:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=589495
;

-- 2020-05-05T08:47:27.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(seqno),0)+10 AS DefaultValue FROM M_Product_Ingredients WHERE M_Product_ID=@M_Product_ID/0@',Updated=TO_TIMESTAMP('2020-05-05 11:47:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569007
;
