-- Kosten Neubewertung (cost revaluation): messages of the line quick-input and of a revaluation that copies a cost element.

-- M_CostRevaluation.QuickInputOnlyForManualSource: the quick-input adds a line with a typed new cost price, which the source "Copy from cost element" would ignore
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545890 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-01 19:50:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Bei der Neubewertungsquelle „Übernahme aus Kostenart“ werden die Zeilen mit „Neubewertungspositionen erstellen“ angelegt.','E',TO_TIMESTAMP('2026-10-01 19:50:00','YYYY-MM-DD HH24:MI:SS'),100,'M_CostRevaluation.QuickInputOnlyForManualSource');
UPDATE AD_Message SET ErrorCode='CostRevaluationQuickInputManualOnly', Updated=TO_TIMESTAMP('2026-10-01 19:50:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545890;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545890
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='With the revaluation source "Copy from cost element", the lines are created with "Create revaluation lines".',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-01 19:50:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545890;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-01 19:50:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545890;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-01 19:50:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545890;

-- M_CostRevaluation.NoSourceCostAsOfEvaluationStartDate: the source cost element has no cost for the product as of the evaluation start date
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545891 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-01 19:50:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die Quell-Kostenart hat für das Produkt {0} zum Startdatum der Bewertung keine Kosten.','E',TO_TIMESTAMP('2026-10-01 19:50:10','YYYY-MM-DD HH24:MI:SS'),100,'M_CostRevaluation.NoSourceCostAsOfEvaluationStartDate');
UPDATE AD_Message SET ErrorCode='CostRevaluationNoSourceCost', Updated=TO_TIMESTAMP('2026-10-01 19:50:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545891;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545891
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='The source cost element has no cost for product {0} as of the evaluation start date.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-01 19:50:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545891;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-01 19:50:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545891;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-01 19:50:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545891;
