
CREATE OR REPLACE FUNCTION ops.es_fts_reindex_bpartners() RETURNS bigint
    LANGUAGE sql
AS
$$

DELETE
FROM es_fts_index_queue
WHERE TRUE;

INSERT INTO es_fts_index_queue (es_fts_config_id, eventtype, ad_table_id, record_id)
SELECT es_fts_config_id,
       'U'                        AS eventtype,
       get_table_id('C_BPartner') AS ad_table_id,
       bp.c_bpartner_id           AS record_id
FROM c_bpartner bp
         JOIN es_fts_config ON es_index = 'fts_bpartner'
ORDER BY bp.c_bpartner_id ;

SELECT count(1) as result FROM es_fts_index_queue;

$$
;

COMMENT ON FUNCTION ops.es_fts_reindex_bpartners() IS 'Causes metasfresh to resend all existing bpartner data to elastic search in order to enable full text search. 
Enqueues the C_BPartners for sending and returns the number of enqueued records.
You can then check the indexing progress with 

    SELECT processed,
       iserror,
       COUNT(1)                                                            AS count,
       (MAX(updated) - MIN(updated))                                       AS duration,
       EXTRACT(MILLISECONDS FROM (MAX(updated) - MIN(updated))) / COUNT(1) AS rate_millis
FROM es_fts_index_queue
GROUP BY processed, iserror;
';

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

    UPDATE ad_user SET password=public.hash_column_value_if_needed(p_target_metasfresh_pw) WHERE name = 'metasfresh';

    IF p_target_has_reports_service
    THEN
        UPDATE ad_sysconfig SET value='http://reports:8080/adempiereJasper/ReportServlet' WHERE name ILIKE 'de.metas.adempiere.report.jasper.JRServerServlet';
        UPDATE ad_sysconfig SET value='http://reports:8080/adempiereJasper/BarcodeServlet' WHERE name ILIKE 'de.metas.adempiere.report.barcode.BarcodeServlet';
    END IF;

    UPDATE externalsystem_config SET isactive = 'N' WHERE TRUE;
    RAISE NOTICE '% !! Deactivated ExternalSystem records !!', CLOCK_TIMESTAMP();

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

    RAISE NOTICE '% !! Enqueued % C_BPartners to be send to elastic search for FTS !!',
        CLOCK_TIMESTAMP(),
        (SELECT ops.es_fts_reindex_bpartners());

    EXECUTE ops.after_transfer_db_custom_end(p_source_instance, p_target_instance);
END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
;

COMMENT ON FUNCTION ops.after_transfer_db(p_source_instance text, p_target_instance text, p_target_webui_url text,
    p_target_metasfresh_pw text,
    p_target_has_reports_service boolean) IS
    'Example - WILL SCRAMBLE YOU DB: select ops.after_transfer_db(p_source_instance := ''instancesprod'', p_target_instance := ''instancesdev'', p_target_webui_url := ''webui.frontend.url'', p_target_metasfresh_pw := ''secret'', p_target_has_reports_service := true)'
;
