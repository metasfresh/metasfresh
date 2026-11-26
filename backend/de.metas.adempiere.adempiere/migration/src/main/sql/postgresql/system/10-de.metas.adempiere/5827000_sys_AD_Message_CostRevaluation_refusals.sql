-- Messages of the Kosten Neubewertung (cost revaluation); 545881 is deleted again by 5827280, 545879 gets a neutral text in 5827310.

-- CostingMethodHandler.RevaluatingAnotherRevaluationIsNotSupported
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545879 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-29 16:30:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Für das Produkt {0} gibt es bereits eine spätere Kosten Neubewertung vom {1}. Eine Kosten Neubewertung kann nicht vor oder am selben Tag wie eine andere Neubewertung desselben Produkts beginnen. Bitte ein späteres Startdatum der Bewertung wählen, nach dem {1}.','E',TO_TIMESTAMP('2026-09-29 16:30:05','YYYY-MM-DD HH24:MI:SS'),100,'CostingMethodHandler.RevaluatingAnotherRevaluationIsNotSupported');
UPDATE AD_Message SET ErrorCode='CostRevaluationOfRevaluationNotSupported', Updated=TO_TIMESTAMP('2026-09-29 16:30:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545879;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545879
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='Product {0} already has a later cost revaluation dated {1}. A cost revaluation cannot start before or on the same day as another revaluation of the same product. Please choose a later evaluation start date, after {1}.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 16:30:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545879;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 16:30:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545879;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 16:30:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545879;

-- M_CostRevaluation.EarlierRevaluationNotPosted: an earlier completed revaluation of the product is still waiting for its accounting
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545881 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-29 20:00:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Für das Produkt {0} ist die Kosten Neubewertung vom {1} fertiggestellt, aber noch nicht gebucht. Bitte warten, bis sie gebucht ist (oder ihren Buchungsfehler beheben), und dann erneut versuchen.','E',TO_TIMESTAMP('2026-09-29 20:00:03','YYYY-MM-DD HH24:MI:SS'),100,'M_CostRevaluation.EarlierRevaluationNotPosted');
UPDATE AD_Message SET ErrorCode='CostRevaluationEarlierNotPosted', Updated=TO_TIMESTAMP('2026-09-29 20:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545881;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545881
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='The cost revaluation of product {0} dated {1} is completed but not posted yet. Please wait until it is posted (or fix its posting error), then try again.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 20:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545881;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 20:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545881;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 20:00:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545881;
