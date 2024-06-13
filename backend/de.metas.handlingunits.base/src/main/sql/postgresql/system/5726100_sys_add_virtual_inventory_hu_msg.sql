-- Run mode: SWING_CLIENT

-- Value: de.metas.handlingunits.model.validator.M_InOut.notifyIfVirtualHUAssignedToShipment
-- 2024-06-12T09:34:54.111Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545419,0,TO_TIMESTAMP('2024-06-12 11:34:53.895','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits','Y','Die Lieferung mit der ID {0} hat über eine Inventur eine virtuelle Hu erzeugt, die nicht automatisch entfernt wird.','I',TO_TIMESTAMP('2024-06-12 11:34:53.895','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits.model.validator.M_InOut.notifyIfVirtualHUAssignedToShipment')
;

-- 2024-06-12T09:34:54.116Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545419 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.model.validator.M_InOut.notifyIfVirtualHUAssignedToShipment
-- 2024-06-12T09:35:25.881Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='The shipment with ID {0} did create an virtual hu via physical inventory, which is not automatically removed.',Updated=TO_TIMESTAMP('2024-06-12 11:35:25.881','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545419
;

-- Value: de.metas.handlingunits.model.validator.M_InOut.notifyIfVirtualHUAssignedToShipment
-- 2024-06-12T09:35:26.931Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-06-12 11:35:26.931','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545419
;

-- Value: de.metas.handlingunits.model.validator.M_InOut.notifyIfVirtualHUAssignedToShipment
-- 2024-06-12T09:35:28.269Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-06-12 11:35:28.269','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545419
;

