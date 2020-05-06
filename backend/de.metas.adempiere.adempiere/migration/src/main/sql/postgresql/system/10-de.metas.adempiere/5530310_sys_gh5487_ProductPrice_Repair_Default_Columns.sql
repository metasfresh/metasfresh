-- 2019-09-10T13:03:47.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(MatchSeqNo),0)+10 AS DefaultValue FROM M_ProductPrice WHERE M_PriceList_Version_ID=@M_PriceList_Version_ID/-1@ AND M_Product_ID=@M_Product_ID/-1@',Updated=TO_TIMESTAMP('2019-09-10 16:03:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556079
;

-- 2019-09-10T13:07:59.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM M_ProductPrice WHERE M_PriceList_Version_ID=@M_PriceList_Version_ID/-1@',Updated=TO_TIMESTAMP('2019-09-10 16:07:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550681
;

