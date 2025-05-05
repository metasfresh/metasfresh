-- Run mode: SWING_CLIENT

-- 2023-10-05T09:00:58.757Z
UPDATE AD_ImpFormat SET SkipFirstNRows=1,Updated=TO_TIMESTAMP('2023-10-05 10:00:58.757','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_ID=540092
;

-- 2023-10-05T09:03:59.952Z
UPDATE AD_ImpFormat_Row SET IsActive='N',Updated=TO_TIMESTAMP('2023-10-05 10:03:59.951','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541956
;

-- Column: I_ModCntr_Log.Amount
-- 2023-10-05T09:18:47.622Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2023-10-05 10:18:47.622','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587515
;

-- 2023-10-05T09:18:51.099Z
INSERT INTO t_alter_column values('i_modcntr_log','Amount','VARCHAR(10)',null,null)
;

-- Column: I_ModCntr_Log.DateTrx
-- 2023-10-05T09:24:23.735Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2023-10-05 10:24:23.734','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587519
;

-- Column: I_ModCntr_Log.DateTrx
-- 2023-10-05T09:24:34.341Z
UPDATE AD_Column SET FieldLength=40,Updated=TO_TIMESTAMP('2023-10-05 10:24:34.34','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587519
;

-- 2023-10-05T09:24:35.899Z
INSERT INTO t_alter_column values('i_modcntr_log','DateTrx','VARCHAR(40)',null,null)
;

-- Table: I_ModCntr_Log
-- 2023-10-05T09:28:49.163Z
UPDATE AD_Table SET IsChangeLog='Y', IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2023-10-05 10:28:49.162','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542372
;


-- Run mode: SWING_CLIENT

-- Column: I_ModCntr_Log.ModCntr_InvoicingGroup_ID
-- 2023-10-05T09:40:25.362Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587526,582647,0,19,542372,'ModCntr_InvoicingGroup_ID',TO_TIMESTAMP('2023-10-05 10:40:18.214','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'E','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Rechnungsgruppe',0,0,TO_TIMESTAMP('2023-10-05 10:40:18.214','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-10-05T09:40:25.369Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587526 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-05T09:40:25.388Z
/* DDL */  select update_Column_Translation_From_AD_Element(582647)
;

-- 2023-10-05T09:42:29.416Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN ModCntr_InvoicingGroup_ID NUMERIC(10)')
;

-- 2023-10-05T09:42:29.424Z
ALTER TABLE I_ModCntr_Log ADD CONSTRAINT ModCntrInvoicingGroup_IModCntrLog FOREIGN KEY (ModCntr_InvoicingGroup_ID) REFERENCES public.ModCntr_InvoicingGroup DEFERRABLE INITIALLY DEFERRED
;

-- 2023-10-05T09:52:51.948Z
UPDATE AD_ImpFormat_Row SET StartNo=13,Updated=TO_TIMESTAMP('2023-10-05 10:52:51.948','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541946
;

-- 2023-10-05T09:53:01.148Z
UPDATE AD_ImpFormat_Row SET StartNo=14,Updated=TO_TIMESTAMP('2023-10-05 10:53:01.147','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541947
;

-- 2023-10-05T09:53:07.612Z
UPDATE AD_ImpFormat_Row SET StartNo=15,Updated=TO_TIMESTAMP('2023-10-05 10:53:07.611','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541948
;

-- 2023-10-05T09:54:21.227Z
UPDATE AD_ImpFormat_Row SET StartNo=16,Updated=TO_TIMESTAMP('2023-10-05 10:54:21.226','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541950
;

-- 2023-10-05T09:54:28.730Z
UPDATE AD_ImpFormat_Row SET StartNo=17,Updated=TO_TIMESTAMP('2023-10-05 10:54:28.73','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541951
;

-- 2023-10-05T09:54:37.683Z
UPDATE AD_ImpFormat_Row SET StartNo=18,Updated=TO_TIMESTAMP('2023-10-05 10:54:37.683','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541952
;

-- 2023-10-05T09:54:48.452Z
UPDATE AD_ImpFormat_Row SET StartNo=19,Updated=TO_TIMESTAMP('2023-10-05 10:54:48.451','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541953
;

-- 2023-10-05T09:55:05.571Z
UPDATE AD_ImpFormat_Row SET StartNo=20,Updated=TO_TIMESTAMP('2023-10-05 10:55:05.57','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541954
;

-- 2023-10-05T09:55:14.636Z
UPDATE AD_ImpFormat_Row SET StartNo=21,Updated=TO_TIMESTAMP('2023-10-05 10:55:14.635','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541955
;

-- 2023-10-05T09:55:44.202Z
UPDATE AD_ImpFormat_Row SET DataFormat='yyyy-mm-dd',Updated=TO_TIMESTAMP('2023-10-05 10:55:44.202','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541954
;

-- Column: I_ModCntr_Log.ContractModuleName
-- 2023-10-05T09:56:39.365Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-10-05 10:56:39.365','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587525
;

-- 2023-10-05T09:56:41.866Z
INSERT INTO t_alter_column values('i_modcntr_log','ContractModuleName','VARCHAR(255)',null,null)
;

-- Column: I_ModCntr_Log.Amount
-- 2023-10-05T09:58:59.771Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-10-05 10:58:59.771','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587515
;

-- Column: I_ModCntr_Log.Amount
-- 2023-10-05T09:59:19.644Z
UPDATE AD_Column SET AD_Reference_ID=22,Updated=TO_TIMESTAMP('2023-10-05 10:59:19.644','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587515
;

-- Column: I_ModCntr_Log.Amount
-- 2023-10-05T09:59:57.275Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2023-10-05 10:59:57.275','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587515
;

-- 2023-10-05T09:59:59.228Z
INSERT INTO t_alter_column values('i_modcntr_log','Amount','VARCHAR(10)',null,null)
;

-- 2023-10-05T09:59:59.231Z
INSERT INTO t_alter_column values('i_modcntr_log','Amount',null,'NULL',null)
;

-- Column: I_ModCntr_Log.Amount
-- 2023-10-05T10:00:09.395Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2023-10-05 11:00:09.395','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587515
;

-- 2023-10-05T10:00:11.281Z
INSERT INTO t_alter_column values('i_modcntr_log','Amount','VARCHAR(255)',null,null)
;

-- 2023-10-05T10:06:36.968Z
UPDATE AD_ImpFormat_Row SET StartNo=15,Updated=TO_TIMESTAMP('2023-10-05 11:06:36.968','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541946
;

-- 2023-10-05T10:07:04.784Z
UPDATE AD_ImpFormat_Row SET StartNo=16,Updated=TO_TIMESTAMP('2023-10-05 11:07:04.783','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541947
;

-- 2023-10-05T10:07:11.752Z
UPDATE AD_ImpFormat_Row SET StartNo=17,Updated=TO_TIMESTAMP('2023-10-05 11:07:11.751','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541948
;

-- 2023-10-05T10:07:18.781Z
UPDATE AD_ImpFormat_Row SET StartNo=18,Updated=TO_TIMESTAMP('2023-10-05 11:07:18.78','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541950
;

-- 2023-10-05T10:07:25.207Z
UPDATE AD_ImpFormat_Row SET StartNo=19,Updated=TO_TIMESTAMP('2023-10-05 11:07:25.207','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541951
;

-- 2023-10-05T10:07:30.761Z
UPDATE AD_ImpFormat_Row SET StartNo=20,Updated=TO_TIMESTAMP('2023-10-05 11:07:30.761','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541952
;

-- 2023-10-05T10:07:36.623Z
UPDATE AD_ImpFormat_Row SET StartNo=21,Updated=TO_TIMESTAMP('2023-10-05 11:07:36.622','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541953
;

-- 2023-10-05T10:07:41.798Z
UPDATE AD_ImpFormat_Row SET StartNo=22,Updated=TO_TIMESTAMP('2023-10-05 11:07:41.797','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541954
;

-- 2023-10-05T10:07:47.958Z
UPDATE AD_ImpFormat_Row SET StartNo=23,Updated=TO_TIMESTAMP('2023-10-05 11:07:47.958','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541955
;



-- Run mode: WEBUI

-- 2023-10-05T09:44:18.793Z
UPDATE AD_ImpFormat_Row SET StartNo=9,Updated=TO_TIMESTAMP('2023-10-05 10:44:18.792','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541944
;

-- 2023-10-05T09:44:22.260Z
UPDATE AD_ImpFormat_Row SET StartNo=10,Updated=TO_TIMESTAMP('2023-10-05 10:44:22.26','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541945
;

-- 2023-10-05T09:44:26.550Z
UPDATE AD_ImpFormat_Row SET StartNo=11,Updated=TO_TIMESTAMP('2023-10-05 10:44:26.55','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541946
;

-- 2023-10-05T09:44:30.155Z
UPDATE AD_ImpFormat_Row SET StartNo=12,Updated=TO_TIMESTAMP('2023-10-05 10:44:30.154','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541947
;

-- 2023-10-05T09:44:35.641Z
UPDATE AD_ImpFormat_Row SET StartNo=13,Updated=TO_TIMESTAMP('2023-10-05 10:44:35.641','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541948
;

-- 2023-10-05T09:44:40.762Z
UPDATE AD_ImpFormat_Row SET StartNo=14,Updated=TO_TIMESTAMP('2023-10-05 10:44:40.762','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541950
;

-- 2023-10-05T09:44:45.196Z
UPDATE AD_ImpFormat_Row SET StartNo=15,Updated=TO_TIMESTAMP('2023-10-05 10:44:45.196','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541951
;

-- 2023-10-05T09:44:49.217Z
UPDATE AD_ImpFormat_Row SET StartNo=16,Updated=TO_TIMESTAMP('2023-10-05 10:44:49.217','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541952
;

-- 2023-10-05T09:44:54.579Z
UPDATE AD_ImpFormat_Row SET StartNo=17,Updated=TO_TIMESTAMP('2023-10-05 10:44:54.579','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541953
;

-- 2023-10-05T09:44:57.883Z
UPDATE AD_ImpFormat_Row SET StartNo=18,Updated=TO_TIMESTAMP('2023-10-05 10:44:57.883','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541954
;

-- 2023-10-05T09:45:01.957Z
UPDATE AD_ImpFormat_Row SET StartNo=19,Updated=TO_TIMESTAMP('2023-10-05 10:45:01.957','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541955
;

-- 2023-10-05T09:45:16.057Z
DELETE FROM AD_ImpFormat_Row WHERE AD_ImpFormat_Row_ID=541956
;

-- 2023-10-05T09:47:02.264Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587526,540092,541957,0,TO_TIMESTAMP('2023-10-05 10:47:02.257','YYYY-MM-DD HH24:MI:SS.US'),100,'N','.','N','Y','Rechnungsgruppe',190,TO_TIMESTAMP('2023-10-05 10:47:02.257','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-05T09:47:18.551Z
UPDATE AD_ImpFormat_Row SET StartNo=8,Updated=TO_TIMESTAMP('2023-10-05 10:47:18.551','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541957
;

-- 2023-10-05T09:47:41.077Z
UPDATE AD_ImpFormat_Row SET SeqNo=75,Updated=TO_TIMESTAMP('2023-10-05 10:47:41.077','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541957
;




-- Run mode: SWING_CLIENT

-- Value: ImportModularLogs
-- Classname: de.metas.contracts.process.ImportModularContractLog
-- 2023-10-05T10:14:47.477Z
UPDATE AD_Process SET AccessLevel='7',Updated=TO_TIMESTAMP('2023-10-05 11:14:47.476','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585323
;

-- Value: ImportModularLogs
-- Classname: de.metas.contracts.process.ImportModularContractLog
-- 2023-10-05T10:14:51.734Z
UPDATE AD_Process SET AccessLevel='3',Updated=TO_TIMESTAMP('2023-10-05 11:14:51.733','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585323
;

-- Value: ImportModularLogs
-- Classname: de.metas.contracts.process.ImportModularContractLog
-- 2023-10-05T10:14:54.430Z
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2023-10-05 11:14:54.429','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585323
;

-- Value: ImportModularLogs
-- Classname: de.metas.contracts.process.ImportModularContractLog
-- 2023-10-05T10:15:13.125Z
UPDATE AD_Process SET Description='Import Modular Logs', TechnicalNote='de.metas.contracts.process.ImportModularContractLog',Updated=TO_TIMESTAMP('2023-10-05 11:15:13.124','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585323
;

-- 2023-10-05T10:15:13.128Z
UPDATE AD_Process_Trl trl SET Description='Import Modular Logs' WHERE AD_Process_ID=585323 AND AD_Language='de_DE'
;

-- Value: ImportModularLogs
-- Classname: de.metas.contracts.process.ImportModularContractLog
-- 2023-10-05T10:15:17.613Z
UPDATE AD_Process SET Help='Import Modular Logs',Updated=TO_TIMESTAMP('2023-10-05 11:15:17.612','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585323
;

-- 2023-10-05T10:15:17.614Z
UPDATE AD_Process_Trl trl SET Help='Import Modular Logs' WHERE AD_Process_ID=585323 AND AD_Language='de_DE'
;


-- Run mode: SWING_CLIENT

-- Value: ImportModularLogs
-- Classname: de.metas.contracts.impexp.process.ImportModularContractLog
-- 2023-10-05T10:59:08.916Z
UPDATE AD_Process SET Classname='de.metas.contracts.impexp.process.ImportModularContractLog',Updated=TO_TIMESTAMP('2023-10-05 11:59:08.915','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585323
;

-- Value: ImportModularContractLogs
-- Classname: de.metas.contracts.impexp.process.ImportModularContractLog
-- 2023-10-05T10:59:47.599Z
UPDATE AD_Process SET Description='Import Modular Contract Logs', Help='Import Modular Contract Logs', Name='Import Modular Contract Logs', TechnicalNote='', Value='ImportModularContractLogs',Updated=TO_TIMESTAMP('2023-10-05 11:59:47.598','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585323
;

-- 2023-10-05T10:59:47.602Z
UPDATE AD_Process_Trl trl SET Description='Import Modular Contract Logs',Help='Import Modular Contract Logs',Name='Import Modular Contract Logs' WHERE AD_Process_ID=585323 AND AD_Language='de_DE'
;


-- Run mode: SWING_CLIENT

-- 2023-10-05T11:38:38.832Z
UPDATE AD_ImpFormat_Row SET StartNo=13,Updated=TO_TIMESTAMP('2023-10-05 12:38:38.831','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541946
;

-- Column: I_ModCntr_Log.Amount
-- 2023-10-05T11:39:26.629Z
UPDATE AD_Column SET AD_Reference_ID=12,Updated=TO_TIMESTAMP('2023-10-05 12:39:26.629','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587515
;

-- Column: I_ModCntr_Log.Amount
-- 2023-10-05T11:40:15.328Z
UPDATE AD_Column SET FieldLength=10,Updated=TO_TIMESTAMP('2023-10-05 12:40:15.328','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587515
;




-- Run mode: SWING_CLIENT

-- Column: I_ModCntr_Log.PriceActual
-- 2023-10-05T11:55:12.531Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587527,519,0,37,542372,'PriceActual',TO_TIMESTAMP('2023-10-05 12:54:55.598','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Effektiver Preis','de.metas.contracts',0,22,'Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Einzelpreis',0,0,TO_TIMESTAMP('2023-10-05 12:54:55.598','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-10-05T11:55:12.547Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587527 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-05T11:55:12.567Z
/* DDL */  select update_Column_Translation_From_AD_Element(519)
;

-- 2023-10-05T11:55:18.078Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN PriceActual NUMERIC')
;

-- Column: I_ModCntr_Log.Price_UOM_ID
-- 2023-10-05T11:56:44.859Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587528,542464,0,18,114,542372,'Price_UOM_ID',TO_TIMESTAMP('2023-10-05 12:56:44.616','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Preiseinheit',0,0,TO_TIMESTAMP('2023-10-05 12:56:44.616','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-10-05T11:56:44.863Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587528 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-05T11:56:44.869Z
/* DDL */  select update_Column_Translation_From_AD_Element(542464)
;

-- 2023-10-05T11:56:47.374Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN Price_UOM_ID NUMERIC(10)')
;

-- 2023-10-05T11:56:47.383Z
ALTER TABLE I_ModCntr_Log ADD CONSTRAINT PriceUOM_IModCntrLog FOREIGN KEY (Price_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;


-- Run mode: WEBUI

-- 2023-10-05T12:00:42.793Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587528,540092,541958,0,TO_TIMESTAMP('2023-10-05 13:00:42.789','YYYY-MM-DD HH24:MI:SS.US'),100,'N','.','N','Y','Preiseinheit',190,TO_TIMESTAMP('2023-10-05 13:00:42.789','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-05T12:00:47.693Z
UPDATE AD_ImpFormat_Row SET StartNo=11,Updated=TO_TIMESTAMP('2023-10-05 13:00:47.693','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541958
;

-- 2023-10-05T12:02:02.214Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587527,540092,541959,0,TO_TIMESTAMP('2023-10-05 13:02:02.212','YYYY-MM-DD HH24:MI:SS.US'),100,'N','.','N','Y','Einzelpreis',200,TO_TIMESTAMP('2023-10-05 13:02:02.212','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-05T12:02:05.261Z
UPDATE AD_ImpFormat_Row SET StartNo=12,Updated=TO_TIMESTAMP('2023-10-05 13:02:05.261','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541959
;

-- 2023-10-05T12:06:31.202Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-10-05 13:06:31.202','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541938
;

-- 2023-10-05T12:06:32.433Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-10-05 13:06:32.433','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541936
;

-- 2023-10-05T12:06:33.479Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-10-05 13:06:33.479','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541939
;

-- 2023-10-05T12:06:34.905Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-10-05 13:06:34.905','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541940
;

-- 2023-10-05T12:06:35.844Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-10-05 13:06:35.844','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541941
;

-- 2023-10-05T12:06:36.660Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-10-05 13:06:36.66','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541942
;

-- 2023-10-05T12:06:37.380Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-10-05 13:06:37.38','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541943
;

-- 2023-10-05T12:06:38.175Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-10-05 13:06:38.175','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541957
;

-- 2023-10-05T12:07:12.752Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-10-05 13:07:12.752','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541944
;

-- 2023-10-05T12:07:43.902Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-10-05 13:07:43.902','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541945
;

-- 2023-10-05T12:07:49.127Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-10-05 13:07:49.127','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541946
;

-- 2023-10-05T12:07:53.098Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-10-05 13:07:53.098','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541947
;

-- 2023-10-05T12:08:01.486Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-10-05 13:08:01.486','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541948
;

-- 2023-10-05T12:08:12.726Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-10-05 13:08:12.726','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541950
;

-- 2023-10-05T12:08:17.917Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-10-05 13:08:17.917','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541951
;

-- 2023-10-05T12:08:25.453Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-10-05 13:08:25.453','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541952
;

-- 2023-10-05T12:08:29.306Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-10-05 13:08:29.306','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541953
;

-- 2023-10-05T12:08:32.884Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-10-05 13:08:32.884','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541954
;

-- 2023-10-05T12:08:36.077Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-10-05 13:08:36.077','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541955
;

-- 2023-10-05T12:08:39.776Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-10-05 13:08:39.776','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541958
;

-- 2023-10-05T12:08:43.779Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-10-05 13:08:43.779','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541959
;

-- 2023-10-05T12:08:49.912Z
UPDATE AD_ImpFormat_Row SET SeqNo=80,Updated=TO_TIMESTAMP('2023-10-05 13:08:49.912','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541957
;

-- 2023-10-05T12:08:55.965Z
UPDATE AD_ImpFormat_Row SET SeqNo=90,Updated=TO_TIMESTAMP('2023-10-05 13:08:55.965','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541944
;

-- 2023-10-05T12:09:01.862Z
UPDATE AD_ImpFormat_Row SET SeqNo=100,Updated=TO_TIMESTAMP('2023-10-05 13:09:01.862','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541945
;

-- 2023-10-05T12:09:06.378Z
UPDATE AD_ImpFormat_Row SET SeqNo=130,Updated=TO_TIMESTAMP('2023-10-05 13:09:06.378','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541946
;

-- 2023-10-05T12:09:10.456Z
UPDATE AD_ImpFormat_Row SET StartNo=14,Updated=TO_TIMESTAMP('2023-10-05 13:09:10.456','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541947
;

-- 2023-10-05T12:09:14.083Z
UPDATE AD_ImpFormat_Row SET StartNo=15,Updated=TO_TIMESTAMP('2023-10-05 13:09:14.083','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541948
;

-- 2023-10-05T12:09:18.637Z
UPDATE AD_ImpFormat_Row SET SeqNo=160, StartNo=16,Updated=TO_TIMESTAMP('2023-10-05 13:09:18.637','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541950
;

-- 2023-10-05T12:09:22.834Z
UPDATE AD_ImpFormat_Row SET SeqNo=170, StartNo=17,Updated=TO_TIMESTAMP('2023-10-05 13:09:22.834','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541951
;

-- 2023-10-05T12:09:33.082Z
UPDATE AD_ImpFormat_Row SET SeqNo=180, StartNo=18,Updated=TO_TIMESTAMP('2023-10-05 13:09:33.082','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541952
;

-- 2023-10-05T12:09:35.637Z
UPDATE AD_ImpFormat_Row SET SeqNo=190, StartNo=19,Updated=TO_TIMESTAMP('2023-10-05 13:09:35.637','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541953
;

-- 2023-10-05T12:09:39.388Z
UPDATE AD_ImpFormat_Row SET SeqNo=200, StartNo=20,Updated=TO_TIMESTAMP('2023-10-05 13:09:39.388','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541954
;

-- 2023-10-05T12:10:02.411Z
UPDATE AD_ImpFormat_Row SET SeqNo=210, StartNo=21,Updated=TO_TIMESTAMP('2023-10-05 13:10:02.41','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541955
;

-- 2023-10-05T12:10:43.755Z
UPDATE AD_ImpFormat_Row SET SeqNo=150,Updated=TO_TIMESTAMP('2023-10-05 13:10:43.755','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541948
;


-- Run mode: SWING_CLIENT

-- 2023-10-05T12:17:21.357Z
UPDATE AD_ImpFormat_Row SET SeqNo=120,Updated=TO_TIMESTAMP('2023-10-05 13:17:21.355','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541958
;

-- 2023-10-05T12:17:41.989Z
UPDATE AD_ImpFormat_Row SET SeqNo=140,Updated=TO_TIMESTAMP('2023-10-05 13:17:41.987','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541948
;

-- 2023-10-05T12:17:44.778Z
UPDATE AD_ImpFormat_Row SET SeqNo=150,Updated=TO_TIMESTAMP('2023-10-05 13:17:44.775','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541950
;

-- 2023-10-05T12:17:47.375Z
UPDATE AD_ImpFormat_Row SET SeqNo=160,Updated=TO_TIMESTAMP('2023-10-05 13:17:47.372','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541951
;

-- 2023-10-05T12:17:49.356Z
UPDATE AD_ImpFormat_Row SET SeqNo=170,Updated=TO_TIMESTAMP('2023-10-05 13:17:49.355','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541952
;

-- 2023-10-05T12:17:51.505Z
UPDATE AD_ImpFormat_Row SET SeqNo=180,Updated=TO_TIMESTAMP('2023-10-05 13:17:51.504','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541953
;

-- 2023-10-05T12:17:54.789Z
UPDATE AD_ImpFormat_Row SET SeqNo=190,Updated=TO_TIMESTAMP('2023-10-05 13:17:54.787','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541954
;

-- 2023-10-05T12:19:01.940Z
UPDATE AD_ImpFormat_Row SET SeqNo=115,Updated=TO_TIMESTAMP('2023-10-05 13:19:01.939','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541959
;

-- 2023-10-05T12:19:22.708Z
UPDATE AD_ImpFormat_Row SET SeqNo=105,Updated=TO_TIMESTAMP('2023-10-05 13:19:22.707','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541959
;

-- 2023-10-05T12:19:39.763Z
UPDATE AD_ImpFormat_Row SET SeqNo=109,Updated=TO_TIMESTAMP('2023-10-05 13:19:39.762','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541958
;

-- 2023-10-05T12:19:59.901Z
UPDATE AD_ImpFormat_Row SET SeqNo=135,Updated=TO_TIMESTAMP('2023-10-05 13:19:59.899','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541947
;

-- 2023-10-05T12:20:42.312Z
UPDATE AD_ImpFormat_Row SET StartNo=11,Updated=TO_TIMESTAMP('2023-10-05 13:20:42.31','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541959
;

-- 2023-10-05T12:20:47.543Z
UPDATE AD_ImpFormat_Row SET StartNo=12,Updated=TO_TIMESTAMP('2023-10-05 13:20:47.543','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541958
;

-- 2023-10-05T12:31:00.204Z
UPDATE AD_ImpFormat_Row SET StartNo=7,Updated=TO_TIMESTAMP('2023-10-05 13:31:00.204','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541957
;

-- 2023-10-05T12:31:40.926Z
UPDATE AD_ImpFormat_Row SET StartNo=8,Updated=TO_TIMESTAMP('2023-10-05 13:31:40.925','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541944
;

-- 2023-10-05T12:41:40.004Z
UPDATE AD_ImpFormat_Row SET StartNo=8,Updated=TO_TIMESTAMP('2023-10-05 13:41:40.002','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541957
;

-- 2023-10-05T12:41:44.148Z
UPDATE AD_ImpFormat_Row SET StartNo=9,Updated=TO_TIMESTAMP('2023-10-05 13:41:44.146','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541944
;

-- 2023-10-05T12:43:00.785Z
UPDATE AD_ImpFormat_Row SET SeqNo=200,Updated=TO_TIMESTAMP('2023-10-05 13:43:00.785','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541954
;

-- 2023-10-05T12:43:03.608Z
UPDATE AD_ImpFormat_Row SET SeqNo=190,Updated=TO_TIMESTAMP('2023-10-05 13:43:03.606','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541953
;

-- 2023-10-05T12:43:08.543Z
UPDATE AD_ImpFormat_Row SET SeqNo=180,Updated=TO_TIMESTAMP('2023-10-05 13:43:08.541','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541952
;

-- 2023-10-05T12:43:12.752Z
UPDATE AD_ImpFormat_Row SET SeqNo=170,Updated=TO_TIMESTAMP('2023-10-05 13:43:12.75','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541951
;

-- 2023-10-05T12:43:16.568Z
UPDATE AD_ImpFormat_Row SET SeqNo=160,Updated=TO_TIMESTAMP('2023-10-05 13:43:16.566','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541950
;

-- 2023-10-05T12:43:19.391Z
UPDATE AD_ImpFormat_Row SET SeqNo=150,Updated=TO_TIMESTAMP('2023-10-05 13:43:19.389','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541948
;

-- 2023-10-05T12:43:23.959Z
UPDATE AD_ImpFormat_Row SET SeqNo=140,Updated=TO_TIMESTAMP('2023-10-05 13:43:23.957','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541947
;

-- 2023-10-05T12:43:26.632Z
UPDATE AD_ImpFormat_Row SET SeqNo=120,Updated=TO_TIMESTAMP('2023-10-05 13:43:26.629','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541958
;

-- 2023-10-05T12:43:29.627Z
UPDATE AD_ImpFormat_Row SET SeqNo=110,Updated=TO_TIMESTAMP('2023-10-05 13:43:29.626','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541959
;

-- 2023-10-05T12:48:15.450Z
UPDATE AD_ImpFormat_Row SET Name='ModCntr_InvoicingGroup_ID',Updated=TO_TIMESTAMP('2023-10-05 13:48:15.45','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541957
;

-- 2023-10-05T12:53:25.305Z
INSERT INTO t_alter_column values('i_modcntr_log','ModCntr_InvoicingGroup_ID','NUMERIC(10)',null,null)
;

-- 2023-10-05T20:23:06.085Z
UPDATE AD_ImpFormat_Row SET DataType='S',Updated=TO_TIMESTAMP('2023-10-05 21:23:06.083','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541946
;

-- 2023-10-05T20:23:33.741Z
UPDATE AD_ImpFormat_Row SET DataType='S',Updated=TO_TIMESTAMP('2023-10-05 21:23:33.74','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541954
;

-- 2023-10-05T20:25:22.132Z
UPDATE AD_ImpFormat_Row SET DataType='S',Updated=TO_TIMESTAMP('2023-10-05 21:25:22.13','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541944
;

-- 2023-10-05T20:26:40.934Z
UPDATE AD_ImpFormat_Row SET IsActive='N',Updated=TO_TIMESTAMP('2023-10-05 21:26:40.934','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541957
;

-- 2023-10-05T20:28:56.629Z
UPDATE AD_ImpFormat_Row SET DataType='N',Updated=TO_TIMESTAMP('2023-10-05 21:28:56.627','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541944
;

-- 2023-10-05T20:29:11.137Z
UPDATE AD_ImpFormat_Row SET DataType='N',Updated=TO_TIMESTAMP('2023-10-05 21:29:11.136','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541946
;

-- 2023-10-05T20:29:31.290Z
UPDATE AD_ImpFormat_Row SET DataFormat='yyyy-mm-dd', DataType='D',Updated=TO_TIMESTAMP('2023-10-05 21:29:31.289','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541948
;



-- Run mode: SWING_CLIENT

-- Column: I_ModCntr_Log.M_Product_ID
-- 2023-10-05T21:43:30.035Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587532,454,0,19,542372,'M_Product_ID',TO_TIMESTAMP('2023-10-05 22:43:05.295','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Produkt, Leistung, Artikel','de.metas.contracts',0,10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produkt',0,0,TO_TIMESTAMP('2023-10-05 22:43:05.295','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-10-05T21:43:30.055Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587532 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-05T21:43:30.077Z
/* DDL */  select update_Column_Translation_From_AD_Element(454)
;

-- 2023-10-05T21:59:30.172Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN M_Product_ID NUMERIC(10)')
;

-- 2023-10-05T21:59:30.181Z
ALTER TABLE I_ModCntr_Log ADD CONSTRAINT MProduct_IModCntrLog FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- Column: I_ModCntr_Log.Producer_BPartner_ID
-- 2023-10-05T22:00:15.986Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587533,582415,0,18,541252,542372,'Producer_BPartner_ID',TO_TIMESTAMP('2023-10-05 23:00:15.586','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produzent',0,0,TO_TIMESTAMP('2023-10-05 23:00:15.586','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-10-05T22:00:15.990Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587533 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-05T22:00:15.996Z
/* DDL */  select update_Column_Translation_From_AD_Element(582415)
;

-- 2023-10-05T22:00:21.113Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN Producer_BPartner_ID NUMERIC(10)')
;

-- 2023-10-05T22:00:21.121Z
ALTER TABLE I_ModCntr_Log ADD CONSTRAINT ProducerBPartner_IModCntrLog FOREIGN KEY (Producer_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- Column: I_ModCntr_Log.Harvesting_Year_ID
-- 2023-10-05T22:00:50.567Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587534,582471,0,18,540133,542372,'Harvesting_Year_ID',TO_TIMESTAMP('2023-10-05 23:00:50.204','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Erntejahr',0,0,TO_TIMESTAMP('2023-10-05 23:00:50.204','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-10-05T22:00:50.569Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587534 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-05T22:00:50.571Z
/* DDL */  select update_Column_Translation_From_AD_Element(582471)
;

-- 2023-10-05T22:00:54.099Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN Harvesting_Year_ID NUMERIC(10)')
;

-- 2023-10-05T22:00:54.105Z
ALTER TABLE I_ModCntr_Log ADD CONSTRAINT HarvestingYear_IModCntrLog FOREIGN KEY (Harvesting_Year_ID) REFERENCES public.C_Year DEFERRABLE INITIALLY DEFERRED
;

-- Column: I_ModCntr_Log.C_UOM_ID
-- 2023-10-05T22:01:10.979Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587535,215,0,19,542372,'C_UOM_ID',TO_TIMESTAMP('2023-10-05 23:01:10.53','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Maßeinheit','de.metas.contracts',0,10,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Maßeinheit',0,0,TO_TIMESTAMP('2023-10-05 23:01:10.53','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-10-05T22:01:10.985Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587535 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-05T22:01:10.992Z
/* DDL */  select update_Column_Translation_From_AD_Element(215)
;

-- 2023-10-05T22:01:12.464Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN C_UOM_ID NUMERIC(10)')
;

-- 2023-10-05T22:01:12.471Z
ALTER TABLE I_ModCntr_Log ADD CONSTRAINT CUOM_IModCntrLog FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- Column: I_ModCntr_Log.C_Currency_ID
-- 2023-10-05T22:01:23.980Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587536,193,0,19,542372,'C_Currency_ID',TO_TIMESTAMP('2023-10-05 23:01:23.473','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Die Währung für diesen Eintrag','de.metas.contracts',0,10,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Währung',0,0,TO_TIMESTAMP('2023-10-05 23:01:23.473','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-10-05T22:01:23.983Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587536 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-05T22:01:23.989Z
/* DDL */  select update_Column_Translation_From_AD_Element(193)
;

-- 2023-10-05T22:01:25.380Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN C_Currency_ID NUMERIC(10)')
;

-- 2023-10-05T22:01:25.386Z
ALTER TABLE I_ModCntr_Log ADD CONSTRAINT CCurrency_IModCntrLog FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED
;

-- Column: I_ModCntr_Log.Bill_BPartner_ID
-- 2023-10-05T22:01:45.248Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587537,2039,0,18,541045,542372,'Bill_BPartner_ID',TO_TIMESTAMP('2023-10-05 23:01:44.97','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Geschäftspartner für die Rechnungsstellung','de.metas.contracts',0,10,'Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rechnungspartner',0,0,TO_TIMESTAMP('2023-10-05 23:01:44.97','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-10-05T22:01:45.251Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587537 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-05T22:01:45.257Z
/* DDL */  select update_Column_Translation_From_AD_Element(2039)
;

-- 2023-10-05T22:01:46.985Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN Bill_BPartner_ID NUMERIC(10)')
;

-- 2023-10-05T22:01:46.991Z
ALTER TABLE I_ModCntr_Log ADD CONSTRAINT BillBPartner_IModCntrLog FOREIGN KEY (Bill_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- Column: I_ModCntr_Log.M_Warehouse_ID
-- 2023-10-05T22:02:52.763Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587538,459,0,19,542372,'M_Warehouse_ID',TO_TIMESTAMP('2023-10-05 23:02:51.325','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Lager oder Ort für Dienstleistung','de.metas.contracts',0,10,'Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lager',0,0,TO_TIMESTAMP('2023-10-05 23:02:51.325','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-10-05T22:02:52.767Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587538 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-05T22:02:52.772Z
/* DDL */  select update_Column_Translation_From_AD_Element(459)
;

-- 2023-10-05T22:02:54.316Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN M_Warehouse_ID NUMERIC(10)')
;

-- 2023-10-05T22:02:54.323Z
ALTER TABLE I_ModCntr_Log ADD CONSTRAINT MWarehouse_IModCntrLog FOREIGN KEY (M_Warehouse_ID) REFERENCES public.M_Warehouse DEFERRABLE INITIALLY DEFERRED
;

-- Column: I_ModCntr_Log.C_Flatrate_Term_ID
-- 2023-10-05T22:03:31.329Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587539,541447,0,19,542372,'C_Flatrate_Term_ID',TO_TIMESTAMP('2023-10-05 23:03:31.056','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Pauschale - Vertragsperiode',0,0,TO_TIMESTAMP('2023-10-05 23:03:31.056','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-10-05T22:03:31.333Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587539 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-05T22:03:31.339Z
/* DDL */  select update_Column_Translation_From_AD_Element(541447)
;

-- 2023-10-05T22:03:33.395Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN C_Flatrate_Term_ID NUMERIC(10)')
;

-- 2023-10-05T22:03:33.401Z
ALTER TABLE I_ModCntr_Log ADD CONSTRAINT CFlatrateTerm_IModCntrLog FOREIGN KEY (C_Flatrate_Term_ID) REFERENCES public.C_Flatrate_Term DEFERRABLE INITIALLY DEFERRED
;

-- Column: I_ModCntr_Log.CollectionPoint_BPartner_ID
-- 2023-10-05T22:04:06.188Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587540,582414,0,18,541252,542372,'CollectionPoint_BPartner_ID',TO_TIMESTAMP('2023-10-05 23:04:05.729','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Sammelstelle',0,0,TO_TIMESTAMP('2023-10-05 23:04:05.729','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-10-05T22:04:06.191Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587540 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-05T22:04:06.196Z
/* DDL */  select update_Column_Translation_From_AD_Element(582414)
;

-- 2023-10-05T22:04:15.551Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN CollectionPoint_BPartner_ID NUMERIC(10)')
;

-- 2023-10-05T22:04:15.557Z
ALTER TABLE I_ModCntr_Log ADD CONSTRAINT CollectionPointBPartner_IModCntrLog FOREIGN KEY (CollectionPoint_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- Column: I_ModCntr_Log.DateTrx
-- 2023-10-05T22:32:03.973Z
UPDATE AD_Column SET AD_Reference_ID=15,Updated=TO_TIMESTAMP('2023-10-05 23:32:03.973','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587519
;

-- Column: I_ModCntr_Log.DateTrx
-- 2023-10-05T22:33:00.455Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2023-10-05 23:33:00.455','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587519
;





-- Run mode: SWING_CLIENT

-- Value: ImportModularContractLogs
-- Classname: de.metas.contracts.modular.log.impexp.ImportModularContractLog
-- 2023-10-06T21:34:56.270Z
UPDATE AD_Process SET Classname='de.metas.contracts.modular.log.impexp.ImportModularContractLog',Updated=TO_TIMESTAMP('2023-10-06 22:34:56.269','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585323
;




-- Run mode: SWING_CLIENT

-- 2023-10-10T10:04:49.915Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582753,0,'ModCntr_InvoicingGroupName',TO_TIMESTAMP('2023-10-10 11:04:44.213','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Invoicing Group Name','Invoicing Group Name',TO_TIMESTAMP('2023-10-10 11:04:44.213','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-10T10:04:49.926Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582753 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: ModCntr_InvoicingGroupName
-- 2023-10-10T10:06:44.926Z
UPDATE AD_Element_Trl SET Name='Name der Rechnungsstellungsgruppe', PrintName='Name der Rechnungsstellungsgruppe',Updated=TO_TIMESTAMP('2023-10-10 11:06:44.926','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582753 AND AD_Language='de_CH'
;

-- 2023-10-10T10:06:44.942Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582753,'de_CH')
;

-- Element: ModCntr_InvoicingGroupName
-- 2023-10-10T10:06:50.315Z
UPDATE AD_Element_Trl SET Name='Name der Rechnungsstellungsgruppe', PrintName='Name der Rechnungsstellungsgruppe',Updated=TO_TIMESTAMP('2023-10-10 11:06:50.315','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582753 AND AD_Language='de_DE'
;

-- 2023-10-10T10:06:50.316Z
UPDATE AD_Element SET Name='Name der Rechnungsstellungsgruppe', PrintName='Name der Rechnungsstellungsgruppe' WHERE AD_Element_ID=582753
;

-- 2023-10-10T10:06:50.619Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582753,'de_DE')
;

-- 2023-10-10T10:06:50.620Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582753,'de_DE')
;

-- Element: ModCntr_InvoicingGroupName
-- 2023-10-10T10:06:54.038Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-10 11:06:54.038','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582753 AND AD_Language='en_US'
;

-- 2023-10-10T10:06:54.039Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582753,'en_US')
;

-- Column: I_ModCntr_Log.ModCntr_InvoicingGroupName
-- 2023-10-10T10:07:15.109Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587542,582753,0,10,542372,'ModCntr_InvoicingGroupName',TO_TIMESTAMP('2023-10-10 11:07:14.823','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Name der Rechnungsstellungsgruppe',0,0,TO_TIMESTAMP('2023-10-10 11:07:14.823','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-10-10T10:07:15.110Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587542 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-10T10:07:15.115Z
/* DDL */  select update_Column_Translation_From_AD_Element(582753)
;

-- 2023-10-10T10:07:25.639Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN ModCntr_InvoicingGroupName VARCHAR(255)')
;

-- 2023-10-10T10:08:37.088Z
UPDATE AD_ImpFormat_Row SET AD_Column_ID=587542, Name='ModCntr_InvoicingGroupName',Updated=TO_TIMESTAMP('2023-10-10 11:08:37.087','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541957
;

-- 2023-10-10T10:13:57.837Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582754,0,'PriceUOM',TO_TIMESTAMP('2023-10-10 11:13:57.517','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Preiseinheit','Preiseinheit',TO_TIMESTAMP('2023-10-10 11:13:57.517','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-10T10:13:57.840Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582754 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PriceUOM
-- 2023-10-10T10:14:19.953Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Price UOM', PrintName='Price UOM',Updated=TO_TIMESTAMP('2023-10-10 11:14:19.953','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582754 AND AD_Language='en_US'
;

-- 2023-10-10T10:14:19.954Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582754,'en_US')
;

-- Column: I_ModCntr_Log.PriceUOM
-- 2023-10-10T10:14:43.264Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587543,582754,0,10,542372,'PriceUOM',TO_TIMESTAMP('2023-10-10 11:14:42.96','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,40,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Preiseinheit',0,0,TO_TIMESTAMP('2023-10-10 11:14:42.96','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-10-10T10:14:43.265Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587543 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-10T10:14:43.267Z
/* DDL */  select update_Column_Translation_From_AD_Element(582754)
;

-- 2023-10-10T10:14:48.075Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN PriceUOM VARCHAR(40)')
;

-- 2023-10-10T10:16:00.666Z
UPDATE AD_ImpFormat_Row SET AD_Column_ID=587543, DataType='S',Updated=TO_TIMESTAMP('2023-10-10 11:16:00.666','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541958
;



-- Run mode: SWING_CLIENT

-- 2023-10-11T10:19:45.316Z
UPDATE AD_ImpFormat SET Description='Import Modular Contract Log', Name='Import Modular Contract Log',Updated=TO_TIMESTAMP('2023-10-11 11:19:45.315','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_ID=540092
;

-- 2023-10-11T10:30:56.565Z
UPDATE AD_ImpFormat_Row SET DataType='D',Updated=TO_TIMESTAMP('2023-10-11 11:30:56.564','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541954
;

-- Column: I_ModCntr_Log.DateTrx
-- 2023-10-11T10:31:33.722Z
UPDATE AD_Column SET AD_Reference_ID=15, DefaultValue='N', FieldLength=1,Updated=TO_TIMESTAMP('2023-10-11 11:31:33.722','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587519
;

-- Column: I_ModCntr_Log.DateTrx
-- 2023-10-11T10:32:02.246Z
UPDATE AD_Column SET AD_Reference_ID=16,Updated=TO_TIMESTAMP('2023-10-11 11:32:02.246','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587519
;

-- Column: I_ModCntr_Log.DateTrx
-- 2023-10-11T10:34:16.868Z
UPDATE AD_Column SET FieldLength=29,Updated=TO_TIMESTAMP('2023-10-11 11:34:16.868','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587519
;

-- Column: I_ModCntr_Log.DateTrx
-- 2023-10-11T10:34:55.539Z
UPDATE AD_Column SET IsActive='N',Updated=TO_TIMESTAMP('2023-10-11 11:34:55.539','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587519
;

-- 2023-10-11T10:35:08.674Z
UPDATE AD_ImpFormat_Row SET IsActive='N',Updated=TO_TIMESTAMP('2023-10-11 11:35:08.673','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541954
;

-- 2023-10-11T10:35:23.068Z
DELETE FROM AD_ImpFormat_Row WHERE AD_ImpFormat_Row_ID=541954
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> dates.Vorgangsdatum
-- Column: I_ModCntr_Log.DateTrx
-- 2023-10-11T10:36:39.777Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=620629
;

-- 2023-10-11T10:36:39.785Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720733
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Vorgangsdatum
-- Column: I_ModCntr_Log.DateTrx
-- 2023-10-11T10:36:39.793Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=720733
;

-- 2023-10-11T10:36:39.795Z
DELETE FROM AD_Field WHERE AD_Field_ID=720733
;

-- Column: I_ModCntr_Log.DateTrx
-- 2023-10-11T10:36:47.572Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=587519
;

-- 2023-10-11T10:36:47.574Z
DELETE FROM AD_Column WHERE AD_Column_ID=587519
;



