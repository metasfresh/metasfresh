-- 2022-03-25T13:15:40.873Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSyncBPartnersToRabbitMQ@=''Y'' | @IsSyncExternalReferencesToRabbitMQ@=''Y''',Updated=TO_TIMESTAMP('2022-03-25 15:15:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=680055
;

