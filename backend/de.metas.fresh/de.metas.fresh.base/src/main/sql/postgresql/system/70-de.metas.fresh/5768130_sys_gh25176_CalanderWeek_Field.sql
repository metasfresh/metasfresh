-- Run mode: SWING_CLIENT

-- Column: M_ReceiptSchedule.CalendarWeek
-- Column SQL (old): (SELECT EXTRACT(WEEK from M_ReceiptSchedule.MovementDate))
-- 2025-09-10T16:59:40.163Z
UPDATE AD_Column SET ColumnSQL='(SELECT EXTRACT(WEEK from COALESCE(M_ReceiptSchedule.DatePromised_Override, M_ReceiptSchedule.MovementDate)))',Updated=TO_TIMESTAMP('2025-09-10 16:59:40.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590640
;

