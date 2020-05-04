-- 2017-12-15T14:11:31.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2017-12-15 14:11:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=540432
;

-- 2017-12-15T14:11:38.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shippertransportation','CollectiveBillReport','CHAR(1)',null,null)
;

-- 2017-12-15T14:11:38.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shippertransportation','CollectiveBillReport',null,'NULL',null)
;

