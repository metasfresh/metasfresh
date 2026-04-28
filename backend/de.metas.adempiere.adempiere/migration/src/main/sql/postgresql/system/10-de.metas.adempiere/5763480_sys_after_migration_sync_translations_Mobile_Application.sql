DROP FUNCTION IF EXISTS after_migration_sync_translations()
;


CREATE FUNCTION after_migration_sync_translations() RETURNS void
    LANGUAGE plpgsql
AS
$$
BEGIN
    PERFORM update_TRL_Tables_On_AD_Element_TRL_Update();
    PERFORM update_Mobile_Application_TRLs();

    RAISE NOTICE 'Synchronized translations';

END;
$$
;
