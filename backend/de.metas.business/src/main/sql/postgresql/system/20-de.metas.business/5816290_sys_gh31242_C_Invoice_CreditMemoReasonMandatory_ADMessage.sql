-- Run mode: SWING_CLIENT

-- User-facing, localized error shown when a credit memo is completed while at least one line is
-- missing its Line_CreditMemoReason (de.metas.invoice.interceptor.C_Invoice). {0} = the affected line numbers.

-- Value: de.metas.invoice.CreditMemoReasonMandatory
-- 1. the message (base text = German)
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value,ErrorCode)
VALUES (0,545792 /*From ID Server*/,0,TO_TIMESTAMP('2026-07-27 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die Gutschrift kann nicht abgeschlossen werden: Bitte erfassen Sie auf allen Positionen einen Gutschriftsgrund. Betroffene Zeilen: {0}','E',TO_TIMESTAMP('2026-07-27 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice.CreditMemoReasonMandatory','CreditMemoReasonMandatory')
;

-- 2. seed AD_Message_Trl for ALL active system languages with the base (DE) text, IsTranslated='N'
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545792
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 3. en_US override (the real English text) + IsTranslated='Y'
UPDATE AD_Message_Trl SET MsgText='The credit memo cannot be completed: please enter a credit-memo reason on all lines. Affected lines: {0}',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-27 12:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545792
;

-- 4. flip de_DE + de_CH to IsTranslated='Y' (their text already equals the DE base)
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-27 12:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545792
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-27 12:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545792
;
