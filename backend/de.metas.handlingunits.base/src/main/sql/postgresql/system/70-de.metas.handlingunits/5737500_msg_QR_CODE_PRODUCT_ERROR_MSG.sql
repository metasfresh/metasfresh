-- Value: de.metas.handlingunits.picking.job.QR_CODE_PRODUCT_ERROR_MSG
-- 2024-10-22T06:23:09.227Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545477,0,TO_TIMESTAMP('2024-10-22 09:23:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Produkt passt nicht','E',TO_TIMESTAMP('2024-10-22 09:23:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.QR_CODE_PRODUCT_ERROR_MSG')
;

-- 2024-10-22T06:23:09.230Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545477 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.QR_CODE_PRODUCT_ERROR_MSG
-- 2024-10-22T06:23:11.628Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-22 09:23:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545477
;

-- Value: de.metas.handlingunits.picking.job.QR_CODE_PRODUCT_ERROR_MSG
-- 2024-10-22T06:23:19.105Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Product not matching',Updated=TO_TIMESTAMP('2024-10-22 09:23:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545477
;

-- Value: de.metas.handlingunits.picking.job.QR_CODE_PRODUCT_ERROR_MSG
-- 2024-10-22T06:23:21.701Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-22 09:23:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545477
;

