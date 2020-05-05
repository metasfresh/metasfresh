

-- 2018-07-19T18:01:28.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544180,0,'DefaultAddressType',TO_TIMESTAMP('2018-07-19 18:01:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','DefaultAddressType','DefaultAddressType',TO_TIMESTAMP('2018-07-19 18:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;




-- 2018-07-23T16:20:24.110
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540874,TO_TIMESTAMP('2018-07-23 16:20:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.base','Y','N','DefaultAddressTypes',TO_TIMESTAMP('2018-07-23 16:20:23','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2018-07-23T16:20:24.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540874 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-07-23T16:20:56.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541661,540874,TO_TIMESTAMP('2018-07-23 16:20:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.base','Y','BillToDefault',TO_TIMESTAMP('2018-07-23 16:20:56','YYYY-MM-DD HH24:MI:SS'),100,'B','BillToDefault')
;

-- 2018-07-23T16:20:56.763
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541661 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-07-23T16:21:17.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541662,540874,TO_TIMESTAMP('2018-07-23 16:21:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.base','Y','ShipToDefault',TO_TIMESTAMP('2018-07-23 16:21:17','YYYY-MM-DD HH24:MI:SS'),100,'S','ShipToDefault')
;

-- 2018-07-23T16:21:17.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541662 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;








-- 2018-07-19T18:01:28.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544180 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
-- 2018-07-20T10:13:01.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540990,'Y','N',TO_TIMESTAMP('2018-07-20 10:13:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.base','Y','N','N','N','N','N','N','Y',0,'Export in Serienbrief Kampagne','N','Y','Java',TO_TIMESTAMP('2018-07-20 10:13:01','YYYY-MM-DD HH24:MI:SS'),100,'MKTG_ContactPerson_CreateFrom_C_BPartner_WithAddress')
;

-- 2018-07-20T10:13:01.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540990 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-07-20T14:40:18.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.marketing.base.process.MKTG_ContactPerson_CreateFrom_C_BPartner_WithAddress',Updated=TO_TIMESTAMP('2018-07-20 14:40:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540990
;

-- 2018-07-20T14:40:58.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,544034,0,540990,541324,19,'MKTG_Campaign_ID',TO_TIMESTAMP('2018-07-20 14:40:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.base',0,'Y','N','Y','N','N','N','MKTG_Campaign',10,TO_TIMESTAMP('2018-07-20 14:40:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-20T14:40:58.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541324 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-07-20T14:41:17.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2018-07-20 14:41:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541324
;

-- 2018-07-20T14:41:36.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-07-20 14:41:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541324
;

-- 2018-07-20T14:42:06.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,544180,0,540990,541325,17,540874,'DefaultAddressType',TO_TIMESTAMP('2018-07-20 14:42:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.base',0,'Y','N','Y','N','N','N','DefaultAddressType',20,TO_TIMESTAMP('2018-07-20 14:42:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-20T14:42:06.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541325 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-07-20T14:42:09.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2018-07-20 14:42:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541325
;

-- 2018-07-20T14:42:51.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540990,291,TO_TIMESTAMP('2018-07-20 14:42:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.base','Y',TO_TIMESTAMP('2018-07-20 14:42:51','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

