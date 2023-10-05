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



