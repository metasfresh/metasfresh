-- Run mode: SWING_CLIENT

-- SysConfig Name: de.metas.handlingunits.picking.job_schedule.RequireCarrierProductSet
-- SysConfig Value: N
-- 2025-10-22T06:29:04.045Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541775,'S',TO_TIMESTAMP('2025-10-22 06:29:03.876000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','Y','de.metas.handlingunits.picking.job_schedule.RequireCarrierProductSet',TO_TIMESTAMP('2025-10-22 06:29:03.876000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N')
;

-- Value: de.metas.handlingunits.picking.job_schedule.CarrierProductNotSet
-- 2025-10-22T06:47:17.823Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545598,0,TO_TIMESTAMP('2025-10-22 06:47:17.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','Y','Bei Lieferdiposition mit Id {} ist kein Lieferweg-Produkt gesetzt. Bitte Traffic Management neu laden und gegebenfalls Status der Lieferweg-Abfrage prüfen.','E',TO_TIMESTAMP('2025-10-22 06:47:17.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits.picking.job_schedule.CarrierProductNotSet')
;

-- 2025-10-22T06:47:17.827Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545598 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job_schedule.CarrierProductNotSet
-- 2025-10-22T06:49:10.755Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='The Shipment Schedule with Id {} has no Carrier Product Set. Please reload the Traffic Management and  if applicable check Carrier Product Advise Status.',Updated=TO_TIMESTAMP('2025-10-22 06:49:10.755000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545598
;

-- 2025-10-22T06:49:10.756Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.handlingunits.picking.job_schedule.CarrierProductNotSet
-- 2025-10-22T06:49:11.550Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-22 06:49:11.550000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545598
;

-- Value: de.metas.handlingunits.picking.job_schedule.CarrierProductNotSet
-- 2025-10-22T06:49:12.685Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-22 06:49:12.685000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545598
;
