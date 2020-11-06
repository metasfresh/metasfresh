--thanks to/further reading: https://stackoverflow.com/a/12608570/1012103
DO
$$
    BEGIN
        BEGIN
            -- 2019-11-15T15:55:12.592Z
            -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
            /* DDL */ PERFORM public.db_alter_table('AD_InputDataSource', 'ALTER TABLE public.AD_InputDataSource ADD COLUMN Value VARCHAR(250)');
        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column Value already exists in AD_InputDataSource.';
        END;
    END;
$$
;

--thanks to/further reading: https://stackoverflow.com/a/12608570/1012103
DO
$$
    BEGIN
        BEGIN
            -- 2019-11-15T15:55:38.549Z
            -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
            /* DDL */ PERFORM public.db_alter_table('AD_InputDataSource', 'ALTER TABLE public.AD_InputDataSource ADD COLUMN ExternalId VARCHAR(250)');
        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column ExternalId already exists in AD_InputDataSource.';
        END;
    END;
$$
;

--thanks to/further reading: https://stackoverflow.com/a/12608570/1012103
DO
$$
    BEGIN
        BEGIN
            -- 2019-11-15T15:56:41.907Z
            -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
            /* DDL */ PERFORM public.db_alter_table('AD_InputDataSource', 'ALTER TABLE public.AD_InputDataSource ADD COLUMN URL VARCHAR(250)');
        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column URL already exists in AD_InputDataSource.';
        END;
    END;
$$
;
