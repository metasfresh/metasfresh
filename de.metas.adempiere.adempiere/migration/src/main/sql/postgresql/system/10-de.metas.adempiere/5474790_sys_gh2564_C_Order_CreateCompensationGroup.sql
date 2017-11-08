-- 2017-10-19T13:21:25.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540877,'N','de.metas.order.process.C_OrderLine_CreateDiscountGroup','N',TO_TIMESTAMP('2017-10-19 13:21:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','N','N','N','N','N','N','Y',0,'Create discount group','N','Y','Java',TO_TIMESTAMP('2017-10-19 13:21:24','YYYY-MM-DD HH24:MI:SS'),100,'C_OrderLine_CreateDiscountGroup')
;

-- 2017-10-19T13:21:25.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540877 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-10-19T13:22:49.231
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540877,260,TO_TIMESTAMP('2017-10-19 13:22:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y',TO_TIMESTAMP('2017-10-19 13:22:49','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

-- 2017-10-20T16:16:02.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.order.process.C_OrderLine_CreateGroup', Name='Create group', Value='C_OrderLine_CreateGroup',Updated=TO_TIMESTAMP('2017-10-20 16:16:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540877
;

-- 2017-10-20T16:16:11.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 16:16:11','YYYY-MM-DD HH24:MI:SS'),Name='Create group' WHERE AD_Process_ID=540877 AND AD_Language='de_CH'
;

-- 2017-10-20T16:16:14.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 16:16:14','YYYY-MM-DD HH24:MI:SS'),Name='Create group' WHERE AD_Process_ID=540877 AND AD_Language='en_US'
;

-- 2017-10-20T16:16:18.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-20 16:16:18','YYYY-MM-DD HH24:MI:SS'),Name='Create group' WHERE AD_Process_ID=540877 AND AD_Language='nl_NL'
;

-- 2017-10-23T11:56:31.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,FieldLength,Help,IsActive,IsMandatory,Name,SeqNo,Updated,UpdatedBy) VALUES (0,454,0,540877,541228,19,'M_Product_ID',TO_TIMESTAMP('2017-10-23 11:56:31','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','Y','Produkt',10,TO_TIMESTAMP('2017-10-23 11:56:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-23T11:56:31.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541228 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2017-10-23T17:52:51.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (Created,CreatedBy,IsActive,AD_Table_ID,AD_Client_ID,WEBUI_QuickAction,WEBUI_QuickAction_Default,AD_Process_ID,AD_Org_ID,EntityType,Updated,UpdatedBy) VALUES (TO_TIMESTAMP('2017-10-23 17:52:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',259,0,'N','N',540877,0,'D',TO_TIMESTAMP('2017-10-23 17:52:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-23T17:53:04.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET EntityType='de.metas.order',Updated=TO_TIMESTAMP('2017-10-23 17:53:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=259 AND AD_Process_ID=540877
;

-- 2017-10-23T17:53:12.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table_Process WHERE AD_Table_ID=260 AND AD_Process_ID=540877
;

-- 2017-10-23T17:54:05.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.order.process.C_Order_CreateCompensationGroup',Updated=TO_TIMESTAMP('2017-10-23 17:54:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540877
;

-- 2017-10-23T18:32:50.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2017-10-23 18:32:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541228
;

