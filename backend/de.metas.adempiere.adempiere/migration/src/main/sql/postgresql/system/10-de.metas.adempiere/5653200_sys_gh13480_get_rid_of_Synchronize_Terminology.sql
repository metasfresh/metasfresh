-- Name: Synchronize Terminology
-- Action Type: P
-- Process: AD_Synchronize(org.compiere.process.SynchronizeTerminology)
-- 2022-08-25T16:19:31.719Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=303
;

-- 2022-08-25T16:19:31.945Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=303
;

-- 2022-08-25T16:19:31.999Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=303 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: AD_Synchronize
-- Classname: org.compiere.process.SynchronizeTerminology
-- 2022-08-25T16:19:43.728Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=172
;

-- 2022-08-25T16:19:43.939Z
DELETE FROM AD_Process WHERE AD_Process_ID=172
;

