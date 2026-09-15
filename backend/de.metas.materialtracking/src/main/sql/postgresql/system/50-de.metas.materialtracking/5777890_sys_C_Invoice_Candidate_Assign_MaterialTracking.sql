-- Run mode: SWING_CLIENT

-- Value: C_Invoice_Candidate_Assign_MaterialTracking
-- Classname: de.metas.materialtracking.process.C_Invoice_Candidate_Assign_MaterialTracking
-- 2025-11-20T17:28:25.108Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585534,'Y','de.metas.materialtracking.process.C_Invoice_Candidate_Assign_MaterialTracking','N',TO_TIMESTAMP('2025-11-20 17:28:24.781000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Dem Materialvorgang zuordnen','json','N','N','xls','Java',TO_TIMESTAMP('2025-11-20 17:28:24.781000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_Invoice_Candidate_Assign_MaterialTracking')
;

-- 2025-11-20T17:28:25.115Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585534 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Invoice_Candidate_Assign_MaterialTracking(de.metas.materialtracking.process.C_Invoice_Candidate_Assign_MaterialTracking)
-- ParameterName: M_Material_Tracking_ID
-- 2025-11-20T17:31:02.135Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542532,0,585534,543052,30,540622,'M_Material_Tracking_ID',TO_TIMESTAMP('2025-11-20 17:31:01.915000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.materialtracking',0,'Y','N','Y','N','N','N','Material-Vorgang-ID',10,TO_TIMESTAMP('2025-11-20 17:31:01.915000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T17:31:02.138Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543052 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2025-11-20T17:32:36.769Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584261,0,'IsClearAggregationKeyOverride',TO_TIMESTAMP('2025-11-20 17:32:36.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','If set, the C_Invoice_Candidate_HeaderAggregation_Override of the selected invoice candidates will be set to null, reverting to their original aggregation.','Y','Clear Aggregation Key Override','Clear Aggregation Key Override',TO_TIMESTAMP('2025-11-20 17:32:36.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T17:32:36.772Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584261 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Process: C_Invoice_Candidate_Assign_MaterialTracking(de.metas.materialtracking.process.C_Invoice_Candidate_Assign_MaterialTracking)
-- ParameterName: M_Material_Tracking_ID
-- 2025-11-20T17:32:48.851Z
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-11-20 17:32:48.851000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543052
;

-- Process: C_Invoice_Candidate_Assign_MaterialTracking(de.metas.materialtracking.process.C_Invoice_Candidate_Assign_MaterialTracking)
-- ParameterName: M_Material_Tracking_ID
-- 2025-11-20T17:32:51.595Z
UPDATE AD_Process_Para SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-11-20 17:32:51.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543052
;

-- Process: C_Invoice_Candidate_Assign_MaterialTracking(de.metas.materialtracking.process.C_Invoice_Candidate_Assign_MaterialTracking)
-- ParameterName: IsClearAggregationKeyOverride
-- 2025-11-20T17:33:20.522Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584261,0,585534,543053,20,'IsClearAggregationKeyOverride',TO_TIMESTAMP('2025-11-20 17:33:20.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','0=1','U',0,'If set, the C_Invoice_Candidate_HeaderAggregation_Override of the selected invoice candidates will be set to null, reverting to their original aggregation.','Y','N','Y','N','N','N','Clear Aggregation Key Override',20,TO_TIMESTAMP('2025-11-20 17:33:20.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-20T17:33:20.523Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543053 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_Invoice_Candidate_Assign_MaterialTracking(de.metas.materialtracking.process.C_Invoice_Candidate_Assign_MaterialTracking)
-- 2025-11-20T18:18:27.829Z
UPDATE AD_Process_Trl SET Description='If no material tracking is provided then all selected invoice candidates shall be removed from their assigned material tracking (if the material tracking has not been processed). If a material tracking is provided then all invoice candidates will be moved to the selected material tracking (if the "from" or "to" material tracking has not been processed).', Name='Assign to Material Tracking',Updated=TO_TIMESTAMP('2025-11-20 18:18:27.829000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585534
;

-- 2025-11-20T18:18:27.831Z
UPDATE AD_Process base SET Description=trl.Description, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: C_Invoice_Candidate_Assign_MaterialTracking(de.metas.materialtracking.process.C_Invoice_Candidate_Assign_MaterialTracking)
-- 2025-11-20T18:18:37.687Z
UPDATE AD_Process_Trl SET Description='Wenn keine Materialverfolgung angegeben ist, werden alle ausgewählten Rechnungskandidaten aus ihrer zugewiesenen Materialverfolgung entfernt (sofern die Materialverfolgung noch nicht verarbeitet wurde). Wenn eine Materialverfolgung angegeben ist, werden alle Rechnungskandidaten auf die ausgewählte Materialverfolgung verschoben (sofern die „von“- bzw. „zu“-Materialverfolgung noch nicht verarbeitet wurde).',Updated=TO_TIMESTAMP('2025-11-20 18:18:37.687000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585534
;

-- 2025-11-20T18:18:37.690Z
UPDATE AD_Process base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: C_Invoice_Candidate_Assign_MaterialTracking
-- Classname: de.metas.materialtracking.process.C_Invoice_Candidate_Assign_MaterialTracking
-- 2025-11-20T18:20:54.332Z
UPDATE AD_Process SET Description='Wenn keine Materialverfolgung angegeben ist, werden alle ausgewählten Rechnungskandidaten aus ihrer zugewiesenen Materialverfolgung entfernt (sofern die Materialverfolgung noch nicht verarbeitet wurde). Wenn eine Materialverfolgung angegeben ist, werden alle Rechnungskandidaten auf die ausgewählte Materialverfolgung verschoben (sofern die „von“- bzw. „zu“-Materialverfolgung noch nicht verarbeitet wurde).',Updated=TO_TIMESTAMP('2025-11-20 18:20:54.330000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585534
;

-- 2025-11-20T18:20:54.338Z
UPDATE AD_Process_Trl trl SET Description='Wenn keine Materialverfolgung angegeben ist, werden alle ausgewählten Rechnungskandidaten aus ihrer zugewiesenen Materialverfolgung entfernt (sofern die Materialverfolgung noch nicht verarbeitet wurde). Wenn eine Materialverfolgung angegeben ist, werden alle Rechnungskandidaten auf die ausgewählte Materialverfolgung verschoben (sofern die „von“- bzw. „zu“-Materialverfolgung noch nicht verarbeitet wurde).' WHERE AD_Process_ID=585534 AND AD_Language='de_DE'
;

-- Process: C_Invoice_Candidate_Assign_MaterialTracking(de.metas.materialtracking.process.C_Invoice_Candidate_Assign_MaterialTracking)
-- 2025-11-20T18:21:01.523Z
UPDATE AD_Process_Trl SET Description='Wenn keine Materialverfolgung angegeben ist, werden alle ausgewählten Rechnungskandidaten aus ihrer zugewiesenen Materialverfolgung entfernt (sofern die Materialverfolgung noch nicht verarbeitet wurde). Wenn eine Materialverfolgung angegeben ist, werden alle Rechnungskandidaten auf die ausgewählte Materialverfolgung verschoben (sofern die „von“- bzw. „zu“-Materialverfolgung noch nicht verarbeitet wurde).',Updated=TO_TIMESTAMP('2025-11-20 18:21:01.523000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585534
;

-- 2025-11-20T18:21:01.525Z
UPDATE AD_Process base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Process: C_Invoice_Candidate_Assign_MaterialTracking(de.metas.materialtracking.process.C_Invoice_Candidate_Assign_MaterialTracking)
-- Table: C_Invoice_Candidate
-- EntityType: D
-- 2025-11-20T18:27:03.320Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585534,540270,541589,TO_TIMESTAMP('2025-11-20 18:27:03.113000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2025-11-20 18:27:03.113000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

