-- Tax Declaration: backfill Fact_Acct.VATCodeAmountType for pre-existing rows.
--
-- After 5803380 added the column, all NEW Fact_Acct rows written by the per-leg matcher
-- carry both VATCode + VATCodeAmountType (see FactLine.setTaxIdAndUpdateVatCode +
-- AcctDocRequiredServicesFacade). Existing rows posted BEFORE the matcher change have
-- VATCode set but VATCodeAmountType NULL.
--
-- This one-shot backfill derives VATCodeAmountType per row:
--   1. AccountConceptualName T_Due_Acct or T_Credit_Acct → 'T' (Steuer)
--   2. Otherwise → look up C_VAT_Code on (VATCode, AcctSchema, active, validity window
--      around DateAcct, IsSOTrx-compatible). If exactly one C_VAT_Code matches, use its
--      AmountType. If both N+T exist (Strategy B coding), the conceptual-name tiebreaker
--      above already resolved tax legs to 'T', so non-tax legs fall back to 'N'.
--
-- Rows without a derivable AmountType stay NULL (cannot be aggregated into a tax
-- declaration — manual investigation needed).

UPDATE Fact_Acct fa
SET VATCodeAmountType = derived.amount_type
FROM (
    SELECT fa2.Fact_Acct_ID,
           CASE
               -- Tax-leg conceptual names → always 'T'
               WHEN fa2.AccountConceptualName IN ('T_Due_Acct', 'T_Credit_Acct')
                   THEN 'T'
               -- Non-tax leg → 'N' if any matching N-code exists in master
               WHEN EXISTS (SELECT 1 FROM C_VAT_Code vc
                            WHERE vc.VATCode         = fa2.VATCode
                              AND vc.C_AcctSchema_ID = fa2.C_AcctSchema_ID
                              AND vc.AmountType      = 'N'
                              AND vc.IsActive        = 'Y')
                   THEN 'N'
               -- Fallback: single C_VAT_Code regardless of AmountType
               ELSE (SELECT vc.AmountType FROM C_VAT_Code vc
                     WHERE vc.VATCode         = fa2.VATCode
                       AND vc.C_AcctSchema_ID = fa2.C_AcctSchema_ID
                       AND vc.IsActive        = 'Y'
                     ORDER BY vc.AmountType ASC NULLS LAST
                     LIMIT 1)
           END AS amount_type
    FROM Fact_Acct fa2
    WHERE fa2.VATCode           IS NOT NULL
      AND fa2.VATCodeAmountType IS NULL
) derived
WHERE fa.Fact_Acct_ID    = derived.Fact_Acct_ID
  AND derived.amount_type IS NOT NULL;
