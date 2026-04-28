-- Window: Mobile Application Action Access, InternalName=mobileApplicationActionAccess
-- 2025-09-25T16:09:31.734Z
UPDATE AD_Window SET InternalName='mobileApplicationActionAccess',Updated=TO_TIMESTAMP('2025-09-25 16:09:31.731000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=541950
;

-- Name: Mobile Application Action Access
-- Action Type: W
-- Window: Mobile Application Action Access(541950,D)
-- 2025-09-25T16:09:37.962Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,584032,542251,0,541950,TO_TIMESTAMP('2025-09-25 16:09:37.733000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','mobileApplicationActionAccess','Y','N','N','N','N','Mobile Application Action Access',TO_TIMESTAMP('2025-09-25 16:09:37.733000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-25T16:09:37.965Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542251 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-09-25T16:09:37.968Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542251, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542251)
;

-- 2025-09-25T16:09:37.992Z
/* DDL */  select update_menu_translation_from_ad_element(584032)
;

-- Reordering children of `Mobile`
-- Node name: `Mobile Configuration (MobileConfiguration)`
-- 2025-09-25T16:09:38.581Z
UPDATE AD_TreeNodeMM SET Parent_ID=542179, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542136 AND AD_Tree_ID=10
;

-- Node name: `Mobile Application (Mobile_Application)`
-- 2025-09-25T16:09:38.582Z
UPDATE AD_TreeNodeMM SET Parent_ID=542179, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542180 AND AD_Tree_ID=10
;

-- Node name: `Mobile Application Role Access (Mobile_Application_Access)`
-- 2025-09-25T16:09:38.583Z
UPDATE AD_TreeNodeMM SET Parent_ID=542179, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542181 AND AD_Tree_ID=10
;

-- Node name: `Mobile Application Action Access`
-- 2025-09-25T16:09:38.584Z
UPDATE AD_TreeNodeMM SET Parent_ID=542179, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542251 AND AD_Tree_ID=10
;

