-- 2019-12-05T20:33:05.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('1',0,0,541216,'Y','de.metas.ui.web.shipment_candidates_editor.process.M_ShipmentSchedule_ShipmentCandidate_Set_BestBeforeDate','N',TO_TIMESTAMP('2019-12-05 21:33:04','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.inoutcandidate','Y','N','N','N','N','N','N','Y','Y',0,'Mindesthaltbarkeitsdatum setzen','N','Y','Java',TO_TIMESTAMP('2019-12-05 21:33:04','YYYY-MM-DD HH24:MI:SS'),100,'M_ShipmentSchedule_ShipmentCandidate_Set_BestBeforeDate')
;

-- 2019-12-05T20:33:05.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541216 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-12-05T20:33:13.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-05 21:33:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541216
;

-- 2019-12-05T20:33:22.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Set best before date',Updated=TO_TIMESTAMP('2019-12-05 21:33:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541216
;

-- 2019-12-05T20:34:00.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577375,0,541216,541637,15,'BestBeforeDate',TO_TIMESTAMP('2019-12-05 21:33:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','Mindesthaltbarkeitsdatum',10,TO_TIMESTAMP('2019-12-05 21:33:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-05T20:34:00.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541637 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-12-05T21:52:17.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.shipment_candidates_editor.process.M_ShipmentSchedule_Set_BestBeforeDate', Value='M_ShipmentSchedule_Set_BestBeforeDate',Updated=TO_TIMESTAMP('2019-12-05 22:52:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541216
;

-- 2019-12-05T21:52:53.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,541216,500221,540758,TO_TIMESTAMP('2019-12-05 22:52:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y',TO_TIMESTAMP('2019-12-05 22:52:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N')
;

-- 2019-12-05T21:55:58.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction='N',Updated=TO_TIMESTAMP('2019-12-05 22:55:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540758
;

-- 2019-12-05T22:06:34.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Erstellt oder überschreibt das Lieferdispo-Merkmal "Mindesthaltbarkeitsdatum" für alle ausgewählten Lieferdispo-Datensätze.',Updated=TO_TIMESTAMP('2019-12-05 23:06:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541216
;

-- 2019-12-05T22:06:39.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Erstellt oder überschreibt das Lieferdispo-Merkmal "Mindesthaltbarkeitsdatum" für alle ausgewählten Lieferdispo-Datensätze.',Updated=TO_TIMESTAMP('2019-12-05 23:06:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541216
;

-- 2019-12-05T22:06:44.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Erstellt oder überschreibt das Lieferdispo-Merkmal "Mindesthaltbarkeitsdatum" für alle ausgewählten Lieferdispo-Datensätze.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-05 23:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541216
;

-- 2019-12-05T22:07:20.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Creates or updates the the "best before date" attribute for all selected shipment schedule records',Updated=TO_TIMESTAMP('2019-12-05 23:07:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541216
;

-- 2019-12-05T22:11:23.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Erstellt oder überschreibt das Lieferdispo-Merkmal "Mindesthaltbarkeitsdatum" für alle ausgewählten Lieferdispo-Datensätze.
Bereits verarbeitete Datensätze sowie Datensätze, die gerade für einen Lieferlauf markiert sind ("Auswahl Liefern"), werden dabei ignoriert.',Updated=TO_TIMESTAMP('2019-12-05 23:11:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541216
;

-- 2019-12-05T22:11:32.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Erstellt oder überschreibt das Lieferdispo-Merkmal "Mindesthaltbarkeitsdatum" für alle ausgewählten Lieferdispo-Datensätze.
Bereits verarbeitete Datensätze sowie Datensätze, die gerade für einen Lieferlauf markiert sind ("Auswahl Liefern"), werden dabei ignoriert.',Updated=TO_TIMESTAMP('2019-12-05 23:11:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541216
;

-- 2019-12-05T22:11:36.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Erstellt oder überschreibt das Lieferdispo-Merkmal "Mindesthaltbarkeitsdatum" für alle ausgewählten Lieferdispo-Datensätze.
Bereits verarbeitete Datensätze sowie Datensätze, die gerade für einen Lieferlauf markiert sind ("Auswahl Liefern"), werden dabei ignoriert.',Updated=TO_TIMESTAMP('2019-12-05 23:11:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541216
;

-- 2019-12-05T22:12:50.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Mindesthaltbarkeitsdatum ändern',Updated=TO_TIMESTAMP('2019-12-05 23:12:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541216
;

-- 2019-12-05T22:12:55.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Mindesthaltbarkeitsdatum ändern',Updated=TO_TIMESTAMP('2019-12-05 23:12:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541216
;

-- 2019-12-05T22:13:00.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Mindesthaltbarkeitsdatum ändern',Updated=TO_TIMESTAMP('2019-12-05 23:13:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541216
;

