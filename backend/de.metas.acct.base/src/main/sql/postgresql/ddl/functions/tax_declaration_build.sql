DROP FUNCTION IF EXISTS de_metas_acct.tax_declaration_build(p_C_TaxDeclaration_ID numeric);

CREATE OR REPLACE FUNCTION de_metas_acct.tax_declaration_build(
    p_C_TaxDeclaration_ID numeric
)
    RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_DateFrom          date;
    v_DateTo            date;
    v_C_AcctSchema_ID   numeric;
    v_AD_Client_ID      numeric;
    v_AD_Org_ID         numeric;
    v_Processed         char(1);
    --
    v_LineID            numeric;
    v_LineMap           record;
    --
    v_acctRowCount      integer;
    v_lineRowCount      integer;
BEGIN
    -- =========================================================
    -- Phase 0 — Guard + load header
    -- =========================================================
    SELECT td.DateFrom::date,
           td.DateTo::date,
           td.C_AcctSchema_ID,
           td.AD_Client_ID,
           td.AD_Org_ID,
           td.Processed
    INTO
        v_DateFrom,
        v_DateTo,
        v_C_AcctSchema_ID,
        v_AD_Client_ID,
        v_AD_Org_ID,
        v_Processed
    FROM C_TaxDeclaration td
    WHERE td.C_TaxDeclaration_ID = p_C_TaxDeclaration_ID;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'tax_declaration_build: C_TaxDeclaration not found for ID=%', p_C_TaxDeclaration_ID;
    END IF;

    IF v_Processed = 'Y' THEN
        RAISE EXCEPTION 'tax_declaration_build: C_TaxDeclaration (ID=%) is already Processed=Y — cannot rebuild', p_C_TaxDeclaration_ID;
    END IF;

    RAISE NOTICE 'tax_declaration_build: Building declaration ID=% (DateFrom=%, DateTo=%, C_AcctSchema_ID=%)',
        p_C_TaxDeclaration_ID, v_DateFrom, v_DateTo, v_C_AcctSchema_ID;

    -- =========================================================
    -- Phase 1 — Pre-clear existing snapshot rows
    -- =========================================================
    DELETE FROM C_TaxDeclarationAcct
    WHERE C_TaxDeclaration_ID = p_C_TaxDeclaration_ID;

    DELETE FROM C_TaxDeclarationLine
    WHERE C_TaxDeclaration_ID = p_C_TaxDeclaration_ID;

    -- =========================================================
    -- Phase 2 — Snapshot: INSERT C_TaxDeclarationAcct from Fact_Acct
    -- Join via fa.vatcode = vc.vatcode (fact_acct has no c_vat_code_id column)
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
        C_VAT_Code_ID,
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
        vc.C_VAT_Code_ID,
        vc.AmountType,
        (fa.AmtAcctDr - fa.AmtAcctCr)  AS Amount,
        fa.AD_Table_ID,
        fa.Record_ID,
        fa.Line_ID,
        fa.Description
    FROM Fact_Acct fa
    INNER JOIN C_VAT_Code vc
           ON vc.VATCode        = fa.VATCode
          AND vc.C_AcctSchema_ID = fa.C_AcctSchema_ID
          AND vc.IsActive        = 'Y'
    WHERE fa.DateAcct         BETWEEN v_DateFrom AND v_DateTo
      AND fa.C_AcctSchema_ID  = v_C_AcctSchema_ID
      AND fa.VATCode          IS NOT NULL
      AND fa.IsActive         = 'Y';

    GET DIAGNOSTICS v_acctRowCount = ROW_COUNT;
    RAISE NOTICE 'tax_declaration_build: Inserted % C_TaxDeclarationAcct row(s)', v_acctRowCount;

    -- =========================================================
    -- Phase 3a — Aggregate: INSERT C_TaxDeclarationLine
    -- C_Currency_ID comes from C_AcctSchema, not from Fact_Acct
    -- Line numbering: ROW_NUMBER() * 10 ordered by (C_VAT_Code_ID, AmountType NULLS LAST)
    -- =========================================================
    FOR v_LineMap IN
        WITH aggregated AS (
            SELECT
                acct.C_VAT_Code_ID,
                acct.AmountType,
                SUM(acct.Amount)    AS TotalAmount,
                COUNT(*)            AS LineCount,
                ROW_NUMBER() OVER (ORDER BY acct.C_VAT_Code_ID, acct.AmountType NULLS LAST) * 10 AS LineNo
            FROM C_TaxDeclarationAcct acct
            WHERE acct.C_TaxDeclaration_ID = p_C_TaxDeclaration_ID
            GROUP BY acct.C_VAT_Code_ID, acct.AmountType
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
            acs.C_Currency_ID,
            agg.C_VAT_Code_ID,
            agg.AmountType,
            agg.TotalAmount,
            agg.LineCount
        FROM aggregated agg
        CROSS JOIN C_AcctSchema acs
        WHERE acs.C_AcctSchema_ID = v_C_AcctSchema_ID
        RETURNING C_TaxDeclarationLine_ID, C_VAT_Code_ID, AmountType
    LOOP
        -- Phase 3b — Backfill: link each Acct row to its Line
        UPDATE C_TaxDeclarationAcct
        SET    C_TaxDeclarationLine_ID = v_LineMap.C_TaxDeclarationLine_ID
        WHERE  C_TaxDeclaration_ID     = p_C_TaxDeclaration_ID
          AND  C_VAT_Code_ID           IS NOT DISTINCT FROM v_LineMap.C_VAT_Code_ID
          AND  AmountType              IS NOT DISTINCT FROM v_LineMap.AmountType;
    END LOOP;

    GET DIAGNOSTICS v_lineRowCount = ROW_COUNT;
    -- v_lineRowCount here reflects last UPDATE affected rows; count lines separately for the notice
    SELECT COUNT(*) INTO v_lineRowCount
    FROM C_TaxDeclarationLine
    WHERE C_TaxDeclaration_ID = p_C_TaxDeclaration_ID;

    RAISE NOTICE 'tax_declaration_build: Inserted % C_TaxDeclarationLine row(s)', v_lineRowCount;
    RAISE NOTICE 'tax_declaration_build: Done for C_TaxDeclaration_ID=%', p_C_TaxDeclaration_ID;
END;
$$
;
