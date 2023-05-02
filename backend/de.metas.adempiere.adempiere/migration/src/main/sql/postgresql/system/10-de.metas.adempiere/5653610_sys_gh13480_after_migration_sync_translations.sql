
CREATE FUNCTION after_migration_sync_translations() RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    count_updated NUMERIC;
BEGIN
    PERFORM update_TRL_Tables_On_AD_Element_TRL_Update();

    RAISE NOTICE 'Synchronized translations';

END;
$$
;
