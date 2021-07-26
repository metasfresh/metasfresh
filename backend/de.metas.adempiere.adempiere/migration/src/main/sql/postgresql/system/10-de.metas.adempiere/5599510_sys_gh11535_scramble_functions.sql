create schema ops;

ALTER FUNCTION scramble_metasfresh(boolean) SET SCHEMA ops;
ALTER FUNCTION scramble_table(varchar, boolean) SET SCHEMA ops;
ALTER FUNCTION scramble_string(varchar, varchar) SET SCHEMA ops;

CREATE OR REPLACE FUNCTION ops.scramble_string(
    p_string            character varying,
    p_delimiter_pattern character varying = '[@,{}\s]' /*by default, use comma and white-spaces as the delimiter */)
    RETURNS text
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE
AS
$BODY$
DECLARE
    v_single_word text;
    v_result      text;
BEGIN
    v_result = p_string; /* init the result with the given input string */

    FOR v_single_word IN SELECT REGEXP_SPLIT_TO_TABLE(p_string, p_delimiter_pattern) /* split the input into single words around the given delimiter pattern */
        LOOP
            /* for each single word, replace it with a random string of the same length */
            v_result = REPLACE(v_result,
                               v_single_word,
                               ARRAY_TO_STRING( /*thx to https://stackoverflow.com/a/54103971/1012103 */
                                       ARRAY(
                                               SELECT SUBSTR('ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789', ((RANDOM() * (62 - 1) + 1)::integer), 1)
                                               FROM GENERATE_SERIES(1, LENGTH(v_single_word))
                                           ), '')
                );
        END LOOP;
    RETURN v_result; /* return the result which*/
END;
$BODY$
;

COMMENT ON FUNCTION ops.scramble_string(character varying, character varying)
    IS 'Takes a string an returns a scrambled version of that string, leaving certain characters intact.
By default, both commas and whitespaces (incl line breaks) are left intact, but this can be varied with the p_delimiter_pattern parameter.
Examples: 
  select ops.scramble_string(''Support-User, IT''); might return the string "4anePaQlNpz0, 2k", preserving the command and the space.
  select ops.scramble_string(''Support-User, IT'', ''[-,\s]''); might return the string "mXUgmFl-0FZc, nb", addionally preserving the minus sign.

To scramble all rendered addresses on all C_Invoices while keeping only the formatting (spaces, tabs, newlines), you can do
  update c_invoice set bpartneraddress=ops.scramble_string(bpartneraddress, ''[\s]'');';


--## Function to scramble all string columns of a given table
--..unless they are flagged with personalDataCategory = NP (not personal)

CREATE OR REPLACE FUNCTION ops.scramble_table(
    p_tableName character varying,
    p_dryRun    boolean = TRUE)
    RETURNS void
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE
AS
$BODY$
DECLARE
    v_columnName             text;
    v_columnIsString         boolean;
    v_columnShallBeScrambled boolean;
    v_columnsToScrambleCnt   INT;
    v_scramble_update_stmt   text;
    v_lastTableRowCount      bigint;
    v_updateStartTime        timestamp;
BEGIN
    v_columnsToScrambleCnt = 0;
    v_scramble_update_stmt = 'UPDATE ' || p_tableName || ' SET ';
    FOR v_columnName, v_columnShallBeScrambled, v_columnIsString IN
        SELECT c.ColumnName,
               TRIM(COALESCE(c.columnsql, '')) = '' AND COALESCE(c.personaldatacategory, '') != 'NP', /*scramble every text column that is not *explicity* marked as not-personal */
               r.Name IN ('Memo', 'String', 'Text', 'Text Long')
        FROM ad_column c
                 JOIN ad_reference r ON c.ad_reference_id = r.ad_reference_id
        WHERE ad_table_id = get_table_id(p_tableName)
        LOOP
            IF v_columnIsString AND v_columnShallBeScrambled
            THEN
                IF v_columnsToScrambleCnt > 0
                THEN
                    v_scramble_update_stmt = v_scramble_update_stmt || ', ';
                END IF;
                v_scramble_update_stmt = v_scramble_update_stmt || v_columnName || ' = ' || 'ops.scramble_string(' || v_columnName || ')';
                v_columnsToScrambleCnt = v_columnsToScrambleCnt + 1;
            END IF;
        END LOOP;
    RAISE NOTICE 'Number of columns to scramble: %', v_columnsToScrambleCnt;
    IF v_columnsToScrambleCnt > 0
    THEN
        IF p_dryRun THEN
            RAISE NOTICE 'DRY-RUN - scramble UPDATE statement = %', v_scramble_update_stmt;
        ELSE
            v_updateStartTime = clock_timestamp();
            EXECUTE v_scramble_update_stmt;
            GET DIAGNOSTICS v_lastTableRowCount = ROW_COUNT;
            RAISE NOTICE '% TableName = % - updated % rows in %', clock_timestamp()
                , p_tableName, v_lastTableRowCount, clock_timestamp() - v_updateStartTime;
        END IF;
    END IF;
END;
$BODY$
;
COMMENT ON FUNCTION ops.scramble_table(character varying, boolean)
    IS 'Uses the function scramble_string to scramble all string-columns of the given table, unless they are marked with PersonalDataCategory=NP (not-personal).
If called with p_dryRun := true, then the corresponding update statement is just constructed but not executed.';


--## Function to scramble all metasfresh-tables

CREATE OR REPLACE FUNCTION ops.scramble_metasfresh(
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
            EXECUTE ops.scramble_table(v_tableName, p_dryRun);
        END LOOP;
END;
$BODY$
;

COMMENT ON FUNCTION ops.scramble_metasfresh(boolean)
    IS 'Uses the function scramble_table to scramble the string-columns of all metasfresh-tables, unless they are marked with PersonalDataCategory=NP (not-personal).
If called with p_dryRun := TRUE (the default value!), then the corresponding update statements are just constructed but not executed.
    
"Scrambled" means that all numbers, characters etc are replaced with random chars. 
Only whitespaces and a few other chars are left unchanged, so that e.g. the formatting of an address field is left intact.
See the DB-function `ops.scramble_string(character varying, character varying)` for more details.

scramble_metasfresh() might take quite some time to finish on large databases.
You might consider emptying certain large tables before running it.
For example: 

-- empty large tables that would take very long to scramble    
truncate table ad_changelog;
truncate table ad_issue;
delete from AD_EventLog_Entry where true;
delete from AD_EventLog where true;
delete from AD_PInstance_Log where true;
delete from AD_PInstance p 
where not exists (select 1 from c_async_batch b where b.ad_pinstance_id=p.ad_pinstance_id)
and not exists (select 1 from c_flatrate_term b where b.ad_pinstance_endofterm_id=p.ad_pinstance_id);
truncate table C_Queue_WorkPackage_Log;
truncate table C_Queue_Element;
delete from C_Queue_WorkPackage where true;
update M_ShipmentSchedule_ExportAudit set forwardeddata=null where true;

-- scramble
select ops.scramble_metasfresh(false);
';
--SELECT ops.scramble_metasfresh(p_dryRun := FALSE);