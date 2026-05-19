-- Tax Declaration Iter4 fixes:
--
-- 1. AmountType columns on C_TaxDeclarationLine + C_TaxDeclarationAcct were created
--    as String (AD_Reference_ID=10) with no reference list. They mirror C_VAT_Code.AmountType
--    which is List (17) → AD_Reference 542087 "C_VAT_Code AmountType" (N=Netto, T=Steuer).
--    Use the same List reference so the UI shows the translated labels.
--
-- 2. AD_Process C_TaxDeclaration_Build (585615): set RefreshAllAfterExecution='Y' so
--    the WebUI invalidates and reloads the document (header + included tabs) after the
--    process completes, making the newly-built lines/accts visible without manual reload.

-- AmountType on C_TaxDeclarationLine
UPDATE AD_Column
SET AD_Reference_ID = 17,
    AD_Reference_Value_ID = 542087,
    Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Column_ID = 592511;

-- AmountType on C_TaxDeclarationAcct
UPDATE AD_Column
SET AD_Reference_ID = 17,
    AD_Reference_Value_ID = 542087,
    Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Column_ID = 592515;

-- AD_Process: refresh document after execution
UPDATE AD_Process
SET RefreshAllAfterExecution = 'Y',
    Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_Process_ID = 585615;
