-- Run mode: SWING_CLIENT

-- Value: SEPA_Payment_Refund
-- 2025-05-13T11:52:46.485Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545546,0,TO_TIMESTAMP('2025-05-13 14:52:46.232','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Rückerstatten','I',TO_TIMESTAMP('2025-05-13 14:52:46.232','YYYY-MM-DD HH24:MI:SS.US'),100,'SEPA_Payment_Refund')
;

-- 2025-05-13T11:52:46.510Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545546 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: SEPA_Payment_Refund
-- 2025-05-13T11:53:09.898Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-13 14:53:09.898','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545546
;

-- Value: SEPA_Payment_Refund
-- 2025-05-13T11:53:11.657Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-13 14:53:11.657','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545546
;

-- Value: SEPA_Payment_Refund
-- 2025-05-13T11:53:16.791Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-13 14:53:16.791','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545546
;

-- Value: SEPA_Payment_Refund
-- 2025-05-13T11:53:46.829Z
UPDATE AD_Message_Trl SET MsgText='Reimbursement',Updated=TO_TIMESTAMP('2025-05-13 14:53:46.829','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545546
;

-- Value: SEPA_Invoice_Nor_Payment_Set
-- 2025-05-13T11:56:13.200Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545547,0,TO_TIMESTAMP('2025-05-13 14:56:12.709','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Neither invoice, nor original payment were set.','E',TO_TIMESTAMP('2025-05-13 14:56:12.709','YYYY-MM-DD HH24:MI:SS.US'),100,'SEPA_Invoice_Nor_Payment_Set')
;

-- 2025-05-13T11:56:13.201Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545547 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: SEPA_Invoice_Nor_Payment_Set
-- 2025-05-13T11:56:20.757Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-13 14:56:20.757','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545547
;

-- Value: SEPA_Invoice_Nor_Payment_Set
-- 2025-05-13T11:56:24.241Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Weder die Rechnung noch die ursprüngliche Zahlung wurden festgelegt.',Updated=TO_TIMESTAMP('2025-05-13 14:56:24.241','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545547
;

-- Value: SEPA_Invoice_Nor_Payment_Set
-- 2025-05-13T11:56:31.647Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Weder die Rechnung noch die ursprüngliche Zahlung wurden festgelegt.',Updated=TO_TIMESTAMP('2025-05-13 14:56:31.647','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545547
;

-- 2025-05-13T11:56:31.649Z
UPDATE AD_Message SET MsgText='Weder die Rechnung noch die ursprüngliche Zahlung wurden festgelegt.' WHERE AD_Message_ID=545547
;

