-- Run mode: SWING_CLIENT

-- Value: de.metas.handlingunits.inventory.InventoryService.MsgCreatedVirtualInventoryForShipmentSchedule
-- 2024-06-14T11:06:48.926Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545422,0,TO_TIMESTAMP('2024-06-14 13:06:48.69','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits','Y','Für die Lieferdisposition mit der ID {0} wurde eine virtuelle Hu via Inventur erzeugt.','I',TO_TIMESTAMP('2024-06-14 13:06:48.69','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits.inventory.InventoryService.MsgCreatedVirtualInventoryForShipmentSchedule')
;

-- 2024-06-14T11:06:48.932Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545422 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.inventory.InventoryService.MsgCreatedVirtualInventoryForShipmentSchedule
-- 2024-06-14T11:09:09.960Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='For Schipment Disposition with ID {0} a virtual Hu was created via Physical Inventory.',Updated=TO_TIMESTAMP('2024-06-14 13:09:09.96','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545422
;

-- Value: de.metas.handlingunits.inventory.InventoryService.MsgCreatedVirtualInventoryForShipmentSchedule
-- 2024-06-14T11:09:17.382Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-06-14 13:09:17.382','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545422
;

