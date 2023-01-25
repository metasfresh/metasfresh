-- Value: de.metas.bpartner.service.impl.BPartnerStatsService.DeliveryInstruction.CreditLimitNotSuffiecient
-- 2023-01-25T15:39:40.944Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545230,0,TO_TIMESTAMP('2023-01-25 17:39:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Delivery Instruction could not be created due to insufficient credit limit.','E',TO_TIMESTAMP('2023-01-25 17:39:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.bpartner.service.impl.BPartnerStatsService.DeliveryInstruction.CreditLimitNotSuffiecient')
;

-- 2023-01-25T15:39:40.947Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545230 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.bpartner.service.impl.BPartnerStatsService.DeliveryInstruction.CreditLimitNotSufficient
-- 2023-01-25T15:40:57.030Z
UPDATE AD_Message SET Value='de.metas.bpartner.service.impl.BPartnerStatsService.DeliveryInstruction.CreditLimitNotSufficient',Updated=TO_TIMESTAMP('2023-01-25 17:40:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545230
;

-- Value: de.metas.shipping.api.impl.ShipperTransportationBL.CreditLimitNotSufficient
-- 2023-01-25T15:55:35.805Z
UPDATE AD_Message SET Value='de.metas.shipping.api.impl.ShipperTransportationBL.CreditLimitNotSufficient',Updated=TO_TIMESTAMP('2023-01-25 17:55:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545230
;



-- Value: de.metas.deliveryplanning.Event_CreditLimitNotSufficient
-- 2023-01-25T18:14:42.433Z
UPDATE AD_Message SET Value='de.metas.deliveryplanning.Event_CreditLimitNotSufficient',Updated=TO_TIMESTAMP('2023-01-25 20:14:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545230
;

