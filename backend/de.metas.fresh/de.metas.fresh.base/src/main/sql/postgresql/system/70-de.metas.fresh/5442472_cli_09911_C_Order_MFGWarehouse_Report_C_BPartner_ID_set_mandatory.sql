-- 15.03.2016 12:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2016-03-15 12:22:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554231
;

-- 15.03.2016 12:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_order_mfgwarehouse_report','C_BPartner_ID','NUMERIC(10)',null,null)
;

-- 15.03.2016 12:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_order_mfgwarehouse_report','C_BPartner_ID',null,'NOT NULL',null)
;

