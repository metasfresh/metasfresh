-- Source DDL: backend/de.metas.edi/src/main/sql/postgresql/ddl/functions/EDI_BPartner_RecipientFlag.sql
-- Migration ID: 5807170
-- EDI derived recipient columns — rewrite the 4 virtual ColumnSQL expressions to use
-- a dedicated DB function (EDI_BPartner_RecipientFlag) instead of doubly-nested subqueries.
--
-- Background: migration 5807040 introduced ORDER BY s.SeqNo … LIMIT 1 resolution, but the
-- resulting doubly-nested subquery is not parseable by
--   de.metas.security.impl.ParsedSql.extractAllSqlSelectStatements
-- which throws IllegalArgumentException → HTTP 500 when C_Order / C_Invoice loads in WebUI.
-- Simple single-level function-call subqueries parse fine in that code path.
--
-- Fix: introduce EDI_BPartner_RecipientFlag(bpartner_id, location_id, message_type) and
-- rewrite every ColumnSQL to the trivial (select EDI_BPartner_RecipientFlag(...)) form.

-- Step 1: create the helper function
CREATE OR REPLACE FUNCTION EDI_BPartner_RecipientFlag(
    p_c_bpartner_id          numeric,
    p_c_bpartner_location_id numeric,
    p_message_type           character
) RETURNS character
    LANGUAGE sql
    STABLE
AS
$$
SELECT COALESCE(
    (SELECT CASE WHEN p_message_type = 'D' THEN s.IsEdiDesadvRecipient ELSE s.IsEdiInvoicRecipient END
       FROM C_BPartner_EDI_Setting s
      WHERE s.C_BPartner_ID = p_c_bpartner_id
        -- match the exact-location row OR the partner-default (NULL-location) row.
        -- A passed location of 0 ("no location" on some documents) matches no exact row
        -- (the FK never stores 0), so only the NULL-default branch applies — which is intended.
        AND (s.C_BPartner_Location_ID = p_c_bpartner_location_id OR s.C_BPartner_Location_ID IS NULL)
        AND s.IsActive = 'Y'
      ORDER BY s.SeqNo, s.C_BPartner_EDI_Setting_ID
      LIMIT 1),
    'N')::character(1)
$$;

-- Step 2: rewrite the 4 virtual ColumnSQL expressions

-- M_ShipmentSchedule.IsEdiDesadvRecipient (keyed on C_Bpartner_ID + C_BPartner_Location_ID)
UPDATE AD_Column SET
    ColumnSQL = '(select EDI_BPartner_RecipientFlag(@JoinTableNameOrAliasIncludingDot@C_Bpartner_ID, @JoinTableNameOrAliasIncludingDot@C_BPartner_Location_ID, ''D''))',
    Updated = TO_TIMESTAMP('2026-06-10 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'M_ShipmentSchedule')
  AND ColumnName = 'IsEdiDesadvRecipient'
;

-- C_Order.IsEdiInvoicRecipient (keyed on Bill_BPartner_ID + Bill_Location_ID)
UPDATE AD_Column SET
    ColumnSQL = '(select EDI_BPartner_RecipientFlag(@JoinTableNameOrAliasIncludingDot@Bill_BPartner_ID, @JoinTableNameOrAliasIncludingDot@Bill_Location_ID, ''I''))',
    Updated = TO_TIMESTAMP('2026-06-10 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Order')
  AND ColumnName = 'IsEdiInvoicRecipient'
;

-- C_Invoice.IsEdiInvoicRecipient (keyed on C_BPartner_ID + C_BPartner_Location_ID)
UPDATE AD_Column SET
    ColumnSQL = '(select EDI_BPartner_RecipientFlag(@JoinTableNameOrAliasIncludingDot@C_BPartner_ID, @JoinTableNameOrAliasIncludingDot@C_BPartner_Location_ID, ''I''))',
    Updated = TO_TIMESTAMP('2026-06-10 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Invoice')
  AND ColumnName = 'IsEdiInvoicRecipient'
;

-- C_Invoice_Candidate.IsEdiInvoicRecipient (keyed on Bill_BPartner_ID + Bill_Location_ID)
UPDATE AD_Column SET
    ColumnSQL = '(select EDI_BPartner_RecipientFlag(@JoinTableNameOrAliasIncludingDot@Bill_BPartner_ID, @JoinTableNameOrAliasIncludingDot@Bill_Location_ID, ''I''))',
    Updated = TO_TIMESTAMP('2026-06-10 10:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Invoice_Candidate')
  AND ColumnName = 'IsEdiInvoicRecipient'
;
