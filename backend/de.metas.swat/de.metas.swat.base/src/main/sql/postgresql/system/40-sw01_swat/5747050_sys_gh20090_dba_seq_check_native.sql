DROP FUNCTION IF EXISTS dba_seq_check_native();
DROP FUNCTION IF EXISTS dba_seq_check_native(varchar);

-- Function: public.dba_seq_check_native(character varying)
-- DROP FUNCTION public.dba_seq_check_native(character varying);

CREATE OR REPLACE FUNCTION public.dba_seq_check_native(p_tablename character varying DEFAULT NULL::character varying)
    RETURNS void
AS
$BODY$
DECLARE
    v_record_to_process record;
    v_firstId           bigint  := 1000000;
    v_nextid            bigint;
    v_sequence_name     varchar;
    --
    v_count_create      integer := 0;
    v_count_update      integer := 0;
BEGIN
    FOR v_record_to_process IN
        (
            WITH v_record_to_process_unfiltered AS
                     (
                         SELECT pt.Table_Name,                                                                                               -- physical table name
                                seq.sequence_schema || '.' || seq.sequence_name                                            AS sequence_name, -- schema name, if any
                                pc.column_name,                                                                                              -- physical column name

                                -- we will create/update the native sequence named "tableName||'_seq'"
                                -- if there is *no* AD_Column or if there is an AD_Column that is both active and is flagged as a key column
                                -- otherwise, this function will not do anything about that native sequence.
                                (ac.AD_Column_ID IS NULL OR (ac.IsActive = 'Y' AND (ac.IsKey = 'Y' OR ac.IsParent = 'Y'))) AS CreateOrUpdateNativeSequence,
                                at.IsView,
                                at.IsActive,
                                at.TableName

                         FROM public.AD_Table at
                                  INNER JOIN information_schema.tables pt ON pt.table_schema = 'public' AND lower(pt.Table_Name) = lower(at.TableName)
                                  LEFT OUTER JOIN information_schema.sequences seq ON seq.sequence_schema = 'public' AND seq.sequence_name = lower(at.tableName || '_seq') -- find out if there is an existing native sequence
                                  LEFT OUTER JOIN public.AD_Column ac ON ac.AD_Table_ID = at.AD_Table_ID AND lower(ac.ColumnName) = lower(at.TableName || '_ID')
                                  LEFT OUTER JOIN information_schema.columns pc ON pc.table_schema = 'public' AND pc.table_name = lower(at.tableName) AND pc.column_name = lower(at.TableName || '_ID')
                     )
            SELECT u.Table_Name,    -- physical table name
                   u.sequence_name, -- schema name, if any
                   u.column_name,
                   u.CreateOrUpdateNativeSequence
            FROM v_record_to_process_unfiltered u
            WHERE TRUE
              -- use the application dictionary's AD_Table and AD_Column to decide what tables the function chall deal with
              AND u.IsView = 'N'
              AND u.IsActive = 'Y'
              AND (
                    quote_literal(p_tableName) IS NULL AND u.TableName NOT LIKE 'X!_%Template' ESCAPE '!'
                    OR
                                                                                                      quote_literal(lower(p_tableName)) = lower('''' || u.TableName || '''')
                )
            ORDER BY u.TableName
        )
        LOOP
            BEGIN
                IF v_record_to_process.column_name IS NULL
                THEN
                    RAISE DEBUG 'Ignoring table % because it has no column %', v_record_to_process.Table_Name, v_record_to_process.Table_Name || '_ID';
                    CONTINUE;
                END IF;
                IF NOT v_record_to_process.CreateOrUpdateNativeSequence
                THEN
                    RAISE DEBUG 'Ignoring table % because of its AD_Column %', v_record_to_process.Table_Name, v_record_to_process.Table_Name || '_ID';
                    CONTINUE;
                END IF;

                -- get the sequence's next value
                -- in case of AD_PInstance table, get the greatest _ID from both AD_PInstance and T_Selection
                IF lower(v_record_to_process.Table_Name) = lower('AD_PInstance') THEN
                    EXECUTE 'select GREATEST(
                    (select max(' || quote_ident(v_record_to_process.column_name) || ') from ' || quote_ident(v_record_to_process.Table_Name) || '), 
                    (select max(' || quote_ident(v_record_to_process.column_name) || ') from public.T_Selection)
                )'
                        INTO v_nextid;
                ELSE
                    EXECUTE 'select max('
                                || quote_ident(v_record_to_process.column_name)
                                || ') from '
                        || quote_ident(v_record_to_process.Table_Name)
                        INTO v_nextid;
                END IF;

                IF (v_nextid IS NULL)
                THEN
                    v_nextid := v_firstId;
                ELSE
                    v_nextid := v_nextid + 1;
                END IF;

                IF (v_nextid < v_firstId)
                THEN
                    v_nextid := v_firstId;
                END IF;

                -- do the actual creating or updating of the native sequence
                IF (v_record_to_process.sequence_name IS NULL)
                THEN
                    -- don't include the "public." in the quote_ident prgument(), because then the sequence's *name* wouldstart with "public.."
                    v_sequence_name := 'public.' || quote_ident(v_record_to_process.Table_Name || '_seq');

                    EXECUTE 'CREATE SEQUENCE ' || v_sequence_name
                                || ' INCREMENT 1'
                                || ' MINVALUE 0'
                                || ' MAXVALUE 2147483647' -- MAX java integer
                                || ' START ' || v_nextid;

                    v_count_create := v_count_create + 1;
                    RAISE DEBUG 'Sequence %: created - v_nextid=%', v_sequence_name, v_nextid;
                ELSE
                    v_sequence_name := v_record_to_process.sequence_name;

                    PERFORM setval(v_sequence_name, v_nextid, FALSE);

                    v_count_update := v_count_update + 1;
                    RAISE DEBUG 'Sequence %: updated - v_nextid=%', v_sequence_name, v_nextid;
                END IF;
            EXCEPTION
                WHEN OTHERS THEN
                    RAISE WARNING 'error while creating/updating native sequence %: % (sql-state=%)', v_sequence_name, SQLERRM, SQLSTATE;
            END;
        END LOOP;

    RAISE NOTICE 'Native sequences: % created, % updated', v_count_create, v_count_update;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
                     COST 100;
ALTER FUNCTION public.dba_seq_check_native(character varying)
    OWNER TO metasfresh;
COMMENT ON FUNCTION public.dba_seq_check_native(character varying) IS 'Creates or updates a native sequences. If a not-null p_tableName parameter is provided (case insensitive), then it works with just that table.
If no p_tableName parameter is provided, it does the job for each metasfresh AD_Table (whose name is not like X!_%Template) that is active and not flagged as a view.
In both cases, it ignores AD_Tables that have an AD_Column with
* ColumnName=TableName||''_ID''
* IsActive=''N'' or both IsParent=''N'' and IsKey=''N''.

In other words, you can use an AD_Column record to explicitly tell this function not to do anything about the respective table''s native sequence.
Also note that the function won''t do anything unless there is a physical column named like "<tablename>_id".

Otherwise, the sequence is named lower(tableName||''_seq'') and its next value is set from the maximum value of the key or parent column.
Note: In case of ''AD_PInstance'' table the next value of the sequence is set from the maximum value of the key or parent column or ''AD_PInstance_ID'' from public.T_Selection';
