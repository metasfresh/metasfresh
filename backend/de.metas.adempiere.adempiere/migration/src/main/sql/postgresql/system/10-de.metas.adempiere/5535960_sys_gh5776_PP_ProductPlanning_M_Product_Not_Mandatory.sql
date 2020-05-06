-- 2019-11-08T09:46:10.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2019-11-08 11:46:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53389
;

-- 2019-11-08T09:46:14.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_product_planning','M_Product_ID','NUMERIC(10)',null,null)
;

-- 2019-11-08T09:46:14.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_product_planning','M_Product_ID',null,'NULL',null)
;

