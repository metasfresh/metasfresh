DROP FUNCTION IF EXISTS de_metas_acct.tax_declaration_build(p_C_TaxDeclaration_ID numeric);

CREATE OR REPLACE FUNCTION de_metas_acct.tax_declaration_build(
    p_C_TaxDeclaration_ID numeric
)
    RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_C_Period_ID       numeric;
    v_C_AcctSchema_ID   numeric;
    v_AD_Client_ID      numeric;
    v_AD_Org_ID         numeric;
    v_C_Currency_ID     numeric;
    v_Processed         char(1);
    --
    v_LineMap           record;
    --
    v_acctRowCount      integer;
    v_lineRowCount      integer;
BEGIN
    -- =========================================================
    -- Phase 0 — Guard + load header
    -- Fact_Acct.C_Period_ID is the authoritative period assignment.
    -- =========================================================
    SELECT td.C_Period_ID,
           td.C_AcctSchema_ID,
           td.AD_Client_ID,
           td.AD_Org_ID,
           td.Processed,
           acs.C_Currency_ID
    INTO
        v_C_Period_ID,
        v_C_AcctSchema_ID,
        v_AD_Client_ID,
        v_AD_Org_ID,
        v_Processed,
        v_C_Currency_ID
    FROM C_TaxDeclaration td
    JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID = td.C_AcctSchema_ID
    WHERE td.C_TaxDeclaration_ID = p_C_TaxDeclaration_ID;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'tax_declaration_build: C_TaxDeclaration not found for ID=%', p_C_TaxDeclaration_ID;
    END IF;

    IF v_C_Period_ID IS NULL THEN
        RAISE EXCEPTION 'tax_declaration_build: C_TaxDeclaration (ID=%) has no C_Period_ID set', p_C_TaxDeclaration_ID;
    END IF;

    IF v_Processed = 'Y' THEN
        RAISE EXCEPTION 'tax_declaration_build: C_TaxDeclaration (ID=%) is already Processed=Y — cannot rebuild', p_C_TaxDeclaration_ID;
    END IF;

    RAISE NOTICE 'tax_declaration_build: Building declaration ID=% (AD_Client_ID=%, C_Period_ID=%, C_AcctSchema_ID=%)',
        p_C_TaxDeclaration_ID, v_AD_Client_ID, v_C_Period_ID, v_C_AcctSchema_ID;

    -- =========================================================
    -- Phase 1 — Pre-clear existing snapshot rows
    -- =========================================================
    DELETE FROM C_TaxDeclarationAcct
    WHERE C_TaxDeclaration_ID = p_C_TaxDeclaration_ID;

    DELETE FROM C_TaxDeclarationLine
    WHERE C_TaxDeclaration_ID = p_C_TaxDeclaration_ID;

    -- =========================================================
    -- Phase 2 — Snapshot: INSERT C_TaxDeclarationAcct from Fact_Acct
    -- Authoritative carriers from Fact_Acct: VATCode (string) + VATCodeAmountType.
    -- Both are written by the per-leg matcher at posting time (no LEFT JOIN to
    -- C_VAT_Code needed → avoids ambiguity when same VATCode has both N+T rows).
    -- =========================================================
    INSERT INTO C_TaxDeclarationAcct (
        C_TaxDeclarationAcct_ID,
        AD_Client_ID,
        AD_Org_ID,
        IsActive,
        Created,
        CreatedBy,
        Updated,
        UpdatedBy,
        C_TaxDeclaration_ID,
        Fact_Acct_ID,
        C_AcctSchema_ID,
        VATCode,
        AmountType,
        Amount,
        AD_Table_ID,
        Record_ID,
        Line_ID,
        Description
    )
    SELECT
        nextval('c_taxdeclarationacct_seq'),
        fa.AD_Client_ID,
        fa.AD_Org_ID,
        'Y',
        NOW(),
        0,
        NOW(),
        0,
        p_C_TaxDeclaration_ID,
        fa.Fact_Acct_ID,
        fa.C_AcctSchema_ID,
        fa.VATCode,
        fa.VATCodeAmountType,
        (fa.AmtAcctDr - fa.AmtAcctCr)  AS Amount,
        fa.AD_Table_ID,
        fa.Record_ID,
        fa.Line_ID,
        fa.Description
    FROM Fact_Acct fa
    WHERE fa.AD_Client_ID     = v_AD_Client_ID
      AND fa.C_Period_ID      = v_C_Period_ID
      AND fa.C_AcctSchema_ID  = v_C_AcctSchema_ID
      AND fa.VATCode          IS NOT NULL
      AND fa.IsActive         = 'Y';
      -- AD_Org_ID intentionally NOT filtered — open question whether a declaration on a
      -- specific org should aggregate only that org's Fact_Acct rows or the whole client.

    GET DIAGNOSTICS v_acctRowCount = ROW_COUNT;
    RAISE NOTICE 'tax_declaration_build: Inserted % C_TaxDeclarationAcct row(s)', v_acctRowCount;

    -- =========================================================
    -- Phase 3a — Aggregate: INSERT C_TaxDeclarationLine
    -- Aggregation key: VATCode (string) + AmountType. C_VAT_Code_ID is a nice-to-have
    -- looked up via the C_VAT_Code master (LEFT JOIN: NULL when no master matches).
    -- =========================================================
    FOR v_LineMap IN
        WITH aggregated AS (
            SELECT
                acct.VATCode,
                acct.AmountType,
                vc.C_VAT_Code_ID              AS C_VAT_Code_ID,
                SUM(acct.Amount)              AS TotalAmount,
                COUNT(*)                      AS LineCount,
                ROW_NUMBER() OVER (ORDER BY acct.VATCode, acct.AmountType NULLS LAST) * 10 AS LineNo
            FROM C_TaxDeclarationAcct acct
            LEFT JOIN C_VAT_Code vc
                   ON vc.VATCode         = acct.VATCode
                  AND vc.C_AcctSchema_ID = acct.C_AcctSchema_ID
                  AND vc.AmountType      IS NOT DISTINCT FROM acct.AmountType
                  AND vc.IsActive        = 'Y'
            WHERE acct.C_TaxDeclaration_ID = p_C_TaxDeclaration_ID
            GROUP BY acct.VATCode, acct.AmountType, vc.C_VAT_Code_ID
        )
        INSERT INTO C_TaxDeclarationLine (
            C_TaxDeclarationLine_ID,
            AD_Client_ID,
            AD_Org_ID,
            IsActive,
            Created,
            CreatedBy,
            Updated,
            UpdatedBy,
            C_TaxDeclaration_ID,
            Line,
            C_Currency_ID,
            VATCode,
            C_VAT_Code_ID,
            AmountType,
            Amount,
            LineCount
        )
        SELECT
            nextval('c_taxdeclarationline_seq'),
            v_AD_Client_ID,
            v_AD_Org_ID,
            'Y',
            NOW(),
            0,
            NOW(),
            0,
            p_C_TaxDeclaration_ID,
            agg.LineNo,
            v_C_Currency_ID,
            agg.VATCode,
            agg.C_VAT_Code_ID,
            agg.AmountType,
            agg.TotalAmount,
            agg.LineCount
        FROM aggregated agg
        RETURNING C_TaxDeclarationLine_ID, VATCode, AmountType
    LOOP
        -- Phase 3b — Backfill: link each Acct row to its Line by VATCode + AmountType
        UPDATE C_TaxDeclarationAcct
        SET    C_TaxDeclarationLine_ID = v_LineMap.C_TaxDeclarationLine_ID
        WHERE  C_TaxDeclaration_ID     = p_C_TaxDeclaration_ID
          AND  VATCode                 IS NOT DISTINCT FROM v_LineMap.VATCode
          AND  AmountType              IS NOT DISTINCT FROM v_LineMap.AmountType;
    END LOOP;

    SELECT COUNT(*) INTO v_lineRowCount
    FROM C_TaxDeclarationLine
    WHERE C_TaxDeclaration_ID = p_C_TaxDeclaration_ID;

    RAISE NOTICE 'tax_declaration_build: Inserted % C_TaxDeclarationLine row(s)', v_lineRowCount;
    RAISE NOTICE 'tax_declaration_build: Done for C_TaxDeclaration_ID=%', p_C_TaxDeclaration_ID;
END;
$$
;
