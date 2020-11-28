
--thanks to/further reading: https://stackoverflow.com/a/12608570/1012103
DO $$
    BEGIN
        BEGIN
            -- 2019-03-25T18:03:32.657
            -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
            /* DDL */ PERFORM public.db_alter_table('AD_Tab','ALTER TABLE public.AD_Tab ADD COLUMN IsRefreshViewOnChangeEvents CHAR(1) DEFAULT ''N'' CHECK (IsRefreshViewOnChangeEvents IN (''Y'',''N'')) NOT NULL')
            ;
        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column IsRefreshViewOnChangeEvents already exists in AD_Tab.';
        END;
    END;
$$;