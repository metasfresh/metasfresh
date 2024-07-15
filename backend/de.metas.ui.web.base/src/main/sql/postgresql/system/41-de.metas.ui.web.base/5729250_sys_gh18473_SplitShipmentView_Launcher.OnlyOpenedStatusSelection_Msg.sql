-- Run mode: SWING_CLIENT

-- Value: de.metas.ui.web.split_shipment.SplitShipmentView_Launcher.OnlyOpenedStatusSelection
-- 2024-07-15T13:55:53.716Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545433,0,TO_TIMESTAMP('2024-07-15 14:55:52.859','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Lieferdisposition ist geschlossen','E',TO_TIMESTAMP('2024-07-15 14:55:52.859','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.ui.web.split_shipment.SplitShipmentView_Launcher.OnlyOpenedStatusSelection')
;

-- 2024-07-15T13:55:53.725Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545433 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.ui.web.split_shipment.SplitShipmentView_Launcher.OnlyOpenedStatusSelection
-- 2024-07-15T13:56:14.501Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Shipment Disposition is closed',Updated=TO_TIMESTAMP('2024-07-15 14:56:14.501','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545433
;

