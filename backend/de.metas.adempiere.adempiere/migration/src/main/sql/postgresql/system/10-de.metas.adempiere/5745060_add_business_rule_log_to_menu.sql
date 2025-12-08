-- Window: Business Rule Log, InternalName=businessRuleLog
-- Window: Business Rule Log, InternalName=businessRuleLog
-- 2025-01-28T11:29:47.833Z
UPDATE AD_Window SET InternalName='businessRuleLog',Updated=TO_TIMESTAMP('2025-01-28 11:29:47.830000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=541840
;

-- Name: Business Rule Log
-- Action Type: W
-- Window: Business Rule Log(541840,D)
-- 2025-01-28T11:29:57.332Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583419,542195,0,541840,TO_TIMESTAMP('2025-01-28 11:29:57.167000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','businessRuleLog','Y','N','N','N','N','Business Rule Log',TO_TIMESTAMP('2025-01-28 11:29:57.167000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-01-28T11:29:57.335Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542195 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-01-28T11:29:57.338Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542195, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542195)
;

-- 2025-01-28T11:29:57.357Z
/* DDL */  select update_menu_translation_from_ad_element(583419) 
;

-- Reordering children of `Business Rules`
-- Node name: `Business Rule (AD_BusinessRule)`
-- 2025-01-28T11:29:57.942Z
UPDATE AD_TreeNodeMM SET Parent_ID=542187, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542188 AND AD_Tree_ID=10
;

-- Node name: `Business Rule Event (AD_BusinessRule_Event)`
-- 2025-01-28T11:29:57.943Z
UPDATE AD_TreeNodeMM SET Parent_ID=542187, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542189 AND AD_Tree_ID=10
;

-- Node name: `Business Rule Log`
-- 2025-01-28T11:29:57.944Z
UPDATE AD_TreeNodeMM SET Parent_ID=542187, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542195 AND AD_Tree_ID=10
;

