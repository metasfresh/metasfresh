-- Run mode: SWING_CLIENT

-- Column: C_Invoice_Candidate.GrandTotal
-- 2026-02-16T10:17:56.025Z
UPDATE AD_Column SET ColumnSQL='(SELECT GrandTotal from C_Order where C_Order_ID = C_Invoice_Candidate.C_Order_ID)', IsLazyLoading='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2026-02-16 10:17:56.024000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592007
;

