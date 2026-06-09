
--## Function to scramble all metasfresh-tables that contain personal data

CREATE OR REPLACE FUNCTION ops.scramble_metasfresh(
    p_dryRun         boolean = TRUE,
    p_truncate_large boolean = FALSE)
    RETURNS void
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE
AS
$BODY$
DECLARE
    v_tableName  text;
    v_tableCnt   INT = 0;
    v_tableTotal INT;
    v_startTime  timestamp;
BEGIN
    v_startTime = clock_timestamp();

    -- Optionally truncate large audit/log tables that are slow to scramble and rarely needed in dev/test
    IF p_truncate_large AND NOT p_dryRun THEN
        RAISE NOTICE 'Truncating large audit/log tables...';
        TRUNCATE TABLE ad_changelog;
        TRUNCATE TABLE ad_issue;
        DELETE FROM AD_EventLog_Entry WHERE true;
        DELETE FROM AD_EventLog WHERE true;
        DELETE FROM AD_PInstance_Log WHERE true;
        DELETE FROM AD_PInstance p
            WHERE NOT EXISTS (SELECT 1 FROM c_async_batch b WHERE b.ad_pinstance_id = p.ad_pinstance_id)
              AND NOT EXISTS (SELECT 1 FROM c_flatrate_term b WHERE b.ad_pinstance_endofterm_id = p.ad_pinstance_id);
        TRUNCATE TABLE C_Queue_WorkPackage_Log;
        TRUNCATE TABLE C_Queue_Element;
        DELETE FROM C_Queue_WorkPackage WHERE true;
        UPDATE M_ShipmentSchedule_ExportAudit SET forwardeddata = NULL WHERE forwardeddata IS NOT NULL;
        RAISE NOTICE 'Done truncating.';
    END IF;

    -- Only iterate tables that actually have P/SP string columns (skip the rest entirely)
    SELECT COUNT(*) INTO v_tableTotal
    FROM ad_table t
    WHERE t.isview = 'N'
      AND EXISTS (
          SELECT 1
          FROM ad_column c
                   JOIN ad_reference r ON c.ad_reference_id = r.ad_reference_id
          WHERE c.ad_table_id = t.ad_table_id
            AND r.Name IN ('Memo', 'String', 'Text', 'Text Long')
            AND TRIM(COALESCE(c.columnsql, '')) = ''
            AND COALESCE(c.personaldatacategory, t.personaldatacategory) IN ('P', 'SP')
      );

    RAISE NOTICE 'Tables with personal data to scramble: %', v_tableTotal;

    FOR v_tableName IN
        SELECT t.TableName
        FROM ad_table t
        WHERE t.isview = 'N'
          AND EXISTS (
              SELECT 1
              FROM ad_column c
                       JOIN ad_reference r ON c.ad_reference_id = r.ad_reference_id
              WHERE c.ad_table_id = t.ad_table_id
                AND r.Name IN ('Memo', 'String', 'Text', 'Text Long')
                AND TRIM(COALESCE(c.columnsql, '')) = ''
                AND COALESCE(c.personaldatacategory, t.personaldatacategory) IN ('P', 'SP')
          )
        ORDER BY t.TableName
        LOOP
            v_tableCnt = v_tableCnt + 1;
            RAISE NOTICE '% [%/%] TableName = %', clock_timestamp(), v_tableCnt, v_tableTotal, v_tableName;
            PERFORM ops.scramble_table(v_tableName, p_dryRun);
        END LOOP;

    RAISE NOTICE 'Finished % tables in %', v_tableCnt, clock_timestamp() - v_startTime;
END;
$BODY$
;

COMMENT ON FUNCTION ops.scramble_metasfresh(boolean, boolean)
    IS 'Scrambles all string columns marked as P (personal) or SP (sensitive personal) across all metasfresh tables.

Uses translate()-based scramble_string which is ~100x faster than the previous RANDOM()+REPLACE() approach.
Only iterates tables that actually contain P/SP string columns (skips the rest entirely).
Shows progress as [current/total] for each table.

Parameters:
  p_dryRun (default TRUE) - If true, constructs UPDATE statements but does not execute them.
  p_truncate_large (default FALSE) - If true, truncates large audit/log tables before scrambling.

Example:
  -- Dry run to see what would be scrambled
  SELECT ops.scramble_metasfresh(TRUE);

  -- Full scramble with auto-truncation of large tables
  SELECT ops.scramble_metasfresh(p_dryRun := FALSE, p_truncate_large := TRUE);

  -- Full scramble without truncation (if you already cleaned up manually)
  SELECT ops.scramble_metasfresh(FALSE);

For proper pseudonymization of production data (dump-time, no source DB modification),
consider using the Greenmask-based tool instead.
See: mf15-private-extensions/scripts/pseudonymize/README.md';
--SELECT ops.scramble_metasfresh(p_dryRun := FALSE);
