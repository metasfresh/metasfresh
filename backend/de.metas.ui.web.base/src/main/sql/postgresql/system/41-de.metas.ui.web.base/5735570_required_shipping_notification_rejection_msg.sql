-- Value: de.metas.ui.web.split_shipment.SplitShipmentView_Launcher.ShippingNotificationRequired
-- 2024-10-02T14:32:10.703Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545443,0,TO_TIMESTAMP('2024-10-02 16:32:10.549','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.ui.web','Y','Lieferavis erforderlich','E',TO_TIMESTAMP('2024-10-02 16:32:10.549','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.ui.web.split_shipment.SplitShipmentView_Launcher.ShippingNotificationRequired')
;

-- 2024-10-02T14:32:10.708Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545443 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.ui.web.split_shipment.SplitShipmentView_Launcher.ShippingNotificationRequired
-- 2024-10-02T14:33:05.285Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-02 16:33:05.285','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545443
;

-- Value: de.metas.ui.web.split_shipment.SplitShipmentView_Launcher.ShippingNotificationRequired
-- 2024-10-02T14:33:22.183Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-02 16:33:22.183','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545443
;

-- Value: de.metas.ui.web.split_shipment.SplitShipmentView_Launcher.ShippingNotificationRequired
-- 2024-10-02T14:33:36.101Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Shipment Notification required',Updated=TO_TIMESTAMP('2024-10-02 16:33:36.101','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545443
;

-- Value: de.metas.ui.web.shipmentschedule.process.M_ShipmentSchedule_EnqueueSelection.ShippingNotificationRequired
-- 2024-10-02T14:43:42.538Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545444,0,TO_TIMESTAMP('2024-10-02 16:43:42.411','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.ui.web','Y','Erforderliche Lieferavis fehlt bei mindestens einer Lieferposition','E',TO_TIMESTAMP('2024-10-02 16:43:42.411','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.ui.web.shipmentschedule.process.M_ShipmentSchedule_EnqueueSelection.ShippingNotificationRequired')
;

-- 2024-10-02T14:43:42.539Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545444 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.ui.web.shipmentschedule.process.M_ShipmentSchedule_EnqueueSelection.ShippingNotificationRequired
-- 2024-10-02T14:43:48.497Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-02 16:43:48.497','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545444
;

-- Value: de.metas.ui.web.shipmentschedule.process.M_ShipmentSchedule_EnqueueSelection.ShippingNotificationRequired
-- 2024-10-02T14:43:50.478Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-02 16:43:50.478','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545444
;

-- Value: de.metas.ui.web.shipmentschedule.process.M_ShipmentSchedule_EnqueueSelection.ShippingNotificationRequired
-- 2024-10-02T14:44:33.858Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Required Shipping Notification missing for at least one selected ',Updated=TO_TIMESTAMP('2024-10-02 16:44:33.858','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545444
;

