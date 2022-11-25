DROP FUNCTION ops.after_transfer_db_custom_begin(p_source_instance text, p_target_instance text);
CREATE OR REPLACE FUNCTION ops.after_transfer_db_custom_begin(p_source_instance text,
                                                              p_target_instance text) RETURNS void
AS
$BODY$
BEGIN
    -- e.g. remove data that shall not be on the target instance.
END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
;

COMMENT ON FUNCTION ops.after_transfer_db_custom_begin(p_source_instance text, p_target_instance text) IS
    'This function is empty by default, but can contain customer specific code. It is invoked by after_transfer_db before anything else is done
For example, it can delete unneeded data to speed up scrambling and spare disk space.'
;

DROP FUNCTION ops.after_transfer_db_custom_end(p_source_instance text, p_target_instance text);
CREATE OR REPLACE FUNCTION ops.after_transfer_db_custom_end(p_source_instance text,
                                                            p_target_instance text) RETURNS void
AS
$BODY$
BEGIN
    -- e.g. re-activate S_ExternalSystems, but change the config to the respective external sandbox-system
END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
;

COMMENT ON FUNCTION ops.after_transfer_db_custom_end(p_source_instance text, p_target_instance text) IS
    'This function is empty by default, but can contain customer specific code. It is invoked by after_transfer_db after anything else is done
For example, it can customize and reactivate settings that are generally deactivated when transferring from PROD to a test instance'
;
