-- 2021-06-22T13:38:30.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN C_Campaign_ID NUMERIC(10)')
;

-- 2021-06-22T13:38:32.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_BPartner ADD CONSTRAINT CCampaign_CBPartner FOREIGN KEY (C_Campaign_ID) REFERENCES public.C_Campaign DEFERRABLE INITIALLY DEFERRED
;