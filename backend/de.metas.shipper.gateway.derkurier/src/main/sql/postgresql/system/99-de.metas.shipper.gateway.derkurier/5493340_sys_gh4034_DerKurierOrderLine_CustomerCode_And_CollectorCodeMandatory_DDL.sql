-- 2018-05-11T14:53:21.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('derkurier_deliveryorderline','CollectorCode','VARCHAR(250)',null,'00')
;
UPDATE derkurier_deliveryorderline SET CollectorCode='00' where CollectorCode IS NULL;

-- 2018-05-11T14:53:21.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('derkurier_deliveryorderline','CollectorCode',null,'NOT NULL','00')
;

-- 2018-05-11T14:54:07.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('derkurier_deliveryorderline','CustomerCode','VARCHAR(250)',null,'00')
;

UPDATE derkurier_deliveryorderline SET CustomerCode='00' where CustomerCode IS NULL;

-- 2018-05-11T14:54:07.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('derkurier_deliveryorderline','CustomerCode',null,'NOT NULL','00')
;

