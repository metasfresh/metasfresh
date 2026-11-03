-- Run mode: SWING_CLIENT

-- Value: de.metas.shipper.gateway.commons.config.NoShipperConfigFound
-- 2025-10-02T19:39:19.249Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545591,0,TO_TIMESTAMP('2025-10-02 19:39:19.077000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Keine Konfiguration für Spediteur gefunden: {}','I',TO_TIMESTAMP('2025-10-02 19:39:19.077000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.shipper.gateway.commons.config.NoShipperConfigFound')
;

-- 2025-10-02T19:39:19.251Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545591 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.shipper.gateway.commons.config.NoShipperConfigFound
-- 2025-10-02T19:39:31.060Z
UPDATE AD_Message_Trl SET MsgText='No configuration found for shipper: {}',Updated=TO_TIMESTAMP('2025-10-02 19:39:31.060000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545591
;

-- 2025-10-02T19:39:31.062Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.shipper.gateway.commons.config.NoShipperConfigFound
-- 2025-10-02T19:40:10.149Z
UPDATE AD_Message_Trl SET MsgText='No configuration found for shipper ID: {}',Updated=TO_TIMESTAMP('2025-10-02 19:40:10.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545591
;

-- 2025-10-02T19:40:10.150Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.shipper.gateway.commons.config.NoShipperConfigFound
-- 2025-10-02T19:40:16.111Z
UPDATE AD_Message_Trl SET MsgText='Keine Konfiguration für Spediteur-ID gefunden: {}',Updated=TO_TIMESTAMP('2025-10-02 19:40:16.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545591
;

-- 2025-10-02T19:40:16.112Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.shipper.gateway.commons.config.NoShipperConfigFound
-- 2025-10-02T19:40:17.320Z
UPDATE AD_Message_Trl SET MsgText='Keine Konfiguration für Spediteur-ID gefunden: {}',Updated=TO_TIMESTAMP('2025-10-02 19:40:17.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545591
;

-- 2025-10-02T19:40:17.321Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.shipper.gateway.commons.config.NoShipperConfigFound
-- 2025-10-02T19:40:19.095Z
UPDATE AD_Message_Trl SET MsgText='Keine Konfiguration für Spediteur-ID gefunden: {}',Updated=TO_TIMESTAMP('2025-10-02 19:40:19.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545591
;

-- 2025-10-02T19:40:19.096Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

