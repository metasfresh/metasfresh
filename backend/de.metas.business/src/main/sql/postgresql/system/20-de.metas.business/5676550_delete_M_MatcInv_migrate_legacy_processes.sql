-- Name: M_MatchInv_CreateMissing
-- Action Type: P
-- Process: M_MatchInv_CreateMissing(de.metas.fresh.invoice.migrateMatchInv.process.M_MatchInv_CreateMissing)
-- 2023-02-10T11:37:14.676Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=540603
;

-- 2023-02-10T11:37:14.684Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=540603
;

-- 2023-02-10T11:37:14.687Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=540603 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Name: M_MatchInv_RecreateForInOutLine
-- Action Type: P
-- Process: M_MatchInv_RecreateForInOutLine(de.metas.spavetti.invoice.migrateMatchInv.process.M_MatchInv_RecreateForInOutLine)
-- 2023-02-10T11:37:24.472Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=540640
;

-- 2023-02-10T11:37:24.480Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=540640
;

-- 2023-02-10T11:37:24.482Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=540640 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Name: M_MatchInv_RecreateForInvoiceLine
-- Action Type: P
-- Process: M_MatchInv_RecreateForInvoiceLine(de.metas.spavetti.invoice.migrateMatchInv.process.M_MatchInv_RecreateForInvoiceLine)
-- 2023-02-10T11:37:37.829Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=540639
;

-- 2023-02-10T11:37:37.835Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=540639
;

-- 2023-02-10T11:37:37.839Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=540639 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: M_MatchInv_RecreateForInvoiceLine
-- Classname: de.metas.spavetti.invoice.migrateMatchInv.process.M_MatchInv_RecreateForInvoiceLine
-- 2023-02-10T11:37:48.839Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=540596
;

-- 2023-02-10T11:37:48.844Z
DELETE FROM AD_Process WHERE AD_Process_ID=540596
;

-- Value: M_MatchInv_RecreateForInOutLine
-- Classname: de.metas.spavetti.invoice.migrateMatchInv.process.M_MatchInv_RecreateForInOutLine
-- 2023-02-10T11:37:50.324Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=540597
;

-- 2023-02-10T11:37:50.331Z
DELETE FROM AD_Process WHERE AD_Process_ID=540597
;

-- Value: M_MatchInv_CreateMissing
-- Classname: de.metas.fresh.invoice.migrateMatchInv.process.M_MatchInv_CreateMissing
-- 2023-02-10T11:37:51.766Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=540552
;

-- 2023-02-10T11:37:51.773Z
DELETE FROM AD_Process WHERE AD_Process_ID=540552
;

