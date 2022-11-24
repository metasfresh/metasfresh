DROP FUNCTION IF EXISTS de_metas_acct.setC_AcctSchema_ChartOfAccounts_ID(
    p_C_AcctSchema_ID  numeric,
    p_New_C_Element_ID numeric)
;


CREATE OR REPLACE FUNCTION de_metas_acct.setC_AcctSchema_ChartOfAccounts_ID(
    p_C_AcctSchema_ID  numeric,
    p_New_C_Element_ID numeric)
    RETURNS void
AS
$$
DECLARE
    v_newElementInfo    record;
    v_defaultAccount    record;
    v_acctSchemaInfo    record;
    v_newAcctSchemaName varchar;
BEGIN
    SELECT e.c_element_id,
           e.name AS element_name
    INTO v_newElementInfo
    FROM c_element e
    WHERE e.c_element_id = p_New_C_Element_ID;

    SELECT ev.c_elementvalue_id,
           ev.value,
           ev.name
    INTO v_defaultAccount
    FROM c_elementvalue ev
    WHERE ev.c_element_id = v_newElementInfo.c_element_id
      AND ev.name = 'DEFAULT_ACCT';

    SELECT e.c_element_id AS old_c_element_id,
           e.name         AS old_element_name,
           ase.c_acctschema_element_id,
           cas.c_acctschema_id,
           cas.name       AS c_acctschema_name,
           cy.iso_code    AS currency_iso_code
    INTO v_acctSchemaInfo
    FROM c_acctschema_element ase
             INNER JOIN c_element e ON ase.c_element_id = e.c_element_id
             INNER JOIN c_acctschema cas ON cas.c_acctschema_id = ase.c_acctschema_id
             INNER JOIN c_currency cy ON cy.c_currency_id = cas.c_currency_id
    WHERE ase.c_acctschema_id = p_C_AcctSchema_ID
      AND ase.elementtype = 'AC';

    RAISE NOTICE 'Changing Chart of Accounts...';
    RAISE NOTICE 'AcctSchema: % (ID=%)', v_acctSchemaInfo.c_acctschema_name, v_acctSchemaInfo.c_acctschema_id;
    RAISE NOTICE 'Old Element: % (ID=%)', v_acctSchemaInfo.old_element_name, v_acctSchemaInfo.old_c_element_id;
    RAISE NOTICE 'New Element: % (ID=%)', v_newElementInfo.element_name, v_newElementInfo.c_element_id;
    RAISE NOTICE 'New Default Element Value: % - % (ID=%)', v_defaultAccount.value, v_defaultAccount.name, v_defaultAccount.c_elementvalue_id;

    UPDATE c_acctschema_element
    SET c_element_id=v_newElementInfo.c_element_id,
        c_elementvalue_id=v_defaultAccount.c_elementvalue_id,
        updated=NOW(),
        updatedby=0
    WHERE c_acctschema_element_id = v_acctSchemaInfo.c_acctschema_element_id;

    v_newAcctSchemaName := v_newElementInfo.element_name || ' / ' || v_acctSchemaInfo.currency_iso_code;
    UPDATE c_acctschema cas
    SET name=v_newAcctSchemaName,
        updated=NOW(), updatedby=0
    WHERE cas.c_acctschema_id = v_acctSchemaInfo.c_acctschema_id;
    RAISE NOTICE 'AcctSchema Name changed to: %', v_newAcctSchemaName;

    IF (v_newElementInfo.c_element_id = v_acctSchemaInfo.old_c_element_id) THEN
        RAISE NOTICE 'Skip updating default accounts because Chart of Accounts didn''t change';
        RETURN;
    END IF;

    PERFORM de_metas_acct.update_accounts_from_c_elementvalue_default_account(
            p_C_AcctSchema_ID := v_acctSchemaInfo.c_acctschema_id,
            p_DryRun := 'N'
        );
END;
$$
    LANGUAGE plpgsql
;

/*
select * from c_acctschema;
select c_element_id, name from c_element;
select de_metas_acct.setC_AcctSchema_ChartOfAccounts_ID(
    p_C_AcctSchema_ID := 1000000,
--     p_New_C_Element_ID := 540014 -- SKR04
    p_New_C_Element_ID := 1000001 -- SKR03
);

select * from m_product_acct;
 */



