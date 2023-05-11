-- Process: C_Order_AllocateToForexContract(de.metas.forex.webui.process.C_Order_AllocateToForexContract)
-- ParameterName: C_ForeignExchangeContract_ID
-- 2023-01-20T08:55:23.412Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2023-01-20 10:55:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542456
;

-- Process: C_Order_AllocateToForexContract(de.metas.forex.webui.process.C_Order_AllocateToForexContract)
-- ParameterName: Amount
-- 2023-01-20T08:55:49.213Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1367,0,585186,542457,12,'Amount',TO_TIMESTAMP('2023-01-20 10:55:49','YYYY-MM-DD HH24:MI:SS'),100,'Amount in a defined currency','D',0,'The Amount indicates the amount for this document line.','Y','N','Y','N','Y','N','Amount',20,TO_TIMESTAMP('2023-01-20 10:55:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-20T08:55:49.215Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542457 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Name: C_ForeignExchangeContract - COmpleted
-- 2023-01-20T11:18:37.081Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540616,'C_ForeignExchangeContract.DocStatus=''CO''',TO_TIMESTAMP('2023-01-20 13:18:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_ForeignExchangeContract - COmpleted','S',TO_TIMESTAMP('2023-01-20 13:18:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Process: C_Order_AllocateToForexContract(de.metas.forex.webui.process.C_Order_AllocateToForexContract)
-- ParameterName: C_ForeignExchangeContract_ID
-- 2023-01-20T11:18:47.059Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540616,Updated=TO_TIMESTAMP('2023-01-20 13:18:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542456
;

-- Name: C_ForeignExchangeContract - Eligible for Order allocation
-- 2023-01-20T11:19:37.093Z
UPDATE AD_Val_Rule SET Code='C_ForeignExchangeContract.DocStatus=''CO'' AND C_ForeignExchangeContract.FEC_Amount_Open > 0', Name='C_ForeignExchangeContract - Eligible for Order allocation',Updated=TO_TIMESTAMP('2023-01-20 13:19:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540616
;

