-- note that our postgres 9.5. does not yet have "ADD COLUMN IF NOT EXISTS"
DO $$
BEGIN
-- 2020-02-11T22:27:45.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Process','ALTER TABLE public.AD_Process ADD COLUMN IsNotifyUserAfterExecution CHAR(1) DEFAULT ''N'' CHECK (IsNotifyUserAfterExecution IN (''Y'',''N'')) NOT NULL')
;
EXCEPTION WHEN SQLSTATE '42701' THEN
    RAISE NOTICE 'At least one column already existed; Ignoring; Error-Code %, Message=%', SQLSTATE, SQLERRM;
END
$$;
