-- Messages and notification group for settling an open invoice by card at the till.
-- IDs allocated from the central ID server.

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545993 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-09 12:00:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Für diese Rechnung ist noch eine Kartenzahlung offen.','E',TO_TIMESTAMP('2026-11-09 12:00:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.InvoiceCardSettlement.CardPaymentStillOpen')
;

UPDATE AD_Message SET ErrorCode='POS_CARD_SETTLEMENT_STILL_OPEN', Updated=TO_TIMESTAMP('2026-11-09 12:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545993
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545993
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='A card payment for this invoice is still open.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545993
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545993
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545993
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545994 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-09 12:00:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Rechnung {0} (Karte)','I',TO_TIMESTAMP('2026-11-09 12:00:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.InvoiceCardSettlement.JournalDescription')
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545994
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='Invoice {0} (card)',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545994
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545994
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545994
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545995 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-09 12:00:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Die Kartenzahlung kann erst nach {0} Sekunden geprüft oder freigegeben werden.','E',TO_TIMESTAMP('2026-11-09 12:00:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.InvoiceCardSettlement.ReleaseNotYetAllowed')
;

UPDATE AD_Message SET ErrorCode='POS_CARD_SETTLEMENT_RELEASE_TOO_EARLY', Updated=TO_TIMESTAMP('2026-11-09 12:00:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545995
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545995
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='The card payment can be checked or released only after {0} seconds.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545995
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545995
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545995
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545996 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-09 12:00:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Mit Karte bezahlt – Buchung ausstehend.','E',TO_TIMESTAMP('2026-11-09 12:00:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.InvoiceCardSettlement.ReleaseRefusedApproved')
;

UPDATE AD_Message SET ErrorCode='POS_CARD_SETTLEMENT_RELEASE_APPROVED', Updated=TO_TIMESTAMP('2026-11-09 12:00:20','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545996
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545996
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='Paid by card — booking pending.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545996
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545996
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545996
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545997 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-09 12:00:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Der Status der Kartenzahlung hat sich geändert.','E',TO_TIMESTAMP('2026-11-09 12:00:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.InvoiceCardSettlement.StateChanged')
;

UPDATE AD_Message SET ErrorCode='POS_CARD_SETTLEMENT_STATE_CHANGED', Updated=TO_TIMESTAMP('2026-11-09 12:00:26','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545997
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545997
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='The card payment''s status has changed.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545997
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545997
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545997
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545998 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-09 12:00:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Nicht möglich: Die Rechnung ist offen und die Kartenzahlung ist nicht zugeordnet.','E',TO_TIMESTAMP('2026-11-09 12:00:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.InvoiceCardSettlement.MarkResolvedRefused')
;

UPDATE AD_Message SET ErrorCode='POS_CARD_SETTLEMENT_RESOLVE_REFUSED', Updated=TO_TIMESTAMP('2026-11-09 12:00:32','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545998
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545998
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='Not possible: the invoice is open and the card payment is not allocated.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545998
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545998
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545998
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545999 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-09 12:00:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Kartenzahlung {0} für Rechnung {1} wurde als Kundenguthaben gebucht – bitte erstatten oder zuordnen.','I',TO_TIMESTAMP('2026-11-09 12:00:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.InvoiceCardSettlement.CreditRefundPending')
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545999
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='Card payment {0} for invoice {1} was booked as customer credit — please refund or allocate.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545999
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545999
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545999
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,546000 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-09 12:00:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Kartenzahlung {0} für Rechnung {1} wurde an der Kasse als nicht belastet freigegeben – bitte mit SumUp abgleichen.','I',TO_TIMESTAMP('2026-11-09 12:00:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.InvoiceCardSettlement.ReleasedAsAbandoned')
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546000
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='Card payment {0} for invoice {1} was released at the till as not charged — please reconcile with SumUp.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=546000
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=546000
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 12:00:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=546000
;

-- Notification group for back-office recipients of card-settlement notifications.
INSERT INTO AD_NotificationGroup (AD_Client_ID,AD_NotificationGroup_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,Name,Updated,UpdatedBy)
VALUES (0,540028 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-09 12:00:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','de.metas.pos.InvoiceCardSettlement','Y','POS Rechnungs-Kartenzahlung',TO_TIMESTAMP('2026-11-09 12:00:47','YYYY-MM-DD HH24:MI:SS'),100)
;
