DROP FUNCTION IF EXISTS delete_not_needed_translations(
    p_DryRun boolean
)
;

CREATE OR REPLACE FUNCTION delete_not_needed_translations(
    p_DryRun boolean = TRUE
)
    RETURNS void
    LANGUAGE plpgsql
    VOLATILE
AS
$$
DECLARE
    activeLanguages varchar;
    v_trlTableName  varchar;
    v_count         integer;
    v_count_total   integer = 0;
BEGIN
    SELECT STRING_AGG(QUOTE_LITERAL(l.ad_language), ',')
    INTO activeLanguages
    FROM ad_language l
    WHERE isactive = 'Y'
      AND (issystemlanguage = 'Y' OR isbaselanguage = 'Y');

    RAISE NOTICE 'Active languages: %', activeLanguages;

    FOR v_trlTableName IN (SELECT t.tablename
                           FROM ad_table t
                           WHERE t.tablename ILIKE '%\_Trl' ESCAPE '\'
                           ORDER BY t.tablename)
        LOOP
            EXECUTE 'DELETE FROM ' || v_trlTableName || ' WHERE ad_language NOT IN (' || activeLanguages || ')';

            GET DIAGNOSTICS v_count = ROW_COUNT;
            v_count_total := v_count_total + v_count;

            RAISE NOTICE '% - Deleted % rows', v_trlTableName, v_count;
        END LOOP;

    RAISE NOTICE 'DONE. Deleted % rows in total', v_count_total;

    IF (p_DryRun) THEN
        RAISE EXCEPTION 'Rollback bacause p_DryRun is true';
    END IF;
END;
$$
;

-- SELECT delete_not_needed_translations(p_DryRun := true);

