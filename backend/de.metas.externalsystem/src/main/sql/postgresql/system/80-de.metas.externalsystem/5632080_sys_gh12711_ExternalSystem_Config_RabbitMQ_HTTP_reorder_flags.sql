-- 2022-03-27T17:12:35.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2022-03-27 20:12:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605256
;

-- 2022-03-27T17:19:10.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N',Updated=TO_TIMESTAMP('2022-03-27 20:19:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575933
;

-- 2022-03-27T17:58:06.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config_rabbitmq_http','IsSyncBPartnersToRabbitMQ','CHAR(1)',null,'N')
;

-- 2022-03-27T17:58:06.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE ExternalSystem_Config_RabbitMQ_HTTP SET IsSyncBPartnersToRabbitMQ='N' WHERE IsSyncBPartnersToRabbitMQ IS NULL
;

