-- 2018-11-27T14:19:59.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-11-27 14:19:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563629
;

-- 2018-11-27T14:20:00.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_order_node','C_UOM_ID','NUMERIC(10)',null,null)
;

-- 2018-11-27T14:20:00.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_order_node','C_UOM_ID',null,'NOT NULL',null)
;

