-- Value: C_Flatrate_Conditions_Extend
-- Classname: de.metas.contracts.flatrate.process.C_Flatrate_Conditions_Extend
-- 2023-07-05T14:50:16.742396627Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585278,'Y','de.metas.contracts.flatrate.process.C_Flatrate_Conditions_Extend','N',TO_TIMESTAMP('2023-07-05 15:50:15.986','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Extend Contract Terms','json','N','N','xls','Java',TO_TIMESTAMP('2023-07-05 15:50:15.986','YYYY-MM-DD HH24:MI:SS.US'),100,'C_Flatrate_Conditions_Extend')
;

-- 2023-07-05T14:50:16.748625108Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585278 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: C_Flatrate_Conditions_Extend
-- Classname: de.metas.contracts.flatrate.process.C_Flatrate_Conditions_Extend
-- 2023-07-05T14:50:41.443143747Z
UPDATE AD_Process SET AllowProcessReRun='N', IsFormatExcelFile='N',Updated=TO_TIMESTAMP('2023-07-05 15:50:41.442','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585278
;

-- Process: C_Flatrate_Conditions_Extend(de.metas.contracts.flatrate.process.C_Flatrate_Conditions_Extend)
-- ParameterName: C_Year_ID
-- 2023-07-05T14:58:22.964935148Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,223,0,585278,542652,30,'C_Year_ID',TO_TIMESTAMP('2023-07-05 15:58:22.12','YYYY-MM-DD HH24:MI:SS.US'),100,'Kalenderjahr','de.metas.contracts',0,'"Jahr" bezeichnet ein eindeutiges Jahr eines Kalenders.','Y','N','Y','N','Y','N','Jahr',10,TO_TIMESTAMP('2023-07-05 15:58:22.12','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-07-05T14:58:22.966256500Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542652 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-07-05T14:58:22.984595906Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(223)
;

-- Process: C_Flatrate_Conditions_Extend(de.metas.contracts.flatrate.process.C_Flatrate_Conditions_Extend)
-- Table: C_Flatrate_Conditions
-- Tab: Vertragsbedingungen(540113,de.metas.contracts) -> Bedingungen(540331,de.metas.contracts)
-- Window: Vertragsbedingungen(540113,de.metas.contracts)
-- EntityType: de.metas.contracts
-- 2023-07-05T15:03:44.146280120Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Tab_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585278,540331,540311,541391,540113,TO_TIMESTAMP('2023-07-05 16:03:42.471','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2023-07-05 16:03:42.471','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','Y')
;

-- Process: C_Flatrate_Conditions_Extend(de.metas.contracts.flatrate.process.C_Flatrate_Conditions_Extend)
-- Table: C_Flatrate_Conditions
-- EntityType: de.metas.contracts
-- 2023-07-05T15:16:42.645352672Z
UPDATE AD_Table_Process SET AD_Tab_ID=NULL, AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2023-07-05 16:16:42.645','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=541391
;

-- Reference Item: Conditions_BehaviourWhenExtending -> Ex_Extension Not Allowed
-- 2023-07-05T16:41:10.994189613Z
UPDATE AD_Ref_List_Trl SET Description='Verlängerung nicht zulässig', Name='Verlängerung nicht zulässig',Updated=TO_TIMESTAMP('2023-07-05 17:41:10.994','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543520
;

-- 2023-07-05T16:41:10.998674625Z
UPDATE AD_Ref_List SET Description='Verlängerung nicht zulässig', Name='Verlängerung nicht zulässig' WHERE AD_Ref_List_ID=543520
;

-- Reference Item: Conditions_BehaviourWhenExtending -> Ex_Extension Not Allowed
-- 2023-07-05T16:41:32.884798853Z
UPDATE AD_Ref_List_Trl SET Description='Eine Vertragsverlängerung ist nicht zulässig',Updated=TO_TIMESTAMP('2023-07-05 17:41:32.884','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543520
;

-- 2023-07-05T16:41:32.885602505Z
UPDATE AD_Ref_List SET Description='Eine Vertragsverlängerung ist nicht zulässig' WHERE AD_Ref_List_ID=543520
;

-- Reference Item: Conditions_BehaviourWhenExtending -> Ex_Extension Not Allowed
-- 2023-07-05T16:41:56.652428320Z
UPDATE AD_Ref_List_Trl SET Name='Verlängerung nicht zulässig',Updated=TO_TIMESTAMP('2023-07-05 17:41:56.652','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543520
;

-- Reference Item: Conditions_BehaviourWhenExtending -> Ex_Extension Not Allowed
-- 2023-07-05T16:42:19.764668222Z
UPDATE AD_Ref_List_Trl SET Description='Eine Vertragsverlängerung ist nicht zulässig',Updated=TO_TIMESTAMP('2023-07-05 17:42:19.764','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543520
;
