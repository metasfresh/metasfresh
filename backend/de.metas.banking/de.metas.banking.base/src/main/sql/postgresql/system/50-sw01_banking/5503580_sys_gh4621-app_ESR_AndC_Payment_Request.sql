
-- 2018-10-16T11:22:49.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,IsDirectPrint,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,UpdatedBy,AD_Process_ID,Value,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Description,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,EntityType,Classname,Type) VALUES (0,'Y',TO_TIMESTAMP('2018-10-16 11:22:49','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-10-16 11:22:49','YYYY-MM-DD HH24:MI:SS'),'N','N','3','Y','N','N','N',100,541023,'C_Invoice_CreateExportData','Y','Y','N','','N','N',0,0,'Exportdaten erstellen','de.metas.invoice','de.metas.invoice.export.process.C_Invoice_CreateExportData','Java')
;

-- 2018-10-16T11:22:49.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=541023 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-10-16T11:24:18.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (Created,CreatedBy,IsActive,AD_Table_ID,EntityType,Updated,UpdatedBy,AD_Client_ID,WEBUI_QuickAction,WEBUI_QuickAction_Default,AD_Process_ID,AD_Window_ID,AD_Org_ID) VALUES (TO_TIMESTAMP('2018-10-16 11:24:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',318,'de.metas.invoice',TO_TIMESTAMP('2018-10-16 11:24:18','YYYY-MM-DD HH24:MI:SS'),100,0,'N','N',541023,167,0)
;

-- 2018-10-16T15:43:17.367
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Description='Eingehende Zahlungsaufforderung',Updated=TO_TIMESTAMP('2018-10-16 15:43:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540608
;

-- 2018-10-16T16:26:02.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Description='Zahlungsaufforderung',Updated=TO_TIMESTAMP('2018-10-16 16:26:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540608
;

-- 2018-10-16T16:26:19.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,Name,ColumnName) VALUES (20,'N',1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-16 16:26:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-10-16 16:26:18','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540608,'N','The Sales Transaction checkbox indicates if this item is a Sales Transaction.',563290,'N','Y','N','N','N','N','N','N','This is a Sales Transaction',0,0,1106,'de.metas.payment','N','N','Sales Transaction','IsSOTrx')
;

-- 2018-10-16T16:26:19.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563290 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-16T16:26:40.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Payment_Request','ALTER TABLE public.C_Payment_Request ADD COLUMN IsSOTrx CHAR(1) DEFAULT ''N'' CHECK (IsSOTrx IN (''Y'',''N'')) NOT NULL')
;

-- 2018-10-16T16:35:08.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2018-10-16 16:35:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540608
;

-- 2018-10-16T16:35:24.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2018-10-16 16:35:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551009
;

-- 2018-10-16T16:35:26.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2018-10-16 16:35:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551015
;

-- 2018-10-16T16:35:28.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2018-10-16 16:35:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551014
;

-- 2018-10-16T16:35:29.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2018-10-16 16:35:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551342
;

-- 2018-10-16T16:35:32.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2018-10-16 16:35:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563290
;

-- 2018-10-16T16:35:35.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2018-10-16 16:35:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551013
;

-- 2018-10-16T16:35:40.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2018-10-16 16:35:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551010
;

-- 2018-10-16T16:35:52.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2018-10-16 16:35:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554397
;

-- 2018-10-16T16:35:53.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2018-10-16 16:35:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551012
;

-- 2018-10-16T16:35:54.418
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2018-10-16 16:35:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551011
;

-- 2018-10-16T16:35:55.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N', EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2018-10-16 16:35:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551016
;

-- 2018-10-16T16:35:55.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2018-10-16 16:35:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551017
;

-- 2018-10-16T16:35:56.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2018-10-16 16:35:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551018
;

-- 2018-10-16T16:35:57.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2018-10-16 16:35:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551019
;

-- 2018-10-17T06:59:05.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540157,Updated=TO_TIMESTAMP('2018-10-17 06:59:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540406
;

-- 2018-10-17T06:59:21.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540157,Updated=TO_TIMESTAMP('2018-10-17 06:59:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540407
;

-- 2018-10-17T07:04:24.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_ReferenceNo_Type SET IsActive='Y', Description='Note: we have no C_ReferenceNo_Type_Table records because ESRReferenceNumber creation is done "hard-coded" by a C_Invoice model validator on invoice completion.
Background: we also create a C_Payment_Request and the invoice reference string at the same time, and we need them to be "in sync" (e.g. the underlying bank-account).',Updated=TO_TIMESTAMP('2018-10-17 07:04:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_ReferenceNo_Type_ID=540005
;

-- 2018-10-17T07:04:34.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM C_ReferenceNo_Type_Table WHERE C_ReferenceNo_Type_Table_ID=1000000
;

-- 2018-10-17T07:05:22.700
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_ReferenceNo_Type SET Value='ESRReferenceNumber',Updated=TO_TIMESTAMP('2018-10-17 07:05:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_ReferenceNo_Type_ID=540005
;

-- 2018-10-17T07:05:36.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_ReferenceNo_Type SET Description='Note: we have no C_ReferenceNo_Type_Table record(s) because ESRReferenceNumber creation is done "hard-coded" by a C_Invoice model validator on invoice completion.
Background: we also create a C_Payment_Request and the invoice reference string at the same time, and we need them to be "in sync" (e.g. the underlying bank-account).',Updated=TO_TIMESTAMP('2018-10-17 07:05:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_ReferenceNo_Type_ID=540005
;

-- 2018-10-17T07:06:35.586
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_ReferenceNo_Type SET IsActive='Y', Description='Note: we have no C_ReferenceNo_Type_Table record(s) because InvoiceReference creation is done "hard-coded" by a C_Invoice model validator on invoice completion.
Background: we also create a C_Payment_Request and the coded ESR string - of which this InvoiceReference is a part - at the same time, and we need them to be "in sync" (e.g. with the underlying bank-account).',Updated=TO_TIMESTAMP('2018-10-17 07:06:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_ReferenceNo_Type_ID=540006
;

-- 2018-10-17T07:06:41.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_ReferenceNo_Type SET Description='Note: we have no C_ReferenceNo_Type_Table record(s) because ESRReferenceNumber creation is done "hard-coded" by a C_Invoice model validator on invoice completion.
Background: we also create a C_Payment_Request and the invoice reference string at the same time, and we need them to be "in sync" (e.g. with the underlying bank-account).',Updated=TO_TIMESTAMP('2018-10-17 07:06:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_ReferenceNo_Type_ID=540005
;

