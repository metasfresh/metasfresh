-- Run mode: SWING_CLIENT

-- 2023-10-04T20:38:46.252Z
INSERT INTO AD_ImpFormat (AD_Client_ID,AD_ImpFormat_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,FileCharset,FormatType,IsActive,IsManualImport,IsMultiLine,Name,Processing,SkipFirstNRows,Updated,UpdatedBy) VALUES (0,540092,0,542372,TO_TIMESTAMP('2023-10-04 21:38:09.099','YYYY-MM-DD HH24:MI:SS.US'),100,'utf-8','S','Y','N','N','Import Module Contract Log','N',0,TO_TIMESTAMP('2023-10-04 21:38:09.099','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Run mode: SWING_CLIENT

-- Column: I_ModCntr_Log.ModCntr_Log_ID
-- 2023-10-04T21:32:56.617Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587523,582413,0,13,541775,542372,'ModCntr_Log_ID',TO_TIMESTAMP('2023-10-04 22:32:56.298','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Contract Module Log',0,0,TO_TIMESTAMP('2023-10-04 22:32:56.298','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-10-04T21:32:56.624Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587523 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-04T21:32:56.644Z
/* DDL */  select update_Column_Translation_From_AD_Element(582413)
;

-- 2023-10-04T21:33:06.669Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN ModCntr_Log_ID NUMERIC(10)')
;

-- Table: I_ModCntr_Log
-- 2023-10-04T21:33:34.110Z
UPDATE AD_Table SET AD_Window_ID=541737,Updated=TO_TIMESTAMP('2023-10-04 22:33:34.11','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542372
;

-- 2023-10-04T21:39:01.802Z
INSERT INTO t_alter_column values('i_modcntr_log','ModCntr_Log_ID','NUMERIC(10)',null,null)
;

-- Column: I_ModCntr_Log.recordid
-- 2023-10-04T21:41:04.522Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587524,540692,0,11,542372,'recordid',TO_TIMESTAMP('2023-10-04 22:40:53.786','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'recordid',0,0,TO_TIMESTAMP('2023-10-04 22:40:53.786','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-10-04T21:41:04.523Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587524 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-04T21:41:04.525Z
/* DDL */  select update_Column_Translation_From_AD_Element(540692)
;

-- Column: I_ModCntr_Log.Record_ID
-- 2023-10-04T21:43:15.536Z
UPDATE AD_Column SET AD_Element_ID=538, AD_Reference_ID=13, ColumnName='Record_ID', Description='Direct internal record ID', Help='The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.', Name='Datensatz-ID',Updated=TO_TIMESTAMP('2023-10-04 22:43:15.536','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587524
;

-- 2023-10-04T21:43:15.541Z
UPDATE AD_Column_Trl trl SET Name='Datensatz-ID' WHERE AD_Column_ID=587524 AND AD_Language='de_DE'
;

-- 2023-10-04T21:43:15.542Z
UPDATE AD_Field SET Name='Datensatz-ID', Description='Direct internal record ID', Help='The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.' WHERE AD_Column_ID=587524
;

-- 2023-10-04T21:43:15.542Z
/* DDL */  select update_Column_Translation_From_AD_Element(538)
;

-- 2023-10-04T21:44:45.312Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN Record_ID NUMERIC(10) NOT NULL')
;

-- 2023-10-04T21:56:00.943Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582738,0,'ContractModuleName',TO_TIMESTAMP('2023-10-04 22:56:00.703','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Contract Module Name','Contract Module Name',TO_TIMESTAMP('2023-10-04 22:56:00.703','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T21:56:00.945Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582738 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: I_ModCntr_Log.ContractModuleName
-- 2023-10-04T21:56:32.116Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587525,582738,0,10,542372,'ContractModuleName',TO_TIMESTAMP('2023-10-04 22:56:31.881','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Contract Module Name',0,0,TO_TIMESTAMP('2023-10-04 22:56:31.881','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-10-04T21:56:32.118Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587525 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-04T21:56:32.119Z
/* DDL */  select update_Column_Translation_From_AD_Element(582738)
;

-- 2023-10-04T21:56:34.267Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN ContractModuleName VARCHAR(255)')
;

-- Column: I_ModCntr_Log.CollectionPointValue
-- 2023-10-04T22:18:28.013Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-10-04 23:18:28.013','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587518
;

-- Column: I_ModCntr_Log.ContractModuleName
-- 2023-10-04T22:18:37.710Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-10-04 23:18:37.71','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587525
;

-- Column: I_ModCntr_Log.IsSOTrx
-- 2023-10-04T22:19:30.885Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-10-04 23:19:30.885','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587510
;

-- Column: I_ModCntr_Log.BPartnerValue
-- 2023-10-04T22:19:54.318Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-10-04 23:19:54.317','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587516
;

-- Column: I_ModCntr_Log.Bill_BPartner_Value
-- 2023-10-04T22:20:00.190Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-10-04 23:20:00.19','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587517
;

-- Column: I_ModCntr_Log.DateTrx
-- 2023-10-04T22:20:18.406Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-10-04 23:20:18.406','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587519
;

-- Column: I_ModCntr_Log.FiscalYear
-- 2023-10-04T22:20:25.566Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-10-04 23:20:25.566','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587521
;

-- Column: I_ModCntr_Log.ModCntr_Log_DocumentType
-- 2023-10-04T22:20:37.038Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-10-04 23:20:37.038','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587509
;

-- Column: I_ModCntr_Log.ProductValue
-- 2023-10-04T22:20:46.013Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-10-04 23:20:46.013','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587512
;

-- Column: I_ModCntr_Log.CollectionPointValue
-- 2023-10-04T22:21:10.239Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-10-04 23:21:10.239','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587518
;

-- Column: I_ModCntr_Log.ProductValue
-- 2023-10-04T22:21:18.758Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-10-04 23:21:18.758','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587512
;

-- Column: I_ModCntr_Log.FiscalYear
-- 2023-10-04T22:21:32.935Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-10-04 23:21:32.935','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587521
;

-- Column: I_ModCntr_Log.DocumentNo
-- 2023-10-04T22:21:44.160Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-10-04 23:21:44.16','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587511
;

-- Value: ImportModularLogs
-- Classname: de.metas.contracts.process.ImportModularContractLog
-- 2023-10-04T22:29:28.958Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585323,'Y','de.metas.contracts.process.ImportModularContractLog','N',TO_TIMESTAMP('2023-10-04 23:29:28.693','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Import Modular Logs','json','N','N','xls','Java',TO_TIMESTAMP('2023-10-04 23:29:28.693','YYYY-MM-DD HH24:MI:SS.US'),100,'ImportModularLogs')
;

-- 2023-10-04T22:29:28.961Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585323 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: ImportModularLogs(de.metas.contracts.process.ImportModularContractLog)
-- Table: I_ModCntr_Log
-- EntityType: de.metas.contracts
-- 2023-10-04T22:30:28.326Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585323,542372,541431,TO_TIMESTAMP('2023-10-04 23:30:28.063','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2023-10-04 23:30:28.063','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;



-- Run mode: WEBUI

-- 2023-10-04T21:36:55.471Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587508,540092,541936,0,TO_TIMESTAMP('2023-10-04 22:36:55.462','YYYY-MM-DD HH24:MI:SS.US'),100,'N','.','N','Y',' ',10,TO_TIMESTAMP('2023-10-04 22:36:55.462','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T21:37:23.901Z
UPDATE AD_ImpFormat_Row SET Name=' TableID',Updated=TO_TIMESTAMP('2023-10-04 22:37:23.901','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541936
;

-- 2023-10-04T21:37:32.492Z
UPDATE AD_ImpFormat_Row SET SeqNo=20,Updated=TO_TIMESTAMP('2023-10-04 22:37:32.492','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541936
;

-- 2023-10-04T21:46:16.969Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587523,540092,541938,0,TO_TIMESTAMP('2023-10-04 22:46:16.963','YYYY-MM-DD HH24:MI:SS.US'),100,'N','.','N','Y','Mod_Cntr_Log_ID',10,TO_TIMESTAMP('2023-10-04 22:46:16.963','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T21:47:02.768Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587524,540092,541939,0,TO_TIMESTAMP('2023-10-04 22:47:02.767','YYYY-MM-DD HH24:MI:SS.US'),100,'N','.','N','Y','RecordID',30,TO_TIMESTAMP('2023-10-04 22:47:02.767','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T21:48:33.096Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587509,540092,541940,0,TO_TIMESTAMP('2023-10-04 22:48:33.095','YYYY-MM-DD HH24:MI:SS.US'),100,'S','.','N','Y','DocumentType',40,TO_TIMESTAMP('2023-10-04 22:48:33.095','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T21:49:02.495Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587510,540092,541941,0,TO_TIMESTAMP('2023-10-04 22:49:02.494','YYYY-MM-DD HH24:MI:SS.US'),100,'B','.','N','Y','SOTrx',50,TO_TIMESTAMP('2023-10-04 22:49:02.494','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T21:49:28.639Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587511,540092,541942,0,TO_TIMESTAMP('2023-10-04 22:49:28.637','YYYY-MM-DD HH24:MI:SS.US'),100,'S','.','N','Y','ContractDocumentNumber',60,TO_TIMESTAMP('2023-10-04 22:49:28.637','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T21:49:55.555Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587512,540092,541943,0,TO_TIMESTAMP('2023-10-04 22:49:55.554','YYYY-MM-DD HH24:MI:SS.US'),100,'S','.','N','Y','ProductValue',70,TO_TIMESTAMP('2023-10-04 22:49:55.554','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T21:50:20.691Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587513,540092,541944,0,TO_TIMESTAMP('2023-10-04 22:50:20.689','YYYY-MM-DD HH24:MI:SS.US'),100,'N','.','N','Y','Quantity',80,TO_TIMESTAMP('2023-10-04 22:50:20.689','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T21:50:47.905Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587514,540092,541945,0,TO_TIMESTAMP('2023-10-04 22:50:47.903','YYYY-MM-DD HH24:MI:SS.US'),100,'S','.','N','Y','UOM',90,TO_TIMESTAMP('2023-10-04 22:50:47.903','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T21:50:59.779Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587515,540092,541946,0,TO_TIMESTAMP('2023-10-04 22:50:59.778','YYYY-MM-DD HH24:MI:SS.US'),100,'N','.','N','Y','Amount',100,TO_TIMESTAMP('2023-10-04 22:50:59.778','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T21:51:18.528Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587522,540092,541947,0,TO_TIMESTAMP('2023-10-04 22:51:18.527','YYYY-MM-DD HH24:MI:SS.US'),100,'S','.','N','Y','Currency',110,TO_TIMESTAMP('2023-10-04 22:51:18.527','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T21:51:42.572Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587521,540092,541948,0,TO_TIMESTAMP('2023-10-04 22:51:42.57','YYYY-MM-DD HH24:MI:SS.US'),100,'S','.','N','Y','Year',120,TO_TIMESTAMP('2023-10-04 22:51:42.57','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T21:57:43.959Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587525,540092,541950,0,TO_TIMESTAMP('2023-10-04 22:57:43.951','YYYY-MM-DD HH24:MI:SS.US'),100,'S','.','N','Y','ContractModuleValue',130,TO_TIMESTAMP('2023-10-04 22:57:43.951','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T21:58:34.314Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587516,540092,541951,0,TO_TIMESTAMP('2023-10-04 22:58:34.313','YYYY-MM-DD HH24:MI:SS.US'),100,'S','.','N','Y','BusinessPartner',140,TO_TIMESTAMP('2023-10-04 22:58:34.313','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T21:59:15.510Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587517,540092,541952,0,TO_TIMESTAMP('2023-10-04 22:59:15.509','YYYY-MM-DD HH24:MI:SS.US'),100,'S','.','N','Y','InvoicePartner',150,TO_TIMESTAMP('2023-10-04 22:59:15.509','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T21:59:33.790Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587518,540092,541953,0,TO_TIMESTAMP('2023-10-04 22:59:33.789','YYYY-MM-DD HH24:MI:SS.US'),100,'S','.','N','Y','CollectionPoint',160,TO_TIMESTAMP('2023-10-04 22:59:33.789','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T21:59:56.617Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587519,540092,541954,0,TO_TIMESTAMP('2023-10-04 22:59:56.615','YYYY-MM-DD HH24:MI:SS.US'),100,'D','.','N','Y','TransactionDate',170,TO_TIMESTAMP('2023-10-04 22:59:56.615','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T22:00:21.212Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587520,540092,541955,0,TO_TIMESTAMP('2023-10-04 23:00:21.211','YYYY-MM-DD HH24:MI:SS.US'),100,'S','.','N','Y','Warehouse',180,TO_TIMESTAMP('2023-10-04 23:00:21.211','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T22:00:49.320Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587504,540092,541956,0,TO_TIMESTAMP('2023-10-04 23:00:49.319','YYYY-MM-DD HH24:MI:SS.US'),100,'B','.','N','Y','Processed',190,TO_TIMESTAMP('2023-10-04 23:00:49.319','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-04T22:02:27.373Z
UPDATE AD_ImpFormat_Row SET StartNo=1,Updated=TO_TIMESTAMP('2023-10-04 23:02:27.373','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541938
;

-- 2023-10-04T22:02:36.129Z
UPDATE AD_ImpFormat_Row SET StartNo=2,Updated=TO_TIMESTAMP('2023-10-04 23:02:36.129','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541936
;

-- 2023-10-04T22:02:43.610Z
UPDATE AD_ImpFormat_Row SET StartNo=3,Updated=TO_TIMESTAMP('2023-10-04 23:02:43.61','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541939
;

-- 2023-10-04T22:02:50.085Z
UPDATE AD_ImpFormat_Row SET StartNo=3,Updated=TO_TIMESTAMP('2023-10-04 23:02:50.084','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541940
;

-- 2023-10-04T22:02:56.547Z
UPDATE AD_ImpFormat_Row SET StartNo=43,Updated=TO_TIMESTAMP('2023-10-04 23:02:56.547','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541940
;

-- 2023-10-04T22:02:58.408Z
UPDATE AD_ImpFormat_Row SET StartNo=4,Updated=TO_TIMESTAMP('2023-10-04 23:02:58.407','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541940
;

-- 2023-10-04T22:03:02.025Z
UPDATE AD_ImpFormat_Row SET StartNo=5,Updated=TO_TIMESTAMP('2023-10-04 23:03:02.025','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541941
;

-- 2023-10-04T22:03:04.800Z
UPDATE AD_ImpFormat_Row SET StartNo=6,Updated=TO_TIMESTAMP('2023-10-04 23:03:04.8','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541942
;

-- 2023-10-04T22:03:07.560Z
UPDATE AD_ImpFormat_Row SET StartNo=7,Updated=TO_TIMESTAMP('2023-10-04 23:03:07.56','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541943
;

-- 2023-10-04T22:03:11.515Z
UPDATE AD_ImpFormat_Row SET StartNo=8,Updated=TO_TIMESTAMP('2023-10-04 23:03:11.515','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541944
;

-- 2023-10-04T22:03:13.024Z
UPDATE AD_ImpFormat_Row SET StartNo=9,Updated=TO_TIMESTAMP('2023-10-04 23:03:13.024','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541945
;

-- 2023-10-04T22:03:14.878Z
UPDATE AD_ImpFormat_Row SET StartNo=10,Updated=TO_TIMESTAMP('2023-10-04 23:03:14.878','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541946
;

-- 2023-10-04T22:03:17.035Z
UPDATE AD_ImpFormat_Row SET StartNo=11,Updated=TO_TIMESTAMP('2023-10-04 23:03:17.035','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541947
;

-- 2023-10-04T22:03:19.329Z
UPDATE AD_ImpFormat_Row SET StartNo=12,Updated=TO_TIMESTAMP('2023-10-04 23:03:19.329','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541948
;

-- 2023-10-04T22:03:21.335Z
UPDATE AD_ImpFormat_Row SET StartNo=13,Updated=TO_TIMESTAMP('2023-10-04 23:03:21.335','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541950
;

-- 2023-10-04T22:03:23.554Z
UPDATE AD_ImpFormat_Row SET StartNo=14,Updated=TO_TIMESTAMP('2023-10-04 23:03:23.554','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541951
;

-- 2023-10-04T22:03:25.605Z
UPDATE AD_ImpFormat_Row SET StartNo=15,Updated=TO_TIMESTAMP('2023-10-04 23:03:25.605','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541952
;

-- 2023-10-04T22:03:29.934Z
UPDATE AD_ImpFormat_Row SET StartNo=16,Updated=TO_TIMESTAMP('2023-10-04 23:03:29.933','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541953
;

-- 2023-10-04T22:03:32.706Z
UPDATE AD_ImpFormat_Row SET StartNo=17,Updated=TO_TIMESTAMP('2023-10-04 23:03:32.706','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541954
;

-- 2023-10-04T22:03:35.569Z
UPDATE AD_ImpFormat_Row SET StartNo=18,Updated=TO_TIMESTAMP('2023-10-04 23:03:35.569','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541955
;

-- 2023-10-04T22:03:38.615Z
UPDATE AD_ImpFormat_Row SET StartNo=19,Updated=TO_TIMESTAMP('2023-10-04 23:03:38.615','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541956
;




