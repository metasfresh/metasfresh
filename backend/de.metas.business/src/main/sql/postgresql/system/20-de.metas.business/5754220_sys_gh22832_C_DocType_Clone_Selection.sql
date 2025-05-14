-- 2025-05-13T08:55:23.238Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('4',0,0,585468,'N','de.metas.document.process.C_DocType_Clone_Selection','N',TO_TIMESTAMP('2025-05-13 11:55:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Auswahl klonen','json','N','N','xls','Java',TO_TIMESTAMP('2025-05-13 11:55:22','YYYY-MM-DD HH24:MI:SS'),100,'C_DocType_Clone_Selection')
;

-- 2025-05-13T08:55:23.251Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585468 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2025-05-13T08:56:23.341Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,113,0,585468,542957,30,'AD_Org_ID',TO_TIMESTAMP('2025-05-13 11:56:23','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','D',22,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','Y','N','Sektion',10,TO_TIMESTAMP('2025-05-13 11:56:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-05-13T08:56:23.346Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542957 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2025-05-13T08:57:55.567Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585468,217,541553,135,TO_TIMESTAMP('2025-05-13 11:57:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2025-05-13 11:57:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N')
;

-- 2025-05-13T09:03:31.376Z
UPDATE AD_Process SET AccessLevel='3',Updated=TO_TIMESTAMP('2025-05-13 12:03:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585468
;

-- 2025-05-13T11:22:12.091Z
UPDATE AD_Process_Trl SET Name='Clone Selection',Updated=TO_TIMESTAMP('2025-05-13 14:22:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585468
;

-- 2025-05-14T18:56:42.991Z
UPDATE AD_Process SET Description='Klonen Sie ausgewählte Dokumenttypen in die unten stehende Organisation. Falls das Dokument nummerngesteuert ist und eine Sequenz hat, klonen Sie auch die Sequenz in die unten stehende Organisation.',Updated=TO_TIMESTAMP('2025-05-14 21:56:42.99','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585468
;

-- 2025-05-14T18:56:43Z
UPDATE AD_Process_Trl trl SET Description='Klonen Sie ausgewählte Dokumenttypen in die unten stehende Organisation. Falls das Dokument nummerngesteuert ist und eine Sequenz hat, klonen Sie auch die Sequenz in die unten stehende Organisation.' WHERE AD_Process_ID=585468 AND AD_Language='de_DE'
;

-- Process: C_DocType_Clone_Selection(de.metas.document.process.C_DocType_Clone_Selection)
-- 2025-05-14T18:57:00.692Z
UPDATE AD_Process_Trl SET Description='Clone selected document types to the below organization and in case the document is number controlled and it has a sequence, also clone the sequence to the below organization.',Updated=TO_TIMESTAMP('2025-05-14 21:57:00.692','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585468
;

-- Process: C_DocType_Clone_Selection(de.metas.document.process.C_DocType_Clone_Selection)
-- 2025-05-14T18:57:04.903Z
UPDATE AD_Process_Trl SET Description='Klonen Sie ausgewählte Dokumenttypen in die unten stehende Organisation. Falls das Dokument nummerngesteuert ist und eine Sequenz hat, klonen Sie auch die Sequenz in die unten stehende Organisation.',Updated=TO_TIMESTAMP('2025-05-14 21:57:04.903','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585468
;

-- Process: C_DocType_Clone_Selection(de.metas.document.process.C_DocType_Clone_Selection)
-- 2025-05-14T18:57:08.526Z
UPDATE AD_Process_Trl SET Description='Klonen Sie ausgewählte Dokumenttypen in die unten stehende Organisation. Falls das Dokument nummerngesteuert ist und eine Sequenz hat, klonen Sie auch die Sequenz in die unten stehende Organisation.',Updated=TO_TIMESTAMP('2025-05-14 21:57:08.526','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585468
;

-- Process: C_DocType_Clone_Selection(de.metas.document.process.C_DocType_Clone_Selection)
-- 2025-05-14T18:57:09.882Z
UPDATE AD_Process_Trl SET Description='Klonen Sie ausgewählte Dokumenttypen in die unten stehende Organisation. Falls das Dokument nummerngesteuert ist und eine Sequenz hat, klonen Sie auch die Sequenz in die unten stehende Organisation.',Updated=TO_TIMESTAMP('2025-05-14 21:57:09.882','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Process_ID=585468
;
