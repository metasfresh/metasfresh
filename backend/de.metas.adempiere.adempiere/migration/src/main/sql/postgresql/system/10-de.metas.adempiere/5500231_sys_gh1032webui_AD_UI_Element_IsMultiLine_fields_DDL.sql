--thanks to/further reading: https://stackoverflow.com/a/12608570/1012103
DO $$
    BEGIN
        BEGIN
            -- 2018-08-30T12:44:17.344
            -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ PERFORM public.db_alter_table('AD_UI_Element','ALTER TABLE public.AD_UI_Element ADD COLUMN IsMultiLine CHAR(1) DEFAULT ''N'' CHECK (IsMultiLine IN (''Y'',''N'')) NOT NULL')
;
        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column IsMultiLine already exists in AD_UI_Element.';
        END;
    END;
$$;


DO $$
    BEGIN
        BEGIN
-- 2018-08-30T12:58:43.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ PERFORM public.db_alter_table('AD_UI_Element','ALTER TABLE public.AD_UI_Element ADD COLUMN MultiLine_LinesCount NUMERIC(10)')
;
        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column MultiLine_LinesCount already exists in AD_UI_Element.';
        END;
    END;
$$;