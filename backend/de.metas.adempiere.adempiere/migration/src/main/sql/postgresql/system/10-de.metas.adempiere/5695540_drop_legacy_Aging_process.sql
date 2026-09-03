-- Name: Aging
-- Action Type: R
-- Report: RV_T_Aging(org.compiere.process.Aging)
-- 2023-07-12T14:02:40.065Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=413
;

-- 2023-07-12T14:02:40.072Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=413
;

-- 2023-07-12T14:02:40.074Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=413 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: RV_T_Aging
-- Classname: org.compiere.process.Aging
-- 2023-07-12T14:02:46.503Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=238
;

-- 2023-07-12T14:02:46.513Z
DELETE FROM AD_Process WHERE AD_Process_ID=238
;

