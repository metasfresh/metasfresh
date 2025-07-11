-- Run mode: SWING_CLIENT

-- Value: de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.NoValidRecords
-- 2025-07-10T17:36:24.001Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545570,0,TO_TIMESTAMP('2025-07-10 17:36:23.870000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Für die Auswahl kann keine Lieferung erstellt werden. Prüfen Sie, ob Einschränkungen bezüglich des zugesagten Termins oder des Bereitstellungsdatums bestehen.','E',TO_TIMESTAMP('2025-07-10 17:36:23.870000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.NoValidRecords')
;

-- 2025-07-10T17:36:24.004Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545570 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.NoValidRecords
-- 2025-07-10T17:37:51.290Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='No shipments can be created for the selection. Check if any restrictions exist pertaining to ''Date Promised'' or ''Date of Provisioning''.',Updated=TO_TIMESTAMP('2025-07-10 17:37:51.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545570
;

-- 2025-07-10T17:37:51.291Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

