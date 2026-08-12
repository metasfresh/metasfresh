-- AD_Message for ESRImportBL.markInvoiceAlreadyPaid() — the "invoice already paid" note
-- written to ESR_ImportLine.MatchErrorMsg, previously a hardcoded German literal.

-- 1. the message (base text = German)
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545795 /*From ID Server*/,0,TO_TIMESTAMP('2026-08-12 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','Rechnung {0} wurde im System als bereits bezahlt markiert','I',TO_TIMESTAMP('2026-08-12 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr.InvoiceAlreadyPaid')
;

-- 2. seed AD_Message_Trl for ALL active system languages with the base (DE) text, IsTranslated='N'
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545795
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 3. en_US override (the real English text) + IsTranslated='Y'
UPDATE AD_Message_Trl SET MsgText='Invoice {0} was marked as already paid in the system',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-08-12 10:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545795
;

-- 4. flip de_DE + de_CH to IsTranslated='Y' (their text already equals the DE base)
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-08-12 10:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545795
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-08-12 10:00:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545795
;
