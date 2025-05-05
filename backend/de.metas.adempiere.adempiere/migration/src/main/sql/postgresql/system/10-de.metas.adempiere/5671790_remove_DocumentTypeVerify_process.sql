-- Name: Verify Document Types
-- Action Type: P
-- Process: C_DocumentType Verify(org.compiere.process.DocumentTypeVerify)
-- 2023-01-13T16:11:43.676Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=541542
;

-- 2023-01-13T16:11:43.694Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=541542
;

-- 2023-01-13T16:11:43.697Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=541542 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;



-- Name: Verify Document Types
-- Action Type: P
-- Process: C_DocumentType Verify(org.compiere.process.DocumentTypeVerify)
-- 2023-01-13T16:16:48.465Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=409
;

-- 2023-01-13T16:16:48.482Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=409
;

-- 2023-01-13T16:16:48.485Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=409 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: C_DocumentType Verify
-- Classname: org.compiere.process.DocumentTypeVerify
-- 2023-01-13T16:17:07.391Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=233
;

-- 2023-01-13T16:17:07.399Z
DELETE FROM AD_Process WHERE AD_Process_ID=233
;

