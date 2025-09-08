-- Name: Invoice Not Realized Gain/Loss
-- Action Type: R
-- Report: T_InvoiceNGL(org.compiere.process.InvoiceNGL)
-- 2022-12-15T12:43:39.715Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=538
;

-- 2022-12-15T12:43:39.756Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=538
;

-- 2022-12-15T12:43:39.762Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=538 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: T_InvoiceNGL
-- Classname: org.compiere.process.InvoiceNGL
-- 2022-12-15T12:44:13.567Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=326
;

-- 2022-12-15T12:44:13.602Z
DELETE FROM AD_Process WHERE AD_Process_ID=326
;

