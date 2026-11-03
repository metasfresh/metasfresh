-- Kosten Neubewertung (cost revaluation): a revaluation with a booked line cannot be voided.

-- M_CostRevaluation.CannotVoidBookedRevaluation
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545884 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-01 14:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Diese Kosten Neubewertung kann nicht mit „Stornieren“ aufgehoben werden, weil sie bereits gebucht wurde.','E',TO_TIMESTAMP('2026-10-01 14:00:00','YYYY-MM-DD HH24:MI:SS'),100,'M_CostRevaluation.CannotVoidBookedRevaluation');
UPDATE AD_Message SET ErrorCode='CostRevaluationCannotVoidBooked', Updated=TO_TIMESTAMP('2026-10-01 14:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545884;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545884
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='This cost revaluation cannot be voided because it has already been posted.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-01 14:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545884;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-01 14:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545884;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-01 14:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545884;
