DROP FUNCTION IF EXISTS "de_metas_acct".get_C_ValidCombination_Records(
    p_AccountValueLike text
)
;

CREATE OR REPLACE FUNCTION "de_metas_acct".get_C_ValidCombination_Records(
    p_AccountValueLike text = NULL
)
    RETURNS TABLE
            (
                TableName                     character varying,
                KeyColumnName                 character varying,
                Record_ID                     numeric,
                C_AcctSchema_ID               numeric,
                C_ValidCombination_ColumnName character varying,
                C_ValidCombination_ID         numeric
            )
AS
$BODY$
    /**
     * Functions retrieves all table column records which are pointing to C_ValidCombination.
     */
DECLARE
    v_validcombination_selection_id numeric;
    v_sql                           text := '';
    rec                             record;
BEGIN
    IF (p_AccountValueLike IS NOT NULL) THEN
        v_validcombination_selection_id := NEXTVAL('ad_pinstance_seq');
        INSERT INTO t_selection (ad_pinstance_id, t_selection_id)
        SELECT v_validcombination_selection_id AS ad_pinstance_id,
               vc.c_validcombination_id        AS t_selection_id
        FROM c_elementvalue ev
                 INNER JOIN c_validcombination vc ON vc.account_id = ev.c_elementvalue_id
        WHERE ev.value LIKE p_AccountValueLike;
    END IF;


    FOR rec IN (SELECT t.TableName::character varying                                                                                                               AS TableName
                     , c.ColumnName::character varying                                                                                                              AS Account_ColumnName
                     --
                     , COALESCE(
                (SELECT MAX(k.ColumnName) FROM AD_Column k WHERE k.AD_Table_ID = t.AD_Table_ID AND k.IsKey = 'Y' AND k.IsActive = 'Y' HAVING COUNT(1) = 1)
            , (SELECT MAX(k.ColumnName) FROM AD_Column k WHERE k.AD_Table_ID = t.AD_Table_ID AND k.IsParent = 'Y' AND k.IsActive = 'Y' AND k.ColumnName <> 'C_AcctSchema_ID' HAVING COUNT(1) = 1)
            , (SELECT MAX(k.ColumnName) FROM AD_Column k WHERE k.AD_Table_ID = t.AD_Table_ID AND k.IsParent = 'Y' AND k.IsActive = 'Y' AND k.ColumnName = 'C_AcctSchema_ID' HAVING COUNT(1) = 1)
            )::character varying                                                                                                                                    AS KeyColumnName
                     --
                     , (CASE WHEN EXISTS(SELECT 1 FROM AD_Column k WHERE k.AD_Table_ID = t.AD_Table_ID AND k.ColumnName = 'C_AcctSchema_ID') THEN 'Y' ELSE 'N' END) AS HasAcctSchema
                     --
                FROM AD_Column c
                         INNER JOIN AD_Table t ON (t.AD_Table_ID = c.AD_Table_ID)
                WHERE c.AD_Reference_ID = 25 /* account */
                  AND c.ColumnSQL IS NULL                                                                          -- not virtual
                  AND t.IsView = 'N'                                                                               -- not a view
                  AND EXISTS(SELECT 1 FROM information_schema.tables WHERE LOWER(table_name) = LOWER(t.TableName)) -- table really exists
                ORDER BY t.TableName, c.ColumnName
    )
        LOOP
            IF (v_sql <> '') THEN
                v_sql := v_sql || ' union all ';
            END IF;

            v_sql := v_sql || 'SELECT ';

            -- TableName
            v_sql := v_sql || '''' || rec.TableName || '''::character varying AS TableName';

            -- KeyColumnName
            v_sql := v_sql || ', ''' || rec.KeyColumnName || '''::character varying AS KeyColumnName';
            IF (rec.KeyColumnName IS NOT NULL) THEN
                v_sql := v_sql || ', ' || rec.KeyColumnName || '::numeric AS Record_ID';
            ELSE
                v_sql := v_sql || ', NULL::numeric AS Record_ID';
            END IF;

            -- C_AcctSchema_ID
            IF (rec.HasAcctSchema = 'Y') THEN
                v_sql := v_sql || ', C_AcctSchema_ID::numeric';
            ELSE
                v_sql := v_sql || ', NULL::numeric AS C_AcctSchema_ID';
            END IF;

            -- C_ValidCombination_ID
            v_sql := v_sql || ', ''' || rec.Account_ColumnName || '''::character varying AS C_ValidCombination_ColumnName';
            v_sql := v_sql || ', ' || rec.Account_ColumnName || '::numeric AS C_ValidCombination_ID';

            -- FROM...
            v_sql := v_sql || ' FROM ' || rec.TableName || ' t ';

            -- WHERE
            v_sql := v_sql || ' WHERE true ';


            IF (v_validcombination_selection_id > 0) THEN
                v_sql := v_sql || ' AND EXISTS (select 1 from t_selection s where '
                             || 's.ad_pinstance_id=' || v_validcombination_selection_id
                             || ' and s.t_selection_id=t.' || rec.Account_ColumnName || '::numeric'
                    || ')';
            END IF;
        END LOOP;

    v_sql := 'SELECT t.TableName, t.KeyColumnName, t.Record_ID, t.C_AcctSchema_ID, t.C_ValidCombination_ColumnName, t.C_ValidCombination_ID FROM (' || v_sql || ') t';
    RAISE DEBUG 'get_C_ValidCombination_Records: sql: %', v_sql;

    RETURN QUERY EXECUTE v_sql;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
                     COST 100
                     ROWS 1000
;
