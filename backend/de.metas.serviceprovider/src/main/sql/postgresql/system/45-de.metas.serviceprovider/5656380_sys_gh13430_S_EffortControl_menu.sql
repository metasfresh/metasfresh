-- Name: S_EffortControl
-- Action Type: W
-- Window: S_EffortControl(541615,de.metas.serviceprovider)
-- 2022-09-15T06:22:20.017Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,581449,542008,0,541615,TO_TIMESTAMP('2022-09-15 09:22:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Effort control','Y','N','N','N','N','S_EffortControl',TO_TIMESTAMP('2022-09-15 09:22:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T06:22:20.025Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542008 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-09-15T06:22:20.033Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542008, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542008)
;

-- 2022-09-15T06:22:20.074Z
/* DDL */  select update_menu_translation_from_ad_element(581449) 
;

-- Reordering children of `Project Management`
-- Node name: `Project Setup and Use`
-- 2022-09-15T06:22:28.354Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=219 AND AD_Tree_ID=10
;

-- Node name: `Project Type (C_ProjectType)`
-- 2022-09-15T06:22:28.354Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=364 AND AD_Tree_ID=10
;

-- Node name: `Project (C_Project)`
-- 2022-09-15T06:22:28.355Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=116 AND AD_Tree_ID=10
;

-- Node name: `Project (Lines/Issues) (C_Project)`
-- 2022-09-15T06:22:28.356Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=387 AND AD_Tree_ID=10
;

-- Node name: `Generate PO from Project (de.metas.project.process.legacy.ProjectGenPO)`
-- 2022-09-15T06:22:28.356Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=402 AND AD_Tree_ID=10
;

-- Node name: `Issue to Project (de.metas.project.process.legacy.ProjectIssue)`
-- 2022-09-15T06:22:28.357Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=401 AND AD_Tree_ID=10
;

-- Node name: `Project Lines not Issued`
-- 2022-09-15T06:22:28.358Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=404 AND AD_Tree_ID=10
;

-- Node name: `Project POs not Issued`
-- 2022-09-15T06:22:28.358Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=405 AND AD_Tree_ID=10
;

-- Node name: `Project Margin (Work Order)`
-- 2022-09-15T06:22:28.359Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=410 AND AD_Tree_ID=10
;

-- Node name: `Project Reporting (C_Cycle)`
-- 2022-09-15T06:22:28.359Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=258 AND AD_Tree_ID=10
;

-- Node name: `Project Status Summary (org.compiere.report.ProjectStatus)`
-- 2022-09-15T06:22:28.360Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=260 AND AD_Tree_ID=10
;

-- Node name: `Project Cycle Report`
-- 2022-09-15T06:22:28.361Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=398 AND AD_Tree_ID=10
;

-- Node name: `Project Detail Accounting Report`
-- 2022-09-15T06:22:28.361Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=403 AND AD_Tree_ID=10
;

-- Node name: `S_EffortControl`
-- 2022-09-15T06:22:28.362Z
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542008 AND AD_Tree_ID=10
;

-- Reordering children of `Service delivery`
-- Node name: `S_EffortControl`
-- 2022-09-15T06:23:18.027Z
UPDATE AD_TreeNodeMM SET Parent_ID=541428, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542008 AND AD_Tree_ID=10
;

-- Node name: `Github config (S_GithubConfig)`
-- 2022-09-15T06:23:18.028Z
UPDATE AD_TreeNodeMM SET Parent_ID=541428, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541465 AND AD_Tree_ID=10
;


-- Node name: `Time Booking (S_TimeBooking)`
-- 2022-09-15T06:23:18.028Z
UPDATE AD_TreeNodeMM SET Parent_ID=541428, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541460 AND AD_Tree_ID=10
;

-- Node name: `Failed time booking (S_FailedTimeBooking)`
-- 2022-09-15T06:23:18.029Z
UPDATE AD_TreeNodeMM SET Parent_ID=541428, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541459 AND AD_Tree_ID=10
;

-- Node name: `Import Everhour time bookings (de.metas.serviceprovider.everhour.EverhourImportProcess)`
-- 2022-09-15T06:23:18.029Z
UPDATE AD_TreeNodeMM SET Parent_ID=541428, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541458 AND AD_Tree_ID=10
;

-- Node name: `Effort issue (S_Issue)`
-- 2022-09-15T06:23:18.030Z
UPDATE AD_TreeNodeMM SET Parent_ID=541428, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541451 AND AD_Tree_ID=10
;

-- Node name: `External project reference (S_ExternalProjectReference)`
-- 2022-09-15T06:23:18.031Z
UPDATE AD_TreeNodeMM SET Parent_ID=541428, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541450 AND AD_Tree_ID=10
;

-- Node name: `Milestone (S_Milestone)`
-- 2022-09-15T06:23:18.031Z
UPDATE AD_TreeNodeMM SET Parent_ID=541428, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541431 AND AD_Tree_ID=10
;

-- Node name: `Import Github issues (de.metas.serviceprovider.github.GithubImportProcess)`
-- 2022-09-15T06:23:18.032Z
UPDATE AD_TreeNodeMM SET Parent_ID=541428, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541449 AND AD_Tree_ID=10
;

-- Node name: `Budget Issue (S_Issue)`
-- 2022-09-15T06:23:18.032Z
UPDATE AD_TreeNodeMM SET Parent_ID=541428, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541453 AND AD_Tree_ID=10
;

