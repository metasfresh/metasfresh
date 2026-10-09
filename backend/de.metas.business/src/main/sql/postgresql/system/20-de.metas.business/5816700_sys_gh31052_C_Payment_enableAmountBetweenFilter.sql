-- Run mode: SWING_CLIENT

-- me03#31052 Nach Beträgen suchen können: enable amount from/to (Between) filter

-- Column: C_Payment.PayAmt
-- 2026-07-28T10:00:00.000Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-07-28 10:00:00.000','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=5303
;
