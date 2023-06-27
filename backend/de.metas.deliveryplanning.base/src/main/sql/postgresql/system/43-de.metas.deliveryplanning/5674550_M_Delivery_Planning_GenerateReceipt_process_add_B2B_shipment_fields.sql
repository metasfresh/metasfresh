-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: IsB2B
-- 2023-01-31T11:53:59.962Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,581680,0,585194,542505,20,'IsB2B',TO_TIMESTAMP('2023-01-31 13:53:59','YYYY-MM-DD HH24:MI:SS'),100,'N','@IsB2B/N@=Y','D',0,'Y','N','Y','N','Y','N','B2B','1=1',90,TO_TIMESTAMP('2023-01-31 13:53:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T11:53:59.967Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542505 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-01-31T12:12:20.715Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581973,0,'IsShipmentFEC',TO_TIMESTAMP('2023-01-31 14:12:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Shipment FEC','Shipment FEC',TO_TIMESTAMP('2023-01-31 14:12:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T12:12:20.717Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581973 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-01-31T12:13:11.835Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581974,0,'FEC_Shipment_ForeignExchangeContract_ID',TO_TIMESTAMP('2023-01-31 14:13:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Shipment Foreign Exchange Contract','Shipment Foreign Exchange Contract',TO_TIMESTAMP('2023-01-31 14:13:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T12:13:11.838Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581974 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-01-31T12:13:50.014Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581975,0,'FEC_SalesOrder_Currency_ID',TO_TIMESTAMP('2023-01-31 14:13:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Sales Order Currency','Sales Order Currency',TO_TIMESTAMP('2023-01-31 14:13:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T12:13:50.016Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581975 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-01-31T12:15:21.042Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581976,0,'FEC_Shipment_From_Currency_ID',TO_TIMESTAMP('2023-01-31 14:15:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','FEC Shipment Currency From','FEC Shipment Currency From',TO_TIMESTAMP('2023-01-31 14:15:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T12:15:21.044Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581976 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-01-31T12:15:49.833Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581977,0,'FEC_Shipment_To_Currency_ID',TO_TIMESTAMP('2023-01-31 14:15:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','FEC Shipment Currency To','FEC Shipment Currency To',TO_TIMESTAMP('2023-01-31 14:15:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T12:15:49.836Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581977 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-01-31T12:16:25.301Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581978,0,'FEC_Shipment_CurrencyRate',TO_TIMESTAMP('2023-01-31 14:16:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','FEC Shipment Currency Rate','FEC Shipment Currency Rate',TO_TIMESTAMP('2023-01-31 14:16:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T12:16:25.304Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581978 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: IsShipmentFEC
-- 2023-01-31T12:17:19.263Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,581973,0,585194,542506,20,'IsShipmentFEC',TO_TIMESTAMP('2023-01-31 14:17:19','YYYY-MM-DD HH24:MI:SS'),100,'N','1=2','D',0,'Y','N','Y','N','Y','N','Shipment FEC','1=1',100,TO_TIMESTAMP('2023-01-31 14:17:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T12:17:19.266Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542506 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Name: C_ForeignExchangeContract
-- 2023-01-31T12:18:36.689Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541709,TO_TIMESTAMP('2023-01-31 14:18:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_ForeignExchangeContract',TO_TIMESTAMP('2023-01-31 14:18:36','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-01-31T12:18:36.691Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541709 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_ForeignExchangeContract
-- Table: C_ForeignExchangeContract
-- Key: C_ForeignExchangeContract.C_ForeignExchangeContract_ID
-- 2023-01-31T12:18:52.305Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,585537,0,541709,542281,TO_TIMESTAMP('2023-01-31 14:18:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2023-01-31 14:18:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_Shipment_ForeignExchangeContract_ID
-- 2023-01-31T12:19:28.395Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,581974,0,585194,542507,30,541709,'FEC_Shipment_ForeignExchangeContract_ID',TO_TIMESTAMP('2023-01-31 14:19:28','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','N','N','Shipment Foreign Exchange Contract','',110,TO_TIMESTAMP('2023-01-31 14:19:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T12:19:28.399Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542507 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_Shipment_ForeignExchangeContract_ID
-- 2023-01-31T12:20:00.457Z
UPDATE AD_Process_Para SET DisplayLogic='@IsShipmentFEC/N@=Y',Updated=TO_TIMESTAMP('2023-01-31 14:20:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542507
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_Shipment_ForeignExchangeContract_ID
-- 2023-01-31T12:20:05.562Z
UPDATE AD_Process_Para SET SeqNo=120,Updated=TO_TIMESTAMP('2023-01-31 14:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542507
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_SalesOrder_Currency_ID
-- 2023-01-31T12:20:41.889Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,581975,0,585194,542508,30,112,'FEC_SalesOrder_Currency_ID',TO_TIMESTAMP('2023-01-31 14:20:41','YYYY-MM-DD HH24:MI:SS'),100,'@IsShipmentFEC/N@=Y','U',0,'Y','N','Y','N','N','N','Sales Order Currency','1=1',110,TO_TIMESTAMP('2023-01-31 14:20:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T12:20:41.892Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542508 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_SalesOrder_Currency_ID
-- 2023-01-31T12:20:49.405Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2023-01-31 14:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542508
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_Shipment_From_Currency_ID
-- 2023-01-31T12:21:33.389Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581976,0,585194,542509,30,112,'FEC_Shipment_From_Currency_ID',TO_TIMESTAMP('2023-01-31 14:21:33','YYYY-MM-DD HH24:MI:SS'),100,'','D',0,'Y','N','Y','N','N','N','FEC Shipment Currency From',130,TO_TIMESTAMP('2023-01-31 14:21:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T12:21:33.391Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542509 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_Shipment_From_Currency_ID
-- 2023-01-31T12:21:37.489Z
UPDATE AD_Process_Para SET DisplayLogic='@IsShipmentFEC/N@=Y',Updated=TO_TIMESTAMP('2023-01-31 14:21:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542509
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_Shipment_To_Currency_ID
-- 2023-01-31T12:22:09.784Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581977,0,585194,542510,30,112,'FEC_Shipment_To_Currency_ID',TO_TIMESTAMP('2023-01-31 14:22:09','YYYY-MM-DD HH24:MI:SS'),100,'@IsShipmentFEC/N@=Y','D',0,'Y','N','Y','N','N','N','FEC Shipment Currency To',140,TO_TIMESTAMP('2023-01-31 14:22:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T12:22:09.786Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542510 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_Shipment_CurrencyRate
-- 2023-01-31T12:22:36.023Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581978,0,585194,542511,22,'FEC_Shipment_CurrencyRate',TO_TIMESTAMP('2023-01-31 14:22:35','YYYY-MM-DD HH24:MI:SS'),100,'@IsShipmentFEC/N@=Y','D',0,'Y','N','Y','N','N','N','FEC Shipment Currency Rate',150,TO_TIMESTAMP('2023-01-31 14:22:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T12:22:36.025Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542511 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-01-31T15:32:37.683Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581980,0,'IsGenerateB2BShipment',TO_TIMESTAMP('2023-01-31 17:32:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Generate B2B Shipment','Generate B2B Shipment',TO_TIMESTAMP('2023-01-31 17:32:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T15:32:37.685Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581980 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: IsGenerateB2BShipment
-- 2023-01-31T15:32:56.588Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581980,0,585194,542515,20,'IsGenerateB2BShipment',TO_TIMESTAMP('2023-01-31 17:32:56','YYYY-MM-DD HH24:MI:SS'),100,'N','U',0,'Y','N','Y','N','Y','N','Generate B2B Shipment',95,TO_TIMESTAMP('2023-01-31 17:32:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T15:32:56.590Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542515 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: IsB2B
-- 2023-01-31T15:33:19.247Z
UPDATE AD_Process_Para SET DisplayLogic='1=2',Updated=TO_TIMESTAMP('2023-01-31 17:33:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542505
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: IsGenerateB2BShipment
-- 2023-01-31T15:33:25.860Z
UPDATE AD_Process_Para SET DisplayLogic='@IsB2B/N@=Y',Updated=TO_TIMESTAMP('2023-01-31 17:33:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542515
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: IsGenerateB2BShipment
-- 2023-01-31T15:33:29.725Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2023-01-31 17:33:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542515
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_SalesOrder_Currency_ID
-- 2023-01-31T15:39:05.765Z
UPDATE AD_Process_Para SET DisplayLogic='@IsGenerateB2BShipment/N@=Y & @IsShipmentFEC/N@=Y',Updated=TO_TIMESTAMP('2023-01-31 17:39:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542508
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_Shipment_ForeignExchangeContract_ID
-- 2023-01-31T15:39:08.286Z
UPDATE AD_Process_Para SET DisplayLogic='@IsGenerateB2BShipment/N@=Y & @IsShipmentFEC/N@=Y',Updated=TO_TIMESTAMP('2023-01-31 17:39:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542507
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_Shipment_From_Currency_ID
-- 2023-01-31T15:39:10.642Z
UPDATE AD_Process_Para SET DisplayLogic='@IsGenerateB2BShipment/N@=Y & @IsShipmentFEC/N@=Y',Updated=TO_TIMESTAMP('2023-01-31 17:39:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542509
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_Shipment_To_Currency_ID
-- 2023-01-31T15:39:13.398Z
UPDATE AD_Process_Para SET DisplayLogic='@IsGenerateB2BShipment/N@=Y & @IsShipmentFEC/N@=Y',Updated=TO_TIMESTAMP('2023-01-31 17:39:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542510
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_Shipment_CurrencyRate
-- 2023-01-31T15:39:17.477Z
UPDATE AD_Process_Para SET DisplayLogic='@IsGenerateB2BShipment/N@=Y & @IsShipmentFEC/N@=Y',Updated=TO_TIMESTAMP('2023-01-31 17:39:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542511
;
















-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: IsB2B
-- 2023-02-01T06:56:00.075Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542505
;

-- 2023-02-01T06:56:00.090Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542505
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: IsGenerateB2BShipment
-- 2023-02-01T06:56:00.549Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542515
;

-- 2023-02-01T06:56:00.554Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542515
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: IsShipmentFEC
-- 2023-02-01T06:56:00.602Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542506
;

-- 2023-02-01T06:56:00.607Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542506
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_SalesOrder_Currency_ID
-- 2023-02-01T06:56:00.639Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542508
;

-- 2023-02-01T06:56:00.643Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542508
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_Shipment_ForeignExchangeContract_ID
-- 2023-02-01T06:56:00.674Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542507
;

-- 2023-02-01T06:56:00.679Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542507
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_Shipment_From_Currency_ID
-- 2023-02-01T06:56:00.705Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542509
;

-- 2023-02-01T06:56:00.709Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542509
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_Shipment_To_Currency_ID
-- 2023-02-01T06:56:00.734Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542510
;

-- 2023-02-01T06:56:00.739Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542510
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: FEC_Shipment_CurrencyRate
-- 2023-02-01T06:56:00.771Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542511
;

-- 2023-02-01T06:56:00.775Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542511
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: IsB2B
-- 2023-02-01T06:56:53.710Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,581680,0,585192,542516,20,'IsB2B',TO_TIMESTAMP('2023-02-01 08:56:53','YYYY-MM-DD HH24:MI:SS'),100,'N','1=2','D',0,'Y','N','Y','N','Y','N','B2B','1=1',80,TO_TIMESTAMP('2023-02-01 08:56:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T06:56:53.711Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542516 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: IsGenerateB2BShipment
-- 2023-02-01T06:57:45.979Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581980,0,585192,542517,20,'IsGenerateB2BShipment',TO_TIMESTAMP('2023-02-01 08:57:45','YYYY-MM-DD HH24:MI:SS'),100,'N','@IsB2B/N@=Y','D',0,'Y','N','Y','N','Y','N','Generate B2B Shipment',90,TO_TIMESTAMP('2023-02-01 08:57:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T06:57:45.981Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542517 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: IsShipmentFEC
-- 2023-02-01T06:58:27.783Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,581973,0,585192,542518,20,'IsShipmentFEC',TO_TIMESTAMP('2023-02-01 08:58:27','YYYY-MM-DD HH24:MI:SS'),100,'N','1=2','D',0,'Y','N','Y','N','Y','N','Shipment FEC','1=1',100,TO_TIMESTAMP('2023-02-01 08:58:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T06:58:27.785Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542518 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_SalesOrder_Currency_ID
-- 2023-02-01T06:58:58.233Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581975,0,585192,542519,30,112,'FEC_SalesOrder_Currency_ID',TO_TIMESTAMP('2023-02-01 08:58:58','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','N','N','Sales Order Currency',110,TO_TIMESTAMP('2023-02-01 08:58:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T06:58:58.235Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542519 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_SalesOrder_Currency_ID
-- 2023-02-01T06:59:10.768Z
UPDATE AD_Process_Para SET DisplayLogic='@IsShipmentFEC/N@=Y', ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2023-02-01 08:59:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542519
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_Shipment_ForeignExchangeContract_ID
-- 2023-02-01T06:59:31.831Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581974,0,585192,542520,30,541709,'FEC_Shipment_ForeignExchangeContract_ID',TO_TIMESTAMP('2023-02-01 08:59:31','YYYY-MM-DD HH24:MI:SS'),100,'@IsShipmentFEC/N@=Y','U',0,'Y','N','Y','N','N','N','Shipment Foreign Exchange Contract',120,TO_TIMESTAMP('2023-02-01 08:59:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T06:59:31.832Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542520 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_Shipment_From_Currency_ID
-- 2023-02-01T07:00:16.341Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581976,0,585192,542521,30,112,'FEC_Shipment_From_Currency_ID',TO_TIMESTAMP('2023-02-01 09:00:16','YYYY-MM-DD HH24:MI:SS'),100,'@IsShipmentFEC/N@=Y','D',0,'Y','N','Y','N','N','N','FEC Shipment Currency From',130,TO_TIMESTAMP('2023-02-01 09:00:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T07:00:16.343Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542521 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_Shipment_To_Currency_ID
-- 2023-02-01T07:00:42.256Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581977,0,585192,542522,30,112,'FEC_Shipment_To_Currency_ID',TO_TIMESTAMP('2023-02-01 09:00:42','YYYY-MM-DD HH24:MI:SS'),100,'@IsShipmentFEC/N@=Y','D',0,'Y','N','Y','N','N','N','FEC Shipment Currency To',140,TO_TIMESTAMP('2023-02-01 09:00:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T07:00:42.258Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542522 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_Shipment_CurrencyRate
-- 2023-02-01T07:00:57.516Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581978,0,585192,542523,22,'FEC_Shipment_CurrencyRate',TO_TIMESTAMP('2023-02-01 09:00:57','YYYY-MM-DD HH24:MI:SS'),100,'@IsShipmentFEC/N@=Y','U',0,'Y','N','Y','N','N','N','FEC Shipment Currency Rate',150,TO_TIMESTAMP('2023-02-01 09:00:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T07:00:57.517Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542523 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_SalesOrder_Currency_ID
-- 2023-02-01T07:03:01.993Z
UPDATE AD_Process_Para SET DisplayLogic='@IsGenerateB2BShipment/N@=Y & @IsShipmentFEC/N@=Y',Updated=TO_TIMESTAMP('2023-02-01 09:03:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542519
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_Shipment_ForeignExchangeContract_ID
-- 2023-02-01T07:03:04.540Z
UPDATE AD_Process_Para SET DisplayLogic='@IsGenerateB2BShipment/N@=Y & @IsShipmentFEC/N@=Y',Updated=TO_TIMESTAMP('2023-02-01 09:03:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542520
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_Shipment_From_Currency_ID
-- 2023-02-01T07:03:06.972Z
UPDATE AD_Process_Para SET DisplayLogic='@IsGenerateB2BShipment/N@=Y & @IsShipmentFEC/N@=Y',Updated=TO_TIMESTAMP('2023-02-01 09:03:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542521
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_Shipment_To_Currency_ID
-- 2023-02-01T07:03:09.182Z
UPDATE AD_Process_Para SET DisplayLogic='@IsGenerateB2BShipment/N@=Y & @IsShipmentFEC/N@=Y',Updated=TO_TIMESTAMP('2023-02-01 09:03:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542522
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_Shipment_CurrencyRate
-- 2023-02-01T07:03:17.111Z
UPDATE AD_Process_Para SET DisplayLogic='@IsGenerateB2BShipment/N@=Y & @IsShipmentFEC/N@=Y',Updated=TO_TIMESTAMP('2023-02-01 09:03:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542523
;

