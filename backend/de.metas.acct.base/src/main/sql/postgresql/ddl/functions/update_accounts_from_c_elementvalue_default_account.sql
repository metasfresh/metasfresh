DROP FUNCTION IF EXISTS de_metas_acct.update_accounts_from_c_elementvalue_default_account(
    p_C_AcctSchema_ID numeric,
    p_DryRun          char(1)
)
;


CREATE OR REPLACE FUNCTION de_metas_acct.update_accounts_from_c_elementvalue_default_account(
    p_C_AcctSchema_ID numeric,
    p_DryRun          char(1) = 'Y'
)
    RETURNS void
AS
$$
DECLARE
    v_C_Element_ID   numeric;
    v_C_Element_Name varchar;
    v_row            record;
    v_count          integer;
BEGIN
    RAISE NOTICE 'update_c_elementvalue_default_account: for p_C_AcctSchema_ID=%, p_DryRun=%', p_C_AcctSchema_ID, p_DryRun;

    SELECT ase.c_element_id, e.name
    INTO v_C_Element_ID, v_C_Element_Name
    FROM c_acctschema_element ase
             INNER JOIN c_element e ON ase.c_element_id = e.c_element_id
    WHERE ase.c_acctschema_id = p_C_AcctSchema_ID
      AND ase.elementtype = 'AC';

    RAISE NOTICE ' => C_Element_ID=% (%)', v_C_Element_ID, v_C_Element_Name;

    --
    -- Make sure we have a default valid combination for each C_ElementValue
    PERFORM de_metas_acct.create_default_c_validcombinations(p_C_AcctSchema_ID);


    --
    -- Prepare default accounts temporary table
    DROP TABLE IF EXISTS tmp_elementvalue_default_account;
    CREATE TEMPORARY TABLE tmp_elementvalue_default_account AS
    SELECT ev.c_elementvalue_id,
           ev.value,
           UNNEST(STRING_TO_ARRAY(ev.default_account, ',')) AS default_account,
           FALSE::boolean                                   AS is_duplicate
    FROM c_elementvalue ev
    WHERE ev.c_element_id = v_C_Element_ID
      AND ev.isactive = 'Y'
      AND ev.default_account IS NOT NULL;
    --
    UPDATE tmp_elementvalue_default_account t
    SET is_duplicate='Y'
    WHERE EXISTS(SELECT 1 FROM tmp_elementvalue_default_account t2 WHERE t2.default_account = t.default_account AND t2.value < t.value);
    -- SELECT * FROM tmp_elementvalue_default_account ORDER BY default_account, value;
    --
    DELETE
    FROM tmp_elementvalue_default_account
    WHERE is_duplicate;
    --
    CREATE UNIQUE INDEX ON tmp_elementvalue_default_account (default_account);


    --
    -- Prepare accounting table & columns temporary table
    DROP TABLE IF EXISTS tmp_accounting_tables_and_columns;
    CREATE TEMPORARY TABLE tmp_accounting_tables_and_columns AS
    SELECT t.tablename,
           c.columnname,
           c.ismandatory,
           da.value                                     AS default_accountno,
           (SELECT vc.c_validcombination_id
            FROM c_validcombination vc
            WHERE TRUE
              AND vc.c_acctschema_id = p_C_AcctSchema_ID
              AND vc.account_id = da.c_elementvalue_id) AS default_validcombination_id
    FROM ad_column c
             INNER JOIN ad_table t ON (t.ad_table_id = c.ad_table_id)
             LEFT OUTER JOIN tmp_elementvalue_default_account da ON (da.default_account = c.columnname)
    WHERE c.isactive = 'Y'
      AND t.isactive = 'Y'
      AND c.ad_reference_id = 25 -- account
      AND t.isview = 'N'
      AND EXISTS(SELECT 1 FROM ad_column z WHERE z.ad_table_id = c.ad_table_id AND z.columnname = 'C_AcctSchema_ID')
      AND (t.tablename LIKE '%\_Acct' ESCAPE '\' OR t.tablename IN ('C_AcctSchema_Default', 'C_AcctSchema_GL'))
    ORDER BY c.columnname, t.tablename;

    --
    -- Update each accounting column
    FOR v_row IN (SELECT *
                  FROM tmp_accounting_tables_and_columns t
                  ORDER BY t.tablename, t.columnname)
        LOOP
            IF (v_row.ismandatory = 'Y' AND v_row.default_validcombination_id IS NULL) THEN
                IF (v_row.default_accountno IS NULL) THEN
                    RAISE WARNING '%.% is mandatory but there is no C_ElementValue with Default_Account=%', v_row.tablename, v_row.columnname, v_row.columnname;
                ELSE
                    RAISE WARNING 'No default valid combination but the column is mandatory: %', v_row;
                END IF;
            END IF;

            EXECUTE 'update ' || v_row.tablename || ' set '
                        || v_row.columnname || ' = ' || (CASE WHEN v_row.default_validcombination_id IS NOT NULL THEN v_row.default_validcombination_id::text ELSE 'null' END)
                        || ', updated=now(), updatedby=0'
                        || ' where c_acctschema_id = ' || p_C_AcctSchema_ID;
            GET DIAGNOSTICS v_count = ROW_COUNT;

            RAISE NOTICE 'Updated %.% = % (ID=%) - % rows', v_row.tablename, v_row.columnname, v_row.default_accountno, v_row.default_validcombination_id, v_count;
        END LOOP;

    --
    -- Remove all unused valid combinations
    PERFORM de_metas_acct.cleanup_c_validcombinations(
            p_exclude_C_Element_ID := v_C_Element_ID,
            p_DryRun := 'N'
        );

    --
    -- Rollback if running in dry mode
    IF (p_DryRun = 'Y') THEN
        RAISE EXCEPTION 'CANCEL because p_DryRun=Y';
    END IF;
END
$$
    LANGUAGE plpgsql
;


/*
SELECT de_metas_acct.update_accounts_from_c_elementvalue_default_account(
               p_C_AcctSchema_ID := 1000000,
               p_DryRun := 'N'
           )
;
*/


