-- 2018-02-18T11:53:11.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,CreatedBy,IsReport,IsDirectPrint,Value,AccessLevel,EntityType,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Type,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,Classname,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'N','N','C_Order_CreateCompensationMultiGroups','3','de.metas.order','Y','N','N','N',540925,'N','Y','N','Java','N','N',0,0,'Multigroup lines','de.metas.order.process.C_Order_CreateCompensationMultiGroups',100,TO_TIMESTAMP('2018-02-18 11:53:11','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-18 11:53:11','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-18T11:53:11.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540925 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-02-18T11:53:50.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,AD_Val_Rule_ID,IsCentrallyMaintained,AD_Element_ID,EntityType,IsEncrypted,Description,Help,ColumnName,IsMandatory,IsAutocomplete,AD_Org_ID,Name,UpdatedBy,Created,Updated,FieldLength,SeqNo) VALUES (0,'Y',100,540925,30,'N',541265,540374,'Y',454,'de.metas.order','N','Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','M_Product_ID','Y','N',0,'Produkt',100,TO_TIMESTAMP('2018-02-18 11:53:50','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-18 11:53:50','YYYY-MM-DD HH24:MI:SS'),0,10)
;

-- 2018-02-18T11:53:50.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541265 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-02-18T11:54:27.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (EntityType,CreatedBy,IsActive,AD_Table_ID,AD_Client_ID,WEBUI_QuickAction,WEBUI_QuickAction_Default,AD_Process_ID,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('de.metas.order',100,'Y',259,0,'N','N',540925,0,100,TO_TIMESTAMP('2018-02-18 11:54:27','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-18 11:54:27','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-18T11:54:44.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET AD_Window_ID=143,Updated=TO_TIMESTAMP('2018-02-18 11:54:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=259 AND AD_Process_ID=540925
;

