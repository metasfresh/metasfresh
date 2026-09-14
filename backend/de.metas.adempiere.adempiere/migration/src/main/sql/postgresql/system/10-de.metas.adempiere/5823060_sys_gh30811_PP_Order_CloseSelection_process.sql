-- "Auswahl schließen" / "Close selection" on PP_Order (AD_Table 53027): closes the selected
-- completed-but-not-closed manufacturing orders.
-- The cost-monitor window lists exactly those orders, and its tab carries no DocAction field, so an
-- order that is already balanced - nothing left for the post-calculation to post - could otherwise not
-- be closed from the window at all.

INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value)
VALUES ('3',0,0,585671 /*From ID Server*/,'N','org.eevolution.process.PP_Order_CloseSelection','N',TO_TIMESTAMP('2026-09-08 14:10:00','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','N','N','N','N','N','Y',0,'Auswahl schließen','N','N','Java',TO_TIMESTAMP('2026-09-08 14:10:00','YYYY-MM-DD HH24:MI:SS'),100,'PP_Order_CloseSelection')
;

-- Seed AD_Process_Trl rows for every active system language with the German base text, IsTranslated='N'.
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Process t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Process_ID=585671
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- en_US override: English label
UPDATE AD_Process_Trl SET Name='Close selection', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 14:10:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Process_ID=585671
;

-- de_DE / de_CH: mark as actively translated (same German text as the base)
UPDATE AD_Process_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 14:10:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Process_ID=585671
;

UPDATE AD_Process_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-08 14:10:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Process_ID=585671
;

-- Scoped to AD_Window 542175 (the cost-monitor window), NOT table-wide: no other PP_Order window may
-- gain a bulk-close button. Offered as a view quick action but not the default one - that slot belongs
-- to the post-calculation.
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Window_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default)
VALUES (0,0,585671,53027,542175,541691 /*From ID Server*/,TO_TIMESTAMP('2026-09-08 14:10:20','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y',TO_TIMESTAMP('2026-09-08 14:10:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N')
;
