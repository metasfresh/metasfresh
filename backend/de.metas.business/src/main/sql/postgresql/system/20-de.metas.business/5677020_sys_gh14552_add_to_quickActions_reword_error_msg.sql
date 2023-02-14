-- Value: Invoice_already_paid
-- 2023-02-14T10:21:57.027Z
UPDATE AD_Message SET MsgText='Die Rechnung wurden bereits bezahlt', Value='Invoice_already_paid',Updated=TO_TIMESTAMP('2023-02-14 12:21:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545236
;

-- 2023-02-14T10:21:57.028Z
UPDATE AD_Message_Trl trl SET MsgText='Die Rechnung wurden bereits bezahlt' WHERE AD_Message_ID=545236 AND AD_Language='de_DE'
;

-- Value: Invoice_already_paid
-- 2023-02-14T10:22:03.140Z
UPDATE AD_Message_Trl SET MsgText='Die Rechnung wurden bereits bezahlt',Updated=TO_TIMESTAMP('2023-02-14 12:22:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545236
;

-- Value: Invoice_already_paid
-- 2023-02-14T10:22:04.517Z
UPDATE AD_Message_Trl SET MsgText='Die Rechnung wurden bereits bezahlt',Updated=TO_TIMESTAMP('2023-02-14 12:22:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545236
;

-- Value: Invoice_already_paid
-- 2023-02-14T10:22:10.133Z
UPDATE AD_Message_Trl SET MsgText='Die Rechnung wurden bereits bezahlt',Updated=TO_TIMESTAMP('2023-02-14 12:22:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545236
;

-- Value: Invoice_already_paid
-- 2023-02-14T10:22:12.276Z
UPDATE AD_Message_Trl SET MsgText='The invoice has already been paid',Updated=TO_TIMESTAMP('2023-02-14 12:22:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545236
;

-- Process: C_Invoice_OverrideDueDate(de.metas.invoice.process.C_Invoice_OverrideDueDate)
-- Table: C_Invoice
-- EntityType: D
-- 2023-02-14T10:22:39.400Z
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction='Y',Updated=TO_TIMESTAMP('2023-02-14 12:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541360
;

-- Process: C_Invoice_OverrideDueDate(de.metas.invoice.process.C_Invoice_OverrideDueDate)
-- Table: C_Invoice
-- EntityType: D
-- 2023-02-14T10:22:43.512Z
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction='Y',Updated=TO_TIMESTAMP('2023-02-14 12:22:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541361
;

