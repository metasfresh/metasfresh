-- Migration ID: 5807040
-- EDI location routing — repoint the 4 derived IsEdi*Recipient virtual columns from the
-- "coalesce(exact-location row, partner-default row, 'N')" resolution introduced in
-- migration 5806470 to the new lowest-SeqNo-among-matching-rows resolution.
--
-- New rule: return the C_BPartner_EDI_Setting flag from the row with the lowest SeqNo
-- (tie-broken by C_BPartner_EDI_Setting_ID) among rows where
--   (C_BPartner_Location_ID matches the host OR C_BPartner_Location_ID IS NULL)
-- This matches the Java resolver (EdiRecipientResolver / lowest-SeqNo logic) so that
-- the virtual column displayed in the WebUI always agrees with what the Java code decides.
--
-- All four are virtual columns (no physical backing) — only AD_Column.ColumnSQL changes; no DDL.

-- M_ShipmentSchedule.IsEdiDesadvRecipient (keyed on C_Bpartner_ID + C_BPartner_Location_ID)
UPDATE AD_Column SET ColumnSQL =
'(SELECT COALESCE((SELECT s.IsEdiDesadvRecipient FROM C_BPartner_EDI_Setting s WHERE s.C_BPartner_ID = @JoinTableNameOrAliasIncludingDot@C_Bpartner_ID AND (s.C_BPartner_Location_ID = @JoinTableNameOrAliasIncludingDot@C_BPartner_Location_ID OR s.C_BPartner_Location_ID IS NULL) AND s.IsActive = ''Y'' ORDER BY s.SeqNo, s.C_BPartner_EDI_Setting_ID LIMIT 1), ''N''))',
Updated = TO_TIMESTAMP('2026-06-09 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'M_ShipmentSchedule')
  AND ColumnName = 'IsEdiDesadvRecipient'
;

-- C_Order.IsEdiInvoicRecipient (keyed on Bill_BPartner_ID + Bill_Location_ID)
UPDATE AD_Column SET ColumnSQL =
'(SELECT COALESCE((SELECT s.IsEdiInvoicRecipient FROM C_BPartner_EDI_Setting s WHERE s.C_BPartner_ID = @JoinTableNameOrAliasIncludingDot@Bill_BPartner_ID AND (s.C_BPartner_Location_ID = @JoinTableNameOrAliasIncludingDot@Bill_Location_ID OR s.C_BPartner_Location_ID IS NULL) AND s.IsActive = ''Y'' ORDER BY s.SeqNo, s.C_BPartner_EDI_Setting_ID LIMIT 1), ''N''))',
Updated = TO_TIMESTAMP('2026-06-09 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Order')
  AND ColumnName = 'IsEdiInvoicRecipient'
;

-- C_Invoice.IsEdiInvoicRecipient (keyed on C_BPartner_ID + C_BPartner_Location_ID)
UPDATE AD_Column SET ColumnSQL =
'(SELECT COALESCE((SELECT s.IsEdiInvoicRecipient FROM C_BPartner_EDI_Setting s WHERE s.C_BPartner_ID = @JoinTableNameOrAliasIncludingDot@C_BPartner_ID AND (s.C_BPartner_Location_ID = @JoinTableNameOrAliasIncludingDot@C_BPartner_Location_ID OR s.C_BPartner_Location_ID IS NULL) AND s.IsActive = ''Y'' ORDER BY s.SeqNo, s.C_BPartner_EDI_Setting_ID LIMIT 1), ''N''))',
Updated = TO_TIMESTAMP('2026-06-09 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Invoice')
  AND ColumnName = 'IsEdiInvoicRecipient'
;

-- C_Invoice_Candidate.IsEdiInvoicRecipient (keyed on Bill_BPartner_ID + Bill_Location_ID)
UPDATE AD_Column SET ColumnSQL =
'(SELECT COALESCE((SELECT s.IsEdiInvoicRecipient FROM C_BPartner_EDI_Setting s WHERE s.C_BPartner_ID = @JoinTableNameOrAliasIncludingDot@Bill_BPartner_ID AND (s.C_BPartner_Location_ID = @JoinTableNameOrAliasIncludingDot@Bill_Location_ID OR s.C_BPartner_Location_ID IS NULL) AND s.IsActive = ''Y'' ORDER BY s.SeqNo, s.C_BPartner_EDI_Setting_ID LIMIT 1), ''N''))',
Updated = TO_TIMESTAMP('2026-06-09 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Invoice_Candidate')
  AND ColumnName = 'IsEdiInvoicRecipient'
;
