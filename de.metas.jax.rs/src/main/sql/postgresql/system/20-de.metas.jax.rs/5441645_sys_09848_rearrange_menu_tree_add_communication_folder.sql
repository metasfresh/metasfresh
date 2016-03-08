

-- 04.03.2016 14:14
-- URL zum Konzept
INSERT INTO AD_Menu (AD_Client_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,540691,0,TO_TIMESTAMP('2016-03-04 14:14:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Communication','Y','N','N','Y','Communication',TO_TIMESTAMP('2016-03-04 14:14:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.03.2016 14:14
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540691 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 04.03.2016 14:14
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540691, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540691)
;

-- 04.03.2016 14:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=155 AND AD_Tree_ID=10
;

-- 04.03.2016 14:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=156 AND AD_Tree_ID=10
;

-- 04.03.2016 14:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=175 AND AD_Tree_ID=10
;

-- 04.03.2016 14:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=157 AND AD_Tree_ID=10
;

-- 04.03.2016 14:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=552 AND AD_Tree_ID=10
;

-- 04.03.2016 14:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540537 AND AD_Tree_ID=10
;

-- 04.03.2016 14:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540603 AND AD_Tree_ID=10
;

-- 04.03.2016 14:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540639 AND AD_Tree_ID=10
;

-- 04.03.2016 14:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540640 AND AD_Tree_ID=10
;

-- 04.03.2016 14:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540645 AND AD_Tree_ID=10
;

-- 04.03.2016 14:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540691 AND AD_Tree_ID=10
;

-- 04.03.2016 14:14
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,540692,0,540287,TO_TIMESTAMP('2016-03-04 14:14:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.jax.rs','JAX-RS-Endpoint','Y','N','N','N','JAX-RS Endpoint',TO_TIMESTAMP('2016-03-04 14:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.03.2016 14:14
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540692 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 04.03.2016 14:14
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540692, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540692)
;

-- 04.03.2016 14:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53053, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53054 AND AD_Tree_ID=10
;

-- 04.03.2016 14:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53053, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53055 AND AD_Tree_ID=10
;

-- 04.03.2016 14:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53053, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53056 AND AD_Tree_ID=10
;

-- 04.03.2016 14:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53053, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53057 AND AD_Tree_ID=10
;

-- 04.03.2016 14:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53053, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53058 AND AD_Tree_ID=10
;

-- 04.03.2016 14:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53053, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53059 AND AD_Tree_ID=10
;

-- 04.03.2016 14:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53053, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53060 AND AD_Tree_ID=10
;

-- 04.03.2016 14:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53053, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=194 AND AD_Tree_ID=10
;

-- 04.03.2016 14:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53053, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540692 AND AD_Tree_ID=10
;

-- 04.03.2016 14:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540691, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540692 AND AD_Tree_ID=10
;

-- 04.03.2016 14:15
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540691, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53098 AND AD_Tree_ID=10
;

-- 04.03.2016 14:15
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540691, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540692 AND AD_Tree_ID=10
;

-- 04.03.2016 14:15
-- URL zum Konzept
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=53247
;

-- 04.03.2016 14:15
-- URL zum Konzept
DELETE FROM AD_Menu WHERE AD_Menu_ID=53247
;

-- 04.03.2016 14:15
-- URL zum Konzept
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=53247 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 04.03.2016 14:15
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540691, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53098 AND AD_Tree_ID=10
;

-- 04.03.2016 14:15
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540691, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540194 AND AD_Tree_ID=10
;

-- 04.03.2016 14:15
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540691, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540692 AND AD_Tree_ID=10
;

