-- Process: WEBUI_M_HU_CreateReceipt_With_FEC_Param(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_With_FEC_Param)
-- ParameterName: FEC_Order_Currency_ID
-- 2023-01-27T12:31:00.639Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,581964,0,585188,542481,30,112,'FEC_Order_Currency_ID',TO_TIMESTAMP('2023-01-27 14:31:00','YYYY-MM-DD HH24:MI:SS'),100,'@IsFEC/N@=Y','de.metas.ui.web',0,'Y','N','Y','N','N','N','Order Currency','1=1',30,TO_TIMESTAMP('2023-01-27 14:31:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T12:31:00.645Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542481 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: WEBUI_M_HU_CreateReceipt_With_FEC_Param(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_With_FEC_Param)
-- ParameterName: FEC_Order_Currency_ID
-- 2023-01-27T12:31:41.275Z
UPDATE AD_Process_Para SET SeqNo=19,Updated=TO_TIMESTAMP('2023-01-27 14:31:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542481
;

-- Process: WEBUI_M_HU_CreateReceipt_With_FEC_Param(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_With_FEC_Param)
-- ParameterName: C_ForeignExchangeContract_ID
-- 2023-01-27T12:31:44.272Z
UPDATE AD_Process_Para SET SeqNo=30,Updated=TO_TIMESTAMP('2023-01-27 14:31:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542461
;

-- Process: WEBUI_M_HU_CreateReceipt_With_FEC_Param(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_With_FEC_Param)
-- ParameterName: FEC_Order_Currency_ID
-- 2023-01-27T12:31:47.220Z
UPDATE AD_Process_Para SET SeqNo=20,Updated=TO_TIMESTAMP('2023-01-27 14:31:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542481
;

-- Process: WEBUI_M_HU_CreateReceipt_With_FEC_Param(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_With_FEC_Param)
-- ParameterName: FEC_From_Currency_ID
-- 2023-01-27T12:32:14.237Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581967,0,585188,542482,30,112,'FEC_From_Currency_ID',TO_TIMESTAMP('2023-01-27 14:32:14','YYYY-MM-DD HH24:MI:SS'),100,'@IsFEC/N@=Y','de.metas.ui.web',0,'Y','N','Y','N','N','N','FEC Currency From',40,TO_TIMESTAMP('2023-01-27 14:32:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T12:32:14.239Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542482 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: WEBUI_M_HU_CreateReceipt_With_FEC_Param(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_With_FEC_Param)
-- ParameterName: FEC_To_Currency_ID
-- 2023-01-27T12:32:41.819Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581968,0,585188,542483,30,112,'FEC_To_Currency_ID',TO_TIMESTAMP('2023-01-27 14:32:41','YYYY-MM-DD HH24:MI:SS'),100,'@IsFEC/N@=Y','de.metas.ui.web',0,'Y','N','Y','N','N','N','FEC Currency To',50,TO_TIMESTAMP('2023-01-27 14:32:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T12:32:41.821Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542483 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: WEBUI_M_HU_CreateReceipt_With_FEC_Param(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_With_FEC_Param)
-- ParameterName: FEC_CurrencyRate
-- 2023-01-27T12:33:10.121Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581963,0,585188,542484,22,'FEC_CurrencyRate',TO_TIMESTAMP('2023-01-27 14:33:10','YYYY-MM-DD HH24:MI:SS'),100,'@IsFEC/N@=Y','de.metas.ui.web',0,'Y','N','Y','N','N','N','FEC Currency Rate',60,TO_TIMESTAMP('2023-01-27 14:33:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T12:33:10.122Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542484 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

