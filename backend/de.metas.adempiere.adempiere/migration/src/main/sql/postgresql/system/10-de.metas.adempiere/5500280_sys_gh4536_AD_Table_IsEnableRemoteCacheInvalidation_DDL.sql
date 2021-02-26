
--thanks to/further reading: https://stackoverflow.com/a/12608570/1012103
DO $$
    BEGIN
        BEGIN
            -- 2018-08-30T17:13:01.042
            -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
            /* DDL */ PERFORM public.db_alter_table('AD_Table','ALTER TABLE public.AD_Table ADD COLUMN IsEnableRemoteCacheInvalidation CHAR(1) DEFAULT ''N'' CHECK (IsEnableRemoteCacheInvalidation IN (''Y'',''N'')) NOT NULL')
            ;
        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column IsEnableRemoteCacheInvalidation already exists in AD_Table.';
        END;
    END;
$$;
