-- Value: M_InOut_ReadyForInterimInvoicing
-- Classname: de.metas.contracts.process.M_InOut_ReadyForInterimInvoicing
-- 2023-08-17T15:20:50.002054400Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585306,'Y','de.metas.contracts.process.M_InOut_ReadyForInterimInvoicing','N',TO_TIMESTAMP('2023-08-17 18:20:49.854','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Für Zwischenabrechnung freigeben','json','N','N','xls','Java',TO_TIMESTAMP('2023-08-17 18:20:49.854','YYYY-MM-DD HH24:MI:SS.US'),100,'M_InOut_ReadyForInterimInvoicing')
;

-- 2023-08-17T15:20:50.010047800Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585306 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_InOut_ReadyForInterimInvoicing(de.metas.contracts.process.M_InOut_ReadyForInterimInvoicing)
-- ParameterName: IsInterimInvoiceable
-- 2023-08-17T15:22:35.952772300Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582652,0,585306,542680,20,'IsInterimInvoiceable',TO_TIMESTAMP('2023-08-17 18:22:35.809','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','D',0,'Y','N','Y','N','N','N','Für Zwischenabrechnung freigegeben',10,TO_TIMESTAMP('2023-08-17 18:22:35.809','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-17T15:22:35.957030900Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542680 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-08-17T15:22:35.960029100Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(582652) 
;

-- Process: M_InOut_ReadyForInterimInvoicing(de.metas.contracts.process.M_InOut_ReadyForInterimInvoicing)
-- 2023-08-18T09:40:43.369614500Z
UPDATE AD_Process_Trl SET Name='Approve for interim invoicing',Updated=TO_TIMESTAMP('2023-08-18 12:40:43.369','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585306
;

