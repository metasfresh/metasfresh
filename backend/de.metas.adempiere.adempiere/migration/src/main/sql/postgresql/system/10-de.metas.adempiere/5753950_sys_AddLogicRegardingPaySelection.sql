-- Run mode: SWING_CLIENT

-- Column: C_PaySelectionLine.C_Invoice_ID
-- 2025-05-08T18:05:16.368Z
UPDATE AD_Column SET IsMandatory='N', MandatoryLogic='@Original_Payment_ID@>0',Updated=TO_TIMESTAMP('2025-05-08 21:05:16.368','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=5639
;

-- 2025-05-08T18:05:19.490Z
INSERT INTO t_alter_column values('c_payselectionline','C_Invoice_ID','NUMERIC(10)',null,null)
;

-- 2025-05-08T18:05:19.495Z
INSERT INTO t_alter_column values('c_payselectionline','C_Invoice_ID',null,'NULL',null)
;

-- Column: C_PaySelectionLine.C_Invoice_ID
-- 2025-05-09T06:58:01.539Z
UPDATE AD_Column SET MandatoryLogic='@Original_Payment_ID@ < 0',Updated=TO_TIMESTAMP('2025-05-09 09:58:01.539','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=5639
;

-- Tab: Zahlung anweisen(206,D) -> Rechnung auswählen
-- Table: C_PaySelectionLine
-- 2025-05-09T06:59:12.044Z
UPDATE AD_Tab SET ReadOnlyLogic='@Original_Payment_ID@ > 0',Updated=TO_TIMESTAMP('2025-05-09 09:59:12.044','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=353
;

-- Column: C_Payment.RefundStatus
-- 2025-05-09T07:40:49.103Z
UPDATE AD_Column SET IsCalculated='Y',Updated=TO_TIMESTAMP('2025-05-09 10:40:49.102','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589929
;

