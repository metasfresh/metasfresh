-- Run mode: SWING_CLIENT

-- Process: Historical_Invoices_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: DocType_Base
-- 2025-12-05T13:07:32.120Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,585485,543074,10,'DocType_Base',TO_TIMESTAMP('2025-12-05 13:07:31.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',10,'Y','N','Y','N','N','N','Dokument Basis Typ',90,TO_TIMESTAMP('2025-12-05 13:07:31.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-05T13:07:32.136Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543074 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Historical_Shipments_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: DocType_Base
-- 2025-12-05T13:12:48.880Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,585488,543075,10,'DocType_Base',TO_TIMESTAMP('2025-12-05 13:12:48.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',10,'Y','N','Y','N','N','N','Dokument Basis Typ',90,TO_TIMESTAMP('2025-12-05 13:12:48.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-05T13:12:48.882Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543075 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Value: Historical_Invoices_JSON
-- Classname: de.metas.postgrest.process.PostgRESTProcessExecutor
-- 2025-12-05T13:11:33.195Z
UPDATE AD_Process SET JSONPath='historical_invoices_json_v?and=(ExternalSystemCode.ilike.@ExternalSystemCode/%%@,or(Updated.gte.@UpdatedGE/9999-01-01T00:00:00@,ExternalId.eq.@ExternalId/-1@,Order_ID.eq.@Order_ID/-1@,BPartnerValue.eq.@BPartnerValue/-1@,Invoice_Date.gte.@DateInvoicedGE/9999-01-01T00:00:00@,DocType_Base.eq.@DocType_Base/-1@,and(BPartnerExternalSystemValue.eq.@BPartnerExternalSystemValue/-1@,BPartnerExternalReference.eq.@BPartnerExternalReference/-1@)))&limit=@Limit/2000@&offset=@Offset/0@',Updated=TO_TIMESTAMP('2025-12-05 13:11:33.194000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585485
;

-- Value: Historical_Shipments_JSON
-- Classname: de.metas.postgrest.process.PostgRESTProcessExecutor
-- 2025-12-05T13:13:07.766Z
UPDATE AD_Process SET JSONPath='historical_m_inout_json_v?and=(ExternalSystemCode.ilike.@ExternalSystemCode/%%@,or(Updated.gte.@UpdatedGE/9999-01-01T00:00:00@,ExternalId.eq.@ExternalId/-1@,Order_ID.eq.@Order_ID/-1@,BPartnerValue.eq.@BPartnerValue/-1@,DocType_Base.eq.@DocType_Base/-1@,Shipment_Date.gte.@ShipmentDateGE/9999-01-01T00:00:00@,and(BPartnerExternalSystemValue.eq.@BPartnerExternalSystemValue/-1@,BPartnerExternalReference.eq.@BPartnerExternalReference/-1@)))&limit=@Limit/2000@&offset=@Offset/0@',Updated=TO_TIMESTAMP('2025-12-05 13:13:07.765000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585488
;
