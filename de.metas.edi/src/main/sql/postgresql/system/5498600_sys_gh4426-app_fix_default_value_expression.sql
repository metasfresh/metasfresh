

-- 2018-08-01T07:12:40.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.esb.edi',Updated=TO_TIMESTAMP('2018-08-01 07:12:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565204
;


-- 2018-08-01T08:41:11.419
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@IsEdiRecipient/N@', ReadOnlyLogic='@IsEdiRecipient/N@ = N',Updated=TO_TIMESTAMP('2018-08-01 08:41:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;

-- 2018-08-01T08:47:15.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='@IsEdiRecipient/N@ is based on an SQL column that is "null" at the time a new document is initialized. that''s why the "/N" in the logic expressions is very impartant',Updated=TO_TIMESTAMP('2018-08-01 08:47:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;

-- 2018-08-01T08:50:52.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='This field''s value is null durinig document initialization; if you use it in a logic expression, please be sure to provide a default value, e.g. use "@IsEdiRecipient/N@"',Updated=TO_TIMESTAMP('2018-08-01 08:50:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552603
;

-- 2018-08-01T08:50:58.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='This field''s value is null during document initialization; if you use it in a logic expression, please be sure to provide a default value, e.g. use "@IsEdiRecipient/N@"',Updated=TO_TIMESTAMP('2018-08-01 08:50:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552603
;

-- 2018-08-01T08:51:10.641
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='@IsEdiRecipient/N@ is based on an SQL column that is "null" at the time a new document is initialized. that''s why the "/N" in the logic expressions is very important.',Updated=TO_TIMESTAMP('2018-08-01 08:51:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552598
;

