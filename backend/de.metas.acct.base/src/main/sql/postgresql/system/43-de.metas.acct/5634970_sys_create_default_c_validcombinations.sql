DROP FUNCTION IF EXISTS de_metas_acct.create_default_c_validcombinations(
    p_C_AcctSchema_ID numeric
)
;


CREATE OR REPLACE FUNCTION de_metas_acct.create_default_c_validcombinations(
    p_C_AcctSchema_ID numeric
)
    RETURNS void
AS
$$
DECLARE
    v_C_Element_ID numeric;
    v_InsertCount  integer;
BEGIN
    --
    -- Get C_Element_ID
    SELECT ase.c_element_id
    INTO v_C_Element_ID
    FROM c_acctschema_element ase
    WHERE ase.c_acctschema_id = p_C_AcctSchema_ID
      AND ase.elementtype = 'AC';
    IF (v_C_Element_ID IS NULL) THEN
        RAISE EXCEPTION 'No C_Element_ID found for C_AcctSchema_ID=%', p_C_AcctSchema_ID;
    END IF;

    RAISE NOTICE 'create_default_c_validcombinations: for C_AcctSchema_ID=%, C_Element_ID=%', p_C_AcctSchema_ID, v_C_Element_ID;


    INSERT INTO c_validcombination(c_validcombination_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, alias, combination, description,
                                   isfullyqualified, c_acctschema_id, account_id, m_product_id, c_bpartner_id, ad_orgtrx_id, c_locfrom_id, c_locto_id, c_salesregion_id,
                                   c_project_id, c_campaign_id, c_activity_id, user1_id, user2_id, c_subacct_id, userelement1_id, userelement2_id, userelementstring1,
                                   userelementstring2, userelementstring3, userelementstring4, userelementstring5, userelementstring6, userelementstring7)
    SELECT NEXTVAL('c_validcombination_seq'),
           ev.ad_client_id,
           ev.ad_org_id,
           ev.isactive,
           NOW()                AS created,
           0                    AS createdby,
           NOW()                AS updated,
           0                    AS updatedby,
           NULL                 AS alias,
           ev.value             AS combination,
           ev.name              AS description,
           'Y'                  AS isfullyqualified,
           p_C_AcctSchema_ID    AS c_acctschema_id,
           ev.c_elementvalue_id AS account_id,
           NULL                 AS m_product_id,
           NULL                 AS c_bpartner_id,
           NULL                 AS ad_orgtrx_id,
           NULL                 AS c_locfrom_id,
           NULL                 AS c_locto_id,
           NULL                 AS c_salesregion_id,
           NULL                 AS c_project_id,
           NULL                 AS c_campaign_id,
           NULL                 AS c_activity_id,
           NULL                 AS user1_id,
           NULL                 AS user2_id,
           NULL                 AS c_subacct_id,
           NULL                 AS userelement1_id,
           NULL                 AS userelement2_id,
           NULL                 AS userelementstring1,
           NULL                 AS userelementstring2,
           NULL                 AS userelementstring3,
           NULL                 AS userelementstring4,
           NULL                 AS userelementstring5,
           NULL                 AS userelementstring6,
           NULL                 AS userelementstring7
    FROM c_elementvalue ev
    WHERE TRUE
      AND ev.c_element_id = v_C_Element_ID
      AND ev.isactive = 'Y'
      AND NOT EXISTS(SELECT 1
                     FROM c_validcombination z
                     WHERE TRUE
                       AND z.ad_client_id = ev.ad_client_id
                       AND z.ad_org_id = ev.ad_org_id
                       AND z.c_acctschema_id = p_C_AcctSchema_ID
                       AND z.account_id = ev.c_elementvalue_id
                       AND z.m_product_id IS NULL
                       AND z.c_bpartner_id IS NULL
                       AND z.ad_orgtrx_id IS NULL
                       AND z.c_locfrom_id IS NULL
                       AND z.c_locto_id IS NULL
                       AND z.c_salesregion_id IS NULL
                       AND z.c_project_id IS NULL
                       AND z.c_campaign_id IS NULL
                       AND z.c_activity_id IS NULL
                       AND z.user1_id IS NULL
                       AND z.user2_id IS NULL
                       AND z.c_subacct_id IS NULL
                       AND z.userelement1_id IS NULL
                       AND z.userelement2_id IS NULL
                       AND z.userelementstring1 IS NULL
                       AND z.userelementstring2 IS NULL
                       AND z.userelementstring3 IS NULL
                       AND z.userelementstring4 IS NULL
                       AND z.userelementstring5 IS NULL
                       AND z.userelementstring6 IS NULL
                       AND z.userelementstring7 IS NULL
        );

    GET DIAGNOSTICS v_InsertCount = ROW_COUNT;
    RAISE NOTICE 'Create % C_ValidCombination rows', v_InsertCount;
END
$$
    LANGUAGE plpgsql
;

/*
SELECT de_metas_acct.create_default_c_validcombinations(p_C_AcctSchema_ID := 1000000);
 */
