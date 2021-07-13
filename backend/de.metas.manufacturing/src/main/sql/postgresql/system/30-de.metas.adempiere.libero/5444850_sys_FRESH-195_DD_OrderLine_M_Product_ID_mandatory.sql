-- 19.04.2016 20:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2016-04-19 20:35:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53929
;

-- 19.04.2016 20:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_orderline','M_Product_ID','NUMERIC(10)',null,null)
;

-- 19.04.2016 20:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('dd_orderline','M_Product_ID',null,'NOT NULL',null)
;

