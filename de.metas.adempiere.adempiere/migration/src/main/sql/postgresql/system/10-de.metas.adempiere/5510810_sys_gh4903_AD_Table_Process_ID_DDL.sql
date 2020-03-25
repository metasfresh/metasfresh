-- note that our postgres 9.5. does not yet have "ADD COLUMN IF NOT EXISTS"
DO $$
BEGIN
-- 2019-01-25T17:32:26.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Table_Process ADD COLUMN AD_Table_Process_ID numeric(10,0)
;
EXCEPTION WHEN SQLSTATE '42701' THEN
    RAISE NOTICE 'At least one column already existed; Ignoring; Error-Code %, Message=%', SQLSTATE, SQLERRM;
END
$$;
