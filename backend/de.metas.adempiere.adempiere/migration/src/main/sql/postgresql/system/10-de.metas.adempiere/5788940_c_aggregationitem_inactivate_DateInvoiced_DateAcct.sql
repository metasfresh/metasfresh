SELECT backup_table('C_AggregationItem', '_dateInvAct')
;

UPDATE C_AggregationItem
SET isactive='N', updatedby=100,
    updated = TO_TIMESTAMP('2026-02-17 14:00:00', 'YYYY-MM-DD HH24:MI:SS')
WHERE c_aggregationitem_id IN (540030/*DateAcct*/, 540031/*DateInvoiced*/)
;

