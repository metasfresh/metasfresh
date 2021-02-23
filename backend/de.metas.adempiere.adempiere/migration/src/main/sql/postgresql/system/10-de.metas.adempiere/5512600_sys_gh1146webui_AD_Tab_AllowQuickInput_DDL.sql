

--thanks to/further reading: https://stackoverflow.com/a/12608570/1012103
DO $$
    BEGIN
        BEGIN
            -- 2019-02-13T12:58:28.244
            -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
            /* DDL */ PERFORM public.db_alter_table('AD_Tab','ALTER TABLE public.AD_Tab ADD COLUMN AllowQuickInput CHAR(1) DEFAULT ''Y'' CHECK (AllowQuickInput IN (''Y'',''N'')) NOT NULL')
            ;
        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column AllowQuickInput already exists in AD_Tab.';
        END;
    END;
$$;
