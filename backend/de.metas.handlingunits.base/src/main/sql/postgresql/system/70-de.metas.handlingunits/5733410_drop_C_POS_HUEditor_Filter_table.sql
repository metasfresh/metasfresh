-- Name: POS HU Editor Filter
-- Action Type: W
-- Window: POS HU Editor Filter(540257,de.metas.handlingunits)
-- 2024-09-13T15:18:30.323Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=540600
;

-- 2024-09-13T15:18:30.329Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=540600
;

-- 2024-09-13T15:18:30.336Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=540600 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Table: C_POS_HUEditor_Filter
-- Table: C_POS_HUEditor_Filter
-- 2024-09-13T15:18:38.353Z
UPDATE AD_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2024-09-13 18:18:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540647
;

-- Window: POS HU Editor Filter, InternalName=null
-- Window: POS HU Editor Filter, InternalName=null
-- 2024-09-13T15:18:43.328Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540257
;

-- 2024-09-13T15:18:43.331Z
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=540257
;

-- 2024-09-13T15:18:43.335Z
DELETE FROM AD_Window WHERE AD_Window_ID=540257
;

-- Table: C_POS_HUEditor_Filter
-- Table: C_POS_HUEditor_Filter
-- 2024-09-13T15:18:56.126Z
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=540647
;

-- 2024-09-13T15:18:56.142Z
DELETE FROM AD_Table WHERE AD_Table_ID=540647
;

-- 2024-09-13T15:19:13.584Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=542705
;

-- 2024-09-13T15:19:13.589Z
DELETE FROM AD_Element WHERE AD_Element_ID=542705
;

delete from ad_sequence where name='C_POS_HUEditor_Filter';
drop sequence if exists c_pos_hueditor_filter_seq;
drop table if exists C_POS_HUEditor_Filter;

