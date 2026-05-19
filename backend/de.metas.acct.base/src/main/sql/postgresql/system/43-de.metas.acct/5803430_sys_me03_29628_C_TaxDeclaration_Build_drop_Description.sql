-- Tax Declaration Iter4: drop the description from the C_TaxDeclaration_Build process.
-- The auto-generated technical description added in 5803210 wasn't useful; the process
-- name alone ("Zeilen erstellen" / "Create lines") is self-explanatory.

UPDATE AD_Process
SET Description = NULL,
    Updated = TIMESTAMP '2026-05-20 00:00:00', UpdatedBy = 100
WHERE AD_Process_ID = 585615;

UPDATE AD_Process_Trl
SET Description = NULL,
    Updated = TIMESTAMP '2026-05-20 00:00:00', UpdatedBy = 100
WHERE AD_Process_ID = 585615;
