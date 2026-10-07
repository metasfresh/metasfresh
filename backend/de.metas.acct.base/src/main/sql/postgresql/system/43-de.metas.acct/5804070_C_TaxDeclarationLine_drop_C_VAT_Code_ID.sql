-- Drop C_VAT_Code_ID from C_TaxDeclarationLine — the VATCode string is the real aggregation
-- key (see ddl/functions/tax_declaration_build.sql header comment). The FK to C_VAT_Code was
-- a nice-to-have lookup that drifted to NULL whenever no master matched; removing the
-- denormalisation simplifies the build function and the unique constraint.

-- 1. Backup before destructive column drop (may have rows in any customer DB).
SELECT backup_table('c_taxdeclarationline', '_drop_C_VAT_Code_ID')
;

-- 2. Replace the unique partial index to key on VATCode string instead of the FK.
DROP INDEX IF EXISTS c_taxdeclarationline_agg_key
;
CREATE UNIQUE INDEX c_taxdeclarationline_agg_key
    ON C_TaxDeclarationLine (C_TaxDeclaration_ID, VATCode, AmountType)
    WHERE IsActive = 'Y'
;

-- 3. Drop the FK constraint then the column itself.
ALTER TABLE C_TaxDeclarationLine DROP CONSTRAINT IF EXISTS c_taxdeclarationline_c_vat_code_id_fkey
;
SELECT public.db_alter_table('C_TaxDeclarationLine', 'ALTER TABLE public.C_TaxDeclarationLine DROP COLUMN C_VAT_Code_ID')
;

-- 4. Remove the AD_Field (+ its UI element, Trl rows) then the AD_Column + translations.
--    AD_Field_ID = 779191 (VAT Code field on Tax Declaration Lines tab)
--    AD_UI_Element_ID = 651175
DELETE FROM AD_UI_Element WHERE AD_Field_ID = 779191
;
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 779191
;
DELETE FROM AD_Field WHERE AD_Field_ID = 779191
;
DELETE FROM AD_Column_Trl WHERE AD_Column_ID = 592510
;
DELETE FROM AD_Column WHERE AD_Column_ID = 592510 -- C_TaxDeclarationLine.C_VAT_Code_ID
;
