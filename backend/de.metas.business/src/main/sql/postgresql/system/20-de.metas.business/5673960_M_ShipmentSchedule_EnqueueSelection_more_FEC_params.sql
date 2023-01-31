-- Process: M_ShipmentSchedule_EnqueueSelection(de.metas.ui.web.shipmentschedule.process.M_ShipmentSchedule_EnqueueSelection)
-- ParameterName: C_ForeignExchangeContract_ID
-- 2023-01-27T12:50:52.521Z
UPDATE AD_Process_Para SET SeqNo=60,Updated=TO_TIMESTAMP('2023-01-27 14:50:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542463
;

-- Process: M_ShipmentSchedule_EnqueueSelection(de.metas.ui.web.shipmentschedule.process.M_ShipmentSchedule_EnqueueSelection)
-- ParameterName: FEC_Order_Currency_ID
-- 2023-01-27T12:51:22.426Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,581964,0,540458,542485,30,112,'FEC_Order_Currency_ID',TO_TIMESTAMP('2023-01-27 14:51:22','YYYY-MM-DD HH24:MI:SS'),100,'@IsFEC/N@=Y','de.metas.handlingunits',0,'Y','N','Y','N','N','N','Order Currency','1=1',70,TO_TIMESTAMP('2023-01-27 14:51:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T12:51:22.427Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542485 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_ShipmentSchedule_EnqueueSelection(de.metas.ui.web.shipmentschedule.process.M_ShipmentSchedule_EnqueueSelection)
-- ParameterName: FEC_Order_Currency_ID
-- 2023-01-27T12:51:35.895Z
UPDATE AD_Process_Para SET SeqNo=50,Updated=TO_TIMESTAMP('2023-01-27 14:51:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542485
;

-- Process: M_ShipmentSchedule_EnqueueSelection(de.metas.ui.web.shipmentschedule.process.M_ShipmentSchedule_EnqueueSelection)
-- ParameterName: FEC_From_Currency_ID
-- 2023-01-27T12:51:55.762Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581967,0,540458,542486,30,112,'FEC_From_Currency_ID',TO_TIMESTAMP('2023-01-27 14:51:55','YYYY-MM-DD HH24:MI:SS'),100,'@IsFEC/N@=Y','de.metas.handlingunits',0,'Y','N','Y','N','N','N','FEC Currency From',70,TO_TIMESTAMP('2023-01-27 14:51:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T12:51:55.764Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542486 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_ShipmentSchedule_EnqueueSelection(de.metas.ui.web.shipmentschedule.process.M_ShipmentSchedule_EnqueueSelection)
-- ParameterName: FEC_To_Currency_ID
-- 2023-01-27T12:52:35.324Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581968,0,540458,542488,30,112,'FEC_To_Currency_ID',TO_TIMESTAMP('2023-01-27 14:52:35','YYYY-MM-DD HH24:MI:SS'),100,'@IsFEC/N@=Y','de.metas.handlingunits',0,'Y','N','Y','N','N','N','FEC Currency To',80,TO_TIMESTAMP('2023-01-27 14:52:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T12:52:35.326Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542488 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_ShipmentSchedule_EnqueueSelection(de.metas.ui.web.shipmentschedule.process.M_ShipmentSchedule_EnqueueSelection)
-- ParameterName: FEC_CurrencyRate
-- 2023-01-27T12:53:02.053Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581963,0,540458,542489,22,'FEC_CurrencyRate',TO_TIMESTAMP('2023-01-27 14:53:01','YYYY-MM-DD HH24:MI:SS'),100,'@IsFEC/N@=Y','de.metas.handlingunits',0,'Y','N','Y','N','N','N','FEC Currency Rate',90,TO_TIMESTAMP('2023-01-27 14:53:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T12:53:02.054Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542489 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

