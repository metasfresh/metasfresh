-- Run mode: SWING_CLIENT

-- Value: de.metas.contracts.modular.interceptor.C_Order.HarvestingDetailsChangeNotAllowed_PurchaseOrder
-- 2023-10-23T09:54:43.027Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545354,0,TO_TIMESTAMP('2023-10-23 10:54:42.723','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Das Erntejahr kann nicht geändert werden, da der Auftrag Positionen enthält, die an Vertragsbedingungen gebunden sind.','E',TO_TIMESTAMP('2023-10-23 10:54:42.723','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.interceptor.C_Order.HarvestingDetailsChangeNotAllowed_PurchaseOrder')
;

-- 2023-10-23T09:54:43.035Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545354 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.interceptor.C_Order.HarvestingDetailsChangeNotAllowed_PurchaseOrder
-- 2023-10-23T09:54:59.122Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='The crop year cannot be changed because the sales order contains items subject to contract terms.',Updated=TO_TIMESTAMP('2023-10-23 10:54:59.122','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545354
;

