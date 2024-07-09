-- Run mode: SWING_CLIENT

-- SysConfig Name: de.metas.handlingunits.model.validator.M_InOut.allowReactivateOfReceiptWithHUAssigned
-- SysConfig Value: N
-- 2024-06-12T14:08:32.456Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541722,'S',TO_TIMESTAMP('2024-06-12 16:08:32.295','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits','Y','de.metas.handlingunits.model.validator.M_InOut.allowReactivateOfReceiptWithHUAssigned',TO_TIMESTAMP('2024-06-12 16:08:32.295','YYYY-MM-DD HH24:MI:SS.US'),100,'N')
;

-- Value: de.metas.handlingunits.model.validator.M_InOut.reactivateReceiptNotAllowedIfHUAssigned
-- 2024-06-12T14:10:16.555Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545420,0,TO_TIMESTAMP('2024-06-12 16:10:16.438','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits','Y','Das reaktivieren des Wareineingangs ist mit zugewiesenen HUs nicht erlaubt.','E',TO_TIMESTAMP('2024-06-12 16:10:16.438','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits.model.validator.M_InOut.reactivateReceiptNotAllowedIfHUAssigned')
;

-- 2024-06-12T14:10:16.557Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545420 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.model.validator.M_InOut.reactivateReceiptNotAllowedIfHUAssigned
-- 2024-06-12T14:11:05.572Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Reactivation of receipts with assigned HUs is not allowed.',Updated=TO_TIMESTAMP('2024-06-12 16:11:05.572','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545420
;

-- Value: de.metas.handlingunits.model.validator.M_InOut.reactivateReceiptNotAllowedIfHUAssigned
-- 2024-06-12T14:11:26.511Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-06-12 16:11:26.511','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545420
;

-- Value: de.metas.handlingunits.model.validator.M_InOut.reactivateReceiptNotAllowedIfHUAssigned
-- 2024-06-12T14:11:27.694Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-06-12 16:11:27.694','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545420
;

