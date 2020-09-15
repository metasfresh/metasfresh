
-- 2020-09-15T07:17:39.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MD_Candidate','ALTER TABLE public.MD_Candidate ADD COLUMN Replenish_MinQty NUMERIC DEFAULT 0 NOT NULL')
;

-- 2020-09-15T07:20:30.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MD_Candidate','ALTER TABLE public.MD_Candidate ADD COLUMN Replenish_MaxQty NUMERIC DEFAULT 0 NOT NULL')
;
