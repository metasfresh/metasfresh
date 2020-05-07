

-- 2018-09-06T06:51:26.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_UI_ElementField','ALTER TABLE public.AD_UI_ElementField ADD COLUMN Type VARCHAR(7) DEFAULT ''widget'' NOT NULL')
;


-- 2018-09-07T07:48:49.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_UI_ElementField','ALTER TABLE public.AD_UI_ElementField ADD COLUMN TooltipIconName VARCHAR(50)')
;

