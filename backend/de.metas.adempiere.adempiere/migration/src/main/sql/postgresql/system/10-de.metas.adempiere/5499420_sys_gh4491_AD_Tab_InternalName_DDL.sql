-- 2018-08-17T16:32:38.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Tab','ALTER TABLE public.AD_Tab ADD COLUMN InternalName VARCHAR(255)')
;

create unique index AD_Tab_InternalName_UQ on AD_Tab (AD_Window_ID, InternalName) where IsActive='Y' and InternalName is not null;

