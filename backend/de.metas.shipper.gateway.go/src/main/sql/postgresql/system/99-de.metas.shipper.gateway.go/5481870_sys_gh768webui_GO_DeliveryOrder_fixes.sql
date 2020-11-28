-- 2018-01-08T15:12:07.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2018-01-08 15:12:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558450
;

-- 2018-01-08T15:12:12.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('go_deliveryorder','GO_DeliverToCompanyName2','VARCHAR(255)',null,null)
;

-- 2018-01-08T15:12:12.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('go_deliveryorder','GO_DeliverToCompanyName2',null,'NULL',null)
;

