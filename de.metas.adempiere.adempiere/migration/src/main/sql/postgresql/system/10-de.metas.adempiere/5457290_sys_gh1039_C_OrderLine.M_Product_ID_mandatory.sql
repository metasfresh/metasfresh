-- Feb 28, 2017 1:07 AM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@S_ResourceAssignment_ID@>0 | @C_Charge_ID@>0',Updated=TO_TIMESTAMP('2017-02-28 01:07:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2221
;

-- Feb 28, 2017 1:07 AM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2017-02-28 01:07:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2221
;

-- Feb 28, 2017 1:08 AM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_orderline','M_Product_ID','NUMERIC(10)',null,null)
;

-- Feb 28, 2017 1:08 AM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_orderline','M_Product_ID',null,'NOT NULL',null)
;

