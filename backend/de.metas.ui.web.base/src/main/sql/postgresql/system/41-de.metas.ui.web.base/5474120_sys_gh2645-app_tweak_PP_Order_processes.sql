-- 2017-10-11T08:52:03.229
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Komplette HUs hinzufügen',Updated=TO_TIMESTAMP('2017-10-11 08:52:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540868
;

-- 2017-10-11T08:52:10.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Enthaltene TUs hinzufügen',Updated=TO_TIMESTAMP('2017-10-11 08:52:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540869
;

-- 2017-10-11T08:53:02.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('1',0,0,540873,'Y','de.metas.ui.web.pporder.process.WEBUI_PP_Order_M_Source_HU_IssueTuQty','N',TO_TIMESTAMP('2017-10-11 08:53:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','N','N','N','N','N','N','Y',0,'TUs aus Quell-HUs hunzufügen','N','Y','Java',TO_TIMESTAMP('2017-10-11 08:53:02','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_PP_Order_M_Source_HU_IssueTuQty')
;

-- 2017-10-11T08:53:02.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540873 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-10-11T08:53:07.764
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-11 08:53:07','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=540873 AND AD_Language='de_CH'
;

-- 2017-10-11T08:53:21.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-11 08:53:21','YYYY-MM-DD HH24:MI:SS'),Name='Issue TUs from source HUs' WHERE AD_Process_ID=540873 AND AD_Language='en_US'
;

-- 2017-10-11T08:53:50.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy,ValueMin) VALUES (0,542490,0,540873,541224,11,'QtyTU',TO_TIMESTAMP('2017-10-11 08:53:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',0,'Y','N','Y','N','Y','N','Menge TU',10,TO_TIMESTAMP('2017-10-11 08:53:50','YYYY-MM-DD HH24:MI:SS'),100,'1')
;

-- 2017-10-11T08:53:50.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541224 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2017-10-11T08:53:54.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-11 08:53:54','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_Para_ID=541224 AND AD_Language='de_CH'
;

-- 2017-10-11T08:57:42.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-11 08:57:42','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='TU quantity' WHERE AD_Process_Para_ID=541224 AND AD_Language='en_US'
;

-- 2017-10-11T12:08:30.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Zur Prüfung freigeben',Updated=TO_TIMESTAMP('2017-10-11 12:08:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540782
;

-- 2017-10-11T12:08:42.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Verarbeiten',Updated=TO_TIMESTAMP('2017-10-11 12:08:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540783
;

-- 2017-10-11T12:08:56.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-11 12:08:56','YYYY-MM-DD HH24:MI:SS'),Name='Verarbeiten' WHERE AD_Process_ID=540783 AND AD_Language='nl_NL'
;

-- 2017-10-11T12:09:02.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-11 12:09:02','YYYY-MM-DD HH24:MI:SS'),Name='Process' WHERE AD_Process_ID=540783 AND AD_Language='en_US'
;

-- 2017-10-11T12:09:11.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-11 12:09:11','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Verarbeiten' WHERE AD_Process_ID=540783 AND AD_Language='de_CH'
;

-- 2017-10-11T17:30:45.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='TUs aus Quell-HUs hinzufügen',Updated=TO_TIMESTAMP('2017-10-11 17:30:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540873
;

-- 2017-10-11T17:30:53.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-11 17:30:53','YYYY-MM-DD HH24:MI:SS'),Name='TUs aus Quell-HUs hinzufügen' WHERE AD_Process_ID=540873 AND AD_Language='de_CH'
;

-- 2017-10-11T17:31:00.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-11 17:31:00','YYYY-MM-DD HH24:MI:SS'),Name='TUs aus Quell-HUs hinzufügen' WHERE AD_Process_ID=540873 AND AD_Language='nl_NL'
;

-- 2017-10-12T12:21:37.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='HU aus Produktion entfernen',Updated=TO_TIMESTAMP('2017-10-12 12:21:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540791
;

-- 2017-10-12T12:22:11.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-12 12:22:11','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='HU aus Produktion entfernen' WHERE AD_Process_ID=540791 AND AD_Language='de_CH'
;

-- 2017-10-12T12:22:27.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-12 12:22:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Remove HU' WHERE AD_Process_ID=540791 AND AD_Language='en_US'
;

-- 2017-10-12T12:22:33.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='HU entfernen',Updated=TO_TIMESTAMP('2017-10-12 12:22:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540791
;

-- 2017-10-12T12:22:40.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-12 12:22:40','YYYY-MM-DD HH24:MI:SS'),Name='HU entfernen' WHERE AD_Process_ID=540791 AND AD_Language='de_CH'
;

