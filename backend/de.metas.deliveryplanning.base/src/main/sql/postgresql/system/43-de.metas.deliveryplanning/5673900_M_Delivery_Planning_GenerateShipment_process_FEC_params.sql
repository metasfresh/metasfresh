-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: IsFEC
-- 2023-01-27T11:21:18.469Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,581947,0,585194,542476,20,'IsFEC',TO_TIMESTAMP('2023-01-27 13:21:18','YYYY-MM-DD HH24:MI:SS'),100,'1=2','D',0,'Y','N','Y','N','Y','N','FEC','1=1	',30,TO_TIMESTAMP('2023-01-27 13:21:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T11:21:18.470Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542476 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_Order_Currency_ID
-- 2023-01-27T11:21:56.360Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,581964,0,585194,542477,30,112,'FEC_Order_Currency_ID',TO_TIMESTAMP('2023-01-27 13:21:56','YYYY-MM-DD HH24:MI:SS'),100,'@IsFEC/N@=Y','D',0,'Y','N','Y','N','N','N','Order Currency','1=1',40,TO_TIMESTAMP('2023-01-27 13:21:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T11:21:56.362Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542477 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: C_ForeignExchangeContract_ID
-- 2023-01-27T11:22:27.052Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581935,0,585194,542478,30,'C_ForeignExchangeContract_ID',TO_TIMESTAMP('2023-01-27 13:22:26','YYYY-MM-DD HH24:MI:SS'),100,'@IsFEC/N@=Y','D',0,'Y','N','Y','N','N','N','Foreign Exchange Contract',50,TO_TIMESTAMP('2023-01-27 13:22:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T11:22:27.054Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542478 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_From_Currency_ID
-- 2023-01-27T11:22:47.452Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581967,0,585194,542479,30,112,'FEC_From_Currency_ID',TO_TIMESTAMP('2023-01-27 13:22:47','YYYY-MM-DD HH24:MI:SS'),100,'@IsFEC/N@=Y','D',0,'Y','N','Y','N','N','N','FEC Currency From',60,TO_TIMESTAMP('2023-01-27 13:22:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T11:22:47.454Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542479 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_To_Currency_ID
-- 2023-01-27T11:23:10.490Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581968,0,585194,542480,30,112,'FEC_To_Currency_ID',TO_TIMESTAMP('2023-01-27 13:23:10','YYYY-MM-DD HH24:MI:SS'),100,'@IsFEC/N@=Y','D',0,'Y','N','Y','N','N','N','FEC Currency To',70,TO_TIMESTAMP('2023-01-27 13:23:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T11:23:10.491Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542480 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

