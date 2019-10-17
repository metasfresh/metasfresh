-- 2018-12-18T14:18:54.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-12-18 14:18:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563716
;

-- 2018-12-18T14:18:55.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_cost','C_UOM_ID','NUMERIC(10)',null,null)
;

-- 2018-12-18T14:18:55.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_cost','C_UOM_ID',null,'NOT NULL',null)
;

-- 2018-12-18T14:19:03.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-12-18 14:19:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563715
;

-- 2018-12-18T14:19:04.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_cost','C_Currency_ID','NUMERIC(10)',null,null)
;

-- 2018-12-18T14:19:04.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_cost','C_Currency_ID',null,'NOT NULL',null)
;

