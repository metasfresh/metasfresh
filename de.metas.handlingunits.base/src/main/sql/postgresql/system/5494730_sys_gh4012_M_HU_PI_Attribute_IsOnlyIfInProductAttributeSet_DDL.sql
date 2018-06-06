

-- 2018-05-29T18:20:14.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_HU_PI_Attribute','ALTER TABLE public.M_HU_PI_Attribute ADD COLUMN IsOnlyIfInProductAttributeSet CHAR(1) DEFAULT ''N'' CHECK (IsOnlyIfInProductAttributeSet IN (''Y'',''N'')) NOT NULL')
;

