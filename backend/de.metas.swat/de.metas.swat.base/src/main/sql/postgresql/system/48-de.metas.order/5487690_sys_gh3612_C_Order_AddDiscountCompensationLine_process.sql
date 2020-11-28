-- 2018-03-07T13:48:23.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,CreatedBy,IsReport,IsDirectPrint,Value,AccessLevel,EntityType,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Type,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,Classname,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'N','N','C_Order_AddDiscountCompensationLine','3','de.metas.order','Y','N','N','N',540934,'N','Y','N','Java','N','N',0,0,'Add group compensation line','de.metas.order.process.C_Order_AddDiscountCompensationLine',100,TO_TIMESTAMP('2018-03-07 13:48:23','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-07 13:48:23','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-07T13:48:23.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540934 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-03-07T13:49:24.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,AD_Val_Rule_ID,IsCentrallyMaintained,AD_Element_ID,EntityType,IsEncrypted,Description,Help,ColumnName,IsMandatory,IsAutocomplete,AD_Org_ID,Name,UpdatedBy,Created,Updated,FieldLength,SeqNo) VALUES (0,'Y',100,540934,30,'N',541270,540374,'Y',454,'de.metas.order','N','Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','M_Product_ID','Y','N',0,'Produkt',100,TO_TIMESTAMP('2018-03-07 13:49:24','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-07 13:49:24','YYYY-MM-DD HH24:MI:SS'),0,10)
;

-- 2018-03-07T13:49:24.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541270 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-03-07T13:49:45.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (EntityType,CreatedBy,IsActive,AD_Table_ID,AD_Client_ID,WEBUI_QuickAction,WEBUI_QuickAction_Default,AD_Process_ID,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('de.metas.order',100,'Y',259,0,'N','N',540934,0,100,TO_TIMESTAMP('2018-03-07 13:49:45','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-03-07 13:49:45','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-03-07T13:50:21.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Neuer Gruppenrabatt',Updated=TO_TIMESTAMP('2018-03-07 13:50:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540934
;

-- 2018-03-07T13:50:28.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-03-07 13:50:28','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Neuer Gruppenrabatt' WHERE AD_Process_ID=540934 AND AD_Language='de_CH'
;

