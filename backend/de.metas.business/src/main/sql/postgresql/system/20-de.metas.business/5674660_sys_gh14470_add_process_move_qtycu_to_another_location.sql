-- Value: WEBUI_M_HU_MoveCUQtyToAnotherLocator
-- 2023-01-31T15:18:25.145Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585206,'N','N',TO_TIMESTAMP('2023-01-31 16:18:25','YYYY-MM-DD HH24:MI:SS'),100,'Move CU Qty To Another Locator','de.metas.ui.web','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Move HU To Another Locator','json','N','N','xls','Java',TO_TIMESTAMP('2023-01-31 16:18:25','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_M_HU_MoveCUQtyToAnotherLocator')
;

-- 2023-01-31T15:18:25.147Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585206 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: WEBUI_M_HU_MoveCUQtyToAnotherLocator
-- 2023-01-31T15:18:35.195Z
UPDATE AD_Process SET Help='Move CU Qty To Another Locator',Updated=TO_TIMESTAMP('2023-01-31 16:18:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585206
;

-- 2023-01-31T15:18:35.196Z
UPDATE AD_Process_Trl trl SET Help='Move CU Qty To Another Locator' WHERE AD_Process_ID=585206 AND AD_Language='en_US'
;

-- Value: WEBUI_M_HU_MoveCUQtyToAnotherLocator
-- Classname: de.metas.ui.web.handlingunits.process.WEBUI_M_HU_MoveCUQtyToAnotherLocator
-- 2023-01-31T15:19:29.691Z
UPDATE AD_Process SET Classname='de.metas.ui.web.handlingunits.process.WEBUI_M_HU_MoveCUQtyToAnotherLocator',Updated=TO_TIMESTAMP('2023-01-31 16:19:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585206
;

-- Process: WEBUI_M_HU_MoveCUQtyToAnotherLocator(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_MoveCUQtyToAnotherLocator)
-- ParameterName: M_Warehouse_ID
-- 2023-01-31T15:21:33.377Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,459,0,585206,542512,17,540369,'M_Warehouse_ID',TO_TIMESTAMP('2023-01-31 16:21:33','YYYY-MM-DD HH24:MI:SS'),100,'Storage Warehouse and Service Point','de.metas.ui.web',0,'The Warehouse identifies a unique Warehouse where products are stored or Services are provided.','Y','N','Y','N','N','N','Warehouse',10,TO_TIMESTAMP('2023-01-31 16:21:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T15:21:33.380Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542512 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: WEBUI_M_HU_MoveCUQtyToAnotherLocator(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_MoveCUQtyToAnotherLocator)
-- ParameterName: M_Warehouse_ID
-- 2023-01-31T15:21:43.189Z
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-01-31 16:21:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542512
;

-- Process: WEBUI_M_HU_MoveCUQtyToAnotherLocator(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_MoveCUQtyToAnotherLocator)
-- ParameterName: M_Locator_ID
-- 2023-01-31T15:22:23.711Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,448,0,585206,542513,30,'M_Locator_ID',TO_TIMESTAMP('2023-01-31 16:22:23','YYYY-MM-DD HH24:MI:SS'),100,'Warehouse Locator','de.metas.ui.web',0,'The Locator indicates where in a Warehouse a product is located.','Y','N','Y','N','Y','N','Locator',20,TO_TIMESTAMP('2023-01-31 16:22:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T15:22:23.713Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542513 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: WEBUI_M_HU_MoveCUQtyToAnotherLocator(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_MoveCUQtyToAnotherLocator)
-- ParameterName: Qty
-- 2023-01-31T15:22:55.219Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,526,0,585206,542514,22,'Qty',TO_TIMESTAMP('2023-01-31 16:22:55','YYYY-MM-DD HH24:MI:SS'),100,'Quantity','de.metas.ui.web',0,'The Quantity indicates the number of a specific product or item for this document.','Y','N','Y','N','N','N','Quantity',30,TO_TIMESTAMP('2023-01-31 16:22:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T15:22:55.220Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542514 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: WEBUI_M_HU_MoveCUQtyToAnotherLocator(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_MoveCUQtyToAnotherLocator)
-- ParameterName: Qty
-- 2023-01-31T15:22:58.673Z
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-01-31 16:22:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542514
;

-- Process: WEBUI_M_HU_MoveCUQtyToAnotherLocator(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_MoveCUQtyToAnotherLocator)
-- Table: M_HU
-- EntityType: de.metas.ui.web
-- 2023-01-31T15:26:02.004Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585206,540516,541346,TO_TIMESTAMP('2023-01-31 16:26:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y',TO_TIMESTAMP('2023-01-31 16:26:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N')
;

-- Process: WEBUI_M_HU_MoveCUQtyToAnotherLocator(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_MoveCUQtyToAnotherLocator)
-- ParameterName: M_Warehouse_ID
-- 2023-01-31T15:57:39.604Z
UPDATE AD_Process_Para SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2023-01-31 16:57:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542512
;

