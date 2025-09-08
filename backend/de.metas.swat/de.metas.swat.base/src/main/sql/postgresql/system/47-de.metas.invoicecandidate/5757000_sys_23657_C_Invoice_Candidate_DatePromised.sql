
-- Cast C_Order.DatePromised to Date for the invoice candidate.
-- That way it's consistent with the AD_Column's Reference-Type.
-- If we went with Reference-Type "Date + Time", it would mean that filtering by date range would not work.  

-- Column: C_Invoice_Candidate.DatePromised
-- Column SQL (old): (SELECT o.DatePromised from C_Order o where o.C_Order_ID = C_Invoice_Candidate.C_Order_ID)
-- 2025-06-06T14:29:41.936Z
UPDATE AD_Column SET AD_Reference_ID=15, ColumnSQL='(SELECT o.DatePromised::date from C_Order o where o.C_Order_ID = C_Invoice_Candidate.C_Order_ID)',Updated=TO_TIMESTAMP('2025-06-06 14:29:41.936000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=554387
;
