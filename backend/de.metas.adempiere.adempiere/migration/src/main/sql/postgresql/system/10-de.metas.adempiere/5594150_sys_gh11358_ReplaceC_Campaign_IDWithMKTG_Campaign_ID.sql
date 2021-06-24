
-- Delete C_Campaign_ID From C_BPartner
-- 2021-06-23T10:20:16.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE C_BPartner DROP COLUMN IF EXISTS C_Campaign_ID')
;

-- Add MKTG_Campaign_ID  to C_BPartner
-- 2021-06-23T10:21:57.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN MKTG_Campaign_ID NUMERIC(10)')
;

-- 2021-06-23T10:21:59.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_BPartner ADD CONSTRAINT MKTGCampaign_CBPartner FOREIGN KEY (MKTG_Campaign_ID) REFERENCES public.MKTG_Campaign DEFERRABLE INITIALLY DEFERRED
;

-- Delete C_Campaign_ID From C_BPartner_QuickInput
-- 2021-06-23T10:23:39.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_QuickInput','ALTER TABLE C_BPartner_QuickInput DROP COLUMN IF EXISTS C_Campaign_ID')
;

-- Add MKTG_Campaign_ID  to C_BPartner_QuickInput
-- 2021-06-23T10:24:33.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_QuickInput','ALTER TABLE public.C_BPartner_QuickInput ADD COLUMN MKTG_Campaign_ID NUMERIC(10)')
;

-- 2021-06-23T10:24:33.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_BPartner_QuickInput ADD CONSTRAINT MKTGCampaign_CBPartnerQuickInput FOREIGN KEY (MKTG_Campaign_ID) REFERENCES public.MKTG_Campaign DEFERRABLE INITIALLY DEFERRED
;