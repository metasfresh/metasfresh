-- Reason shown instead of the "close selection" action when nothing in the selection is closeable;
-- without it the action fails at execution with a bare "@NoSelection@", i.e. reads as a malfunction.
-- MsgType 'E' with an ErrorCode, same shape as AD_Message 545829.

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545832/*From ID Server*/,0,TO_TIMESTAMP('2026-09-09 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',
        'Keiner der ausgewählten Fertigungsaufträge ist fertiggestellt. Geschlossen werden können nur fertiggestellte Aufträge.',
        'E',TO_TIMESTAMP('2026-09-09 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'org.eevolution.process.PP_Order_CloseSelection.NoCompletedOrderInSelection');

UPDATE AD_Message SET ErrorCode='PPOrderNoCompletedOrderInSelection',
       Updated=TO_TIMESTAMP('2026-09-09 09:00:00.500000','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100
WHERE AD_Message_ID=545832;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545832
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);

UPDATE AD_Message_Trl SET MsgText='None of the selected manufacturing orders is completed. Only completed orders can be closed.',
       IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-09 09:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545832;

-- de_DE + de_CH already carry the base text, so only the flag flips
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-09 09:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545832;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-09 09:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545832;
