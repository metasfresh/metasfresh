-- Backfill ErrorCode on the Tax Declaration Corrections AD_Messages.
-- Aligns these messages with the Iter-5 convention (UPPER_SNAKE ErrorCode prefixed TAXDECLARATION_).
-- Idempotent: only sets ErrorCode where it is still NULL, so it is a no-op on fresh DBs
-- (the INSERTs already carry the ErrorCode).

UPDATE AD_Message SET ErrorCode='TAXDECLARATION_HAS_CORRECTIONS',          Updated=now(), UpdatedBy=100 WHERE Value='TaxDeclaration_HasCorrections'                     AND ErrorCode IS NULL;
UPDATE AD_Message SET ErrorCode='TAXDECLARATION_ORIGINAL_MUST_BE_ORIGINAL',Updated=now(), UpdatedBy=100 WHERE Value='TaxDeclaration_OriginalMustBeOriginal'             AND ErrorCode IS NULL;
UPDATE AD_Message SET ErrorCode='TAXDECLARATION_ORIGINAL_REQUIRED',        Updated=now(), UpdatedBy=100 WHERE Value='TaxDeclaration_OriginalRequired'                   AND ErrorCode IS NULL;
UPDATE AD_Message SET ErrorCode='TAXDECLARATION_CORRECTION_INHERITS_PD',   Updated=now(), UpdatedBy=100 WHERE Value='TaxDeclaration_CorrectionInheritsPeriod'           AND ErrorCode IS NULL;
UPDATE AD_Message SET ErrorCode='TAXDECLARATION_CORRECTION_NOT_LOCKED',    Updated=now(), UpdatedBy=100 WHERE Value='TaxDeclaration_CreateCorrection_OriginalNotLocked' AND ErrorCode IS NULL;
UPDATE AD_Message SET ErrorCode='TAXDECLARATION_PROCESSED_LOCKED',         Updated=now(), UpdatedBy=100 WHERE Value='TaxDeclaration_ProcessedLocked'                    AND ErrorCode IS NULL;
