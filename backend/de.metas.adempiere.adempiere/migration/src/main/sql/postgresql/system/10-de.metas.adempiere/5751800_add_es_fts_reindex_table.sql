DROP FUNCTION IF EXISTS ops.es_fts_reindex_table(p_ad_table_name    text)
;

DROP FUNCTION IF EXISTS ops.es_fts_reindex_table(p_ad_table_name    text,
                                                 p_es_fts_config_id numeric)
;

CREATE OR REPLACE FUNCTION ops.es_fts_reindex_table(p_ad_table_name    text,
                                                    p_es_fts_config_id numeric = NULL) RETURNS bigint
    LANGUAGE plpgsql
    VOLATILE
AS
$$
DECLARE
    v_count              bigint;
    v_count_tmp          bigint;
    v_ad_table_id        numeric;
    v_source_ad_table_id numeric;
    v_es_fts_config_id numeric;
BEGIN
    SELECT get_table_id(p_ad_table_name)
    INTO v_ad_table_id;


    FOR v_source_ad_table_id IN (SELECT DISTINCT ad_table_id
                                 FROM es_fts_config_sourcemodel
                                 WHERE (p_es_fts_config_id IS NOT NULL AND es_fts_config_id = p_es_fts_config_id)
                                    OR (p_es_fts_config_id IS NULL AND es_fts_config_id IN (SELECT DISTINCT es_fts_config_id
                                                                                            FROM es_fts_config_sourcemodel
                                                                                            WHERE ad_table_id = v_ad_table_id)))
        LOOP
            DELETE
            FROM es_fts_index_queue
            WHERE ad_table_id = v_source_ad_table_id;
            --
            GET DIAGNOSTICS v_count = ROW_COUNT;
            RAISE NOTICE 'Deletes % es_fts_index_queue rows for tableID %', v_count, v_source_ad_table_id;
        END LOOP;

    v_count := 0;

    IF p_es_fts_config_id ISNULL
    THEN
        FOR v_es_fts_config_id IN (SELECT DISTINCT es_fts_config_id FROM es_fts_config_sourcemodel WHERE ad_table_id = v_ad_table_id)
            LOOP
            --
            -- Ask system to delete elastic search index first
            -- We do this to cover the case when the schema was changed so we have to recreate it.
                EXECUTE 'INSERT INTO es_fts_index_queue (es_fts_config_id, eventtype, ad_table_id, record_id)'
                            || ' SELECT ' || v_es_fts_config_id || ', ''X'' AS eventtype, ' || v_ad_table_id || ' AS ad_table_id, 0 AS record_id'
                            || ' FROM ' || p_ad_table_name
                            || ' ORDER BY ' || p_ad_table_name || '_ID';


                EXECUTE 'INSERT INTO es_fts_index_queue (es_fts_config_id, eventtype, ad_table_id, record_id)'
                            || ' SELECT ' || v_es_fts_config_id || ', ''U'' AS eventtype,' || v_ad_table_id || ' AS ad_table_id, ' || 't.' || p_ad_table_name || '_ID AS record_id'
                            || ' FROM ' || p_ad_table_name || ' t'
                            || ' ORDER BY ' || p_ad_table_name || '_ID';
                GET DIAGNOSTICS v_count_tmp = ROW_COUNT;
                RAISE NOTICE 'Enqueued % %s', v_count_tmp, p_ad_table_name;
                v_count := v_count + v_count_tmp;
            END LOOP;
    ELSE

        --
        -- Ask system to delete elastic search index first
        -- We do this to cover the case when the schema was changed so we have to recreate it.
        EXECUTE 'INSERT INTO es_fts_index_queue (es_fts_config_id, eventtype, ad_table_id, record_id)'
                    || ' SELECT ' || p_es_fts_config_id || ', ''X'' AS eventtype, ' || v_ad_table_id || ' AS ad_table_id, 0 AS record_id'
                    || ' FROM ' || p_ad_table_name
                    || ' ORDER BY ' || p_ad_table_name || '_ID';


        EXECUTE 'INSERT INTO es_fts_index_queue (es_fts_config_id, eventtype, ad_table_id, record_id)'
                    || ' SELECT ' || p_es_fts_config_id || ', ''U'' AS eventtype,' || v_ad_table_id || ' AS ad_table_id, ' || 't.' || p_ad_table_name || '_ID AS record_id'
                    || ' FROM ' || p_ad_table_name || ' t'
                    || ' ORDER BY ' || p_ad_table_name || '_ID';

        GET DIAGNOSTICS v_count = ROW_COUNT;
        RAISE NOTICE 'Enqueued % %s', v_count, p_ad_table_name;
    END IF;

    RETURN v_count;
END
$$
;
