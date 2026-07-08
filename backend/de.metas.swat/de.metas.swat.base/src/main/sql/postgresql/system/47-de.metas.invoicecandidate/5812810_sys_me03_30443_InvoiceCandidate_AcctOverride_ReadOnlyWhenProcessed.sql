-- me03 30443 (F01010.4 Invoice Accounting Overrides) — UAT follow-up
-- The override-account field on the purchase invoice candidate
-- (C_Invoice_Candidate.C_ElementValue_Override_ID, AD_Column 592836) stayed editable after the
-- candidate was processed. Root cause: the feature migration (5808640) created the column with
-- IsAlwaysUpdateable='Y'. The WebUI's DocumentReadonly.computeFieldReadonly() makes every field of
-- a processed document read-only UNLESS the column is flagged always-updateable, so that flag
-- exempted the override field from the standard processed-lock.
--
-- Once the candidate is processed it has already been aggregated into the invoice and the override
-- has no further effect, so the field must lock like every other candidate field. Remove the
-- exemption (IsAlwaysUpdateable='N') — matching the sibling column C_Tax_Override_ID on this table,
-- which locks on processing with no explicit ReadOnlyLogic. No ReadOnlyLogic is needed: the
-- framework's processed-lock handles it.

UPDATE AD_Column
   SET IsAlwaysUpdateable = 'N',
       Updated            = TO_TIMESTAMP('2026-07-08 12:00:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC',
       UpdatedBy          = 100
 WHERE AD_Column_ID = 592836
   AND ColumnName    = 'C_ElementValue_Override_ID'
   AND AD_Table_ID   = 540270
;
