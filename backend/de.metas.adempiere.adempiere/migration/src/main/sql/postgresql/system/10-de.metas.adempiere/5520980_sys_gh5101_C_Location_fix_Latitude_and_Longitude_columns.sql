alter table C_Location drop column if exists latitude;

-- 2019-05-06T19:27:24.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Location','ALTER TABLE public.C_Location ADD COLUMN Latitude NUMERIC')
;

alter table C_Location drop column if exists longitude;

-- 2019-05-06T19:30:06.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Location','ALTER TABLE public.C_Location ADD COLUMN Longitude NUMERIC')
;

