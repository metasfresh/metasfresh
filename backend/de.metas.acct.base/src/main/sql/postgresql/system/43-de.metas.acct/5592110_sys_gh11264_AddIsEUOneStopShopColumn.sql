-- 2021-06-10T12:23:23.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Org','ALTER TABLE public.AD_Org ADD COLUMN IsEUOneStopShop CHAR(1) DEFAULT ''N'' CHECK (IsEUOneStopShop IN (''Y'',''N'')) NOT NULL')
;