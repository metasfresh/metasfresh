-- Run mode: SWING_CLIENT

-- Value: de.metas.pricing.ProductNotInPriceSystem
-- 2024-04-09T08:47:29.190Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545387,0,TO_TIMESTAMP('2024-04-09 11:47:29.033','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.pricing','Y','Kein Preis für das Produkt definiert: {} im Preissystem: {}','E',TO_TIMESTAMP('2024-04-09 11:47:29.033','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.pricing.ProductNotInPriceSystem')
;

-- 2024-04-09T08:47:29.202Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545387 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.pricing.ProductNotInPriceSystem
-- 2024-04-09T08:47:44.883Z
UPDATE AD_Message_Trl SET MsgText='No price defined for product: {} in pricing system: {}',Updated=TO_TIMESTAMP('2024-04-09 11:47:44.883','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545387
;

