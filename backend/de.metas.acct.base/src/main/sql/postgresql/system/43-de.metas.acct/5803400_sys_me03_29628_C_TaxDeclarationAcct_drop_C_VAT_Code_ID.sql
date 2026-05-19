-- Tax Declaration: drop C_VAT_Code_ID from C_TaxDeclarationAcct entirely.
-- Rationale: VATCode (string) + AmountType are the authoritative carriers; for the
-- per-Fact_Acct detail snapshot we don't need the FK to the master record.
-- (C_TaxDeclarationLine keeps C_VAT_Code_ID as a nice-to-have aggregated reference.)

-- 1. Remove the C_VAT_Code_ID UI element from the Accts tab
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID = 651183;

-- 2. Renumber the VATCode element to take the freed slot (40 instead of 45) for clean ordering
UPDATE AD_UI_Element
SET SeqNo = 40, SeqNoGrid = 40, Updated = TIMESTAMP '2026-05-19 00:00:00', UpdatedBy = 100
WHERE AD_UI_Element_ID = 651703;

-- 3. Remove the AD_Field row (and its translations)
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 779199;
DELETE FROM AD_Field     WHERE AD_Field_ID = 779199;

-- 4. Remove the AD_Column row (and its translations)
DELETE FROM AD_Column_Trl WHERE AD_Column_ID = 592514;
DELETE FROM AD_Column     WHERE AD_Column_ID = 592514;

-- 5. Drop the physical column (PostgreSQL auto-drops the FK constraint)
ALTER TABLE C_TaxDeclarationAcct DROP COLUMN IF EXISTS C_VAT_Code_ID;
