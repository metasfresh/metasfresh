-- 2024-05-14T06:41:36.705Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545397,0,TO_TIMESTAMP('2024-05-14 07:41:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Das Produkt kann nicht geändert werden, da der zugehörige Rechnungskandidaten verarbeitet wird','E',TO_TIMESTAMP('2024-05-14 07:41:36','YYYY-MM-DD HH24:MI:SS'),100,'C_OrderLine.onProductChanged.Msg_Error_Invoice_Candidate_Is_Processed')
;

-- 2024-05-14T06:41:36.710Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545397 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2024-05-14T06:41:54.939Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='The product cannot be changed because the related invoice candidate is processed',Updated=TO_TIMESTAMP('2024-05-14 07:41:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545397
;

