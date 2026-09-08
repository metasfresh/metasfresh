-- "Auswahl schließen" / "Close selection" on PP_Order (AD_Table 53027): closes the selected
-- completed-but-not-closed manufacturing orders.
-- The cost-monitor window lists exactly those orders. The post-calculation action closes the ones whose
-- residual it posts, but an order that is already balanced has nothing left to post, and the tab carries
-- no DocAction field - so without this action such an order cannot be closed from the window at all.
--
-- IDs allocated from idserver.metas.de on 2026-09-08:
--   AD_Process       585671
--   AD_Table_Process 541691

-- Java process, precondition-gated to a non-empty selection; EntityType EE01 (org.eevolution), like the
-- other PP_Order process rows. Name is German (base language), English via the AD_Process_Trl override below.
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

-- Scoped to AD_Window 542175 (the cost-monitor window), NOT table-wide: the Produktionsauftrag window
-- (AD_Window 53009) must not gain a bulk-close button. ADProcessDAO filters AD_Window_ID IN (null,<window>)
-- and prefers the more specific row, so a window-scoped row adds the action to this window only.
-- Offered as a view quick action but not the default one - that slot belongs to the post-calculation.
-- WEBUI_DocumentAction='Y' as on every other PP_Order process row: the window's tab has no DocAction
-- field, so without it a controller who drilled into a single order could not close it from there either.
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Window_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default)
VALUES (0,0,585671,53027,542175,541691 /*From ID Server*/,TO_TIMESTAMP('2026-09-08 14:10:20','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y',TO_TIMESTAMP('2026-09-08 14:10:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N')
;
