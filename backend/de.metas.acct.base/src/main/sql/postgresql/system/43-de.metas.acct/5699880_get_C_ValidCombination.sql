DROP FUNCTION IF EXISTS de_metas_acct.get_C_ValidCombination(
    p_ElementValue    varchar,
    p_C_AcctSchema_ID numeric
)
;

CREATE OR REPLACE FUNCTION de_metas_acct.get_C_ValidCombination(
    p_ElementValue    varchar,
    p_C_AcctSchema_ID numeric
)
    RETURNS numeric
    LANGUAGE plpgsql
    VOLATILE
AS
$$
DECLARE
    v_count                 integer;
    v_AD_Client_ID          numeric;
    v_C_Element_ID          numeric;
    v_C_ElementValue_ID     numeric;
    v_C_ValidCombination_ID numeric;
BEGIN
    RAISE NOTICE 'p_ElementValue=%, p_C_AcctSchema_ID=%', p_ElementValue, p_C_AcctSchema_ID;

    --
    --
    SELECT AD_Client_ID INTO v_AD_Client_ID FROM C_AcctSchema WHERE C_AcctSchema_ID = p_C_AcctSchema_ID;
    RAISE NOTICE '=> AD_Client_ID=%', v_AD_Client_ID;

    --
    --
    SELECT C_Element_ID INTO v_C_Element_ID FROM C_AcctSchema_Element WHERE ElementType = 'AC';
    RAISE NOTICE '=> C_Element_ID=%', v_C_Element_ID;

    --
    --
    SELECT c_elementvalue_id
    INTO v_c_elementvalue_id
    FROM C_ElementValue
    WHERE c_element_id = v_C_Element_ID
      AND value = p_ElementValue
      AND isactive = 'Y';
    IF (v_C_ElementValue_ID IS NULL) THEN
        RAISE EXCEPTION 'No C_ElementValue record found for Value=%', p_ElementValue;
    END IF;
    RAISE NOTICE '=> C_ElementValue_ID=%', v_C_ElementValue_ID;

    --
    --
    SELECT c_validcombination_id
    INTO v_C_ValidCombination_ID
    FROM C_ValidCombination
    WHERE TRUE
      AND ad_client_id = v_AD_Client_ID
      AND ad_org_id = 0
      AND isactive = 'Y'
      AND c_acctschema_id = p_C_AcctSchema_ID
      AND account_id = v_C_ElementValue_ID
      AND m_product_id IS NULL
      AND c_bpartner_id IS NULL
      AND ad_orgtrx_id IS NULL
      AND c_locfrom_id IS NULL
      AND c_locto_id IS NULL
      AND c_salesregion_id IS NULL
      AND c_project_id IS NULL
      AND c_campaign_id IS NULL
      AND c_activity_id IS NULL
      AND user1_id IS NULL
      AND user2_id IS NULL
      AND c_subacct_id IS NULL
      AND userelement1_id IS NULL
      AND userelement2_id IS NULL
      AND userelementstring1 IS NULL
      AND userelementstring2 IS NULL
      AND userelementstring3 IS NULL
      AND userelementstring4 IS NULL
      AND userelementstring5 IS NULL
      AND userelementstring6 IS NULL
      AND userelementstring7 IS NULL
      AND c_orderso_id IS NULL
      AND m_sectioncode_id IS NULL;

    IF (v_C_ValidCombination_ID IS NULL) THEN

        WITH inserted_row AS (
            INSERT INTO c_validcombination (c_validcombination_id,
                                            ad_client_id, ad_org_id, isactive,
                                            created, createdby, updated, updatedby,
                                            ALIAS, combination, description,
                                            isfullyqualified,
                                            c_acctschema_id,
                                            account_id)
                SELECT NEXTVAL('c_validcombination_seq') AS c_validcombination_id,
                       v_AD_Client_ID                    AS ad_client_id,
                       0                                 AS ad_org_id,
                       'Y'                               AS isactive,
                       NOW()                             AS created,
                       99                                AS createdby,
                       NOW()                             AS updated,
                       99                                AS updatedby,
                       NULL                              AS ALIAS,
                       ev.value                          AS combination,
                       ev.name                           AS description,
                       'Y'                               AS isfullyqualified,
                       p_C_AcctSchema_ID                 AS c_acctschema_id,
                       ev.c_elementvalue_id              AS account_id
                FROM c_elementvalue ev
                WHERE ev.c_elementvalue_id = v_C_ElementValue_ID
                RETURNING c_validcombination_id)
        SELECT c_validcombination_id
        INTO v_C_ValidCombination_ID
        FROM inserted_row;

        RAISE NOTICE '=> C_ValidCombination_ID=% (created)', v_C_ValidCombination_ID;
    ELSE
        RAISE NOTICE '=> C_ValidCombination_ID=% (existing)', v_C_ValidCombination_ID;
    END IF;

    --
    --
    RETURN v_C_ValidCombination_ID;
END
$$
;

/*
SELECT de_metas_acct.get_C_ValidCombination(
               p_ElementValue := '2001',
               p_C_AcctSchema_ID := 1000000
           ),
       de_metas_acct.get_C_ValidCombination(
               p_ElementValue := '2001',
               p_C_AcctSchema_ID := 1000000
           )
;

delete from C_ValidCombination where C_ValidCombination_ID>=1000695;
select * from c_validcombination where combination='2000';
 */

