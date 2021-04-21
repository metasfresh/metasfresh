-- 2021-02-18T23:02:43.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Window','ALTER TABLE public.AD_Window ADD COLUMN IsExcludeFromZoomTargets CHAR(1) DEFAULT ''N'' CHECK (IsExcludeFromZoomTargets IN (''Y'',''N'')) NOT NULL')
;

