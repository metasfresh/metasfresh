
--## Function to scramble all metasfresh-tables

CREATE OR REPLACE FUNCTION public.scramble_metasfresh(
    p_dryRun boolean = TRUE)
    RETURNS void
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE
AS
$BODY$
DECLARE
    v_tableName text;
BEGIN
    FOR v_tableName IN
        SELECT t.TableName
        FROM ad_table t
        WHERE isview = 'N'
        ORDER BY t.TableName
        LOOP
            RAISE NOTICE '% !! TableName = % !!', clock_timestamp(), v_tableName;
            EXECUTE public.scramble_table(v_tableName, p_dryRun);
        END LOOP;
END;
$BODY$
;

COMMENT ON FUNCTION public.scramble_metasfresh(boolean)
    IS 'Uses the function scramble_table to scramble the string-columns of all metasfresh-tables, unless they are marked with PersonalDataCategory=NP (not-personal).
If called with p_dryRun := true, then the corresponding update statements are just constructed but not executed.';

--SELECT public.scramble_metasfresh(p_dryRun := FALSE);
