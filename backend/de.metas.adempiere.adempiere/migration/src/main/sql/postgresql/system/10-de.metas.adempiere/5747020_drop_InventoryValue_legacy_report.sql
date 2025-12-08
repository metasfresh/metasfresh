-- Name: Bestandsbewertung
-- Action Type: R
-- Report: InventoryValue(org.compiere.process.InventoryValue)
-- 2025-02-17T20:10:13.638Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=311
;

-- 2025-02-17T20:10:13.643Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=311
;

-- 2025-02-17T20:10:13.647Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=311 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2025-02-17T20:10:28.883Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=574964
;

-- 2025-02-17T20:10:28.888Z
DELETE FROM AD_Element WHERE AD_Element_ID=574964
;

-- Value: InventoryValue
-- Classname: org.compiere.process.InventoryValue
-- 2025-02-17T20:11:00.247Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=180
;

-- 2025-02-17T20:11:00.253Z
DELETE FROM AD_Process WHERE AD_Process_ID=180
;

-- Table: T_InventoryValue
-- 2025-02-17T20:12:08.117Z
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=478
;

-- 2025-02-17T20:12:08.123Z
DELETE FROM AD_Table WHERE AD_Table_ID=478
;

drop table if exists T_InventoryValue;

delete from ad_sequence where name='T_InventoryValue';

drop SEQUENCE if EXISTS T_InventoryValue_seq;

