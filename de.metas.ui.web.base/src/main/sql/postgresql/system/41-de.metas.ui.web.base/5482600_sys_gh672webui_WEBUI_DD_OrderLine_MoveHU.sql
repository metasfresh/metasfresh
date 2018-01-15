-- 2018-01-13T16:06:27.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,CreatedBy,IsReport,IsDirectPrint,Value,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,Type,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,Name,Classname,EntityType,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'N','N','WEBUI_DD_OrderLine_MoveHU','3','Y','N','N','N',540912,'Y','Y','N','Java','N','N',0,0,'Move HU','de.metas.ui.web.ddorder.process.WEBUI_DD_OrderLine_MoveHU','de.metas.ui.web',100,TO_TIMESTAMP('2018-01-13 16:06:26','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-01-13 16:06:26','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-13T16:06:27.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540912 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-01-13T16:06:32.761
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AllowProcessReRun='N',Updated=TO_TIMESTAMP('2018-01-13 16:06:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540912
;

-- 2018-01-13T16:07:21.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,IsCentrallyMaintained,AD_Element_ID,IsEncrypted,ColumnName,IsMandatory,IsAutocomplete,AD_Org_ID,Name,EntityType,UpdatedBy,Created,Updated,FieldLength,SeqNo) VALUES (0,'Y',100,540912,30,'N',541259,'Y',542140,'N','M_HU_ID','Y','N',0,'Handling Units','de.metas.handlingunits',100,TO_TIMESTAMP('2018-01-13 16:07:21','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-01-13 16:07:21','YYYY-MM-DD HH24:MI:SS'),0,10)
;

-- 2018-01-13T16:07:21.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541259 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-01-13T16:07:48.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,IsCentrallyMaintained,AD_Element_ID,IsEncrypted,Description,Help,ColumnName,IsMandatory,IsAutocomplete,AD_Org_ID,Name,EntityType,UpdatedBy,Created,Updated,FieldLength,SeqNo) VALUES (0,'Y',100,540912,30,'N',541260,'Y',448,'N','Lagerort im Lager','"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.','M_Locator_ID','Y','N',0,'Lagerort','de.metas.ui.web',100,TO_TIMESTAMP('2018-01-13 16:07:48','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-01-13 16:07:48','YYYY-MM-DD HH24:MI:SS'),0,20)
;

-- 2018-01-13T16:07:48.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541260 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-01-13T16:08:35.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (CreatedBy,IsActive,AD_Table_ID,AD_Client_ID,WEBUI_QuickAction,WEBUI_QuickAction_Default,AD_Process_ID,AD_Org_ID,EntityType,UpdatedBy,Created,Updated) VALUES (100,'Y',53038,0,'Y','Y',540912,0,'de.metas.ui.web',100,TO_TIMESTAMP('2018-01-13 16:08:35','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-01-13 16:08:35','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-13T16:18:13.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_Value_ID=191, AD_Element_ID=1029, Description='Lagerort, an den Bestand bewegt wird', Help='"Lagerort An" bezeichnet den Lagerort, auf den ein Warenbestand bewegt wird.', ColumnName='M_LocatorTo_ID', Name='Lagerort An',Updated=TO_TIMESTAMP('2018-01-13 16:18:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541260
;

