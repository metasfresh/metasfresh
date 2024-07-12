-- Process: PP_Order_Candidate_CreateMaturingCandidates(org.eevolution.productioncandidate.process.PP_Order_Candidate_CreateMaturingCandidates)
-- 2024-01-26T12:34:41.437Z
UPDATE AD_Process_Trl SET Name='Aktualisierung - Reifende Kandidaten',Updated=TO_TIMESTAMP('2024-01-26 14:34:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585349
;

-- Value: PP_Order_Candidate_CreateMaturingCandidates
-- Classname: org.eevolution.productioncandidate.process.PP_Order_Candidate_CreateMaturingCandidates
-- 2024-01-26T12:34:43.933Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Aktualisierung - Reifende Kandidaten',Updated=TO_TIMESTAMP('2024-01-26 14:34:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585349
;

-- Process: PP_Order_Candidate_CreateMaturingCandidates(org.eevolution.productioncandidate.process.PP_Order_Candidate_CreateMaturingCandidates)
-- 2024-01-26T12:34:43.894Z
UPDATE AD_Process_Trl SET Name='Aktualisierung - Reifende Kandidaten',Updated=TO_TIMESTAMP('2024-01-26 14:34:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585349
;

-- Name: Reifedisposition
-- Action Type: W
-- Window: Reifedisposition(541756,EE01)
-- 2024-01-26T12:37:41.917Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=542134
;

-- 2024-01-26T12:37:41.921Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=542134
;

-- 2024-01-26T12:37:41.924Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=542134 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Reordering children of `Manufacturing`
-- Node name: `Maturing candidate (PP_Order_Candidate)`
-- 2024-01-26T12:38:37.630Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542134 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing candidate (PP_Order_Candidate)`
-- 2024-01-26T12:38:37.631Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541831 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Order (PP_Order)`
-- 2024-01-26T12:38:37.632Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000081 AND AD_Tree_ID=10
;

-- Node name: `Cost Collector (PP_Cost_Collector)`
-- 2024-01-26T12:38:37.633Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541397 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2024-01-26T12:38:37.634Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000055 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2024-01-26T12:38:37.635Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000063 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2024-01-26T12:38:37.635Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000071 AND AD_Tree_ID=10
;

