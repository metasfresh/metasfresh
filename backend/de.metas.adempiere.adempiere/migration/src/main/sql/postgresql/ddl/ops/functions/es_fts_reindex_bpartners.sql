
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
