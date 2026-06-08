-- 2026-06-08T10:00:00.000Z
-- me03 https://github.com/metasfresh/me03/issues/30080 — Fail-loud invariant: spreading a complex-but-invalid
-- payment term (breaks sum ≠ 100%) must throw a clear, user-facing error instead of silently writing a wrong DueAmt.
-- {0} = term value, {1} = term name, {2} = reason (e.g. "Total percent must be exactly 100%, but it was: 30%")
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545748 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-08 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Zahlungsbedingung {0} ({1}) ist ungültig und kann nicht für die Zahlungsplanerstellung verwendet werden: {2}','E',TO_TIMESTAMP('2026-06-08 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'PaymentTerm_Invalid')
;

-- 2026-06-08T10:00:01.000Z
UPDATE AD_Message SET ErrorCode='PAYMENT_TERM_INVALID', Updated=TO_TIMESTAMP('2026-06-08 10:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545748
;

-- 2026-06-08T10:00:02.000Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545748 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2026-06-08T10:00:03.000Z
UPDATE AD_Message_Trl SET MsgText='Payment term {0} ({1}) is invalid and cannot be used for pay-schedule creation: {2}',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-08 10:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545748
;

-- 2026-06-08T10:00:04.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-08 10:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545748
;

-- 2026-06-08T10:00:05.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-08 10:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545748
;
