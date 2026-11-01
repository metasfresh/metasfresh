-- gh#28680: Change Date filter from exact match to date range
-- on C_Order_M_InOut_C_Invoice_Overview_V window
-- This enables "from date - to date" filtering in the WebUI

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Date
UPDATE AD_Column
SET FilterOperator = 'B',
    Updated        = TO_TIMESTAMP('2026-03-21 14:30:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy      = 100
WHERE AD_Column_ID = 591972
;
