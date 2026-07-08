-- me03 30443 (F01010.4 Invoice Accounting Overrides) — UAT follow-up
-- The override-account field on the purchase invoice candidate
-- (C_Invoice_Candidate.C_ElementValue_Override_ID, AD_Column 592836, AD_Field 781214 on
--  window 540983 "Rechnungsdisposition Einkauf" / tab 543052) stayed editable after the
-- candidate was processed. Once the candidate is processed it has already been aggregated into
-- the invoice, so changing the override has no effect — the field must be read-only.
--
-- Guard the column (not the field): the WebUI applies AD_Column.ReadOnlyLogic when the field has
-- none of its own, exactly as the sibling column C_Invoice_Candidate.POReference already does on
-- this same tab (ReadOnlyLogic = '@Processed@ = ''Y'''). Column-level keeps it DRY and covers any
-- future window that shows this column.

UPDATE AD_Column
   SET ReadOnlyLogic = '@Processed@ = ''Y''',
       Updated       = TO_TIMESTAMP('2026-07-08 12:00:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC',
       UpdatedBy     = 100
 WHERE AD_Column_ID = 592836
   AND ColumnName    = 'C_ElementValue_Override_ID'
   AND AD_Table_ID   = 540270
;
