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