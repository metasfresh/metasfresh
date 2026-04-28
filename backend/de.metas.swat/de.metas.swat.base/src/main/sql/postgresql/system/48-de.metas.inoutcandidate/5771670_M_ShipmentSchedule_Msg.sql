
-- Value: de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.NoValidRecords
-- 2025-09-29T14:07:30.845Z
UPDATE AD_Message_Trl SET MsgText='Keine Lieferungen können für die Auswahl erstellt werden. Die Positionen sind nicht versandbereit, da einer oder mehrere der folgenden Gründe zutreffen können: >> - Es ist nichts mehr zu liefern oder kein Bestand verfügbar. >> - Sie sind inaktiv, bereits verarbeitet oder geschlossen. >> - Die Kunden stehen auf Kreditstopp/-halt. >> - Das Bereitstellungs- oder Lieferdatum ist noch nicht erreicht.
',Updated=TO_TIMESTAMP('2025-09-29 14:07:30.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545570
;

-- 2025-09-29T14:07:30.845Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.NoValidRecords
-- 2025-09-29T14:07:46.515Z
UPDATE AD_Message_Trl SET MsgText='Keine Lieferungen können für die Auswahl erstellt werden. Die Positionen sind nicht versandbereit, da einer oder mehrere der folgenden Gründe zutreffen können: >> - Es ist nichts mehr zu liefern oder kein Bestand verfügbar. >> - Sie sind inaktiv, bereits verarbeitet oder geschlossen. >> - Die Kunden stehen auf Kreditstopp/-halt. >> - Das Bereitstellungs- oder Lieferdatum ist noch nicht erreicht.',Updated=TO_TIMESTAMP('2025-09-29 14:07:46.515000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545570
;

-- 2025-09-29T14:07:46.516Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.NoValidRecords
-- 2025-09-29T14:08:40.425Z
UPDATE AD_Message_Trl SET MsgText='No shipments can be created for the selection. The shipment schedules aren’t ready to ship because one or more of the following may apply: >>- There’s nothing left to deliver or no available stock.  >>- They are inactive, already processed, or closed.  >> - The customers are on credit stop/hold. >>- They are not yet due based on preparation date or promised date.',Updated=TO_TIMESTAMP('2025-09-29 14:08:40.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545570
;

-- 2025-09-29T14:08:40.426Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

