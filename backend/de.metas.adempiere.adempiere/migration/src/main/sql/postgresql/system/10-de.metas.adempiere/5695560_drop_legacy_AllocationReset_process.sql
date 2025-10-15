-- Name: Reset Allocation
-- Action Type: P
-- Process: C_Allocation_Reset(org.compiere.process.AllocationReset)
-- 2023-07-12T14:19:08.387Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=496
;

-- 2023-07-12T14:19:08.400Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=496
;

-- 2023-07-12T14:19:08.403Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=496 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: C_Allocation_Reset
-- Classname: org.compiere.process.AllocationReset
-- 2023-07-12T14:19:19.252Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=303
;

-- 2023-07-12T14:19:19.261Z
DELETE FROM AD_Process WHERE AD_Process_ID=303
;

