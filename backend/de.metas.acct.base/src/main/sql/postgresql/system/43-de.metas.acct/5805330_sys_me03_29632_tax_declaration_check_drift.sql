-- Tax Declaration — create drift-check function
-- Checks 3 conditions: dead FKs, orphan Fact_Acct rows, amount drift.
-- Returns TRUE if any drift is detected; FALSE if the snapshot matches live data.
-- https://github.com/metasfresh/me03/issues/29632

CREATE OR REPLACE FUNCTION de_metas_acct.tax_declaration_check_drift(
    p_c_taxdeclaration_id NUMERIC
) RETURNS BOOLEAN AS
$$
DECLARE
    v_c_period_id     NUMERIC;
    v_c_acctschema_id NUMERIC;
    v_ad_client_id    NUMERIC;
BEGIN
    SELECT C_Period_ID, C_AcctSchema_ID, AD_Client_ID
    INTO   v_c_period_id, v_c_acctschema_id, v_ad_client_id
    FROM   C_TaxDeclaration
    WHERE  C_TaxDeclaration_ID = p_c_taxdeclaration_id;

    -- Check 1: dead FKs — C_TaxDeclarationAcct rows whose Fact_Acct no longer exists
    IF EXISTS (
        SELECT 1
        FROM   C_TaxDeclarationAcct tda
        LEFT JOIN Fact_Acct fa ON fa.Fact_Acct_ID = tda.Fact_Acct_ID
        WHERE  tda.C_TaxDeclaration_ID = p_c_taxdeclaration_id
          AND  fa.Fact_Acct_ID IS NULL
    ) THEN
        RETURN TRUE;
    END IF;

    -- Check 2: orphan Fact_Acct — VAT rows in the period not captured in C_TaxDeclarationAcct
    IF EXISTS (
        SELECT 1
        FROM   Fact_Acct fa
        WHERE  fa.AD_Client_ID    = v_ad_client_id
          AND  fa.C_Period_ID     = v_c_period_id
          AND  fa.C_AcctSchema_ID = v_c_acctschema_id
          AND  fa.VATCode IS NOT NULL
          AND  fa.IsActive = 'Y'
          AND  NOT EXISTS (
              SELECT 1
              FROM   C_TaxDeclarationAcct tda
              WHERE  tda.Fact_Acct_ID         = fa.Fact_Acct_ID
                AND  tda.C_TaxDeclaration_ID  = p_c_taxdeclaration_id
          )
    ) THEN
        RETURN TRUE;
    END IF;

    -- Check 3: amount drift — snapshot amount differs from current Fact_Acct amounts
    IF EXISTS (
        SELECT 1
        FROM   C_TaxDeclarationAcct tda
        JOIN   Fact_Acct fa ON fa.Fact_Acct_ID = tda.Fact_Acct_ID
        WHERE  tda.C_TaxDeclaration_ID = p_c_taxdeclaration_id
          AND  (fa.AmtAcctDr - fa.AmtAcctCr) <> tda.Amount
    ) THEN
        RETURN TRUE;
    END IF;

    RETURN FALSE;
END;
$$ LANGUAGE plpgsql SECURITY DEFINER;
