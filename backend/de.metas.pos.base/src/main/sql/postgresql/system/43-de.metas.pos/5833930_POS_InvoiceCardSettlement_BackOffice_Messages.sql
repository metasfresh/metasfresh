-- Messages of the back-office processes of the card settlement window, and a clearer refusal text for "mark resolved manually".
-- IDs allocated from the central ID server.

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,546001 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-10 09:00:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Die Buchung der Kartenzahlung ist nicht abgeschlossen: {0}','E',TO_TIMESTAMP('2026-11-10 09:00:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.InvoiceCardSettlement.RetryBookingNotCompleted')
;

UPDATE AD_Message SET ErrorCode='POS_CARD_SETTLEMENT_RETRY_NOT_COMPLETED', Updated=TO_TIMESTAMP('2026-11-10 09:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=546001
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546001
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='The booking of the card payment is not completed: {0}',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=546001
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=546001
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,546002 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-10 09:00:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','SumUp konnte nicht gefragt werden, ob die Kartenzahlung inzwischen erstattet wurde. Bitte später erneut versuchen.','E',TO_TIMESTAMP('2026-11-10 09:00:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.InvoiceCardSettlement.RetryRefusedSumUpNotReachable')
;

UPDATE AD_Message SET ErrorCode='POS_CARD_SETTLEMENT_SUMUP_UNREACHABLE', Updated=TO_TIMESTAMP('2026-11-10 09:00:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=546002
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546002
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='SumUp could not be asked whether the card payment was refunded meanwhile. Please try again later.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=546002
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=546002
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,546003 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-10 09:00:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Für den aktuellen Status der Kartenzahlung nicht möglich.','E',TO_TIMESTAMP('2026-11-10 09:00:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.InvoiceCardSettlement.NotPossibleForStatus')
;

UPDATE AD_Message SET ErrorCode='POS_CARD_SETTLEMENT_WRONG_STATUS', Updated=TO_TIMESTAMP('2026-11-10 09:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=546003
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546003
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='Not possible for the current status of the card payment.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=546003
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=546003
;

-- de.metas.pos.InvoiceCardSettlement.MarkResolvedRefused: also fits a settlement without a card payment
UPDATE AD_Message SET MsgText='Nicht möglich: Die Rechnung ist offen, und die Kartenzahlung fehlt oder ist nicht vollständig zugeordnet.', Updated=TO_TIMESTAMP('2026-11-10 09:00:16','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545998
;

UPDATE AD_Message_Trl SET MsgText='Nicht möglich: Die Rechnung ist offen, und die Kartenzahlung fehlt oder ist nicht vollständig zugeordnet.', Updated=TO_TIMESTAMP('2026-11-10 09:00:17','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language<>'en_US' AND AD_Message_ID=545998
;

UPDATE AD_Message_Trl SET MsgText='Not possible: the invoice is open, and the card payment is missing or not fully allocated.', Updated=TO_TIMESTAMP('2026-11-10 09:00:18','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545998
;
