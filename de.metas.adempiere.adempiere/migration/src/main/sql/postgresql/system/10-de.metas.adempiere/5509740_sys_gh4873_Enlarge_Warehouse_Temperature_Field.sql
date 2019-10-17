-- 2019-01-16T17:13:20.015
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=14, FieldLength=2000,Updated=TO_TIMESTAMP('2019-01-16 17:13:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=561006
;

-- 2019-01-16T17:13:27.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_product','Warehouse_temperature','VARCHAR(2000)',null,null)
;

