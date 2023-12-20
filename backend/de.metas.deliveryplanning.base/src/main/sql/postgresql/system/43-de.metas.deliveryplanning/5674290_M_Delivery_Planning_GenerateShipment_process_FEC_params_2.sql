-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_CurrencyRate
-- 2023-01-30T13:44:40.652Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581963,0,585194,542504,22,'FEC_CurrencyRate',TO_TIMESTAMP('2023-01-30 15:44:40','YYYY-MM-DD HH24:MI:SS'),100,'@IsFEC/N@=Y','U',0,'Y','N','Y','N','N','N','FEC Currency Rate',80,TO_TIMESTAMP('2023-01-30 15:44:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-30T13:44:40.653Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542504 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_CurrencyRate
-- 2023-01-30T13:44:44.739Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2023-01-30 15:44:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542504
;

