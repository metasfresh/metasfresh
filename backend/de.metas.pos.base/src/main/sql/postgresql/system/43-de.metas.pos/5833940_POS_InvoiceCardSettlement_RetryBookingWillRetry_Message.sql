-- Info message of "retry booking" when the booking failed with a transient error and is retried automatically.
-- ID allocated from the central ID server.

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,546004 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-10 10:00:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Die Buchung wird automatisch erneut versucht.','I',TO_TIMESTAMP('2026-11-10 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.InvoiceCardSettlement.RetryBookingWillRetry')
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546004
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='The booking will be retried automatically.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 10:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=546004
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 10:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=546004
;
