DROP FUNCTION IF EXISTS ops.es_fts_reindex_products()
;


CREATE OR REPLACE FUNCTION ops.es_fts_reindex_products() RETURNS bigint
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_es_fts_config_id   numeric;
    v_count              bigint;
BEGIN
    SELECT es_fts_config_id
    INTO v_es_fts_config_id
    FROM es_fts_config
    WHERE es_index = 'fts_product'
      AND isactive = 'Y';
    IF (v_es_fts_config_id IS NULL OR v_es_fts_config_id <= 0) THEN
        RAISE EXCEPTION 'No configuration found for `fts_product` elasticsearch index';
    END IF;

    SELECT ops.es_fts_reindex_table('M_Product', v_es_fts_config_id) INTO v_count;

    RETURN v_count;
END
$$
;

COMMENT ON FUNCTION ops.es_fts_reindex_products() IS 'Causes metasfresh to delete the index and resend all existing product data to elasticsearch in order to enable full text search.
Enqueues the M_Products for sending and returns the number of enqueued records.
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
