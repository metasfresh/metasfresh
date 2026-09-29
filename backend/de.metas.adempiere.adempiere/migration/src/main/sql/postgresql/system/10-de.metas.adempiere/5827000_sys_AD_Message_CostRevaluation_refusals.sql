-- Refusal messages shown when completing a Kosten Neubewertung (cost revaluation) that cannot be booked correctly.

-- M_CostRevaluation.StockMovementAfterDateAcct: the evaluation window contains a stock movement posted after the revaluation's posting date
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545878 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-29 16:30:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Für das Produkt {0} gibt es am {1} eine Lagerbewegung, also nach dem Buchungsdatum {2} dieser Kosten Neubewertung. Die Neubewertung würde diese Bewegung vor ihrem eigenen Datum neu bewerten. Bitte das Buchungsdatum auf den {1} oder später setzen.','E',TO_TIMESTAMP('2026-09-29 16:30:00','YYYY-MM-DD HH24:MI:SS'),100,'M_CostRevaluation.StockMovementAfterDateAcct');
UPDATE AD_Message SET ErrorCode='CostRevaluationMovementAfterDateAcct', Updated=TO_TIMESTAMP('2026-09-29 16:30:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545878;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545878
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='Product {0} has a stock movement on {1}, which is after the accounting date {2} of this cost revaluation. The revaluation would restate that movement before its own date. Please set the accounting date to {1} or later.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 16:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545878;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 16:30:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545878;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 16:30:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545878;

-- CostingMethodHandler.RevaluatingAnotherRevaluationIsNotSupported: the evaluation window contains an already posted cost revaluation of the same product
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545879 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-29 16:30:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Für das Produkt {0} wurde am {1} bereits eine Kosten Neubewertung gebucht, die ab dem Startdatum der Bewertung liegt. Eine Kosten Neubewertung kann keine andere neu bewerten. Bitte ein Startdatum der Bewertung nach dem {1} wählen.','E',TO_TIMESTAMP('2026-09-29 16:30:05','YYYY-MM-DD HH24:MI:SS'),100,'CostingMethodHandler.RevaluatingAnotherRevaluationIsNotSupported');
UPDATE AD_Message SET ErrorCode='CostRevaluationOfRevaluationNotSupported', Updated=TO_TIMESTAMP('2026-09-29 16:30:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545879;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545879
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='A cost revaluation was already posted for product {0} on {1}, on or after the evaluation start date. A cost revaluation cannot restate another one. Please choose an evaluation start date after {1}.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 16:30:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545879;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 16:30:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545879;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 16:30:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545879;
