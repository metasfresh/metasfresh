-- 2021-06-23T07:20:36.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_QuickInput','ALTER TABLE public.C_BPartner_QuickInput ADD COLUMN C_Campaign_ID NUMERIC(10)')
;

-- 2021-06-23T07:20:36.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_BPartner_QuickInput ADD CONSTRAINT CCampaign_CBPartnerQuickInput FOREIGN KEY (C_Campaign_ID) REFERENCES public.C_Campaign DEFERRABLE INITIALLY DEFERRED
;