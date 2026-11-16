-- DD_NetworkDistribution (window 53018, "Distributionsnetzwerk"): make the DocumentNo field non-mandatory.
--
-- Why: the DocumentNo field (AD_Field 54371) is field-level IsMandatory='Y' + IsReadOnly='Y', but the WebUI
-- has no way to pre-fill it for this table: DD_NetworkDistribution has no C_DocType, and the WebUI auto-sequence
-- default-value path requires AD_Field.AD_Sequence_ID (GridTabVOBasedDocumentEntityDescriptorFactory ->
-- DefaultValueExpressionsFactory.extractDefaultValueExpression), which is never populated. So a new record's
-- DocumentNo stays NULL, DocumentField.checkValid flags invalidFieldMandatoryNotFilled (readonly is NOT exempt),
-- the record is not saveable -> "cannot create a new distribution network" with no error and an empty field.
-- The underlying AD_Column.DocumentNo is already IsMandatory='N'; only the field-level override blocks it.
--
-- Fix: drop the field-level mandatory. On save, PO.saveNew populates DocumentNo from the
-- DocumentNo_DD_NetworkDistribution sequence (forTableName lookup), so the number is still assigned automatically.

UPDATE AD_Field
SET IsMandatory = 'N',
    Updated     = TO_TIMESTAMP('2026-06-05 10:00:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Field_ID = 54371;
