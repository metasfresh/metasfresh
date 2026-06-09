-- Run mode: SWING_CLIENT

-- Reference: C_Pay_Selection_Target
-- Table: C_PaySelectionLine
-- Key: C_PaySelectionLine.C_Payment_ID
-- 2026-01-09T08:48:47.564Z
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2026-01-09 08:48:47.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541432
;

-- Reference: C_Pay_Selection_Target
-- Table: C_PaySelection
-- Key: C_PaySelection.C_PaySelection_ID
-- 2026-01-09T09:32:53.629Z
UPDATE AD_Ref_Table SET AD_Key=5609, AD_Table_ID=426, WhereClause='C_PaySelection.C_PaySelection_ID IN (SELECT ps.C_PaySelection_ID from C_PaySelection ps JOIN C_PaySelectionline psl on ps.c_payselection_id = psl.c_payselection_id AND psl.c_payment_id=@C_Payment_ID/-1@)',Updated=TO_TIMESTAMP('2026-01-09 09:32:53.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541432
;

