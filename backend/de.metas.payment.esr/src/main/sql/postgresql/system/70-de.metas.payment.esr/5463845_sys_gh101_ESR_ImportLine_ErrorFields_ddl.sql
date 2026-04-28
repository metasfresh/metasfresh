
SELECT public.db_alter_table('esr_importline', 'ALTER TABLE public.ESR_ImportLine RENAME ErrorMsg TO ImportErrorMsg')
;

-- 2017-05-30T07:52:44.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('esr_importline','ALTER TABLE public.ESR_ImportLine ADD COLUMN MatchErrorMsg VARCHAR(2500)')
;

