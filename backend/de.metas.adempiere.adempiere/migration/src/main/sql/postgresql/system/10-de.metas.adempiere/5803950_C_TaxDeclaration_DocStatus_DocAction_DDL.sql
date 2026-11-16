-- Tax Declaration Iter 5 — add the *physical* DocStatus + DocAction columns to C_TaxDeclaration.
--
-- Why this script is needed:
--   AD_Table_ConvertToDocument (run as System Administrator) auto-records the AD_Column metadata
--   for DocStatus + DocAction (script 5803890_C_TaxDeclaration_convert_to_document.sql), but it
--   marks those columns with IsSyncDatabase='N' and never emits the matching db_alter_table DDL.
--   So the AD-side believes the columns exist while the DB does not have them physically.
--   Without this script, every Java save path crashes with "column ... does not exist".
--
-- DDL shape mirrors C_Customs_Invoice (verified via information_schema.columns on c_customs_invoice):
--   DocAction CHAR(2)    NOT NULL DEFAULT 'CO'
--   DocStatus VARCHAR(2) NOT NULL DEFAULT 'DR'

SELECT public.db_alter_table('C_TaxDeclaration', 'ALTER TABLE public.C_TaxDeclaration ADD COLUMN DocAction CHAR(2) NOT NULL DEFAULT ''CO''')
;
SELECT public.db_alter_table('C_TaxDeclaration', 'ALTER TABLE public.C_TaxDeclaration ADD COLUMN DocStatus VARCHAR(2) NOT NULL DEFAULT ''DR''')
;

-- Flip IsSyncDatabase='Y' on both AD_Column rows so future framework operations recognise
-- the columns as in-sync with the DB.
UPDATE AD_Column SET IsSyncDatabase='Y', Updated=TIMESTAMP '2026-05-21 00:00:00', UpdatedBy=100
WHERE AD_Column_ID IN (592578, 592579) -- DocAction, DocStatus
;
