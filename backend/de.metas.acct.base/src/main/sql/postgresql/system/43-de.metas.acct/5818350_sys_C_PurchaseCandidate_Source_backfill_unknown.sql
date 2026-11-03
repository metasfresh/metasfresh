-- Backfill Source on legacy purchase candidates created before the Source column existed (Source=NULL).
-- 'UNK' (Unknown) is truthful — the origin was never recorded — and is excluded by the sales-order interceptor
-- (which only auto-orders Source='SO'), so backfilled rows are never auto-turned into purchase orders.
-- Runs before the NOT NULL migration (lower prefix).
SELECT backup_table('c_purchasecandidate','_source_backfill_unknown');

UPDATE C_PurchaseCandidate
SET Source='UNK', Updated=TO_TIMESTAMP('2026-08-11 10:05:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE Source IS NULL
;
