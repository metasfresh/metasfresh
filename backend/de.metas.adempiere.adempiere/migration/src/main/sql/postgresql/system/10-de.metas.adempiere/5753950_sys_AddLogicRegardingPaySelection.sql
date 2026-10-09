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

-- Value: PaySelectionLine.Document.InvalidCurrency
-- 2025-05-09T10:28:04.729Z
UPDATE AD_Message SET MsgText='Die Position kann nicht gespeichert werden, weil die dokument währung{0} nicht zu der Währung {1} des Bankkontos in der Zahlungsanweisung passt.', Value='PaySelectionLine.Document.InvalidCurrency',Updated=TO_TIMESTAMP('2025-05-09 13:28:04.727','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=542834
;

-- 2025-05-09T10:28:04.741Z
UPDATE AD_Message_Trl trl SET MsgText='Die Position kann nicht gespeichert werden, weil die dokument währung{0} nicht zu der Währung {1} des Bankkontos in der Zahlungsanweisung passt.' WHERE AD_Message_ID=542834 AND AD_Language='de_DE'
;

-- Value: PaySelectionLine.Document.InvalidCurrency
-- 2025-05-09T10:28:23.380Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='La position ne peut pas être enregistrée car la monnaie comptable {0} ne correspond pas à la monnaie {1} du compte bancaire dans l''instruction de paiement.',Updated=TO_TIMESTAMP('2025-05-09 13:28:23.379','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=542834
;

-- Value: PaySelectionLine.Document.InvalidCurrency
-- 2025-05-09T10:28:37.155Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Die Position kann nicht gespeichert werden, weil die dokument währung{0} nicht zu der Währung {1} des Bankkontos in der Zahlungsanweisung passt.',Updated=TO_TIMESTAMP('2025-05-09 13:28:37.155','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=542834
;

-- Value: PaySelectionLine.Document.InvalidCurrency
-- 2025-05-09T10:29:03.201Z
UPDATE AD_Message_Trl SET MsgText='The item cannot be saved because the document currency {0} does not match the bank account currency {1 in the payment instruction.',Updated=TO_TIMESTAMP('2025-05-09 13:29:03.201','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=542834
;

-- Value: PaySelectionLine.Document.InvalidCurrency
-- 2025-05-09T10:29:05.997Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-09 13:29:05.997','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=542834
;

-- Value: PaySelectionLine.Document.InvalidCurrency
-- 2025-05-09T10:29:14.618Z
UPDATE AD_Message_Trl SET MsgText='Die Position kann nicht gespeichert werden, weil die dokument währung{0} nicht zu der Währung {1} des Bankkontos in der Zahlungsanweisung passt.',Updated=TO_TIMESTAMP('2025-05-09 13:29:14.618','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Message_ID=542834
;

-- Column: C_PaySelection.C_BP_BankAccount_ID
-- 2025-05-09T14:00:35.901Z
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2025-05-09 17:00:35.901','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=5619
;

