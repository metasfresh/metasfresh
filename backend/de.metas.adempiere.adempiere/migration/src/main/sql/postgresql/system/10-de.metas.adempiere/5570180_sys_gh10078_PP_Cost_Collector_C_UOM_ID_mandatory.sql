-- 2020-10-09T20:40:58.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-10-09 23:40:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53812
;

-- 2020-10-09T20:40:59.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_cost_collector','C_UOM_ID','NUMERIC(10)',null,null)
;

-- 2020-10-09T20:40:59.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_cost_collector','C_UOM_ID',null,'NOT NULL',null)
;

