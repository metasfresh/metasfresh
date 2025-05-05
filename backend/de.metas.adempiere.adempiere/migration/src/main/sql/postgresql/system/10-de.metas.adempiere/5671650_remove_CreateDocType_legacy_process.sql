-- Name: Create Doc Type to Manufacturing
-- Action Type: P
-- Process: PP_Create DocType(org.eevolution.process.CreateDocType)
-- 2023-01-13T11:39:05.191Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=53075
;

-- 2023-01-13T11:39:05.219Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=53075
;

-- 2023-01-13T11:39:05.223Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=53075 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: PP_Create DocType
-- Classname: org.eevolution.process.CreateDocType
-- 2023-01-13T11:39:11.903Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=53050
;

-- 2023-01-13T11:39:11.911Z
DELETE FROM AD_Process WHERE AD_Process_ID=53050
;

