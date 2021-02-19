-- 2021-02-18T01:49:17.123Z
-- URL zum Konzept
UPDATE AD_Column SET TechnicalNote='!!! Note that this column is only relevant when doing quickentry *in the swing client* !!!',Updated=TO_TIMESTAMP('2021-02-18 02:49:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=505000
;


-- 2021-02-18T09:07:41.649Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540610 AND AD_Tree_ID=10
;

-- 2021-02-18T09:07:41.691Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541054 AND AD_Tree_ID=10
;

-- 2021-02-18T09:07:41.722Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541096 AND AD_Tree_ID=10
;

-- 2021-02-18T09:07:41.756Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541068 AND AD_Tree_ID=10
;

-- 2021-02-18T09:07:41.782Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540794 AND AD_Tree_ID=10
;

-- 2021-02-18T09:07:41.804Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541101 AND AD_Tree_ID=10
;

-- 2021-02-18T09:07:41.827Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541040 AND AD_Tree_ID=10
;

-- 2021-02-18T09:07:41.851Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000096 AND AD_Tree_ID=10
;

-- 2021-02-18T09:07:41.879Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541130 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:39.562Z
-- URL zum Konzept
INSERT INTO AD_Menu (AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,167,541598,0,TO_TIMESTAMP('2021-02-18 10:08:39','YYYY-MM-DD HH24:MI:SS'),100,'D','M_Attribute_Folder','Y','N','N','N','Y','Merkmal',TO_TIMESTAMP('2021-02-18 10:08:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T09:08:39.869Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541598 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2021-02-18T09:08:39.893Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541598, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541598)
;

-- 2021-02-18T09:08:39.968Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(167) 
;

-- 2021-02-18T09:08:49.883Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541293 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:49.908Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000034 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:49.930Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541391 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:49.952Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541468 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:49.973Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000089 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:49.993Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541408 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:50.018Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540840 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:50.057Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540990 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:50.086Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:50.111Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541376 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:50.136Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000093 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:50.160Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541230 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:50.194Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541062 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:50.215Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541115 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:50.239Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541116 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:50.262Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541126 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:50.290Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541127 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:50.314Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541194 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:50.340Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541411 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:50.362Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000044 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:50.387Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000035 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:50.411Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000037 AND AD_Tree_ID=10
;

-- 2021-02-18T09:08:50.433Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541598 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:00.176Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540610 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:00.198Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541598 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:00.220Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541054 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:00.245Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541096 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:00.265Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541068 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:00.287Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540794 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:00.325Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541101 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:00.349Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541040 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:00.391Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000096 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:00.412Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541130 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:10.815Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541598, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540990 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:18.910Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541598, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540840 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:18.937Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541598, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540990 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:21.871Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541598, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541408 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:21.893Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541598, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540840 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:21.914Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541598, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540990 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:25.182Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541598, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000089 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:25.209Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541598, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541408 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:25.237Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541598, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540840 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:25.257Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541598, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540990 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:34.682Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541598 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:34.703Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541054 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:34.741Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540610 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:34.763Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541096 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:34.785Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541068 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:34.809Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540794 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:34.837Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541101 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:34.861Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541040 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:34.894Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000096 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:34.918Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541130 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:36.934Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541598 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:36.959Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540610 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:36.991Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541054 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:37.015Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541096 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:37.041Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541068 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:37.068Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540794 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:37.097Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541101 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:37.132Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541040 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:37.167Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000096 AND AD_Tree_ID=10
;

-- 2021-02-18T09:09:37.192Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541130 AND AD_Tree_ID=10
;

