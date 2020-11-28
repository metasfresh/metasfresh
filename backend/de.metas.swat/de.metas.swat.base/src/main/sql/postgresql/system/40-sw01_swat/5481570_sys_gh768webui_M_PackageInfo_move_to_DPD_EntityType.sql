-- 2018-01-03T13:42:07.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET EntityType='de.metas.dpd',Updated=TO_TIMESTAMP('2018-01-03 13:42:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540122
;

update AD_Column set EntityType='de.metas.dpd' where AD_Table_ID=540122;

