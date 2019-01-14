-- 2018-12-18T13:59:51.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-12-18 13:59:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563714
;

-- 2018-12-18T13:59:54.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_costdetail','C_UOM_ID','NUMERIC(10)',null,null)
;

-- 2018-12-18T13:59:54.478
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_costdetail','C_UOM_ID',null,'NOT NULL',null)
;

-- 2018-12-18T14:00:07.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-12-18 14:00:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563713
;

-- 2018-12-18T14:00:08.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_costdetail','C_Currency_ID','NUMERIC(10)',null,null)
;

-- 2018-12-18T14:00:08.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_costdetail','C_Currency_ID',null,'NOT NULL',null)
;

