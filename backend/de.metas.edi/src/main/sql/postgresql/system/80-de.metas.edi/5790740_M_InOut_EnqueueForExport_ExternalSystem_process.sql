-- Run mode: SWING_CLIENT

-- Value: M_InOut_EnqueueForExport_ExternalSystem
-- Classname: de.metas.edi.process.M_InOut_EnqueueForExport_ExternalSystem
-- 2026-02-27T08:53:52.294Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585586,'Y','de.metas.edi.process.M_InOut_EnqueueForExport_ExternalSystem','N',TO_TIMESTAMP('2026-02-27 08:53:52.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Export DESADV','json','N','N','xls','Java',TO_TIMESTAMP('2026-02-27 08:53:52.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_InOut_EnqueueForExport_ExternalSystem')
;

-- 2026-02-27T08:53:52.295Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585586 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_InOut_EnqueueForExport_ExternalSystem(de.metas.edi.process.M_InOut_EnqueueForExport_ExternalSystem)
-- 2026-02-27T08:53:58.910Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-27 08:53:58.910000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585586
;

-- Process: M_InOut_EnqueueForExport_ExternalSystem(de.metas.edi.process.M_InOut_EnqueueForExport_ExternalSystem)
-- 2026-02-27T08:53:59.311Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-27 08:53:59.311000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585586
;

-- Process: M_InOut_EnqueueForExport_ExternalSystem(de.metas.edi.process.M_InOut_EnqueueForExport_ExternalSystem)
-- 2026-02-27T08:54:00.754Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-27 08:54:00.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585586
;

-- Process: M_InOut_EnqueueForExport_ExternalSystem(de.metas.edi.process.M_InOut_EnqueueForExport_ExternalSystem)
-- Table: M_InOut
-- EntityType: de.metas.esb.edi
-- 2026-02-27T08:54:36.996Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585586,319,541625,TO_TIMESTAMP('2026-02-27 08:54:36.888000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi','Y',TO_TIMESTAMP('2026-02-27 08:54:36.888000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;
