

-- note that our postgres 9.5. does not yet have "ADD COLUMN IF NOT EXISTS"
DO $$
    BEGIN
        -- 2019-02-07T17:32:44.291
        -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
        /* DDL */ SELECT public.db_alter_table('AD_Process','ALTER TABLE public.AD_Process ADD COLUMN IsTranslateExcelHeaders CHAR(1) DEFAULT ''Y'' CHECK (IsTranslateExcelHeaders IN (''Y'',''N'')) NOT NULL')
        ;
    EXCEPTION WHEN SQLSTATE '42701' THEN
        RAISE NOTICE 'IsTranslateExcelHeaders column already existed; Ignoring; Error-Code %, Message=%', SQLSTATE, SQLERRM;
    END
$$;
