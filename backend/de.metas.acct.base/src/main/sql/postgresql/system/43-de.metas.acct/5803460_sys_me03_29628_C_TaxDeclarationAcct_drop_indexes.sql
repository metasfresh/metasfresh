-- Tax Declaration: drop the non-unique lookup indexes on C_TaxDeclarationAcct per reviewer
-- feedback. Originals lived in 5801990 (FactAcct_idx, decl_vatcode_idx — the latter was
-- auto-dropped with the C_VAT_Code_ID column in 5803400). 5803450 had replaced the
-- aggregation lookup with one on (VATCode, AmountType); reviewer asked to drop both.
-- The (C_TaxDeclaration_ID, Fact_Acct_ID) UNIQUE constraint stays — it's data integrity,
-- not a perf index.

DROP INDEX IF EXISTS C_TaxDeclarationAcct_FactAcct_idx;
DROP INDEX IF EXISTS C_TaxDeclarationAcct_decl_vatcode_idx;
