-- 2017-09-26T20:02:09.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2017-09-26 20:02:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557318
;

-- 2017-09-26T20:02:14.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipmentschedule','Bill_BPartner_ID','NUMERIC(10)',null,null)
;

-- 2017-09-26T20:02:14.990
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_shipmentschedule','Bill_BPartner_ID',null,'NOT NULL',null)
;

