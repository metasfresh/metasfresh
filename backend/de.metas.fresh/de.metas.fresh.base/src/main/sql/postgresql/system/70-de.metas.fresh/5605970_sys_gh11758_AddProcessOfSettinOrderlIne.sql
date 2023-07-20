-- 2021-09-21T15:50:06.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584909,'N','org.eevolution.process.PP_Order_UnClose','N',TO_TIMESTAMP('2021-09-21 18:50:06','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','Y','N','N','N','N','N','Y','Y',0,'Set order line','json','N','N','Java',TO_TIMESTAMP('2021-09-21 18:50:06','YYYY-MM-DD HH24:MI:SS'),100,'PP_Order_SetC_OrderLine')
;

-- 2021-09-21T15:50:07.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584909 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-09-21T15:50:11.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Auftragszeile festlegen',Updated=TO_TIMESTAMP('2021-09-21 18:50:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584909
;

-- 2021-09-21T15:50:14.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Auftragszeile festlegen',Updated=TO_TIMESTAMP('2021-09-21 18:50:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584909
;

-- 2021-09-21T15:50:14.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Auftragszeile festlegen',Updated=TO_TIMESTAMP('2021-09-21 18:50:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584909
;

-- 2021-09-21T15:50:48.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,561,0,584909,542099,30,'C_OrderLine_ID',TO_TIMESTAMP('2021-09-21 18:50:48','YYYY-MM-DD HH24:MI:SS'),100,'Auftragsposition','EE01',0,'"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','Y','N','Y','N','Auftragsposition',10,TO_TIMESTAMP('2021-09-21 18:50:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-21T15:50:48.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542099 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-09-21T15:51:17.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584909,53027,540999,TO_TIMESTAMP('2021-09-21 18:51:17','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y',TO_TIMESTAMP('2021-09-21 18:51:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2021-09-21T15:57:15.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='org.eevolution.process.PP_Order_SetC_OrderLine',Updated=TO_TIMESTAMP('2021-09-21 18:57:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584909
;

-- 2021-09-22T14:59:39.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (select 1 from C_order o where o.C_Order_ID = C_OrderLine.C_Order_ID and o.IsSOTrx=''Y'')',Updated=TO_TIMESTAMP('2021-09-22 17:59:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=271
;

-- 2021-09-22T15:03:15.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=271,Updated=TO_TIMESTAMP('2021-09-22 18:03:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542099
;


-- 2021-09-22T15:04:08.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='',Updated=TO_TIMESTAMP('2021-09-22 18:04:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=271
;

-- 2021-09-22T15:04:15.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2021-09-22 18:04:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542099
;

-- 2021-09-22T15:05:35.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540558,'exists (select 1 from C_order o where o.C_Order_ID = C_OrderLine.C_Order_ID and o.IsSOTrx=''Y'') ',TO_TIMESTAMP('2021-09-22 18:05:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_OrderLine of Sales Order','S',TO_TIMESTAMP('2021-09-22 18:05:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-22T15:05:45.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540558,Updated=TO_TIMESTAMP('2021-09-22 18:05:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542099
;

