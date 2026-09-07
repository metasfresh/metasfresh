-- Corrections are looked up by their original via C_TaxDeclaration_Original_ID.
CREATE INDEX IF NOT EXISTS C_TaxDeclaration_Original_ID
    ON C_TaxDeclaration (C_TaxDeclaration_Original_ID);
