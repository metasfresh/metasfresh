-- 2023-01-06T08:32:54.560Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585175,'Y','de.metas.externalsystem.other.export.project.C_BudgetProject_SyncTo_Other','N',TO_TIMESTAMP('2023-01-06 10:32:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'An Other senden','json','N','N','xls','Java',TO_TIMESTAMP('2023-01-06 10:32:54','YYYY-MM-DD HH24:MI:SS'),100,'C_BudgetProject_SyncTo_Other')
;

-- 2023-01-06T08:32:54.566Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585175 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Name: Other_ExternalSystem_Config
-- 2023-01-06T08:33:43.851Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541703,TO_TIMESTAMP('2023-01-06 10:33:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','Other_ExternalSystem_Config',TO_TIMESTAMP('2023-01-06 10:33:43','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-01-06T08:33:43.852Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541703 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Other_ExternalSystem_Config
-- Table: ExternalSystem_Config
-- Key: ExternalSystem_Config.ExternalSystem_Config_ID
-- 2023-01-06T08:37:16.021Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,572724,0,541703,541576,TO_TIMESTAMP('2023-01-06 10:37:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2023-01-06 10:37:16','YYYY-MM-DD HH24:MI:SS'),100,'Type=''Other''')
;

-- Name: Other_ExternalSystem_Config
-- 2023-01-06T08:38:09.143Z
UPDATE AD_Reference SET EntityType='D',Updated=TO_TIMESTAMP('2023-01-06 10:38:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541703
;

-- 2023-01-06T08:38:14.111Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,578728,0,585175,542436,18,541703,'ExternalSystem_Config_ID',TO_TIMESTAMP('2023-01-06 10:38:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem',0,'Y','N','Y','N','N','N','External System Config',10,TO_TIMESTAMP('2023-01-06 10:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-06T08:38:14.112Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542436 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-01-06T08:38:37.876Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585175,203,541326,TO_TIMESTAMP('2023-01-06 10:38:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-01-06 10:38:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N')
;

-- 2023-01-06T08:42:15.311Z
UPDATE AD_Process SET EntityType='D',Updated=TO_TIMESTAMP('2023-01-06 10:42:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585175
;

-- 2023-01-10T09:50:40.555Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2023-01-10 11:50:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542436
;


-- 2023-01-11T13:41:34.057Z
UPDATE AD_Process SET Name='An Other senden',Updated=TO_TIMESTAMP('2023-01-11 15:41:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585175
;


-- 2023-01-24T07:20:32.848Z
UPDATE AD_Table_Process SET AD_Window_ID=541506,Updated=TO_TIMESTAMP('2023-01-24 09:20:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541326
;

-- 2023-01-24T07:20:43.371Z
UPDATE AD_Process_Trl SET Name='Send to Other',Updated=TO_TIMESTAMP('2023-01-24 09:20:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585175
;

-- 2023-01-24T07:21:37.433Z
UPDATE AD_Process SET IsActive='N',Updated=TO_TIMESTAMP('2023-01-24 09:21:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585175
;


