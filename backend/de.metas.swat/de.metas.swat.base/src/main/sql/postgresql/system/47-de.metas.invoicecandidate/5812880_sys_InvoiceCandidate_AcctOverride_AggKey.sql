-- F01010.4 "Invoice Accounting Overrides"
-- Make the per-line GL account override part of the invoice-line aggregation key, so that
-- invoice candidates differing only by C_ElementValue_Override_ID split into separate invoice
-- lines instead of merging. NULL overrides (the default) still merge (NULL = NULL in the key).
--
-- Added to aggregation 540003 "Invoice Line Standard Fields" — the shared line-key aggregation
-- included (INC item 540020) by 540002 "Per invoice candidate or packing material".
-- Type='COL', AD_Column_ID=592836 (C_Invoice_Candidate.C_ElementValue_Override_ID).
--
-- C_AggregationItem_ID 540129  (from ID server)

INSERT INTO C_AggregationItem
    (C_AggregationItem_ID, C_Aggregation_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Type, AD_Column_ID, EntityType)
VALUES
    (540129 /*From ID Server*/, 540003, 0, 0, 'Y',
     TO_TIMESTAMP('2026-07-09 10:05:00','YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC', 100,
     TO_TIMESTAMP('2026-07-09 10:05:00','YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC', 100,
     'COL', 592836, 'de.metas.invoicecandidate')
;
