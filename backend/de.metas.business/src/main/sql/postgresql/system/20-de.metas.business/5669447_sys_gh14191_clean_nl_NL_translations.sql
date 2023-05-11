UPDATE ad_language
SET issystemlanguage = 'N', isactive='N', updated = NOW()
WHERE ad_language = 'nl_NL'
;

DO
$$
    DECLARE
        query text;
    BEGIN
        FOR query IN SELECT 'DELETE FROM ' || t.tablename || ' WHERE AD_Language =' || '''nl_NL''' || ';'
                     FROM AD_Table t
                     WHERE tablename ILIKE '%_TRL'
            LOOP
                --RAISE NOTICE 'Executed Query (%)', query;
                EXECUTE query;
            END LOOP;
    END
$$
;
