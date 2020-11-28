-- 2017-09-05T16:52:18.230
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=540745,Updated=TO_TIMESTAMP('2017-09-05 16:52:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557117
;

alter table I_Flatrate_Term drop column I_IsImported;

-- 2017-09-05T17:03:48.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('i_flatrate_term','ALTER TABLE public.I_Flatrate_Term ADD COLUMN I_IsImported CHAR(1) DEFAULT ''N'' NOT NULL')
;

