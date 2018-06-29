-- 2018-06-29T18:14:11.835
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=11,Updated=TO_TIMESTAMP('2018-06-29 18:14:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560543
;

/* DDL */ SELECT public.db_alter_table('I_Datev_Payment','ALTER TABLE public.I_Datev_Payment Drop COLUMN BPartnerValue')
;


-- 2018-06-29T18:14:59.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_Datev_Payment','ALTER TABLE public.I_Datev_Payment ADD COLUMN BPartnerValue NUMERIC(10)')
;

