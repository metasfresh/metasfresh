-- 2024-08-08T11:50:44.475Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541728,'S',TO_TIMESTAMP('2024-08-08 11:50:44.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','PreventVoidingShipmentsWhenInvoiceExists',TO_TIMESTAMP('2024-08-08 11:50:44.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N')
;

-- Value: de.metas.inout.model.validator.M_InOut.PreventVoidingShipmentsWhenInvoiceExists
-- 2024-08-08T12:19:10.260Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545441,0,TO_TIMESTAMP('2024-08-08 12:19:10.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Diese Lieferschein kann nicht storniert werden weil es schon eine Rechnung gibt.','E',TO_TIMESTAMP('2024-08-08 12:19:10.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.inout.model.validator.M_InOut.PreventVoidingShipmentsWhenInvoiceExists')
;

-- 2024-08-08T12:19:10.267Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545441 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.inout.model.validator.M_InOut.PreventVoidingShipmentsWhenInvoiceExists
-- 2024-08-08T12:19:21.407Z
UPDATE AD_Message_Trl SET MsgText='This Shipment can not be voided because there is already an invoice.',Updated=TO_TIMESTAMP('2024-08-08 12:19:21.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545441
;
