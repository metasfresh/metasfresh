-- 2018-07-24T10:23:55.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540761,541663,TO_TIMESTAMP('2018-07-24 10:23:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Vertrag wurde falsch erfasst',TO_TIMESTAMP('2018-07-24 10:23:55','YYYY-MM-DD HH24:MI:SS'),100,'Err','IncorrectlyRecorded')
;

-- 2018-07-24T10:23:55.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541663 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-07-24T13:29:02.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540991,'Y','de.metas.order.restart.process.C_Order_VoidWithRelatedDocsAndRecreate','N',TO_TIMESTAMP('2018-07-24 13:29:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','N','N','N','N','N','N','Y',0,'C_Order_VoidWithRelatedDocsAndRecreate','N','Y','Java',TO_TIMESTAMP('2018-07-24 13:29:02','YYYY-MM-DD HH24:MI:SS'),100,'10000000')
;

-- 2018-07-24T13:29:02.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540991 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-07-24T13:30:21.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540991,541326,10,'VoidedOrderDocumentNoPrefix',TO_TIMESTAMP('2018-07-24 13:30:21','YYYY-MM-DD HH24:MI:SS'),100,'S-','de.metas.order',0,'Y','N','Y','N','Y','N','Beleg-Nr-Präfix für stornierten Auftrag',10,TO_TIMESTAMP('2018-07-24 13:30:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-24T13:30:21.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541326 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-07-24T13:31:13.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET Description='Präfix, der der Belegnummer des stornierten Auftrags vorangestellt wird.',Updated=TO_TIMESTAMP('2018-07-24 13:31:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541326
;

-- 2018-07-24T13:31:31.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DisplayLogic='1=0',Updated=TO_TIMESTAMP('2018-07-24 13:31:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541326
;

-- 2018-07-24T13:34:36.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Storniert die Ausgewählten Aufträge mitsamt ihren Folgebelegen (Vertragsperiode, Rechnung) 
und erzeugt jeweils eine Auftrags-Kopie, die von neuem bearbeitet und fertig gestellt werden kann.', Name='Ausgewählte Aufträge mit Folgebelegen stornieren', Value='C_Order_VoidWithRelatedDocsAndRecreate',Updated=TO_TIMESTAMP('2018-07-24 13:34:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540991
;

-- 2018-07-24T13:34:55.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540991,259,143,TO_TIMESTAMP('2018-07-24 13:34:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y',TO_TIMESTAMP('2018-07-24 13:34:55','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

-- 2018-07-24T15:07:16.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-07-24 15:07:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546110
;

-- 2018-07-24T15:07:16.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-07-24 15:07:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546111
;

-- 2018-07-24T17:08:10.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='S',Updated=TO_TIMESTAMP('2018-07-24 17:08:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541326
;

-- 2018-07-24T21:56:38.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.dunning',Updated=TO_TIMESTAMP('2018-07-24 21:56:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548501
;

-- 2018-07-24T21:59:05.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.dunning',Updated=TO_TIMESTAMP('2018-07-24 21:59:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548816
;

-- 2018-07-24T21:59:09.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.dunning',Updated=TO_TIMESTAMP('2018-07-24 21:59:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548817
;

-- 2018-07-24T22:03:21.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.dunning',Updated=TO_TIMESTAMP('2018-07-24 22:03:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548979
;

-- 2018-07-24T22:27:31.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Storniert den ausgewählten Auftrag mitsamt seinen Folgebelegen (Vertragsperiode, Rechnung) 
und erzeugt eine Auftrags-Kopie, die von neuem bearbeitet und fertig gestellt werden kann.', Name='Ausgewählten Auftrag mit Folgebelegen stornieren',Updated=TO_TIMESTAMP('2018-07-24 22:27:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540991
;

-- 2018-07-24T22:27:50.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-24 22:27:50','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Ausgewählten Auftrag mit Folgebelegen stornieren' WHERE AD_Process_ID=540991 AND AD_Language='de_CH'
;

-- 2018-07-24T22:27:55.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-24 22:27:55','YYYY-MM-DD HH24:MI:SS'),Description='Storniert den ausgewählten Auftrag mitsamt seinen Folgebelegen (Vertragsperiode, Rechnung) 
und erzeugt eine Auftrags-Kopie, die von neuem bearbeitet und fertig gestellt werden kann.' WHERE AD_Process_ID=540991 AND AD_Language='de_CH'
;

-- 2018-07-24T22:28:44.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-24 22:28:44','YYYY-MM-DD HH24:MI:SS'),Name='Void selected order with related documents' WHERE AD_Process_ID=540991 AND AD_Language='en_US'
;

-- 2018-07-24T22:28:55.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-24 22:28:55','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Description='' WHERE AD_Process_ID=540991 AND AD_Language='en_US'
;

-- 2018-07-25T08:51:14.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.order.voidorderwithrelateddocs.process.C_Order_VoidWithRelatedDocsAndRecreate',Updated=TO_TIMESTAMP('2018-07-25 08:51:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540991
;

-- 2018-07-25T08:53:21.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.order.voidorderandrelateddocs.process.C_Order_VoidWithRelatedDocsAndRecreate',Updated=TO_TIMESTAMP('2018-07-25 08:53:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540991
;

-- 2018-07-25T08:54:57.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='C_Order_VoidAndRelatedDocsAndRecreate',Updated=TO_TIMESTAMP('2018-07-25 08:54:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540991
;

