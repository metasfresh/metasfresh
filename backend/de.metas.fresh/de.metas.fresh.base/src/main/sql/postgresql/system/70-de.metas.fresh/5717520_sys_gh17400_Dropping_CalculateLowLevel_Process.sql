-- 2024-02-16T14:41:13.475Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=53036
;

-- 2024-02-16T14:41:13.483Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=53036
;

-- 2024-02-16T14:41:13.486Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=53036 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

DELETE
FROM AD_Wf_Node
WHERE AD_Process_ID = 53012
;

-- 2024-02-16T14:43:16.209Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=53012
;

-- 2024-02-16T14:43:16.213Z
DELETE FROM AD_Process WHERE AD_Process_ID=53012
;

