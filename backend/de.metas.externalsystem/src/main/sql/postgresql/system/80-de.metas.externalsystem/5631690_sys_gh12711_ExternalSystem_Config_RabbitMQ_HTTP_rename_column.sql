-- 2022-03-24T09:22:15.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='SubjectCreatedByUserGroup_ID',Updated=TO_TIMESTAMP('2022-03-24 11:22:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580584
;

-- 2022-03-24T09:22:15.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SubjectCreatedByUserGroup_ID', Name='Nutzergruppe', Description=NULL, Help=NULL WHERE AD_Element_ID=580584
;

-- 2022-03-24T09:22:15.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SubjectCreatedByUserGroup_ID', Name='Nutzergruppe', Description=NULL, Help=NULL, AD_Element_ID=580584 WHERE UPPER(ColumnName)='SUBJECTCREATEDBYUSERGROUP_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-24T09:22:15.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SubjectCreatedByUserGroup_ID', Name='Nutzergruppe', Description=NULL, Help=NULL WHERE AD_Element_ID=580584 AND IsCentrallyMaintained='Y'
;

-- 2022-03-24T09:24:52.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_RabbitMQ_HTTP','ALTER TABLE public.ExternalSystem_Config_RabbitMQ_HTTP ADD COLUMN SubjectCreatedByUserGroup_ID NUMERIC(10)')
;

-- 2022-03-24T09:24:52.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE ExternalSystem_Config_RabbitMQ_HTTP ADD CONSTRAINT SubjectCreatedByUserGroup_ExternalSystemConfigRabbitMQHTTP FOREIGN KEY (SubjectCreatedByUserGroup_ID) REFERENCES public.AD_UserGroup DEFERRABLE INITIALLY DEFERRED
;


UPDATE ExternalSystem_Config_RabbitMQ_HTTP rabbitmqConfig
SET subjectcreatedbyusergroup_id = rabbitmqConfig.bpartnercreatedbyusergroup_id, Updated=now(), UpdatedBy=99
;

