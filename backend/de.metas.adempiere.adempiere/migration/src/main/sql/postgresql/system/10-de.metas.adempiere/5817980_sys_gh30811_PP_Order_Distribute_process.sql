-- "Distribute" action on PP_Order (AD_Table 53027): discharges the WIP cost residual of a
-- completed-but-not-closed manufacturing order via a new CostDifferenceDistribution PP_Cost_Collector.

-- Java process, precondition-gated to a single completed-not-closed PP_Order; EntityType EE01
-- (org.eevolution), like the other PP_Order process rows.
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value)
VALUES ('3',0,0,585649 /*From ID Server*/,'N','org.eevolution.process.PP_Order_Distribute','N',TO_TIMESTAMP('2026-08-09 10:10:00','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','N','N','N','N','N','Y',0,'Kostendifferenz verteilen','N','N','Java',TO_TIMESTAMP('2026-08-09 10:10:00','YYYY-MM-DD HH24:MI:SS'),100,'PP_Order_Distribute')
;

-- Seed AD_Process_Trl rows for every active non-base system language.
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Process t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=585649
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- en_US override: English label
UPDATE AD_Process_Trl SET Name='Distribute cost difference', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-09 10:10:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Process_ID=585649
;

-- de_CH: mark as actively translated (same German text as the base)
UPDATE AD_Process_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-09 10:10:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Process_ID=585649
;

-- Table-wide (AD_Window_ID/AD_Tab_ID left NULL) so the action surfaces on every window that opens
-- PP_Order, as both a quick action and a document action.
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default)
VALUES (0,0,585649,53027,541660 /*From ID Server*/,TO_TIMESTAMP('2026-08-09 10:10:20','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y',TO_TIMESTAMP('2026-08-09 10:10:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N')
;
