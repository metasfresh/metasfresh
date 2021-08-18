CREATE OR REPLACE FUNCTION ops.after_transfer_db_custom_begin(p_sourceInstance text,
                                                              p_targetInstance text) RETURNS void
AS
$BODY$
BEGIN
    -- e.g. remove data that shall not be on the target instance.
END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
;

COMMENT ON FUNCTION ops.after_transfer_db_custom_begin(p_sourceInstance text, p_targetInstance text) IS
    'This function is empty by default, but can contain customer specific code. It is invoked by after_transfer_db before anything else is done
For example, it can delete unneeded data to speed up scrambling and spare disk space.'
;

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

CREATE OR REPLACE FUNCTION ops.after_transfer_db(
    p_source_instance            text,
    p_target_instance            text,
    p_target_webui_url           text,
    p_target_metasfresh_pw       text,
    p_target_has_reports_service boolean) RETURNS void
AS
$BODY$
BEGIN
    EXECUTE ops.after_transfer_db_custom_begin(p_source_instance, p_target_instance);

    UPDATE AD_SysConfig SET Value=p_target_webui_url WHERE Name = 'webui.frontend.url';

    UPDATE ad_user SET password =public.hash_column_value_if_needed(p_target_metasfresh_pw) WHERE name = 'metasfresh';

    IF p_target_has_reports_service
    THEN
        UPDATE ad_sysconfig SET value='http://reports:8080/adempiereJasper/ReportServlet' WHERE name ILIKE 'de.metas.adempiere.report.jasper.JRServerServlet';
        UPDATE ad_sysconfig SET value='http://reports:8080/adempiereJasper/BarcodeServlet' WHERE name ILIKE 'de.metas.adempiere.report.barcode.BarcodeServlet';
    END IF;

    UPDATE externalsystem_config SET isactive = 'N' WHERE 1 = 1;

    RAISE NOTICE '% !! ExternalSystem records deactivated !!', CLOCK_TIMESTAMP();

    UPDATE ad_scheduler
    SET isactive = 'N'
    WHERE ad_scheduler_id IN (
        SELECT scheduler.ad_scheduler_id
        FROM ad_column adColumn
                 INNER JOIN ad_table adTable ON adColumn.ad_table_id = adTable.ad_table_id
                 INNER JOIN ad_table_process adTablePorcess ON adTable.ad_table_id = adTablePorcess.ad_table_id
                 INNER JOIN ad_scheduler scheduler ON adTablePorcess.ad_process_id = scheduler.ad_process_id
        WHERE adColumn.columnname ILIKE 'ExternalSystem_Config_Id'
          AND adTable.tablename ILIKE '%ExternalSystem%'
    );

    RAISE NOTICE '% !! Deactivated Ad_Schedulers for ExternalSystems !!', CLOCK_TIMESTAMP();

    IF p_source_instance ILIKE '%prod'
    THEN
        EXECUTE ops.scramble_metasfresh(FALSE);
    END IF;

    EXECUTE ops.after_transfer_db_custom_end(p_source_instance, p_target_instance);
END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
;

COMMENT ON FUNCTION ops.after_transfer_db(p_source_instance text, p_target_instance text, p_target_webui_url text,
    p_target_metasfresh_pw text,
    p_target_has_reports_service boolean) IS
    'Example: select ops.after_transfer_db(''instancesprod'',''instancesdev'', ''webui.frontend.url'', ''secret'', true)'
;