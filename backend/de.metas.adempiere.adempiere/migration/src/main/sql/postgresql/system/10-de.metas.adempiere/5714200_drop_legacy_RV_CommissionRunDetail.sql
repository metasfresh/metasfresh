-- Run mode: SWING_CLIENT

-- Name: Kommissions-Lauf - Details
-- Action Type: R
-- Report: RV_CommissionRunDetail
-- 2023-12-20T19:29:56.359Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=513
;

-- 2023-12-20T19:29:56.369Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=513
;

-- 2023-12-20T19:29:56.372Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=513 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: RV_CommissionRunDetail
-- 2023-12-20T19:30:01.092Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=320
;

-- 2023-12-20T19:30:01.104Z
DELETE FROM AD_Process WHERE AD_Process_ID=320
;

