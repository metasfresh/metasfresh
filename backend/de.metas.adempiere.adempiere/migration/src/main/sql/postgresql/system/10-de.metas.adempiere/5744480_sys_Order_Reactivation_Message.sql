-- Value: ERR_NoReactivationIfNotVoidedNotReversedShipments
-- 2025-01-23T11:15:59.344Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545490,0,TO_TIMESTAMP('2025-01-23 11:15:59.089000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Before reactivating the sales order, the shipments scheduled for it must be canceled: {0}','E',TO_TIMESTAMP('2025-01-23 11:15:59.089000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ERR_NoReactivationIfNotVoidedNotReversedShipments')
;

-- 2025-01-23T11:15:59.350Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545490 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ERR_NoReactivationIfNotVoidedNotReversedShipments
-- 2025-01-23T11:16:12.059Z
UPDATE AD_Message_Trl SET MsgText='Bevor der Auftrag reaktiviert werden kann, müssen die dafür geplanten Lieferungen storniert werden: {0}',Updated=TO_TIMESTAMP('2025-01-23 11:16:12.059000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545490
;

-- Value: ERR_NoReactivationIfNotVoidedNotReversedShipments
-- 2025-01-23T11:16:17.752Z
UPDATE AD_Message_Trl SET MsgText='Bevor der Auftrag reaktiviert werden kann, müssen die dafür geplanten Lieferungen storniert werden: {0}',Updated=TO_TIMESTAMP('2025-01-23 11:16:17.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545490
;

-- 2025-01-23T11:16:17.760Z
UPDATE AD_Message SET MsgText='Bevor der Auftrag reaktiviert werden kann, müssen die dafür geplanten Lieferungen storniert werden: {0}', Updated=TO_TIMESTAMP('2025-01-23 11:16:17.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC' WHERE AD_Message_ID=545490
;

