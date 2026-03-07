-- Run mode: SWING_CLIENT

-- Process: Available_For_Sales_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: ProductValue
-- 2025-11-10T10:38:16.884Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1675,0,585498,543029,10,'ProductValue',TO_TIMESTAMP('2025-11-10 10:38:16.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID','D',255,'Y','N','Y','N','N','N','Produktschlüssel',40,TO_TIMESTAMP('2025-11-10 10:38:16.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-10T10:38:16.896Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543029 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Run mode: SWING_CLIENT

-- Process: Available_For_Sales_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: ProductValue
-- 2025-11-10T15:01:06.892Z
UPDATE AD_Process_Para SET AD_Element_ID=NULL,Updated=TO_TIMESTAMP('2025-11-10 15:01:06.892000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543029
;

-- Process: Available_For_Sales_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: ProductValue
-- 2025-11-10T15:04:33.803Z
UPDATE AD_Process_Para SET Description='',Updated=TO_TIMESTAMP('2025-11-10 15:04:33.803000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543029
;

-- 2025-11-10T15:04:33.803Z
UPDATE AD_Process_Para_Trl trl SET Description='' WHERE AD_Process_Para_ID=543029 AND AD_Language='de_DE'
;

-- 2025-11-10T15:04:40.325Z
UPDATE AD_Process_Para_Trl SET Description='',Updated=TO_TIMESTAMP('2025-11-10 15:04:40.325000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_Para_ID=543029
;

-- 2025-11-10T15:04:40.325Z
UPDATE AD_Process_Para base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Para_Trl trl  WHERE trl.AD_Process_Para_ID=base.AD_Process_Para_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-10T15:04:42.698Z
UPDATE AD_Process_Para_Trl SET Description='',Updated=TO_TIMESTAMP('2025-11-10 15:04:42.698000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=543029
;

-- 2025-11-10T15:04:42.698Z
UPDATE AD_Process_Para base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Para_Trl trl  WHERE trl.AD_Process_Para_ID=base.AD_Process_Para_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-10T15:04:45.463Z
UPDATE AD_Process_Para_Trl SET Description='',Updated=TO_TIMESTAMP('2025-11-10 15:04:45.463000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_Para_ID=543029
;

-- 2025-11-10T15:04:45.463Z
UPDATE AD_Process_Para base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Para_Trl trl  WHERE trl.AD_Process_Para_ID=base.AD_Process_Para_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Process: Available_For_Sales_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: ProductExternalReference
-- 2025-11-10T15:06:51.997Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,585498,543030,10,'ProductExternalReference',TO_TIMESTAMP('2025-11-10 15:06:51.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',255,'Y','N','Y','N','N','N','Produkt Externe Referenz',50,TO_TIMESTAMP('2025-11-10 15:06:51.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-10T15:06:52.004Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543030 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2025-11-10T15:07:09.573Z
UPDATE AD_Process_Para_Trl SET Name='Product External Reference',Updated=TO_TIMESTAMP('2025-11-10 15:07:09.557000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=543030
;

-- 2025-11-10T15:07:09.573Z
UPDATE AD_Process_Para base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Para_Trl trl  WHERE trl.AD_Process_Para_ID=base.AD_Process_Para_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: Available_For_Sales_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: ProductValue
-- 2025-11-10T15:07:37.043Z
UPDATE AD_Process_Para SET SeqNo=16,Updated=TO_TIMESTAMP('2025-11-10 15:07:37.043000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543029
;

-- Process: Available_For_Sales_JSON(de.metas.postgrest.process.PostgRESTProcessExecutor)
-- ParameterName: ProductExternalReference
-- 2025-11-10T15:07:41.910Z
UPDATE AD_Process_Para SET SeqNo=17,Updated=TO_TIMESTAMP('2025-11-10 15:07:41.910000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543030
;

-- Value: Available_For_Sales_JSON
-- Classname: de.metas.postgrest.process.PostgRESTProcessExecutor
-- 2025-11-10T15:29:31.706Z
UPDATE AD_Process SET JSONPath='/available_for_sales_json_v?ExternalSystem=ilike.@ExternalSystem/%@&WarehouseCode=ilike.@WarehouseCode/%@&ProductValue=ilike.@ProductValue/%@&ProductExternalReference=ilike.@ProductExternalReference/%@&limit=@Limit/2000@&offset=@Offset/0@',Updated=TO_TIMESTAMP('2025-11-10 15:29:31.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585498
;
