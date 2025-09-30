-- Run mode: SWING_CLIENT

-- Process: Available_For_Sales_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: WarehouseValue
-- 2025-09-29T12:33:01.189Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2070,0,585498,543002,10,'WarehouseValue',TO_TIMESTAMP('2025-09-29 12:33:00.879000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Suchschlüssel des Lagers','D',40,'Schlüssel zur Identifikation des Lagers.','Y','N','Y','N','N','N','Lager-Schlüssel',15,TO_TIMESTAMP('2025-09-29 12:33:00.879000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-29T12:33:01.189Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543002 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Value: Available_For_Sales_JSON
-- Classname: de.metas.postgrest.process.PostgRESTProcessExecutor
-- 2025-09-29T12:35:38.670Z
UPDATE AD_Process SET JSONPath='/available_for_sales_json_v?ExternalSystem=ilike.@ExternalSystem/%@&WarehouseValue=ilike.@WarehouseValue/%@&limit=@Limit/2000@&offset=@Offset/0@',Updated=TO_TIMESTAMP('2025-09-29 12:35:38.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585498
;
