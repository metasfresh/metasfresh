-- Tax Declaration: replace the legacy decl_vatcode_idx (auto-dropped with C_VAT_Code_ID
-- column) with index on the actual aggregation key (VATCode + AmountType) and add a
-- unique constraint preventing the same Fact_Acct row from being snapshotted twice into
-- the same declaration.

-- Aggregation lookup: build function groups Acct rows by (declaration, VATCode, AmountType)
CREATE INDEX IF NOT EXISTS C_TaxDeclarationAcct_decl_vatcode_idx
    ON C_TaxDeclarationAcct(C_TaxDeclaration_ID, VATCode, AmountType);

-- Uniqueness: one Acct row per (declaration, Fact_Acct) — protects against duplicate
-- snapshots if the build function is invoked concurrently or buggy.
CREATE UNIQUE INDEX IF NOT EXISTS C_TaxDeclarationAcct_decl_factacct_uq
    ON C_TaxDeclarationAcct(C_TaxDeclaration_ID, Fact_Acct_ID);
