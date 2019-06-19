-- 2019-06-14T16:37:23.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541147,'Y','N',TO_TIMESTAMP('2019-06-14 16:37:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dataentry','Y','N','N','N','N','N','N','Y','Y',0,'Import - Erweiterte Dateneingabe','N','N','Java',TO_TIMESTAMP('2019-06-14 16:37:23','YYYY-MM-DD HH24:MI:SS'),100,'Import_DataEntryRecord')
;

-- 2019-06-14T16:37:23.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541147 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-06-14T16:40:38.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1922,0,541147,541484,20,'DeleteOldImported',TO_TIMESTAMP('2019-06-14 16:40:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Vor der Verarbeitung die alten, bereits importierten Daten aus der Importtabelle löschen','de.metas.dataentry',1,'Y','N','Y','N','Y','N','Lösche alte Importdaten',10,TO_TIMESTAMP('2019-06-14 16:40:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-06-14T16:40:38.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541484 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-06-14T16:40:51.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,541147,541372,540721,TO_TIMESTAMP('2019-06-14 16:40:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dataentry','Y',TO_TIMESTAMP('2019-06-14 16:40:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2019-06-14T17:09:27.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=1,Updated=TO_TIMESTAMP('2019-06-14 17:09:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563969
;

-- 2019-06-14T17:11:51.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=1,Updated=TO_TIMESTAMP('2019-06-14 17:11:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568234
;

-- 2019-06-14T17:19:41.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540656,Updated=TO_TIMESTAMP('2019-06-14 17:19:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541372
;

-- 2019-06-14T17:19:48.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsHighVolume='Y',Updated=TO_TIMESTAMP('2019-06-14 17:19:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541372
;

-- 2019-06-14T17:20:06.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-06-14 17:20:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=581926
;

-- 2019-06-14T17:20:19.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-06-14 17:20:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=581928
;

-- 2019-06-14T17:20:27.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-06-14 17:20:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=581930
;

-- 2019-06-14T17:20:31.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-06-14 17:20:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=581931
;

-- 2019-06-14T17:20:40.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-06-14 17:20:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=581934
;

-- 2019-06-14T17:20:50.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-06-14 17:20:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=581937
;

-- 2019-06-14T17:20:54.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-06-14 17:20:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=581938
;

-- 2019-06-14T17:20:58.127
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-06-14 17:20:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=581939
;

-- 2019-06-14T17:21:02.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-06-14 17:21:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=581940
;

-- 2019-06-14T17:21:09.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-06-14 17:21:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=581942
;

-- 2019-06-14T17:22:20.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2019-06-14 17:22:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568236
;

-- 2019-06-14T17:23:28.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2019-06-14 17:23:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568231
;

-- 2019-06-14T17:23:48.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2019-06-14 17:23:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568237
;

-- 2019-06-14T17:23:57.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2019-06-14 17:23:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568238
;

-- 2019-06-14T17:53:02.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.dataentry.data.impexp.DataEntryRecordsImportProcessLauncher',Updated=TO_TIMESTAMP('2019-06-14 17:53:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541147
;

