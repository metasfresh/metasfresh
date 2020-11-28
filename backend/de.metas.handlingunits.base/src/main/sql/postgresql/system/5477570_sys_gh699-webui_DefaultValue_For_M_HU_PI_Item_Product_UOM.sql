-- 2017-11-20T12:00:14.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=select c_uom_id from m_product where M_product_ID=@M_Product_ID@',Updated=TO_TIMESTAMP('2017-11-20 12:00:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549286
;
