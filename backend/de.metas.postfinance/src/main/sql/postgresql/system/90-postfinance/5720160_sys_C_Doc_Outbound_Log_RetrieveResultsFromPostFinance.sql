-- Run mode: SWING_CLIENT

-- Value: C_Doc_Outbound_Log_RetrieveResultsFromPostFinance
-- Classname: de.metas.postfinance.docoutboundlog.process.C_Doc_Outbound_Log_RetrieveResultsFromPostFinance
-- 2024-03-25T12:33:43.126Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585366,'Y','de.metas.postfinance.docoutboundlog.process.C_Doc_Outbound_Log_RetrieveResultsFromPostFinance','N',TO_TIMESTAMP('2024-03-25 14:33:42.914','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Verarbeitungsergebnisse von PostFinance abrufen','json','N','N','xls','Java',TO_TIMESTAMP('2024-03-25 14:33:42.914','YYYY-MM-DD HH24:MI:SS.US'),100,'C_Doc_Outbound_Log_RetrieveResultsFromPostFinance')
;

-- 2024-03-25T12:33:43.136Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585366 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Doc_Outbound_Log_RetrieveResultsFromPostFinance(de.metas.postfinance.docoutboundlog.process.C_Doc_Outbound_Log_RetrieveResultsFromPostFinance)
-- Table: C_Doc_Outbound_Log
-- EntityType: de.metas.postfinance
-- 2024-03-25T12:38:38.157Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585366,540453,541470,TO_TIMESTAMP('2024-03-25 14:38:37.994','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','Y',TO_TIMESTAMP('2024-03-25 14:38:37.994','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Process: C_Doc_Outbound_Log_RetrieveResultsFromPostFinance(de.metas.postfinance.docoutboundlog.process.C_Doc_Outbound_Log_RetrieveResultsFromPostFinance)
-- ParameterName: AD_Org_ID
-- 2024-03-25T12:39:56.608Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,113,0,585366,542794,30,'AD_Org_ID',TO_TIMESTAMP('2024-03-25 14:39:56.488','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','de.metas.postfinance',0,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','N','Organisation',10,TO_TIMESTAMP('2024-03-25 14:39:56.488','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-25T12:39:56.610Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542794 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-03-25T12:39:56.644Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(113)
;

-- Process: C_Doc_Outbound_Log_RetrieveResultsFromPostFinance(de.metas.postfinance.docoutboundlog.process.C_Doc_Outbound_Log_RetrieveResultsFromPostFinance)
-- ParameterName: AD_Org_ID
-- 2024-03-25T12:42:02.469Z
UPDATE AD_Process_Para SET DefaultValue='@#AD_Org_ID/-1@', IsMandatory='Y',Updated=TO_TIMESTAMP('2024-03-25 14:42:02.469','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542794
;

-- Name: AD_Org_With_PostFinance_ID
-- 2024-03-25T12:46:33.416Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540670,TO_TIMESTAMP('2024-03-25 14:46:33.28','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','Y','AD_Org_With_PostFinance_ID','S',TO_TIMESTAMP('2024-03-25 14:46:33.28','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: AD_Org_With_PostFinance_ID
-- 2024-03-25T12:56:34.942Z
UPDATE AD_Val_Rule SET Code='AD_Org_ID IN (SELECT o.ad_org_ID
                    FROM postfinance_org_config pfo
                             JOIN AD_Org o ON pfo.ad_org_id = o.ad_org_id
                    WHERE pfo.isactive = ''Y''
                      AND o.isactive = ''Y'')',Updated=TO_TIMESTAMP('2024-03-25 14:56:34.94','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540670
;

-- Process: C_Doc_Outbound_Log_RetrieveResultsFromPostFinance(de.metas.postfinance.docoutboundlog.process.C_Doc_Outbound_Log_RetrieveResultsFromPostFinance)
-- ParameterName: AD_Org_ID
-- 2024-03-25T12:57:26.035Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=270,Updated=TO_TIMESTAMP('2024-03-25 14:57:26.035','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542794
;

-- Process: C_Doc_Outbound_Log_RetrieveResultsFromPostFinance(de.metas.postfinance.docoutboundlog.process.C_Doc_Outbound_Log_RetrieveResultsFromPostFinance)
-- ParameterName: AD_Org_ID
-- 2024-03-25T12:57:59.168Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540670,Updated=TO_TIMESTAMP('2024-03-25 14:57:59.168','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542794
;

-- Value: C_Doc_Outbound_Log_RetrieveResultsFromPostFinance
-- Classname: de.metas.postfinance.document.results.process.C_Doc_Outbound_Log_RetrieveResultsFromPostFinance
-- 2024-03-25T17:37:03.746Z
UPDATE AD_Process SET Classname='de.metas.postfinance.document.results.process.C_Doc_Outbound_Log_RetrieveResultsFromPostFinance',Updated=TO_TIMESTAMP('2024-03-25 19:37:03.743','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585366
;


-- Process: C_Doc_Outbound_Log_RetrieveResultsFromPostFinance(de.metas.postfinance.document.results.process.C_Doc_Outbound_Log_RetrieveResultsFromPostFinance)
-- 2024-03-26T18:46:21.522Z
UPDATE AD_Process_Trl SET Name='Retrieve Results From PostFinance',Updated=TO_TIMESTAMP('2024-03-26 20:46:21.521','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585366
;

-- Process: C_Doc_Outbound_Log_RetrieveResultsFromPostFinance(de.metas.postfinance.document.results.process.C_Doc_Outbound_Log_RetrieveResultsFromPostFinance)
-- 2024-03-27T10:44:12.794Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-27 12:44:12.791','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585366
;

