-- Run mode: SWING_CLIENT

-- gh26253: Register AD_Process for C_AcctSchema_InitMovingAvgInvoice.
-- Adds a document action on the Accounting Schema window that calls the
-- C_AcctSchema_InitMovingAvgInvoice() PL/pgSQL function.

-- AD_Element
-- 2026-06-02
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,584927 /*From ID Server*/,0,now(),100,'D','Y','Initialize Moving Average Invoice Costing','Initialize Moving Average Invoice Costing',now(),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Element_ID=584927
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl
SET IsTranslated='Y', Name='Moving Average Invoice initialisieren', PrintName='Moving Average Invoice initialisieren', Updated=now(), UpdatedBy=100
WHERE AD_Element_ID=584927 AND AD_Language IN ('de_DE','de_CH')
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584927,'de_DE')
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584927,'de_CH')
;

-- AD_Process
-- 2026-06-02
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SQLStatement,Type,Updated,UpdatedBy,Value)
VALUES ('3',0,0,585629 /*From ID Server*/,'Y','de.metas.process.ExecuteUpdateSQL','N',now(),100,'D','Y','N','N','N','Y','N','N','Y','Y',0,'Initialize Moving Average Invoice Costing','json','Y','N','SELECT public.C_AcctSchema_InitMovingAvgInvoice(@C_AcctSchema_ID@)','SQL',now(),100,'C_AcctSchema_InitMovingAvgInvoice')
;

INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Process t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Process_ID=585629
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

UPDATE AD_Process_Trl
SET IsTranslated='Y', Name='Moving Average Invoice initialisieren', Updated=now(), UpdatedBy=100
WHERE AD_Process_ID=585629 AND AD_Language IN ('de_DE','de_CH')
;

-- AD_Table_Process: attach to C_AcctSchema (AD_Table_ID=265) as a document action
-- 2026-06-02
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default)
VALUES (0,0,585629,265,541647 /*From ID Server*/,now(),100,'D','Y',now(),100,'Y','N','N','N','N')
;

-- Make CostingMethod read-only: users must use the initialization process to change it
-- 2026-06-02
UPDATE AD_Field
SET IsReadOnly='Y', Updated=now(), UpdatedBy=100
WHERE IsActive='Y'
  AND AD_Column_ID IN (
      SELECT c.AD_Column_ID
      FROM AD_Column c
      JOIN AD_Table t ON t.AD_Table_ID = c.AD_Table_ID
      WHERE t.TableName = 'C_AcctSchema' AND c.ColumnName = 'CostingMethod'
  )
;
