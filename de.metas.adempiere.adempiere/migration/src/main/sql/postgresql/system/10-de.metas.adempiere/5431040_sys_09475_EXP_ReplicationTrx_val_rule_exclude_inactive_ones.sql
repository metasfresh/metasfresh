

--
-- exclude inactive transactions
--
-- 22.10.2015 07:02
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='exists (
	select 1 from EXP_ReplicationTrxLine
	where EXP_ReplicationTrxLine.EXP_ReplicationTrx_ID=EXP_ReplicationTrx.EXP_ReplicationTrx_ID
		and EXP_ReplicationTrxLine.IsActive=''Y''
		and EXP_ReplicationTrxLine.AD_Table_ID=(select AD_Table_ID from AD_Table where TableName=''C_OLCand'') 		
		and EXP_ReplicationTrxLine.ReplicationTrxStatus=''ImportedWithIssues''
)
AND EXP_ReplicationTrx.IsActive=''Y''',Updated=TO_TIMESTAMP('2015-10-22 07:02:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540222
;

--
-- Move the replication-trx window to the remplication-folder
--
-- 22.10.2015 07:04
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=157, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540194 AND AD_Tree_ID=10
;

-- 22.10.2015 07:04
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=157, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=159 AND AD_Tree_ID=10
;

-- 22.10.2015 07:04
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=157, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=163 AND AD_Tree_ID=10
;

-- 22.10.2015 07:04
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=157, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53098 AND AD_Tree_ID=10
;

-- 22.10.2015 07:04
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=157, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540528 AND AD_Tree_ID=10
;

-- 22.10.2015 07:04
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=157, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53247 AND AD_Tree_ID=10
;

-- 22.10.2015 07:04
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=157, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540457 AND AD_Tree_ID=10
;

-- 22.10.2015 07:04
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=157, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540610 AND AD_Tree_ID=10
;

-- 22.10.2015 07:04
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540528 AND AD_Tree_ID=10
;

-- 22.10.2015 07:04
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53099 AND AD_Tree_ID=10
;

-- 22.10.2015 07:04
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=385 AND AD_Tree_ID=10
;

-- 22.10.2015 07:04
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=386 AND AD_Tree_ID=10
;

-- 22.10.2015 07:04
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53100 AND AD_Tree_ID=10
;

-- 22.10.2015 07:04
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53125 AND AD_Tree_ID=10
;

-- 22.10.2015 07:04
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53130 AND AD_Tree_ID=10
;

-- 22.10.2015 07:04
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53131 AND AD_Tree_ID=10
;

-- 22.10.2015 07:04
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53102 AND AD_Tree_ID=10
;

-- 22.10.2015 07:04
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- 22.10.2015 07:04
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53104 AND AD_Tree_ID=10
;

-- 22.10.2015 07:04
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=53098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

