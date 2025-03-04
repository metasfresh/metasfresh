-- Value: C_Order_CreateCost
-- Classname: de.metas.ui.web.order.costs.C_Order_CreateCost
-- 2023-02-07T07:29:14.159Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585214,'Y','de.metas.ui.web.order.costs.C_Order_CreateCost','N',TO_TIMESTAMP('2023-02-07 09:29:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Add costs','json','N','N','xls','Java',TO_TIMESTAMP('2023-02-07 09:29:13','YYYY-MM-DD HH24:MI:SS'),100,'C_Order_CreateCost')
;

-- 2023-02-07T07:29:14.163Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585214 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Order_CreateCost(de.metas.ui.web.order.costs.C_Order_CreateCost)
-- Table: C_Order
-- EntityType: D
-- 2023-02-07T07:29:29.270Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585214,259,541356,TO_TIMESTAMP('2023-02-07 09:29:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-02-07 09:29:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Process: C_Order_CreateCost(de.metas.ui.web.order.costs.C_Order_CreateCost)
-- Table: C_Order
-- EntityType: D
-- 2023-02-07T07:29:36.953Z
UPDATE AD_Table_Process SET WEBUI_DocumentAction='N', WEBUI_ViewAction='N',Updated=TO_TIMESTAMP('2023-02-07 09:29:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541356
;

-- Process: C_Order_CreateCost(de.metas.ui.web.order.costs.C_Order_CreateCost)
-- ParameterName: C_Cost_Type_ID
-- 2023-02-07T07:29:57.778Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582023,0,585214,542533,30,'C_Cost_Type_ID',TO_TIMESTAMP('2023-02-07 09:29:57','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','Y','N','Cost Type',10,TO_TIMESTAMP('2023-02-07 09:29:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T07:29:57.782Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542533 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_Order_CreateCost(de.metas.ui.web.order.costs.C_Order_CreateCost)
-- ParameterName: CostCalculationMethod
-- 2023-02-07T07:30:41.774Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,582025,0,585214,542534,17,541713,'CostCalculationMethod',TO_TIMESTAMP('2023-02-07 09:30:41','YYYY-MM-DD HH24:MI:SS'),100,'1=2','D',0,'Y','N','Y','N','Y','N','Calculation Method','1=1',20,TO_TIMESTAMP('2023-02-07 09:30:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T07:30:41.775Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542534 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_Order_CreateCost(de.metas.ui.web.order.costs.C_Order_CreateCost)
-- ParameterName: Amount
-- 2023-02-07T07:31:48.517Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,DisplayLogic,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1367,0,585214,542535,12,'Amount',TO_TIMESTAMP('2023-02-07 09:31:48','YYYY-MM-DD HH24:MI:SS'),100,'Amount in a defined currency','@CostCalculationMethod/-@=F','D',0,'The Amount indicates the amount for this document line.','Y','N','Y','N','N','N','Amount',30,TO_TIMESTAMP('2023-02-07 09:31:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T07:31:48.520Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542535 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_Order_CreateCost(de.metas.ui.web.order.costs.C_Order_CreateCost)
-- ParameterName: Percentage
-- 2023-02-07T07:32:25.557Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,DisplayLogic,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2004,0,585214,542536,22,'Percentage',TO_TIMESTAMP('2023-02-07 09:32:25','YYYY-MM-DD HH24:MI:SS'),100,'Percent of the entire amount','@CostCalculationMethod/-@=P','D',0,'Percentage of an amount (up to 100)','Y','N','Y','N','N','N','Percentage',40,TO_TIMESTAMP('2023-02-07 09:32:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T07:32:25.559Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542536 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

