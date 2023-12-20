DROP FUNCTION IF EXISTS ops.es_fts_reindex_bpartners()
;


CREATE OR REPLACE FUNCTION ops.es_fts_reindex_bpartners() RETURNS bigint
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_es_fts_config_id numeric;
    v_count            bigint;
BEGIN
    SELECT es_fts_config_id
    INTO v_es_fts_config_id
    FROM es_fts_config
    WHERE es_index = 'fts_bpartner'
      AND isactive = 'Y';
    IF (v_es_fts_config_id IS NULL OR v_es_fts_config_id <= 0) THEN
        RAISE EXCEPTION 'No configuration found for `fts_bpartner` elasticsearch index';
    END IF;

    DELETE
    FROM es_fts_index_queue
    WHERE TRUE;
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    RAISE NOTICE 'Deletes % es_fts_index_queue rows', v_count;

    --
    -- Ask system to delete elastic search index first
    -- We do this to cover the case when the schema was changed so we have to recreate it.
    INSERT INTO es_fts_index_queue (es_fts_config_id, eventtype, ad_table_id, record_id)
    SELECT v_es_fts_config_id,
           'X'                        AS eventtype,
           get_table_id('C_BPartner') AS ad_table_id,
           0                          AS record_id;


    INSERT INTO es_fts_index_queue (es_fts_config_id, eventtype, ad_table_id, record_id)
    SELECT v_es_fts_config_id,
           'U'                        AS eventtype,
           get_table_id('C_BPartner') AS ad_table_id,
           bp.c_bpartner_id           AS record_id
    FROM c_bpartner bp
    ORDER BY bp.c_bpartner_id;
    --
    GET DIAGNOSTICS v_count = ROW_COUNT;
    RAISE NOTICE 'Enqueued % bpartners', v_count;

    RETURN v_count;
END
$$
;

COMMENT ON FUNCTION ops.es_fts_reindex_bpartners() IS 'Causes metasfresh to delete the index and resend all existing bpartner data to elasticsearch in order to enable full text search.
Enqueues the C_BPartners for sending and returns the number of enqueued records.
You can then check the indexing progress with

    SELECT processed,
       iserror,
       COUNT(1)                                                            AS count,
       (MAX(updated) - MIN(updated))                                       AS duration,
       EXTRACT(MILLISECONDS FROM (MAX(updated) - MIN(updated))) / COUNT(1) AS rate_millis
FROM es_fts_index_queue
GROUP BY processed, iserror;
'
;
