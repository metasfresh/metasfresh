-- Kosten Neubewertung (cost revaluation): a revaluation that copies a cost element (CopyFromCostElement) is corrected by Reverse, not by Void.

-- M_CostRevaluation.CopyFromCostElementCannotBeVoided
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545885 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-01 16:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Eine Kosten Neubewertung, die ein Kostenelement kopiert, kann nicht mit „Stornieren“ aufgehoben werden. Bitte stattdessen „Storno“ verwenden.','E',TO_TIMESTAMP('2026-10-01 16:00:00','YYYY-MM-DD HH24:MI:SS'),100,'M_CostRevaluation.CopyFromCostElementCannotBeVoided');
UPDATE AD_Message SET ErrorCode='CostRevaluationCopyFromCannotBeVoided', Updated=TO_TIMESTAMP('2026-10-01 16:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545885;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545885
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='A cost revaluation that copies a cost element cannot be voided. Please use "Reverse - Correct" instead.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-01 16:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545885;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-01 16:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545885;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-01 16:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545885;
