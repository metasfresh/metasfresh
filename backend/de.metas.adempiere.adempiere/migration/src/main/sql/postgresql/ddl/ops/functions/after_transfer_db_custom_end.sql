CREATE OR REPLACE FUNCTION ops.after_transfer_db_custom_end(p_sourceInstance text,
                                                            p_targetInstance text) RETURNS void
AS
$BODY$
BEGIN
    -- e.g. re-activate S_ExternalSystems, but change the config to the respective external sandbox-system
END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
;

COMMENT ON FUNCTION ops.after_transfer_db_custom_end(p_sourceInstance text, p_targetInstance text) IS
    'This function is empty by default, but can contain customer specific code. It is invoked by after_transfer_db after anything else is done
For example, it can customize and reactivate settings that are generally deactivated when transferring from PROD to a test instance'
;