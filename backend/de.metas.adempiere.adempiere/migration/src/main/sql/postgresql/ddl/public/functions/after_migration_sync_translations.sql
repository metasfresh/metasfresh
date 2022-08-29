
DROP FUNCTION IF EXISTS after_migration_sync_translations();


CREATE FUNCTION after_migration_sync_translations() RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    count_updated NUMERIC;
BEGIN
    SELECT 1 FROM update_TRL_Tables_On_AD_Element_TRL_Update(NULL, NULL);

    RAISE NOTICE 'Synchronized translations';

END;
$$
;

ALTER FUNCTION after_migration_sync_translations() OWNER TO metasfresh
;

select count(1), ad_client_id from c_doctype group by ad_client_id