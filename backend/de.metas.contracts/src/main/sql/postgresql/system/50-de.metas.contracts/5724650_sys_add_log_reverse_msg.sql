-- Run mode: SWING_CLIENT

-- Value: de.metas.contracts.modular.workpackage.ModularContractLogHandlerHelper.ReverseLogDescription
-- 2024-05-28T10:50:32.302Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545413,0,TO_TIMESTAMP('2024-05-28 12:50:32.171','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','{0} für Produkt {1} mit der Menge {2} wurde storniert.','I',TO_TIMESTAMP('2024-05-28 12:50:32.171','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.workpackage.ModularContractLogHandlerHelper.ReverseLogDescription')
;

-- 2024-05-28T10:50:32.308Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545413 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.workpackage.ModularContractLogHandlerHelper.ReverseLogDescription
-- 2024-05-28T10:50:40.744Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-28 12:50:40.744','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545413
;

-- Value: de.metas.contracts.modular.workpackage.ModularContractLogHandlerHelper.ReverseLogDescription
-- 2024-05-28T10:51:06.706Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-28 12:51:06.706','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545413
;

-- Value: de.metas.contracts.modular.workpackage.ModularContractLogHandlerHelper.ReverseLogDescription
-- 2024-05-28T10:51:19.304Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='{0} for Product {1} with Quantity {2} was reversed.',Updated=TO_TIMESTAMP('2024-05-28 12:51:19.304','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545413
;

