-- Run mode: SWING_CLIENT

-- 2024-06-14T12:36:43.420Z
UPDATE AD_Message_Trl trl SET MsgText='Die Lieferung {0} hat über eine Inventur eine virtuelle Hu erzeugt, die nicht automatisch entfernt wird.' WHERE AD_Message_ID=545419 AND AD_Language='de_DE'
;

-- Value: de.metas.handlingunits.model.validator.M_InOut.notifyIfVirtualHUAssignedToShipment
-- 2024-06-14T12:37:16.772Z
UPDATE AD_Message_Trl SET MsgText='Die Lieferung {0} hat über eine Inventur eine virtuelle Hu erzeugt, die nicht automatisch entfernt wird.',Updated=TO_TIMESTAMP('2024-06-14 14:37:16.772','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545419
;

-- Value: de.metas.handlingunits.model.validator.M_InOut.notifyIfVirtualHUAssignedToShipment
-- 2024-06-14T12:37:33.767Z
UPDATE AD_Message_Trl SET MsgText='The shipment {0} did create an virtual hu via physical inventory, which is not automatically removed.',Updated=TO_TIMESTAMP('2024-06-14 14:37:33.767','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545419
;

