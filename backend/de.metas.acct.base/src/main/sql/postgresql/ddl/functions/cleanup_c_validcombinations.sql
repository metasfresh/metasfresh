DROP FUNCTION IF EXISTS de_metas_acct.cleanup_c_validcombinations(
    p_exclude_C_Element_ID numeric,
    p_DryRun               char(1)
)
;

CREATE OR REPLACE FUNCTION de_metas_acct.cleanup_c_validcombinations(
    p_exclude_C_Element_ID numeric = NULL,
    p_DryRun               char(1) = 'Y'
)
    RETURNS void
AS
$$
DECLARE
    v_count integer;
BEGIN
    RAISE NOTICE 'Deleting not used C_ValidCombination records for p_exclude_C_Element_ID=%, p_DryRun=%',
        p_exclude_C_Element_ID, p_DryRun;

    DROP TABLE IF EXISTS tmp_C_ValidCombination_Records;
    CREATE TEMPORARY TABLE tmp_C_ValidCombination_Records
    AS
    SELECT * FROM de_metas_acct.get_C_ValidCombination_Records();


    DELETE
    FROM c_validcombination vc
    WHERE TRUE
      AND NOT EXISTS(SELECT 1 FROM tmp_C_ValidCombination_Records vcr WHERE vcr.c_validcombination_id = vc.c_validcombination_id)
      AND (p_exclude_C_Element_ID IS NULL
        OR NOT EXISTS(SELECT 1 FROM c_elementvalue ev WHERE ev.c_elementvalue_id = vc.account_id AND ev.c_element_id = p_exclude_C_Element_ID)
        );

    GET DIAGNOSTICS v_count = ROW_COUNT;
    RAISE NOTICE 'Removed % rows', v_count;

    --
    -- Rollback if running in dry mode
    IF (p_DryRun = 'Y') THEN
        RAISE EXCEPTION 'CANCEL because p_DryRun=Y';
    END IF;
END ;
$$
    LANGUAGE plpgsql
;

/*
SELECT de_metas_acct.cleanup_c_validcombinations(
               p_exclude_C_Element_ID := 1000001,
               p_DryRun := 'N'
           )
;
*/

