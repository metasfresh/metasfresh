-- 2017-10-20T14:27:05.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MD_Candidate_Transaction_Detail','ALTER TABLE public.MD_Candidate_Transaction_Detail ADD COLUMN MovementQty NUMERIC NOT NULL')
;

