-- Migration ID: 5806470
-- EDI location routing — repoint the derived IsEdi*Recipient virtual columns to C_BPartner_EDI_Setting.
--
-- Context: migration 5806040 dropped the physical EDI columns from C_BPartner and repointed the
-- EDI *views* (M_InOut_Export_EDI_DESADV_JSON_V, edi_cctop_000_v, edi_cctop_invoic_v) to the new
-- child table C_BPartner_EDI_Setting. It MISSED four derived (ColumnSQL / virtual) AD_Columns that
-- still read the now-dropped C_BPartner.IsEdi*Recipient column:
--
--   AD_Column 552463  M_ShipmentSchedule.IsEdiDesadvRecipient   (window Lieferdisposition)
--   AD_Column 552603  C_Order.IsEdiInvoicRecipient              (windows Bestellung, Auftrag)
--   AD_Column 552604  C_Invoice.IsEdiInvoicRecipient            (windows Eingangsrechnung, Rechnung)
--   AD_Column 592213  C_Invoice_Candidate.IsEdiInvoicRecipient  (window Rechnungsdisposition)
--
-- Because these are displayed fields, loading any C_Order / C_Invoice / C_Invoice_Candidate /
-- M_ShipmentSchedule row materialises the virtual column and emits
-- "SELECT ... IsEdiInvoicRecipient FROM C_BPartner ...", which now fails with
-- "column isediinvoicrecipient does not exist" -> HTTP 500 on partner/customer/vendor selection.
--
-- Fix: repoint each ColumnSQL to C_BPartner_EDI_Setting using the SAME resolution rule the feature's
-- views already use: coalesce(exact-location row, partner-default(NULL-location) row, 'N'). The
-- data-copy (5806030) stored each partner's old single flag in its NULL-location row, so for every
-- migrated partner the coalesce falls through to the default row and returns the original value
-- (behaviour preserved); a location-specific row, when present, takes precedence (feature intent).
--
-- Host-table columns are referenced via @JoinTableNameOrAliasIncludingDot@ (the WebUI aliases the
-- host table as "master" in grids); the new-table alias "s" stays literal.
-- All four are virtual columns (no physical backing) — only AD_Column.ColumnSQL changes; no DDL.

-- M_ShipmentSchedule.IsEdiDesadvRecipient (keyed on C_Bpartner_ID + C_BPartner_Location_ID)
UPDATE AD_Column SET ColumnSQL =
'(SELECT COALESCE(
    (SELECT s.IsEdiDesadvRecipient FROM C_BPartner_EDI_Setting s
       WHERE s.C_BPartner_ID = @JoinTableNameOrAliasIncludingDot@C_Bpartner_ID
         AND s.C_BPartner_Location_ID = @JoinTableNameOrAliasIncludingDot@C_BPartner_Location_ID
         AND s.IsActive = ''Y'' LIMIT 1),
    (SELECT s.IsEdiDesadvRecipient FROM C_BPartner_EDI_Setting s
       WHERE s.C_BPartner_ID = @JoinTableNameOrAliasIncludingDot@C_Bpartner_ID
         AND s.C_BPartner_Location_ID IS NULL
         AND s.IsActive = ''Y'' LIMIT 1),
    ''N''))',
Updated = TO_TIMESTAMP('2026-06-05 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'M_ShipmentSchedule')
  AND ColumnName = 'IsEdiDesadvRecipient'
;

-- C_Order.IsEdiInvoicRecipient (keyed on Bill_BPartner_ID + Bill_Location_ID)
UPDATE AD_Column SET ColumnSQL =
'(SELECT COALESCE(
    (SELECT s.IsEdiInvoicRecipient FROM C_BPartner_EDI_Setting s
       WHERE s.C_BPartner_ID = @JoinTableNameOrAliasIncludingDot@Bill_BPartner_ID
         AND s.C_BPartner_Location_ID = @JoinTableNameOrAliasIncludingDot@Bill_Location_ID
         AND s.IsActive = ''Y'' LIMIT 1),
    (SELECT s.IsEdiInvoicRecipient FROM C_BPartner_EDI_Setting s
       WHERE s.C_BPartner_ID = @JoinTableNameOrAliasIncludingDot@Bill_BPartner_ID
         AND s.C_BPartner_Location_ID IS NULL
         AND s.IsActive = ''Y'' LIMIT 1),
    ''N''))',
Updated = TO_TIMESTAMP('2026-06-05 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Order')
  AND ColumnName = 'IsEdiInvoicRecipient'
;

-- C_Invoice.IsEdiInvoicRecipient (keyed on C_BPartner_ID + C_BPartner_Location_ID)
UPDATE AD_Column SET ColumnSQL =
'(SELECT COALESCE(
    (SELECT s.IsEdiInvoicRecipient FROM C_BPartner_EDI_Setting s
       WHERE s.C_BPartner_ID = @JoinTableNameOrAliasIncludingDot@C_BPartner_ID
         AND s.C_BPartner_Location_ID = @JoinTableNameOrAliasIncludingDot@C_BPartner_Location_ID
         AND s.IsActive = ''Y'' LIMIT 1),
    (SELECT s.IsEdiInvoicRecipient FROM C_BPartner_EDI_Setting s
       WHERE s.C_BPartner_ID = @JoinTableNameOrAliasIncludingDot@C_BPartner_ID
         AND s.C_BPartner_Location_ID IS NULL
         AND s.IsActive = ''Y'' LIMIT 1),
    ''N''))',
Updated = TO_TIMESTAMP('2026-06-05 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Invoice')
  AND ColumnName = 'IsEdiInvoicRecipient'
;

-- C_Invoice_Candidate.IsEdiInvoicRecipient (keyed on Bill_BPartner_ID + Bill_Location_ID)
UPDATE AD_Column SET ColumnSQL =
'(SELECT COALESCE(
    (SELECT s.IsEdiInvoicRecipient FROM C_BPartner_EDI_Setting s
       WHERE s.C_BPartner_ID = @JoinTableNameOrAliasIncludingDot@Bill_BPartner_ID
         AND s.C_BPartner_Location_ID = @JoinTableNameOrAliasIncludingDot@Bill_Location_ID
         AND s.IsActive = ''Y'' LIMIT 1),
    (SELECT s.IsEdiInvoicRecipient FROM C_BPartner_EDI_Setting s
       WHERE s.C_BPartner_ID = @JoinTableNameOrAliasIncludingDot@Bill_BPartner_ID
         AND s.C_BPartner_Location_ID IS NULL
         AND s.IsActive = ''Y'' LIMIT 1),
    ''N''))',
Updated = TO_TIMESTAMP('2026-06-05 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Invoice_Candidate')
  AND ColumnName = 'IsEdiInvoicRecipient'
;
