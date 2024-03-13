-- Value: PaySelectionLine.CannotCreatePayment
-- 2024-03-13T09:01:41.821Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545376,0,TO_TIMESTAMP('2024-03-13 11:01:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment','Y','Die Zahlung in Zeile {0} konnte nicht erstellt werden.','E',TO_TIMESTAMP('2024-03-13 11:01:41','YYYY-MM-DD HH24:MI:SS'),100,'PaySelectionLine.CannotCreatePayment')
;

-- 2024-03-13T09:01:41.826Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545376 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: PaySelectionLine.CannotCreatePayment
-- 2024-03-13T09:04:32.145Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-13 11:04:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545376
;

-- Value: PaySelectionLine.CannotCreatePayment
-- 2024-03-13T09:04:35.068Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Failed to create payment at line {0} ',Updated=TO_TIMESTAMP('2024-03-13 11:04:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545376
;

-- Value: PaySelectionLine.CannotCreatePayment
-- 2024-03-13T09:04:37.143Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-13 11:04:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545376
;

