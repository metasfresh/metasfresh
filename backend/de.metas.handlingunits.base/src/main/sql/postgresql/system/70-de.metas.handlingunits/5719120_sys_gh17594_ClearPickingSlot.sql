-- Value: ClearPickingSlot
-- Classname: de.metas.handlingunits.picking.process.ClearPickingSlot
-- 2024-03-13T11:57:02.857Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585360,'Y','de.metas.handlingunits.picking.process.ClearPickingSlot','N',TO_TIMESTAMP('2024-03-13 13:57:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'ClearPickingSlot','json','N','N','xls','Java',TO_TIMESTAMP('2024-03-13 13:57:02','YYYY-MM-DD HH24:MI:SS'),100,'ClearPickingSlot')
;

-- 2024-03-13T11:57:02.869Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585360 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- ParameterName: AbortOngoingPickingJobs
-- 2024-03-13T11:58:00.198Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,585360,542787,20,'AbortOngoingPickingJobs',TO_TIMESTAMP('2024-03-13 13:58:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',1,'Y','N','Y','N','Y','N','AbortOngoingPickingJobs',10,TO_TIMESTAMP('2024-03-13 13:58:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-13T11:58:00.204Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542787 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- ParameterName: RemoveUnprocessedHUsFromSlot
-- 2024-03-13T11:58:21.548Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,585360,542788,20,'RemoveUnprocessedHUsFromSlot',TO_TIMESTAMP('2024-03-13 13:58:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',1,'Y','N','Y','N','Y','N','RemoveUnprocessedHUsFromSlot',20,TO_TIMESTAMP('2024-03-13 13:58:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-13T11:58:21.554Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542788 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- ParameterName: RemoveQueuedHUsFromSlot
-- 2024-03-13T11:58:41.555Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,585360,542789,20,'RemoveQueuedHUsFromSlot',TO_TIMESTAMP('2024-03-13 13:58:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',1,'Y','N','Y','N','Y','N','RemoveQueuedHUsFromSlot',30,TO_TIMESTAMP('2024-03-13 13:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-13T11:58:41.558Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542789 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- Table: M_PickingSlot
-- EntityType: de.metas.handlingunits
-- 2024-03-13T12:02:00.483Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585360,540543,541464,TO_TIMESTAMP('2024-03-13 14:02:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',TO_TIMESTAMP('2024-03-13 14:02:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Value: PickingSlot_ChangeIsDynamic
-- Classname: de.metas.handlingunits.picking.process.PickingSlot_ChangeIsDynamic
-- 2024-03-13T12:03:01.866Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585361,'Y','de.metas.handlingunits.picking.process.PickingSlot_ChangeIsDynamic','N',TO_TIMESTAMP('2024-03-13 14:03:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'PickingSlot_ChangeIsDynamic','json','N','N','xls','Java',TO_TIMESTAMP('2024-03-13 14:03:01','YYYY-MM-DD HH24:MI:SS'),100,'PickingSlot_ChangeIsDynamic')
;

-- 2024-03-13T12:03:01.868Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585361 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: PickingSlot_ChangeIsDynamic(de.metas.handlingunits.picking.process.PickingSlot_ChangeIsDynamic)
-- ParameterName: IsDynamic
-- 2024-03-13T12:03:46.922Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,585361,542790,20,'IsDynamic',TO_TIMESTAMP('2024-03-13 14:03:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',1,'Y','N','Y','N','Y','N','IsDynamic',10,TO_TIMESTAMP('2024-03-13 14:03:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-13T12:03:46.924Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542790 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: PickingSlot_ChangeIsDynamic(de.metas.handlingunits.picking.process.PickingSlot_ChangeIsDynamic)
-- Table: M_PickingSlot
-- EntityType: de.metas.handlingunits
-- 2024-03-13T12:04:06.579Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585361,540543,541465,TO_TIMESTAMP('2024-03-13 14:04:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',TO_TIMESTAMP('2024-03-13 14:04:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Value: ClearPickingSlot
-- Classname: de.metas.handlingunits.picking.process.ClearPickingSlot
-- 2024-03-13T12:09:05.034Z
UPDATE AD_Process SET Description='If none of the parameters is checked, metasfresh tries to release the picking slot performing the following actions: first, it checks for allocated but not started picking jobs (i.e. a job where nothing was picked) and performs an ''Abort'' operation. Then, if the slot is not allocated to any other ongoing jobs, or ongoing pickings performed in the Picking Terminal, and no HUs were already picked and queued on it, the slot is released.',Updated=TO_TIMESTAMP('2024-03-13 14:09:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585360
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- 2024-03-13T12:09:11.292Z
UPDATE AD_Process_Trl SET Description='Wenn keiner der Parameter geprüft wird, versucht metasfresh, den Kommissionier-Slot wie folgt zu relaisieren: Zunächst wird geprüft, ob es sich um einen zugewiesenen, aber nicht gestarteten Kommissionierauftrag handelt (d. h. einen Auftrag, bei dem nichts kommissioniert wurde), und es wird eine "Abbruch"-Operation durchgeführt. Wenn der Slot dann keinen anderen laufenden Aufträgen oder laufenden Kommissionierungen im Kommissionierterminal zugewiesen ist und keine HUs bereits kommissioniert wurden und in der Warteschlange stehen, wird der Slot freigegeben.',Updated=TO_TIMESTAMP('2024-03-13 14:09:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585360
;

-- Value: ClearPickingSlot
-- Classname: de.metas.handlingunits.picking.process.ClearPickingSlot
-- 2024-03-13T12:09:13.627Z
UPDATE AD_Process SET Description='Wenn keiner der Parameter geprüft wird, versucht metasfresh, den Kommissionier-Slot wie folgt zu relaisieren: Zunächst wird geprüft, ob es sich um einen zugewiesenen, aber nicht gestarteten Kommissionierauftrag handelt (d. h. einen Auftrag, bei dem nichts kommissioniert wurde), und es wird eine "Abbruch"-Operation durchgeführt. Wenn der Slot dann keinen anderen laufenden Aufträgen oder laufenden Kommissionierungen im Kommissionierterminal zugewiesen ist und keine HUs bereits kommissioniert wurden und in der Warteschlange stehen, wird der Slot freigegeben.', Help=NULL, Name='ClearPickingSlot',Updated=TO_TIMESTAMP('2024-03-13 14:09:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585360
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- 2024-03-13T12:09:13.623Z
UPDATE AD_Process_Trl SET Description='Wenn keiner der Parameter geprüft wird, versucht metasfresh, den Kommissionier-Slot wie folgt zu relaisieren: Zunächst wird geprüft, ob es sich um einen zugewiesenen, aber nicht gestarteten Kommissionierauftrag handelt (d. h. einen Auftrag, bei dem nichts kommissioniert wurde), und es wird eine "Abbruch"-Operation durchgeführt. Wenn der Slot dann keinen anderen laufenden Aufträgen oder laufenden Kommissionierungen im Kommissionierterminal zugewiesen ist und keine HUs bereits kommissioniert wurden und in der Warteschlange stehen, wird der Slot freigegeben.',Updated=TO_TIMESTAMP('2024-03-13 14:09:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585360
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- 2024-03-13T12:09:33.661Z
UPDATE AD_Process_Trl SET Description='Wenn keiner der Parameter geprüft wird, versucht metasfresh, den Kommissionier-Slot wie folgt zu relaisieren: Zunächst wird geprüft, ob es sich um einen zugewiesenen, aber nicht gestarteten Kommissionierauftrag handelt (d. h. einen Auftrag, bei dem nichts kommissioniert wurde), und es wird eine "Abbruch"-Operation durchgeführt. Wenn der Slot dann keinen anderen laufenden Aufträgen oder laufenden Kommissionierungen im Kommissionierterminal zugewiesen ist und keine HUs bereits kommissioniert wurden und in der Warteschlange stehen, wird der Slot freigegeben.',Updated=TO_TIMESTAMP('2024-03-13 14:09:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585360
;

-- Value: ClearPickingSlot
-- Classname: de.metas.handlingunits.picking.process.ClearPickingSlot
-- 2024-03-13T12:09:37.016Z
UPDATE AD_Process SET Description='Wenn keiner der Parameter geprüft wird, versucht metasfresh, den Kommissionier-Slot wie folgt zu relaisieren: Zunächst wird geprüft, ob es sich um einen zugewiesenen, aber nicht gestarteten Kommissionierauftrag handelt (d. h. einen Auftrag, bei dem nichts kommissioniert wurde), und es wird eine "Abbruch"-Operation durchgeführt. Wenn der Slot dann keinen anderen laufenden Aufträgen oder laufenden Kommissionierungen im Kommissionierterminal zugewiesen ist und keine HUs bereits kommissioniert wurden und in der Warteschlange stehen, wird der Slot freigegeben.', Help=NULL, Name='ClearPickingSlot',Updated=TO_TIMESTAMP('2024-03-13 14:09:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585360
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- 2024-03-13T12:09:37.013Z
UPDATE AD_Process_Trl SET Description='Wenn keiner der Parameter geprüft wird, versucht metasfresh, den Kommissionier-Slot wie folgt zu relaisieren: Zunächst wird geprüft, ob es sich um einen zugewiesenen, aber nicht gestarteten Kommissionierauftrag handelt (d. h. einen Auftrag, bei dem nichts kommissioniert wurde), und es wird eine "Abbruch"-Operation durchgeführt. Wenn der Slot dann keinen anderen laufenden Aufträgen oder laufenden Kommissionierungen im Kommissionierterminal zugewiesen ist und keine HUs bereits kommissioniert wurden und in der Warteschlange stehen, wird der Slot freigegeben.',Updated=TO_TIMESTAMP('2024-03-13 14:09:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585360
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- 2024-03-13T12:09:57.205Z
UPDATE AD_Process_Trl SET Description='If none of the parameters is checked, metasfresh tries to release the picking slot performing the following actions: first, it checks for allocated but not started picking jobs (i.e. a job where nothing was picked) and performs an ''Abort'' operation. Then, if the slot is not allocated to any other ongoing jobs, or ongoing pickings performed in the Picking Terminal, and no HUs were already picked and queued on it, the slot is released.',Updated=TO_TIMESTAMP('2024-03-13 14:09:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585360
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- 2024-03-13T12:10:24.417Z
UPDATE AD_Process_Trl SET Name='Clear Picking Slot',Updated=TO_TIMESTAMP('2024-03-13 14:10:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585360
;

-- Value: ClearPickingSlot
-- Classname: de.metas.handlingunits.picking.process.ClearPickingSlot
-- 2024-03-13T12:10:39.128Z
UPDATE AD_Process SET Description='Wenn keiner der Parameter geprüft wird, versucht metasfresh, den Kommissionier-Slot wie folgt zu relaisieren: Zunächst wird geprüft, ob es sich um einen zugewiesenen, aber nicht gestarteten Kommissionierauftrag handelt (d. h. einen Auftrag, bei dem nichts kommissioniert wurde), und es wird eine "Abbruch"-Operation durchgeführt. Wenn der Slot dann keinen anderen laufenden Aufträgen oder laufenden Kommissionierungen im Kommissionierterminal zugewiesen ist und keine HUs bereits kommissioniert wurden und in der Warteschlange stehen, wird der Slot freigegeben.', Help=NULL, Name='Kommissionierschlitz freigeben',Updated=TO_TIMESTAMP('2024-03-13 14:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585360
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- 2024-03-13T12:10:39.123Z
UPDATE AD_Process_Trl SET Name='Kommissionierschlitz freigeben',Updated=TO_TIMESTAMP('2024-03-13 14:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585360
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- 2024-03-13T12:10:47.428Z
UPDATE AD_Process_Trl SET Name='Kommissionierschlitz freigeben',Updated=TO_TIMESTAMP('2024-03-13 14:10:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585360
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- 2024-03-13T12:10:53.480Z
UPDATE AD_Process_Trl SET Name='Kommissionierschlitz freigeben',Updated=TO_TIMESTAMP('2024-03-13 14:10:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585360
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- 2024-03-13T12:10:56.132Z
UPDATE AD_Process_Trl SET Name='Kommissionierschlitz freigeben',Updated=TO_TIMESTAMP('2024-03-13 14:10:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585360
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- 2024-03-13T12:11:09.947Z
UPDATE AD_Process_Trl SET Name='Release picking slot',Updated=TO_TIMESTAMP('2024-03-13 14:11:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585360
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- ParameterName: AbortOngoingPickingJobs
-- 2024-03-13T12:11:34.947Z
UPDATE AD_Process_Para SET Name='Abort ongoing picking jobs',Updated=TO_TIMESTAMP('2024-03-13 14:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542787
;

-- 2024-03-13T12:11:43.008Z
UPDATE AD_Process_Para_Trl SET Name='Abbrechen von laufenden Kommissionieraufträgen',Updated=TO_TIMESTAMP('2024-03-13 14:11:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_Para_ID=542787
;

-- 2024-03-13T12:11:49.349Z
UPDATE AD_Process_Para_Trl SET Name='Abbrechen von laufenden Kommissionieraufträgen',Updated=TO_TIMESTAMP('2024-03-13 14:11:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_Para_ID=542787
;

-- 2024-03-13T12:11:51.761Z
UPDATE AD_Process_Para_Trl SET Name='Abbrechen von laufenden Kommissionieraufträgen',Updated=TO_TIMESTAMP('2024-03-13 14:11:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_Para_ID=542787
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- ParameterName: AbortOngoingPickingJobs
-- 2024-03-13T12:12:03.024Z
UPDATE AD_Process_Para SET Name='Abbrechen von laufenden Kommissionieraufträgen',Updated=TO_TIMESTAMP('2024-03-13 14:12:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542787
;

-- 2024-03-13T12:12:08.013Z
UPDATE AD_Process_Para_Trl SET Name='Abort ongoing picking jobs',Updated=TO_TIMESTAMP('2024-03-13 14:12:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=542787
;

-- 2024-03-13T12:12:43.516Z
UPDATE AD_Process_Para_Trl SET Description='If checked, the process will also abort ongoing jobs (jobs where something was already picked but the job was not completed).',Updated=TO_TIMESTAMP('2024-03-13 14:12:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=542787
;

-- 2024-03-13T12:12:58.690Z
UPDATE AD_Process_Para_Trl SET Description='Wenn diese Option aktiviert ist, bricht der Prozess auch laufende Aufträge ab (Aufträge, bei denen bereits etwas kommissioniert wurde, der Auftrag aber noch nicht abgeschlossen ist).',Updated=TO_TIMESTAMP('2024-03-13 14:12:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_Para_ID=542787
;

-- 2024-03-13T12:13:02.310Z
UPDATE AD_Process_Para_Trl SET Description='Wenn diese Option aktiviert ist, bricht der Prozess auch laufende Aufträge ab (Aufträge, bei denen bereits etwas kommissioniert wurde, der Auftrag aber noch nicht abgeschlossen ist).',Updated=TO_TIMESTAMP('2024-03-13 14:13:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_Para_ID=542787
;

-- 2024-03-13T12:13:04.300Z
UPDATE AD_Process_Para_Trl SET Description='Wenn diese Option aktiviert ist, bricht der Prozess auch laufende Aufträge ab (Aufträge, bei denen bereits etwas kommissioniert wurde, der Auftrag aber noch nicht abgeschlossen ist).',Updated=TO_TIMESTAMP('2024-03-13 14:13:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_Para_ID=542787
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- ParameterName: AbortOngoingPickingJobs
-- 2024-03-13T12:13:09.697Z
UPDATE AD_Process_Para SET Description='Wenn diese Option aktiviert ist, bricht der Prozess auch laufende Aufträge ab (Aufträge, bei denen bereits etwas kommissioniert wurde, der Auftrag aber noch nicht abgeschlossen ist).',Updated=TO_TIMESTAMP('2024-03-13 14:13:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542787
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- ParameterName: RemoveUnprocessedHUsFromSlot
-- 2024-03-13T12:13:28.471Z
UPDATE AD_Process_Para SET Name='Unpick non-processed HUs from Picking Terminal',Updated=TO_TIMESTAMP('2024-03-13 14:13:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542788
;

-- 2024-03-13T12:13:35.532Z
UPDATE AD_Process_Para_Trl SET Name='Unpick non-processed HUs from Picking Terminal',Updated=TO_TIMESTAMP('2024-03-13 14:13:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=542788
;

-- 2024-03-13T12:14:01.419Z
UPDATE AD_Process_Para_Trl SET Name='Nicht verarbeitete HUs aus dem Kommissionierterminal entpacken',Updated=TO_TIMESTAMP('2024-03-13 14:14:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_Para_ID=542788
;

-- 2024-03-13T12:14:03.950Z
UPDATE AD_Process_Para_Trl SET Name='Nicht verarbeitete HUs aus dem Kommissionierterminal entpacken',Updated=TO_TIMESTAMP('2024-03-13 14:14:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_Para_ID=542788
;

-- 2024-03-13T12:14:08.737Z
UPDATE AD_Process_Para_Trl SET Name='Nicht verarbeitete HUs aus dem Kommissionierterminal entpacken',Updated=TO_TIMESTAMP('2024-03-13 14:14:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_Para_ID=542788
;

-- 2024-03-13T12:14:30.479Z
UPDATE AD_Process_Para_Trl SET Description='If checked, the process will also unpick any candidate-HUs from the Picking Terminal ( HUs that were picked but the picking was not yet processed).',Updated=TO_TIMESTAMP('2024-03-13 14:14:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=542788
;

-- 2024-03-13T12:14:36.600Z
UPDATE AD_Process_Para_Trl SET Description='Wenn dieses Kontrollkästchen aktiviert ist, werden auch alle Kandidaten-HUs aus dem Kommissionierungsterminal entnommen (HUs, die kommissioniert wurden, aber die Kommissionierung noch nicht verarbeitet wurde).',Updated=TO_TIMESTAMP('2024-03-13 14:14:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_Para_ID=542788
;

-- 2024-03-13T12:14:40.934Z
UPDATE AD_Process_Para_Trl SET Description='Wenn dieses Kontrollkästchen aktiviert ist, werden auch alle Kandidaten-HUs aus dem Kommissionierungsterminal entnommen (HUs, die kommissioniert wurden, aber die Kommissionierung noch nicht verarbeitet wurde).',Updated=TO_TIMESTAMP('2024-03-13 14:14:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_Para_ID=542788
;

-- 2024-03-13T12:14:43.729Z
UPDATE AD_Process_Para_Trl SET Description='Wenn dieses Kontrollkästchen aktiviert ist, werden auch alle Kandidaten-HUs aus dem Kommissionierungsterminal entnommen (HUs, die kommissioniert wurden, aber die Kommissionierung noch nicht verarbeitet wurde).',Updated=TO_TIMESTAMP('2024-03-13 14:14:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_Para_ID=542788
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- ParameterName: RemoveUnprocessedHUsFromSlot
-- 2024-03-13T12:14:49.162Z
UPDATE AD_Process_Para SET Description='Wenn dieses Kontrollkästchen aktiviert ist, werden auch alle Kandidaten-HUs aus dem Kommissionierungsterminal entnommen (HUs, die kommissioniert wurden, aber die Kommissionierung noch nicht verarbeitet wurde).',Updated=TO_TIMESTAMP('2024-03-13 14:14:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542788
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- ParameterName: RemoveQueuedHUsFromSlot
-- 2024-03-13T12:40:10.654Z
UPDATE AD_Process_Para SET Name='Remove queued HUs',Updated=TO_TIMESTAMP('2024-03-13 14:40:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542789
;

-- 2024-03-13T12:40:13.929Z
UPDATE AD_Process_Para_Trl SET Name='Remove queued HUs',Updated=TO_TIMESTAMP('2024-03-13 14:40:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=542789
;

-- 2024-03-13T12:40:27.324Z
UPDATE AD_Process_Para_Trl SET Name='HUs in der Warteschlange entfernen',Updated=TO_TIMESTAMP('2024-03-13 14:40:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_Para_ID=542789
;

-- 2024-03-13T12:40:30.472Z
UPDATE AD_Process_Para_Trl SET Name='HUs in der Warteschlange entfernen',Updated=TO_TIMESTAMP('2024-03-13 14:40:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_Para_ID=542789
;

-- 2024-03-13T12:40:33.044Z
UPDATE AD_Process_Para_Trl SET Name='HUs in der Warteschlange entfernen',Updated=TO_TIMESTAMP('2024-03-13 14:40:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_Para_ID=542789
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- ParameterName: RemoveQueuedHUsFromSlot
-- 2024-03-13T12:41:03.105Z
UPDATE AD_Process_Para SET Description='If checked, the process will remove any HUs already queued on the slot (those are HUs that have been successfully picked but not yet shipped).',Updated=TO_TIMESTAMP('2024-03-13 14:41:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542789
;

-- 2024-03-13T12:41:09.814Z
UPDATE AD_Process_Para_Trl SET Description='If checked, the process will remove any HUs already queued on the slot (those are HUs that have been successfully picked but not yet shipped).',Updated=TO_TIMESTAMP('2024-03-13 14:41:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=542789
;

-- 2024-03-13T12:41:15.185Z
UPDATE AD_Process_Para_Trl SET Description='Wenn diese Option aktiviert ist, entfernt der Prozess alle HUs, die sich bereits in der Warteschlange des Slots befinden (das sind HUs, die erfolgreich kommissioniert, aber noch nicht versandt wurden).',Updated=TO_TIMESTAMP('2024-03-13 14:41:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_Para_ID=542789
;

-- 2024-03-13T12:41:20.820Z
UPDATE AD_Process_Para_Trl SET Description='Wenn diese Option aktiviert ist, entfernt der Prozess alle HUs, die sich bereits in der Warteschlange des Slots befinden (das sind HUs, die erfolgreich kommissioniert, aber noch nicht versandt wurden).',Updated=TO_TIMESTAMP('2024-03-13 14:41:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_Para_ID=542789
;

-- 2024-03-13T12:41:24.006Z
UPDATE AD_Process_Para_Trl SET Description='Wenn diese Option aktiviert ist, entfernt der Prozess alle HUs, die sich bereits in der Warteschlange des Slots befinden (das sind HUs, die erfolgreich kommissioniert, aber noch nicht versandt wurden).',Updated=TO_TIMESTAMP('2024-03-13 14:41:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_Para_ID=542789
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- ParameterName: RemoveQueuedHUsFromSlot
-- 2024-03-13T12:41:31.025Z
UPDATE AD_Process_Para SET Description='Wenn diese Option aktiviert ist, entfernt der Prozess alle HUs, die sich bereits in der Warteschlange des Slots befinden (das sind HUs, die erfolgreich kommissioniert, aber noch nicht versandt wurden).',Updated=TO_TIMESTAMP('2024-03-13 14:41:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542789
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- ParameterName: RemoveQueuedHUsFromSlot
-- 2024-03-13T12:41:47.117Z
UPDATE AD_Process_Para SET Name='HUs in der Warteschlange entfernen',Updated=TO_TIMESTAMP('2024-03-13 14:41:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542789
;

-- Value: PickingSlot_ChangeIsDynamic
-- Classname: de.metas.handlingunits.picking.process.PickingSlot_ChangeIsDynamic
-- 2024-03-13T12:43:00.030Z
UPDATE AD_Process SET Name='Change Dynamic Allocation',Updated=TO_TIMESTAMP('2024-03-13 14:43:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585361
;

-- Value: PickingSlot_ChangeIsDynamic
-- Classname: de.metas.handlingunits.picking.process.PickingSlot_ChangeIsDynamic
-- 2024-03-13T12:43:49.785Z
UPDATE AD_Process SET Description='When switching off the dynamic allocation, the process will try to release the slot, if that''s not possible, the operation will fail with a suggested action to take.',Updated=TO_TIMESTAMP('2024-03-13 14:43:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585361
;

-- Process: PickingSlot_ChangeIsDynamic(de.metas.handlingunits.picking.process.PickingSlot_ChangeIsDynamic)
-- 2024-03-13T12:44:00.545Z
UPDATE AD_Process_Trl SET Name='Dynamische Zuweisung ändern',Updated=TO_TIMESTAMP('2024-03-13 14:44:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585361
;

-- Value: PickingSlot_ChangeIsDynamic
-- Classname: de.metas.handlingunits.picking.process.PickingSlot_ChangeIsDynamic
-- 2024-03-13T12:44:03.499Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Dynamische Zuweisung ändern',Updated=TO_TIMESTAMP('2024-03-13 14:44:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585361
;

-- Process: PickingSlot_ChangeIsDynamic(de.metas.handlingunits.picking.process.PickingSlot_ChangeIsDynamic)
-- 2024-03-13T12:44:03.494Z
UPDATE AD_Process_Trl SET Name='Dynamische Zuweisung ändern',Updated=TO_TIMESTAMP('2024-03-13 14:44:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585361
;

-- Process: PickingSlot_ChangeIsDynamic(de.metas.handlingunits.picking.process.PickingSlot_ChangeIsDynamic)
-- 2024-03-13T12:44:07.523Z
UPDATE AD_Process_Trl SET Name='Dynamische Zuweisung ändern',Updated=TO_TIMESTAMP('2024-03-13 14:44:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585361
;

-- Process: PickingSlot_ChangeIsDynamic(de.metas.handlingunits.picking.process.PickingSlot_ChangeIsDynamic)
-- 2024-03-13T12:44:10.675Z
UPDATE AD_Process_Trl SET Name='Dynamische Zuweisung ändern',Updated=TO_TIMESTAMP('2024-03-13 14:44:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585361
;

-- Process: PickingSlot_ChangeIsDynamic(de.metas.handlingunits.picking.process.PickingSlot_ChangeIsDynamic)
-- 2024-03-13T12:44:15.902Z
UPDATE AD_Process_Trl SET Name='Change Dynamic Allocation',Updated=TO_TIMESTAMP('2024-03-13 14:44:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585361
;

-- Process: PickingSlot_ChangeIsDynamic(de.metas.handlingunits.picking.process.PickingSlot_ChangeIsDynamic)
-- 2024-03-13T12:44:29.230Z
UPDATE AD_Process_Trl SET Description='When switching off the dynamic allocation, the process will try to release the slot, if that''s not possible, the operation will fail with a suggested action to take.',Updated=TO_TIMESTAMP('2024-03-13 14:44:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585361
;

-- Value: PickingSlot_ChangeIsDynamic
-- Classname: de.metas.handlingunits.picking.process.PickingSlot_ChangeIsDynamic
-- 2024-03-13T12:44:36.942Z
UPDATE AD_Process SET Description='Wenn die dynamische Zuweisung ausgeschaltet wird, versucht der Prozess, den Steckplatz freizugeben. Wenn das nicht möglich ist, schlägt der Vorgang fehl und es wird eine Maßnahme vorgeschlagen.', Help=NULL, Name='Dynamische Zuweisung ändern',Updated=TO_TIMESTAMP('2024-03-13 14:44:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585361
;

-- Process: PickingSlot_ChangeIsDynamic(de.metas.handlingunits.picking.process.PickingSlot_ChangeIsDynamic)
-- 2024-03-13T12:44:36.938Z
UPDATE AD_Process_Trl SET Description='Wenn die dynamische Zuweisung ausgeschaltet wird, versucht der Prozess, den Steckplatz freizugeben. Wenn das nicht möglich ist, schlägt der Vorgang fehl und es wird eine Maßnahme vorgeschlagen.',Updated=TO_TIMESTAMP('2024-03-13 14:44:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585361
;

-- Process: PickingSlot_ChangeIsDynamic(de.metas.handlingunits.picking.process.PickingSlot_ChangeIsDynamic)
-- 2024-03-13T12:44:39.131Z
UPDATE AD_Process_Trl SET Description='Wenn die dynamische Zuweisung ausgeschaltet wird, versucht der Prozess, den Steckplatz freizugeben. Wenn das nicht möglich ist, schlägt der Vorgang fehl und es wird eine Maßnahme vorgeschlagen.',Updated=TO_TIMESTAMP('2024-03-13 14:44:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585361
;

-- Process: PickingSlot_ChangeIsDynamic(de.metas.handlingunits.picking.process.PickingSlot_ChangeIsDynamic)
-- 2024-03-13T12:44:43.065Z
UPDATE AD_Process_Trl SET Description='Wenn die dynamische Zuweisung ausgeschaltet wird, versucht der Prozess, den Steckplatz freizugeben. Wenn das nicht möglich ist, schlägt der Vorgang fehl und es wird eine Maßnahme vorgeschlagen.',Updated=TO_TIMESTAMP('2024-03-13 14:44:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585361
;

-- Process: PickingSlot_ChangeIsDynamic(de.metas.handlingunits.picking.process.PickingSlot_ChangeIsDynamic)
-- ParameterName: IsDynamic
-- 2024-03-13T12:47:02.566Z
UPDATE AD_Process_Para SET Name='Is Dynamic',Updated=TO_TIMESTAMP('2024-03-13 14:47:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542790
;

-- 2024-03-13T12:47:11.983Z
UPDATE AD_Process_Para_Trl SET Name='Is Dynamic',Updated=TO_TIMESTAMP('2024-03-13 14:47:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_Para_ID=542790
;

-- 2024-03-13T12:47:15.356Z
UPDATE AD_Process_Para_Trl SET Name='Is Dynamic',Updated=TO_TIMESTAMP('2024-03-13 14:47:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=542790
;

-- 2024-03-13T12:47:26.504Z
UPDATE AD_Process_Para_Trl SET Name='Ist dynamisch',Updated=TO_TIMESTAMP('2024-03-13 14:47:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_Para_ID=542790
;

-- 2024-03-13T12:47:29.133Z
UPDATE AD_Process_Para_Trl SET Name='Ist dynamisch',Updated=TO_TIMESTAMP('2024-03-13 14:47:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_Para_ID=542790
;

-- 2024-03-13T12:47:31.673Z
UPDATE AD_Process_Para_Trl SET Name='Ist dynamisch',Updated=TO_TIMESTAMP('2024-03-13 14:47:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_Para_ID=542790
;

-- Value: de.metas.handlingunits.picking.process.SLOT_CANNOT_BE_RELEASED
-- 2024-03-13T12:49:00.011Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545378,0,TO_TIMESTAMP('2024-03-13 14:48:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','The picking slot cannot be released, clear all assignments before updating the ''Is Dynamic'' property.','E',TO_TIMESTAMP('2024-03-13 14:48:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.process.SLOT_CANNOT_BE_RELEASED')
;

-- 2024-03-13T12:49:00.018Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545378 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.process.SLOT_CANNOT_BE_RELEASED
-- 2024-03-13T12:49:18.248Z
UPDATE AD_Message SET MsgText='Der Kommissionierplatz kann nicht freigegeben werden, löschen Sie alle Zuweisungen, bevor Sie die Eigenschaft "Ist dynamisch" aktualisieren.',Updated=TO_TIMESTAMP('2024-03-13 14:49:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545378
;

-- Value: de.metas.handlingunits.picking.process.SLOT_CANNOT_BE_RELEASED
-- 2024-03-13T12:49:25.450Z
UPDATE AD_Message_Trl SET MsgText='Der Kommissionierplatz kann nicht freigegeben werden, löschen Sie alle Zuweisungen, bevor Sie die Eigenschaft "Ist dynamisch" aktualisieren.',Updated=TO_TIMESTAMP('2024-03-13 14:49:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545378
;

-- Value: de.metas.handlingunits.picking.process.SLOT_CANNOT_BE_RELEASED
-- 2024-03-13T12:49:28.155Z
UPDATE AD_Message_Trl SET MsgText='Der Kommissionierplatz kann nicht freigegeben werden, löschen Sie alle Zuweisungen, bevor Sie die Eigenschaft "Ist dynamisch" aktualisieren.',Updated=TO_TIMESTAMP('2024-03-13 14:49:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545378
;

-- Value: de.metas.handlingunits.picking.process.SLOT_CANNOT_BE_RELEASED
-- 2024-03-13T12:49:32.144Z
UPDATE AD_Message_Trl SET MsgText='Der Kommissionierplatz kann nicht freigegeben werden, löschen Sie alle Zuweisungen, bevor Sie die Eigenschaft "Ist dynamisch" aktualisieren.',Updated=TO_TIMESTAMP('2024-03-13 14:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545378
;

-- Value: de.metas.handlingunits.picking.ONGOING_PICKING_JOBS_ERR_MSG
-- 2024-03-13T12:53:59.936Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value) VALUES (0,545379,0,TO_TIMESTAMP('2024-03-13 14:53:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Es gibt laufende Kommissionieraufträge mit bereits kommissionierter Menge! Sie können diese abbrechen, indem Sie die Option "Abbrechen von laufenden Kommissionieraufträgen" aktivieren.','','E',TO_TIMESTAMP('2024-03-13 14:53:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.ONGOING_PICKING_JOBS_ERR_MSG')
;

-- 2024-03-13T12:53:59.938Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545379 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.ONGOING_PICKING_JOBS_ERR_MSG
-- 2024-03-13T12:55:00.642Z
UPDATE AD_Message_Trl SET MsgText='There are ongoing picking jobs with qty already picked! You can abort them by enabling the ''Abort ongoing picking jobs'' option.',Updated=TO_TIMESTAMP('2024-03-13 14:55:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545379
;

-- Value: de.metas.handlingunits.picking.ONGOING_PICKING_JOBS_ERR_MSG
-- 2024-03-13T12:55:03.337Z
UPDATE AD_Message_Trl SET MsgText='There are ongoing picking jobs with qty already picked! You can abort them by enabling the ''Abort ongoing picking jobs'' option.',Updated=TO_TIMESTAMP('2024-03-13 14:55:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545379
;

-- Value: de.metas.handlingunits.picking.ONGOING_PICKING_JOBS_ERR_MSG
-- 2024-03-13T12:55:18.674Z
UPDATE AD_Message_Trl SET MsgText='Es gibt laufende Kommissionieraufträge mit bereits kommissionierter Menge! Sie können diese abbrechen, indem Sie die Option "Abbrechen von laufenden Kommissionieraufträgen" aktivieren.',Updated=TO_TIMESTAMP('2024-03-13 14:55:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545379
;

-- Value: de.metas.handlingunits.picking.DRAFTED_PICKING_CANDIDATES_ERR_MSG
-- 2024-03-13T12:57:41.327Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545380,0,TO_TIMESTAMP('2024-03-13 14:57:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','There are drafted picking candidates allocated to the target slot! You can delete them by enabling the ''Unpick non-processed HUs from Picking Terminal'' option.','E',TO_TIMESTAMP('2024-03-13 14:57:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.DRAFTED_PICKING_CANDIDATES_ERR_MSG')
;

-- 2024-03-13T12:57:41.329Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545380 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.DRAFTED_PICKING_CANDIDATES_ERR_MSG
-- 2024-03-13T12:58:22.224Z
UPDATE AD_Message_Trl SET MsgText='Es gibt eingezogene Kommissionierkandidaten, die dem Zielsteckplatz zugeordnet sind! Sie können diese löschen, indem Sie die Option "Nicht verarbeitete HUs aus dem Kommissionierterminal entpacken" aktivieren.',Updated=TO_TIMESTAMP('2024-03-13 14:58:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545380
;

-- Value: de.metas.handlingunits.picking.DRAFTED_PICKING_CANDIDATES_ERR_MSG
-- 2024-03-13T12:58:25.333Z
UPDATE AD_Message_Trl SET MsgText='Es gibt eingezogene Kommissionierkandidaten, die dem Zielsteckplatz zugeordnet sind! Sie können diese löschen, indem Sie die Option "Nicht verarbeitete HUs aus dem Kommissionierterminal entpacken" aktivieren.',Updated=TO_TIMESTAMP('2024-03-13 14:58:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545380
;

-- Value: de.metas.handlingunits.picking.DRAFTED_PICKING_CANDIDATES_ERR_MSG
-- 2024-03-13T12:58:30.682Z
UPDATE AD_Message_Trl SET MsgText='Es gibt eingezogene Kommissionierkandidaten, die dem Zielsteckplatz zugeordnet sind! Sie können diese löschen, indem Sie die Option "Nicht verarbeitete HUs aus dem Kommissionierterminal entpacken" aktivieren.',Updated=TO_TIMESTAMP('2024-03-13 14:58:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545380
;

-- Value: de.metas.handlingunits.picking.QUEUED_HUS_ON_SLOT_ERR_MSG
-- 2024-03-13T13:03:50.505Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545381,0,TO_TIMESTAMP('2024-03-13 15:03:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','There are queued HUs on the target slot! You can remove them by enabling the ''Remove queued HUs'' option.','E',TO_TIMESTAMP('2024-03-13 15:03:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.QUEUED_HUS_ON_SLOT_ERR_MSG')
;

-- 2024-03-13T13:03:50.507Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545381 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.QUEUED_HUS_ON_SLOT_ERR_MSG
-- 2024-03-13T13:04:25.970Z
UPDATE AD_Message SET MsgText='Auf dem Zielsteckplatz befinden sich HUs in der Warteschlange! Sie können diese entfernen, indem Sie die Option "HUs in der Warteschlange entfernen" aktivieren.',Updated=TO_TIMESTAMP('2024-03-13 15:04:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545381
;

-- Value: de.metas.handlingunits.picking.QUEUED_HUS_ON_SLOT_ERR_MSG
-- 2024-03-13T13:04:31.779Z
UPDATE AD_Message_Trl SET MsgText='Auf dem Zielsteckplatz befinden sich HUs in der Warteschlange! Sie können diese entfernen, indem Sie die Option "HUs in der Warteschlange entfernen" aktivieren.',Updated=TO_TIMESTAMP('2024-03-13 15:04:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545381
;

-- Value: de.metas.handlingunits.picking.QUEUED_HUS_ON_SLOT_ERR_MSG
-- 2024-03-13T13:04:34.134Z
UPDATE AD_Message_Trl SET MsgText='Auf dem Zielsteckplatz befinden sich HUs in der Warteschlange! Sie können diese entfernen, indem Sie die Option "HUs in der Warteschlange entfernen" aktivieren.',Updated=TO_TIMESTAMP('2024-03-13 15:04:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545381
;

-- Value: de.metas.handlingunits.picking.QUEUED_HUS_ON_SLOT_ERR_MSG
-- 2024-03-13T13:04:37.615Z
UPDATE AD_Message_Trl SET MsgText='Auf dem Zielsteckplatz befinden sich HUs in der Warteschlange! Sie können diese entfernen, indem Sie die Option "HUs in der Warteschlange entfernen" aktivieren.',Updated=TO_TIMESTAMP('2024-03-13 15:04:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545381
;

-- Column: M_PickingSlot.IsDynamic
-- Column: M_PickingSlot.IsDynamic
-- 2024-03-13T13:10:04.547Z
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2024-03-13 15:10:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550679
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- ParameterName: RemoveQueuedHUsFromSlot
-- 2024-03-13T13:14:53.485Z
UPDATE AD_Process_Para SET DefaultValue='N',Updated=TO_TIMESTAMP('2024-03-13 15:14:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542789
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- ParameterName: RemoveUnprocessedHUsFromSlot
-- 2024-03-13T13:14:59.176Z
UPDATE AD_Process_Para SET DefaultValue='N',Updated=TO_TIMESTAMP('2024-03-13 15:14:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542788
;

-- Process: ClearPickingSlot(de.metas.handlingunits.picking.process.ClearPickingSlot)
-- ParameterName: AbortOngoingPickingJobs
-- 2024-03-13T13:15:06.395Z
UPDATE AD_Process_Para SET DefaultValue='N',Updated=TO_TIMESTAMP('2024-03-13 15:15:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542787
;

-- Process: PickingSlot_ChangeIsDynamic(de.metas.handlingunits.picking.process.PickingSlot_ChangeIsDynamic)
-- ParameterName: IsDynamic
-- 2024-03-13T13:15:30.738Z
UPDATE AD_Process_Para SET DefaultValue='N',Updated=TO_TIMESTAMP('2024-03-13 15:15:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542790
;

