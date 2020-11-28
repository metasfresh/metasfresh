-- 2018-08-13T19:12:06.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2018-08-13 19:12:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3815
;

-- 2018-08-13T19:12:08.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_inventory','M_Warehouse_ID','NUMERIC(10)',null,null)
;

-- 2018-08-13T19:12:08.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_inventory','M_Warehouse_ID',null,'NULL',null)
;

