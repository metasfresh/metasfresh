-- 2017-07-26T08:46:57.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544459,0,TO_TIMESTAMP('2017-07-26 08:46:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Es muss eine verarbeitete HU-Zeile ausgewählt sen','I',TO_TIMESTAMP('2017-07-26 08:46:57','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Picking_No_Processed_Records')
;

-- 2017-07-26T08:46:57.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544459 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-07-26T08:49:54.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Es muss eine verarbeitete HU-Zeile ausgewählt sein',Updated=TO_TIMESTAMP('2017-07-26 08:49:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544459
;

-- 2017-07-26T09:57:53.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET EntityType='de.metas.picking', MsgText='Es muss eine unverarbeitete HU-Zeile ausgewählt sein',Updated=TO_TIMESTAMP('2017-07-26 09:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544451
;

-- 2017-07-26T09:58:37.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET EntityType='de.metas.picking', MsgText='Es muss eine Pickingslot-Zeile ausgewählt sein.',Updated=TO_TIMESTAMP('2017-07-26 09:58:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544458
;

-- 2017-07-26T09:58:51.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-26 09:58:51','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='A picking slot item shall be selected' WHERE AD_Message_ID=544458 AND AD_Language='en_US'
;

-- 2017-07-26T09:59:29.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-26 09:59:29','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='An unprocessed TU item shall be selected' WHERE AD_Message_ID=544451 AND AD_Language='en_US'
;

-- 2017-07-26T09:59:50.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-26 09:59:50','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='A processed TU item shall be selected' WHERE AD_Message_ID=544459 AND AD_Language='en_US'
;

-- 2017-07-26T10:09:23.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544460,0,TO_TIMESTAMP('2017-07-26 10:09:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Es muss eine kommissionierte Menge geben','I',TO_TIMESTAMP('2017-07-26 10:09:23','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Picking_PickSomething')
;

-- 2017-07-26T10:09:23.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544460 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-07-26T10:09:42.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-26 10:09:42','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='There shall be a picked quantity' WHERE AD_Message_ID=544460 AND AD_Language='en_US'
;

-- 2017-07-26T10:12:35.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544461,0,TO_TIMESTAMP('2017-07-26 10:12:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Es muss oberste HU-Ebene ausgewählt sein','I',TO_TIMESTAMP('2017-07-26 10:12:35','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Picking_Not_TopLevelHU')
;

-- 2017-07-26T10:12:35.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544461 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-07-26T10:12:49.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-26 10:12:49','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='A top level HU shall be selected' WHERE AD_Message_ID=544461 AND AD_Language='en_US'
;

-- 2017-07-26T10:13:02.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Es muss die oberste HU-Ebene ausgewählt sein',Updated=TO_TIMESTAMP('2017-07-26 10:13:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544461
;

-- 2017-07-26T10:15:37.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.picking.process.WEBUI_Picking_M_Picking_Candidate_Process', Value='WEBUI_Picking_M_Picking_Candidate_Process',Updated=TO_TIMESTAMP('2017-07-26 10:15:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540810
;

-- 2017-07-26T10:17:39.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Verknüpft die kommissionierten Datensätze mit den jeweiligen Lieferdispo-Daten, so dass bereits Lieferscheine erstellt werden können.', Name='Kommissionierung verarbeiten',Updated=TO_TIMESTAMP('2017-07-26 10:17:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540810
;

-- 2017-07-26T10:18:01.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-26 10:18:01','YYYY-MM-DD HH24:MI:SS'),Name='Process picking' WHERE AD_Process_ID=540810 AND AD_Language='en_US'
;

-- 2017-07-26T10:18:51.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-26 10:18:51','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Description='Associates the picking records with their respective shipment schedule records such that shipments can be created.' WHERE AD_Process_ID=540810 AND AD_Language='en_US'
;

-- 2017-07-26T10:18:59.835
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Verknüpft die kommissionierten Datensätze mit den jeweiligen Lieferdispo-Daten, so dass Lieferscheine erstellt werden können.',Updated=TO_TIMESTAMP('2017-07-26 10:18:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540810
;

-- 2017-07-26T10:25:20.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540815,'N','de.metas.ui.web.picking.process.WEBUI_Picking_M_Picking_Candidate_Unprocess','N',TO_TIMESTAMP('2017-07-26 10:25:20','YYYY-MM-DD HH24:MI:SS'),100,'Löst die Verknüpfung zwischen kommissionierten Datensätzen und den jeweiligen Lieferdispo-Daten.','U','Y','N','N','N','N','N','N','Y',0,'Kommissionierung reaktivieren','N','Y','Java',TO_TIMESTAMP('2017-07-26 10:25:20','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Picking_M_Picking_Candidate_Unprocess')
;

-- 2017-07-26T10:25:20.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540815 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-07-26T10:29:08.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-26 10:29:08','YYYY-MM-DD HH24:MI:SS'),Name='Reactivate picking',Description='Un-associates the selected picking data with their respective shipment schedules.' WHERE AD_Process_ID=540815 AND AD_Language='en_US'
;

-- 2017-07-26T10:37:41.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.picking.process.WEBUI_Picking_Start', Name='Kommissionieren', Value='WEBUI_Picking_Start',Updated=TO_TIMESTAMP('2017-07-26 10:37:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540801
;

-- 2017-07-26T10:37:52.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-26 10:37:52','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=540801 AND AD_Language='en_US'
;

-- 2017-07-26T12:50:33.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544462,0,TO_TIMESTAMP('2017-07-26 12:50:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','HU {1} hat den Status {2} (erfordericher Status ist {3})','E',TO_TIMESTAMP('2017-07-26 12:50:33','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Picking_Wrong_HU_Status')
;

-- 2017-07-26T12:50:33.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544462 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-07-26T12:58:01.859
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-26 12:58:01','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='HU {1} has status {2} (required status is {3})' WHERE AD_Message_ID=544462 AND AD_Language='en_US'
;

-- 2017-07-26T13:35:59.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544463,0,TO_TIMESTAMP('2017-07-26 13:35:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Zur HU {0} git es schon eine Lieferung mit Belegnummer {1}','E',TO_TIMESTAMP('2017-07-26 13:35:58','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Picking_Already_Shipped')
;

-- 2017-07-26T13:35:59.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544463 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-07-26T13:36:28.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-26 13:36:28','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='HU {0} already has a shipment with document number {1}' WHERE AD_Message_ID=544463 AND AD_Language='en_US'
;

-- 2017-07-26T13:45:58.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='HU {0} hat den Status {1} (erfordericher Status ist {2})',Updated=TO_TIMESTAMP('2017-07-26 13:45:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544462
;

-- 2017-07-26T13:46:07.245
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-26 13:46:07','YYYY-MM-DD HH24:MI:SS'),MsgText='HU {0} has status {1} (required status is {2})' WHERE AD_Message_ID=544462 AND AD_Language='en_US'
;

