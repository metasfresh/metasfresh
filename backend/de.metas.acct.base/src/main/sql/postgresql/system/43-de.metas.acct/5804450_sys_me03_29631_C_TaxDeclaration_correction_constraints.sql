ALTER TABLE C_TaxDeclaration
    ADD CONSTRAINT C_TaxDeclaration_StarTopology_check
    CHECK (
        (IsCorrection = 'N' AND C_TaxDeclaration_Original_ID IS NULL)
     OR (IsCorrection = 'Y' AND C_TaxDeclaration_Original_ID IS NOT NULL)
    );

-- A correction shares its original's period, so uniqueness applies to originals only.
DROP INDEX IF EXISTS C_TaxDeclaration_acctschema_period_unique;
CREATE UNIQUE INDEX C_TaxDeclaration_acctschema_period_unique
    ON C_TaxDeclaration(C_AcctSchema_ID, C_Period_ID)
    WHERE IsActive    = 'Y'
      AND Processed   = 'Y'
      AND IsCorrection = 'N';
