-- DD_Order_Picking_Rebuild scheduler (AD_Scheduler_ID=550124): set AD_Client_ID/AD_Org_ID to 1000000.
-- Mirrors the dt204 fix that set the same client/org on its DD_Order schedulers.
-- AD_Scheduler is operator data — backup before the UPDATE.

-- 2026-06-16T09:30:00Z
SELECT backup_table('AD_Scheduler');

UPDATE AD_Scheduler
SET AD_Client_ID=1000000,
    AD_Org_ID=1000000,
    Updated=TO_TIMESTAMP('2026-06-16 09:30:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Scheduler_ID=550124;
