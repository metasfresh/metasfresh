-- Value: PP_Order_Candidate_EnqueueSelectionForOrdering_Maturing
-- Classname: org.eevolution.productioncandidate.process.PP_Order_Candidate_EnqueueSelectionForOrdering
-- 2024-01-14T13:47:54.068Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585346,'Y','org.eevolution.productioncandidate.process.PP_Order_Candidate_EnqueueSelectionForOrdering','N',TO_TIMESTAMP('2024-01-14 15:47:53','YYYY-MM-DD HH24:MI:SS'),100,'Erstellt Produktionsaufträge zu den ausgewählten und noch nicht verarbeiteten Dispozeilen.','EE01','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Reifung für die Auswahl einleiten','json','N','Y','xls','Java',TO_TIMESTAMP('2024-01-14 15:47:53','YYYY-MM-DD HH24:MI:SS'),100,'PP_Order_Candidate_EnqueueSelectionForOrdering_Maturing')
;

-- 2024-01-14T13:47:54.070Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585346 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: PP_Order_Candidate_EnqueueSelectionForOrdering_Maturing(org.eevolution.productioncandidate.process.PP_Order_Candidate_EnqueueSelectionForOrdering)
-- 2024-01-14T13:48:26.117Z
UPDATE AD_Process_Trl SET Description='Creates production orders for the selected and not yet processed candidate lines.', Name='Initiate maturation for selection',Updated=TO_TIMESTAMP('2024-01-14 15:48:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585346
;

-- Process: PP_Order_Candidate_EnqueueSelectionForOrdering_Maturing(org.eevolution.productioncandidate.process.PP_Order_Candidate_EnqueueSelectionForOrdering)
-- ParameterName: IsDocComplete
-- 2024-01-14T13:49:21.736Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542644,0,585346,542767,20,'IsDocComplete',TO_TIMESTAMP('2024-01-14 15:49:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','Legt fest, ob ggf erstellte Belege (z.B. Produktionsaufträge) auch direkt automatisch fertig gestellt werden sollen.','1=0','EE01',0,'Y','N','Y','N','N','N','Beleg fertig stellen',10,TO_TIMESTAMP('2024-01-14 15:49:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-14T13:49:21.738Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542767 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: PP_Order_Candidate_EnqueueSelectionForOrdering_Maturing(org.eevolution.productioncandidate.process.PP_Order_Candidate_EnqueueSelectionForOrdering)
-- ParameterName: AutoProcessCandidatesAfterProduction
-- 2024-01-14T13:49:56.679Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581640,0,585346,542768,20,'AutoProcessCandidatesAfterProduction',TO_TIMESTAMP('2024-01-14 15:49:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','Wenn angehakt werden die ausgewählten Kandidaten nach dem Erstellen des Produktionsauftrags sofort als bearbeitet markiert, auch wenn es sich um eine Teilmenge handelt.','1=0','EE01',0,'Y','N','Y','N','N','N','Immer als verarbeitet markieren',20,TO_TIMESTAMP('2024-01-14 15:49:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-14T13:49:56.681Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542768 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: PP_Order_Candidate_EnqueueSelectionForOrdering_Maturing(org.eevolution.productioncandidate.process.PP_Order_Candidate_EnqueueSelectionForOrdering)
-- Table: PP_Order_Candidate
-- Window: Reifedisposition(541756,EE01)
-- EntityType: EE01
-- 2024-01-14T13:51:06.569Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585346,541913,541453,541756,TO_TIMESTAMP('2024-01-14 15:51:06','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y',TO_TIMESTAMP('2024-01-14 15:51:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N')
;

-- Value: PP_Order_Candidate_AlreadyMaturedForOrdering
-- Classname: org.eevolution.productioncandidate.process.PP_Order_Candidate_AlreadyMaturedForOrdering
-- 2024-01-15T20:15:14.630Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585347,'Y','org.eevolution.productioncandidate.process.PP_Order_Candidate_AlreadyMaturedForOrdering','N',TO_TIMESTAMP('2024-01-15 22:15:12','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Reifung für alle geeigneten Kandidaten einleiten','json','N','Y','xls','Java',TO_TIMESTAMP('2024-01-15 22:15:12','YYYY-MM-DD HH24:MI:SS'),100,'PP_Order_Candidate_AlreadyMaturedForOrdering')
;

-- 2024-01-15T20:15:14.640Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585347 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: PP_Order_Candidate_AlreadyMaturedForOrdering(org.eevolution.productioncandidate.process.PP_Order_Candidate_AlreadyMaturedForOrdering)
-- 2024-01-15T20:15:27.489Z
UPDATE AD_Process_Trl SET Name='Initiate maturation for all eligible candidates',Updated=TO_TIMESTAMP('2024-01-15 22:15:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585347
;

-- Process: PP_Order_Candidate_AlreadyMaturedForOrdering(org.eevolution.productioncandidate.process.PP_Order_Candidate_AlreadyMaturedForOrdering)
-- ParameterName: IsDocComplete
-- 2024-01-15T20:16:22.023Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542644,0,585347,542769,20,'IsDocComplete',TO_TIMESTAMP('2024-01-15 22:16:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','Legt fest, ob ggf erstellte Belege (z.B. Produktionsaufträge) auch direkt automatisch fertig gestellt werden sollen.','1=0','EE01',0,'Y','N','Y','N','N','N','Beleg fertig stellen',10,TO_TIMESTAMP('2024-01-15 22:16:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-15T20:16:22.026Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542769 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: PP_Order_Candidate_AlreadyMaturedForOrdering(org.eevolution.productioncandidate.process.PP_Order_Candidate_AlreadyMaturedForOrdering)
-- ParameterName: AutoProcessCandidatesAfterProduction
-- 2024-01-15T20:16:49.889Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581640,0,585347,542770,20,'AutoProcessCandidatesAfterProduction',TO_TIMESTAMP('2024-01-15 22:16:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','Wenn angehakt werden die ausgewählten Kandidaten nach dem Erstellen des Produktionsauftrags sofort als bearbeitet markiert, auch wenn es sich um eine Teilmenge handelt.','1=0','EE01',0,'Y','N','Y','N','N','N','Immer als verarbeitet markieren',20,TO_TIMESTAMP('2024-01-15 22:16:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-15T20:16:49.891Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542770 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: PP_Order_Candidate_AlreadyMaturedForOrdering(org.eevolution.productioncandidate.process.PP_Order_Candidate_AlreadyMaturedForOrdering)
-- 2024-01-15T20:18:23.465Z
UPDATE AD_Process_Trl SET Description='Checks all maturing candidates for any that have not yet been processed and whose "date promised" lies in the past, and creates manufacturing orders from them.',Updated=TO_TIMESTAMP('2024-01-15 22:18:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585347
;

-- Value: PP_Order_Candidate_AlreadyMaturedForOrdering
-- Classname: org.eevolution.productioncandidate.process.PP_Order_Candidate_AlreadyMaturedForOrdering
-- 2024-01-15T20:18:27.180Z
UPDATE AD_Process SET Description='Prüft alle Reifende Kandidaten auf solche, die noch nicht verarbeitet wurden und deren "zugesagter Termin" in der Vergangenheit liegt, und erstellt daraus Produktionsaufträge.', Help=NULL, Name='Reifung für alle geeigneten Kandidaten einleiten',Updated=TO_TIMESTAMP('2024-01-15 22:18:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585347
;

-- Process: PP_Order_Candidate_AlreadyMaturedForOrdering(org.eevolution.productioncandidate.process.PP_Order_Candidate_AlreadyMaturedForOrdering)
-- 2024-01-15T20:18:27.175Z
UPDATE AD_Process_Trl SET Description='Prüft alle Reifende Kandidaten auf solche, die noch nicht verarbeitet wurden und deren "zugesagter Termin" in der Vergangenheit liegt, und erstellt daraus Produktionsaufträge.',Updated=TO_TIMESTAMP('2024-01-15 22:18:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585347
;

-- Process: PP_Order_Candidate_AlreadyMaturedForOrdering(org.eevolution.productioncandidate.process.PP_Order_Candidate_AlreadyMaturedForOrdering)
-- 2024-01-15T20:18:29.222Z
UPDATE AD_Process_Trl SET Description='Prüft alle Reifende Kandidaten auf solche, die noch nicht verarbeitet wurden und deren "zugesagter Termin" in der Vergangenheit liegt, und erstellt daraus Produktionsaufträge.',Updated=TO_TIMESTAMP('2024-01-15 22:18:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585347
;

-- Process: PP_Order_Candidate_AlreadyMaturedForOrdering(org.eevolution.productioncandidate.process.PP_Order_Candidate_AlreadyMaturedForOrdering)
-- 2024-01-15T20:18:31.901Z
UPDATE AD_Process_Trl SET Description='Prüft alle Reifende Kandidaten auf solche, die noch nicht verarbeitet wurden und deren "zugesagter Termin" in der Vergangenheit liegt, und erstellt daraus Produktionsaufträge.',Updated=TO_TIMESTAMP('2024-01-15 22:18:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585347
;

-- 2024-01-16T18:17:52.075Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582901,0,'AutoCloseCandidatesAfterProduction',TO_TIMESTAMP('2024-01-16 20:17:51','YYYY-MM-DD HH24:MI:SS'),100,'Wenn dieses Kontrollkästchen aktiviert ist, werden die ausgewählten Kandidaten sofort nach der Erstellung des Produktionsauftrags als abgeschlossen markiert, auch wenn es sich um eine Teilmenge handelt.','EE01','Y','Immer als geschlossen markieren','Immer als geschlossen markieren',TO_TIMESTAMP('2024-01-16 20:17:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-16T18:17:52.083Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582901 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: AutoCloseCandidatesAfterProduction
-- 2024-01-16T18:18:10.037Z
UPDATE AD_Element_Trl SET Description='If ticked, selected candidates will be marked as closed immediately after the manufacturing order was created, even if it''s a partial quantity.', Name='Always mark as closed', PrintName='Always mark as closed',Updated=TO_TIMESTAMP('2024-01-16 20:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582901 AND AD_Language='en_US'
;

-- 2024-01-16T18:18:10.067Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582901,'en_US') 
;

-- Process: PP_Order_Candidate_EnqueueSelectionForOrdering(org.eevolution.productioncandidate.process.PP_Order_Candidate_EnqueueSelectionForOrdering)
-- ParameterName: AutoCloseCandidatesAfterProduction
-- 2024-01-16T18:19:56.445Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582901,0,584935,542771,20,'AutoCloseCandidatesAfterProduction',TO_TIMESTAMP('2024-01-16 20:19:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Wenn dieses Kontrollkästchen aktiviert ist, werden die ausgewählten Kandidaten sofort nach der Erstellung des Produktionsauftrags als abgeschlossen markiert, auch wenn es sich um eine Teilmenge handelt.','1=0','EE01',0,'Y','N','Y','N','N','N','Immer als geschlossen markieren',30,TO_TIMESTAMP('2024-01-16 20:19:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-16T18:19:56.448Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542771 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: PP_Order_Candidate_EnqueueSelectionForOrdering_Maturing(org.eevolution.productioncandidate.process.PP_Order_Candidate_EnqueueSelectionForOrdering)
-- ParameterName: AutoCloseCandidatesAfterProduction
-- 2024-01-16T18:20:26.427Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582901,0,585346,542772,20,'AutoCloseCandidatesAfterProduction',TO_TIMESTAMP('2024-01-16 20:20:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','Wenn dieses Kontrollkästchen aktiviert ist, werden die ausgewählten Kandidaten sofort nach der Erstellung des Produktionsauftrags als abgeschlossen markiert, auch wenn es sich um eine Teilmenge handelt.','1=0','EE01',0,'Y','N','Y','N','N','N','Immer als geschlossen markieren',30,TO_TIMESTAMP('2024-01-16 20:20:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-16T18:20:26.428Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542772 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: PP_Order_Candidate_AlreadyMaturedForOrdering(org.eevolution.productioncandidate.process.PP_Order_Candidate_AlreadyMaturedForOrdering)
-- ParameterName: AutoCloseCandidatesAfterProduction
-- 2024-01-16T18:21:04.129Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582901,0,585347,542773,20,'AutoCloseCandidatesAfterProduction',TO_TIMESTAMP('2024-01-16 20:21:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','Wenn dieses Kontrollkästchen aktiviert ist, werden die ausgewählten Kandidaten sofort nach der Erstellung des Produktionsauftrags als abgeschlossen markiert, auch wenn es sich um eine Teilmenge handelt.','1=0','EE01',0,'Y','N','Y','N','N','N','Immer als geschlossen markieren',30,TO_TIMESTAMP('2024-01-16 20:21:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-16T18:21:04.130Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542773 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-01-25T19:22:14.691Z
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,540105,'de.metas.handlingunits.pporder.api.impl.async.HUMaturingWorkpackageProcessor',TO_TIMESTAMP('2024-01-25 21:22:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','HUMaturingWorkpackageProcessor','Y',TO_TIMESTAMP('2024-01-25 21:22:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-25T19:24:51.373Z
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540076,TO_TIMESTAMP('2024-01-25 21:24:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000,'HUMaturingWorkpackageProcessor',1,TO_TIMESTAMP('2024-01-25 21:24:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-25T19:25:08.743Z
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540105,540119,540076,TO_TIMESTAMP('2024-01-25 21:25:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2024-01-25 21:25:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-26T17:53:10.228Z
INSERT INTO AD_Scheduler (AD_Client_ID, AD_Org_ID, AD_Process_ID, AD_Role_ID, AD_Scheduler_ID, Created, CreatedBy, CronPattern, Description, EntityType, Frequency, FrequencyType, IsActive, IsIgnoreProcessingTime, KeepLogDays, ManageScheduler, Name, Processing, SchedulerProcessType, ScheduleType, Status, Supervisor_ID, Updated, UpdatedBy)
VALUES (0, 0, 585347, 0, 550110, TO_TIMESTAMP('2024-01-26 19:53:09', 'YYYY-MM-DD HH24:MI:SS'), 100, '00 2 * * *', 'Runs nightly to initiate maturation for all eligible candidates', 'EE01', 0, 'D', 'Y', 'N', 7, 'N', 'PP_Order_Candidate_AlreadyMaturedForOrdering', 'N', 'P', 'C', 'NEW', 100, TO_TIMESTAMP('2024-01-26 19:53:09', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
