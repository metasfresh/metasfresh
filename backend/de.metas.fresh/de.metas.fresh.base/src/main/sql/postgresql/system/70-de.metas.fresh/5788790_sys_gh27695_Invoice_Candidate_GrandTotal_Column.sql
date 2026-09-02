-- Run mode: SWING_CLIENT

-- Column: C_Invoice_Candidate.GrandTotal
-- Column SQL (old): (SELECT GrandTotal from C_Order where C_Order_ID = C_Invoice_Candidate.C_Order_ID)
-- 2026-02-16T13:16:07.026Z
UPDATE AD_Column SET ColumnSQL='(get_Invoice_Candidate_GrandTotal (C_Invoice_Candidate.C_Invoice_Candidate_ID))',Updated=TO_TIMESTAMP('2026-02-16 13:16:07.026000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592007
;

